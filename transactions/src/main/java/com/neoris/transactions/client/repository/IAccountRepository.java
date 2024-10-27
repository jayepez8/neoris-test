package com.neoris.transactions.client.repository;

import com.neoris.transactions.client.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 27/10/2024
 */
@Repository
public interface IAccountRepository extends JpaRepository<AccountEntity,Integer> {

    Boolean existsByAccountNumber(String accountNumber);

    Optional<AccountEntity> findByAccountNumberAndStatusIsTrue(String accountNumber);

    Collection<AccountEntity> findAllByClientIdAndStatusIsTrue(Integer clientID);
}
