package com.neoris.transactions.client.service;

import com.neoris.transactions.client.entity.AccountEntity;
import com.neoris.transactions.vo.AccountVo;

import java.util.Collection;

/**
 * @author jyepez on 27/10/2024
 */
public interface IAccountService {

    Collection<AccountVo> findAll();

    Collection<AccountEntity> findAllByClientID(Integer clientID);

    AccountEntity findByAccountNumber(String accountNumber);

    AccountVo findByAccountNumberExt(String accountNumber);

    AccountVo create(AccountVo accountVo);

    AccountVo update(String accountNumber,AccountVo accountVo);

    AccountVo updateAccountType(String accountNumber,String accountType);

    AccountVo updateInitialBalance(String accountNumber,Double initialBalance);

    void delete(String accountNumber);

}

