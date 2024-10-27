package com.neoris.transactions.core.service;

import com.neoris.transactions.client.common.DateUtil;
import com.neoris.transactions.client.connector.IClientConnector;
import com.neoris.transactions.client.entity.AccountEntity;
import com.neoris.transactions.client.exception.ExistException;
import com.neoris.transactions.client.service.IAccountService;
import com.neoris.transactions.client.service.IMovementService;
import com.neoris.transactions.client.service.IReportService;
import com.neoris.transactions.vo.AccountStatusVo;
import com.neoris.transactions.vo.ClientVo;
import com.neoris.transactions.vo.DateRangeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author jyepez on 27/10/2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService implements IReportService {

    private final IClientConnector clientConnector;
    private final IAccountService accountService;
    private final IMovementService movementService;

    @Override
    public DateRangeVo buildDateRangeVo(String startDate, String endDate) {
        DateUtil.validatedDates(startDate,endDate);
        return DateRangeVo.builder()
                .start(DateUtil.asLocalDateTime(startDate))
                .end(DateUtil.asLocalDateTime(endDate).withHour(23).withMinute(59).withSecond(59).withNano(999999999))
                .build();
    }

    @Override
    public Collection<AccountStatusVo> accountStatus(DateRangeVo dateRange, String identification) {
        try {
            ClientVo client = this.clientConnector.getByIdentification(identification);
            Collection<AccountEntity> accounts = this.accountService.findAllByClientID(client.getClientID());
            return accounts.stream()
                    .map(item -> AccountStatusVo.builder()
                            .accountNumber(item.getAccountNumber())
                            .accountType(item.getAccountType())
                            .initialBalance(item.getInitialBalance())
                            .nameClient(client.getName())
                            .movements(this.movementService.findByAccountNumberInRange(item.getAccountNumber(),dateRange))
                            .build())
                    .collect(Collectors.toList());
        }catch (ExistException e){
            throw new ExistException("Cannot obtain report because the client does not exist");
        }
    }
}
