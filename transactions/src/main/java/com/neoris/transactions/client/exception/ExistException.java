package com.neoris.transactions.client.exception;

import lombok.Getter;

/**
 * @author jyepez on 19/7/2024
 */
@Getter
public class ExistException extends RuntimeException {

    public ExistException(){

    }

    public ExistException(String message) {
        super(message);
    }
}
