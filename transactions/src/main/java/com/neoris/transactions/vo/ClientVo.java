package com.neoris.transactions.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jyepez on 27/10/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientVo {

    private Integer clientID;
    private String name;
    private String identification;
    private String address;
}
