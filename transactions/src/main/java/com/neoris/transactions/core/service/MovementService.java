package com.neoris.transactions.core.service;

import com.neoris.transactions.client.connector.IClientConnector;
import com.neoris.transactions.client.entity.AccountEntity;
import com.neoris.transactions.client.entity.MovementEntity;
import com.neoris.transactions.client.exception.ExistException;
import com.neoris.transactions.client.exception.NotFoundException;
import com.neoris.transactions.client.exception.PersistException;
import com.neoris.transactions.client.mapper.MovementMapper;
import com.neoris.transactions.client.repository.IMovementRepository;
import com.neoris.transactions.client.service.IAccountService;
import com.neoris.transactions.client.service.IMovementService;
import com.neoris.transactions.vo.AccountVo;
import com.neoris.transactions.vo.ClientVo;
import com.neoris.transactions.vo.DateRangeVo;
import com.neoris.transactions.vo.MovementVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.neoris.transactions.client.common.TransactionsConstants.DEBIT;
import static com.neoris.transactions.client.common.TransactionsConstants.DEPOSIT;

/**
 * @author jyepez on 27/10/2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MovementService implements IMovementService {

    private final IMovementRepository movementRepository;
    private final MovementMapper movementMapper;
    private final IAccountService accountService;
    private final IClientConnector clientConnector;

    @Override
    public Collection<MovementVo> findAll() {
        List<MovementEntity> movementsList = movementRepository.findAll();
        return this.movementMapper.toMovementVoCollection(movementsList);
    }

    @Override
    public Collection<MovementVo> findByAccountNumber(String accountNumber) {
        Collection<MovementEntity> movements = this.movementRepository
                .findAllByAccountAccountNumberAndAccountStatus(accountNumber, Boolean.TRUE);
        AccountVo accountVo =this.accountService.findByAccountNumberExt(accountNumber);
        Collection<MovementVo> movementVos = this.movementMapper.toMovementVoCollection(movements);
        movementVos.forEach(item -> item.setName(accountVo.getNameClient()));
        return movementVos;
    }

    @Override
    public Collection<MovementVo> findByAccountNumberInRange(String accountNumber, DateRangeVo dateRange) {
        Collection<MovementEntity> movements = this.movementRepository
                .findByAccountNumberAndDateRange(accountNumber, dateRange.getStart(), dateRange.getEnd());
        return this.movementMapper.toMovementVoCollection(movements);
    }

    @Override
    public MovementEntity findByID(Integer movementID) {
        return movementRepository.findById(movementID)
                .orElseThrow(() -> new NotFoundException("No movement found with the number "+movementID));
    }

    @Override
    public MovementVo create(MovementVo movementVo) {
        AccountEntity account =this.accountService.findByAccountNumber(movementVo.getAccountNumber());
        try {
            MovementEntity movement = this.movementMapper.toMovement(movementVo);
            movement.setDate(LocalDateTime.now());
            movement.setMovementType(movementVo.getAmount() > 0 ? DEPOSIT : DEBIT);
            movement.setBalance(calculateBalance(movementVo));
            movement.setAccount(account);
            MovementEntity movementSaved = this.movementRepository.save(movement);
            return buildMovementResponse(movementSaved);
        }catch (ExistException e){
            throw new ExistException(e.getMessage());
        }catch (Exception e){
            throw new PersistException("A problem occurred, movement could not be saved");
        }
    }

    @Override
    public MovementVo updateAmount(Integer movementID, Double amount) {
        MovementEntity existingMovement = findByID(movementID);
        try {
            existingMovement.setAmount(amount);
            MovementEntity movementUpdated = this.movementRepository.save(existingMovement);
            return buildMovementResponse(movementUpdated);
        }catch (Exception e){
            throw new PersistException("A problem occurred, the movement could not be updated");
        }
    }

    @Override
    public void delete(Integer movementID) {
        MovementEntity existingMovement = findByID(movementID);
        try {
            existingMovement.setStatus(Boolean.FALSE);
            MovementEntity lastMovement = getLastMovement(existingMovement.getAccount().getAccountNumber());
            if(Objects.nonNull(lastMovement)){
                double newBalance = lastMovement.getBalance() + (existingMovement.getAmount() * -1);
                lastMovement.setBalance(newBalance);
                this.movementRepository.save(lastMovement);
            }
            this.movementRepository.save(existingMovement);
        }catch (Exception e){
            throw new PersistException("A problem occurred, movement could not be deleted");
        }
    }

    private MovementVo buildMovementResponse(MovementEntity movement){
        ClientVo client = this.clientConnector.getByID(String.valueOf(movement.getAccount().getClientId()));
        MovementVo movementResponse = this.movementMapper.toMovementVo(movement);
        movementResponse.setName(client.getName());
        return  movementResponse;
    }

    private Double calculateBalance(MovementVo movementVo){
        MovementEntity movement = getLastMovement(movementVo.getAccountNumber());
        if(Objects.isNull(movement)){
            AccountEntity account = this.accountService.findByAccountNumber(movementVo.getAccountNumber());
            validateFounds(account.getInitialBalance() , movementVo.getAmount());
            return account.getInitialBalance() + movementVo.getAmount();
        }else{
            validateFounds(movement.getBalance() , movementVo.getAmount());
            return movement.getBalance() + movementVo.getAmount();
        }
    }

    private void validateFounds(Double balance, Double amount){
        if(amount ==0){
            throw  new ExistException("A move cannot be made with a quantity of 0");
        }
        if(amount < 0){
            amount = amount*-1;
            if(amount > balance){
                throw  new ExistException("Balance not available");
            }
        }
    }

    private MovementEntity getLastMovement(String accountNumber){
        return this.movementRepository.findTopByAccountAccountNumberAndStatusIsTrueOrderByDateDesc(accountNumber)
                .orElse(null);
    }
}
