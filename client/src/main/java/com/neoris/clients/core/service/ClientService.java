package com.neoris.clients.core.service;

import com.neoris.clients.client.entity.Client;
import com.neoris.clients.client.repository.IClientRepository;
import com.neoris.clients.client.service.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author jyepez on 26/10/2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    public Collection<Client> findAll() {
        return clientRepository.findAll();
    }
}
