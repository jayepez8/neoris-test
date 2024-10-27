package com.neoris.transactions.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author jyepez on 19/7/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseVo {

    private Integer code;
    private String message;
    private Collection<String> errors;
}