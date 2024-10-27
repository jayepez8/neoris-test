package com.neoris.transactions.client.exception;

import lombok.Getter;

/**
 * @author jyepez on 19/7/2024
 */
@Getter
public class PersistException extends RuntimeException {

    public PersistException(String message) {
        super(message);
    }
}
