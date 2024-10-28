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
public class AccountVo {

    private String accountNumber;
    @NotEmpty(message = "Account type is required")
    private String accountType;
    @NotNull(message = "Initial balance is required")
    private Double initialBalance;
    private Boolean status;
    @NotEmpty(message = "Identification is required")
    private String identification;
    private String nameClient;
    private Integer clientId;
    @NotEmpty(message = "User who creates the record is required")
    private String createdBy;

}
