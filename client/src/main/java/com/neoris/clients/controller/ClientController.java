package com.neoris.clients.controller;

import com.neoris.clients.client.entity.Client;
import com.neoris.clients.client.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static com.neoris.clients.client.common.ClientConstants.V1_API_VERSION;

/**
 * @author jyepez on 26/10/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("client" + V1_API_VERSION)
public class ClientController {

    private final IClientService clientService;

    @GetMapping()
    public ResponseEntity<Collection<Client>> findAll() {
        Collection<Client> response = this.clientService.findAll();
        return ResponseEntity.ok().body(response);
    }
}
