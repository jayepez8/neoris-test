package com.neoris.transactions.controller;

import com.neoris.transactions.client.service.IReportService;
import com.neoris.transactions.vo.AccountStatusVo;
import com.neoris.transactions.vo.DateRangeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static com.neoris.transactions.client.common.TransactionsConstants.V1_API_VERSION;

/**
 * @author jyepez on 27/10/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("report" + V1_API_VERSION)
public class ReportController {

    private final IReportService reportService;

    @GetMapping()
    public ResponseEntity<Collection<AccountStatusVo>> accountStatus(@RequestParam String identification,
                                                                     @RequestParam String startDate,
                                                                     @RequestParam String endDate){
        DateRangeVo dateRange = this.reportService.buildDateRangeVo(startDate, endDate);
        Collection<AccountStatusVo> response = this.reportService.accountStatus(dateRange, identification);
        return ResponseEntity.ok().body(response);
    }

}

