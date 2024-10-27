package com.neoris.clients.client.exception;

import lombok.Getter;

/**
 * @author jyepez on 26/10/2024
 */
@Getter
public class ExistException extends RuntimeException {

    public ExistException(String message) {
        super(message);
    }
}
