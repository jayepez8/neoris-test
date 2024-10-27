package com.neoris.clients.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author jyepez on 27/10/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientPasswordVo {

    @NotEmpty(message = "Identification is required")
    private String identification;
    @NotEmpty(message = "Password is required")
    private String password;
}
