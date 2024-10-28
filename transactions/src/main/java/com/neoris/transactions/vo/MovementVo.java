package com.neoris.transactions.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author jyepez on 27/10/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementVo {

    @NotNull(message = "Amount balance is required")
    private Double amount;

    @NotEmpty(message = "Account number is required")
    private String accountNumber;

    private Double balance;
    private String movementType;
    private String name;
    private String  date;
    private String accountType;

    @NotEmpty(message = "User who creates the record is required")
    private String createdBy;
}
