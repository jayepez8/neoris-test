package com.neoris.transactions.client.common;

import com.neoris.transactions.client.exception.ExistException;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static com.neoris.transactions.client.common.TransactionsConstants.FORMATTER;

/**
 * @author jyepez on 27/10/2024
 */
@Component
public final class DateUtil {

    /**
     * Constructor.
     */
    private DateUtil() {
    }

    @Named("localDateTimeToString")
    public String asString(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.format(FORMATTER);
    }

    public static LocalDateTime asLocalDateTime(String date) {
        return date == null ? null : LocalDateTime.parse(date, FORMATTER);
    }

    public static void validatedDates(String startDate, String endDate) {
        try {
            LocalDateTime start = LocalDateTime.parse(startDate, FORMATTER);
            LocalDateTime end = LocalDateTime.parse(endDate, FORMATTER);

            if (start.isAfter(end)) {
                throw new ExistException("The start date must be before or equal to the end date");
            }
        } catch (DateTimeParseException e) {
            throw new ExistException("Invalid date format. Please use dd/MM/yyyy");
        }
    }

}
