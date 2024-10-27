package com.neoris.transactions.client.exception;

import lombok.Getter;

/**
 * @author jyepez on 19/7/2024
 */
@Getter
public class NotFoundException extends RuntimeException  {

    public NotFoundException(String message) {
        super(message);
    }
}
