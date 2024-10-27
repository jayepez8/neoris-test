package com.neoris.transactions.client.service;

import com.neoris.transactions.client.entity.MovementEntity;
import com.neoris.transactions.vo.DateRangeVo;
import com.neoris.transactions.vo.MovementVo;

import java.util.Collection;

/**
 * @author jyepez on 27/10/2024
 */
public interface IMovementService {

    Collection<MovementVo> findAll();

    Collection<MovementVo> findByAccountNumber(String accountNumber);

    Collection<MovementVo> findByAccountNumberInRange(String accountNumber, DateRangeVo dateRange);

    MovementEntity findByID(Integer movementID);

    MovementVo create(MovementVo movementVo);

    MovementVo updateAmount(Integer movementID, Double amount);

    void delete(Integer movementID);
}
