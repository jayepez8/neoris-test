package com.neoris.transactions.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author jyepez on 27/10/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatusVo {

    private String accountNumber;

    private String accountType;

    private Double initialBalance;

    private String nameClient;

    private Collection<MovementVo> movements;
}
