package com.neoris.clients.vo;

import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * @author jyepez on 27/10/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientVo {

    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Identification is required")
    private String identification;
    @NotEmpty(message = "Address is required")
    private String address;
    @NotEmpty(message = "Phone is required")
    private String phone;
    @NotEmpty(message = "Password is required")
    private String password;
    private Boolean status;
    @NotEmpty(message = "Created By is required")
    private String createdBy;
}
