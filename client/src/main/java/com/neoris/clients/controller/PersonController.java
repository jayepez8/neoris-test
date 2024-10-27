package com.neoris.clients.controller;

import com.neoris.clients.client.entity.Person;
import com.neoris.clients.client.service.IPersonService;
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
@RequestMapping("person" + V1_API_VERSION)
public class PersonController {

    private final IPersonService personService;

    @GetMapping()
    public ResponseEntity<Collection<Person>> findAll() {
        Collection<Person> response = this.personService.findAll();
        return ResponseEntity.ok().body(response);
    }
}
