package com.neoris.transactions.core.service;

import com.neoris.transactions.client.connector.IClientConnector;
import com.neoris.transactions.client.entity.AccountEntity;
import com.neoris.transactions.client.exception.ExistException;
import com.neoris.transactions.client.exception.NotFoundException;
import com.neoris.transactions.client.exception.PersistException;
import com.neoris.transactions.client.mapper.AccountMapper;
import com.neoris.transactions.client.repository.IAccountRepository;
import com.neoris.transactions.client.service.IAccountService;
import com.neoris.transactions.vo.AccountVo;
import com.neoris.transactions.vo.ClientVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;

import static com.neoris.transactions.client.common.TransactionsConstants.ACCOUNT_PREFIX;

/**
 * @author jyepez on 27/10/2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;
    private final IClientConnector clientConnector;
    private final AccountMapper accountMapper;

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 9999;
    private final Random random = new Random();

    @Override
    public Collection<AccountVo> findAll() {
        Collection<AccountEntity> accounts = this.accountRepository.findAll();
        Collection<AccountVo> accountVos = this.accountMapper.toAccountVoCollection(accounts);
        accountVos.forEach(item-> {
            ClientVo client = this.clientConnector.getByID(String.valueOf(item.getClientId()));
            item.setNameClient(client.getName());
            item.setIdentification(client.getIdentification());
            item.setClientId(null);
        });
        return accountVos;
    }

    @Override
    public Collection<AccountEntity> findAllByClientID(Integer clientID) {
        return this.accountRepository.findAllByClientIdAndStatusIsTrue(clientID);
    }

    @Override
    public AccountEntity findByAccountNumber(String accountNumber) {
        return this.accountRepository.findByAccountNumberAndStatusIsTrue(accountNumber)
                .orElseThrow(() -> new NotFoundException("No account found with the number "+accountNumber));
    }

    @Override
    public AccountVo findByAccountNumberExt(String accountNumber) {
        AccountEntity existingAccount = findByAccountNumber(accountNumber);
        return buildAccountResponse(existingAccount,null);
    }

    @Override
    public AccountVo create(AccountVo accountVo) {
        try {
            ClientVo client = this.clientConnector.getByIdentification(accountVo.getIdentification());
            AccountEntity account = this.accountMapper.toAccount(accountVo);
            account.setClientId(client.getClientID());
            account.setAccountNumber(createAccount());
            AccountEntity accountSaved = this.accountRepository.save(account);
            return buildAccountResponse(accountSaved,client.getName());
        }catch (ExistException e){
            throw new ExistException("Cannot create account because there is no client");
        }catch (Exception e){
            throw new PersistException("A problem occurred, account could not be saved");
        }
    }

    @Override
    public AccountVo update(String accountNumber, AccountVo accountVo) {
        AccountEntity existingAccount = findByAccountNumber(accountNumber);
        try {
            existingAccount.setAccountType( accountVo.getAccountType() );
            existingAccount.setInitialBalance( accountVo.getInitialBalance() );
            existingAccount.setClientId( accountVo.getClientId() );
            existingAccount.setModifiedBy(accountVo.getCreatedBy());
            existingAccount.setModifiedDate(LocalDateTime.now());
            AccountEntity accountUpdated = this.accountRepository.save(existingAccount);
            return buildAccountResponse(accountUpdated,null);
        }catch (Exception e){
            throw new PersistException("A problem occurred, the account could not be updated");
        }
    }

    @Override
    public AccountVo updateAccountType(String accountNumber, String accountType) {
        AccountEntity existingAccount = findByAccountNumber(accountNumber);
        try {
            existingAccount.setAccountType(accountType);
            existingAccount.setModifiedDate(LocalDateTime.now());
            AccountEntity accountUpdated = this.accountRepository.save(existingAccount);
            return buildAccountResponse(accountUpdated,null);
        }catch (Exception e){
            throw new PersistException("A problem occurred, the account could not be updated");
        }
    }

    @Override
    public AccountVo updateInitialBalance(String accountNumber, Double initialBalance) {
        AccountEntity existingAccount = findByAccountNumber(accountNumber);
        try {
            existingAccount.setInitialBalance(initialBalance);
            existingAccount.setModifiedDate(LocalDateTime.now());
            AccountEntity accountUpdated = this.accountRepository.save(existingAccount);
            return buildAccountResponse(accountUpdated,null);
        }catch (Exception e){
            throw new PersistException("A problem occurred, the account could not be updated");
        }
    }

    @Override
    public void delete(String accountNumber) {
        AccountEntity existingAccount = findByAccountNumber(accountNumber);
        try {
            existingAccount.setStatus(Boolean.FALSE);
            existingAccount.setModifiedDate(LocalDateTime.now());
            this.accountRepository.save(existingAccount);
        }catch (Exception e){
            throw new PersistException("A problem occurred, account could not be deleted");
        }
    }

    private String createAccount() {
        String identifier;
        do {
            int number = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
            identifier = String.format("%s%04d", ACCOUNT_PREFIX, number);
        } while (this.accountRepository.existsByAccountNumber(identifier));
        return identifier;
    }

    private AccountVo buildAccountResponse(AccountEntity account, String name){
        AccountVo accountResponse = this.accountMapper.toAccountVo(account);
        if(Objects.isNull(name)){
            ClientVo client = this.clientConnector.getByID(String.valueOf(account.getClientId()));
            accountResponse.setNameClient(client.getName());
        }else {
            accountResponse.setNameClient(name);
        }
        accountResponse.setClientId(null);
        return accountResponse;
    }
}
