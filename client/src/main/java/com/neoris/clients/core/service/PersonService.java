package com.neoris.clients.core.service;

import com.neoris.clients.client.entity.Person;
import com.neoris.clients.client.repository.IPersonRepository;
import com.neoris.clients.client.service.IPersonService;
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
public class PersonService implements IPersonService {

    private final IPersonRepository personRepository;


    @Override
    public Collection<Person> findAll() {
        return personRepository.findAll();
    }
}
