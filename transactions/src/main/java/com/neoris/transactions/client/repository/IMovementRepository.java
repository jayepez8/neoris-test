package com.neoris.transactions.client.repository;

import com.neoris.transactions.client.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 27/10/2024
 */
@Repository
public interface IMovementRepository extends JpaRepository<MovementEntity,Integer> {

    Collection<MovementEntity> findAllByAccountAccountNumberAndAccountStatus(String accountNumber, Boolean status);

    Optional<MovementEntity> findTopByAccountAccountNumberAndStatusIsTrueOrderByDateDesc(String accountNumber);

    @Query("SELECT m FROM MovementEntity m WHERE m.account.accountNumber = :accountNumber AND m.date BETWEEN :startDate AND :endDate AND m.status = true" )
    Collection<MovementEntity> findByAccountNumberAndDateRange(@Param("accountNumber") String accountNumber,
                                                               @Param("startDate") LocalDateTime startDate,
                                                               @Param("endDate") LocalDateTime endDate);

}
