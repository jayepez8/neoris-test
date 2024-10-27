package com.neoris.transactions.client.common;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * @author jyepez on 19/7/2024
 */
public class TransactionsConstants {

    public static final String V1_API_VERSION = "/api/v1";
    public static final String DOMAIN_CLIENT = "http://localhost:8081";
    public static final String URL_GET_CLIENT_BY_ID = "/client/api/v1/";
    public static final String URL_GET_CLIENT_BY_IDENTIFICATION = "/client/api/v1/findByIdentificationExt?identification=";
    public static final String ACCOUNT_PREFIX = "ACC";
    public static final String DEBIT = "Withdrawal";
    public static final String DEPOSIT = "Deposit";
    public static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd/MM/yyyy")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();


    private TransactionsConstants(){

    }
}
