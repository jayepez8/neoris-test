package com.neoris.transactions.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author jyepez on 27/10/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateRangeVo {

    LocalDateTime start;
    LocalDateTime end;
}
