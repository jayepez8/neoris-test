package com.neoris.clients.client.service;

import com.neoris.clients.client.entity.Person;

import java.util.Collection;

/**
 * @author jyepez on 26/10/2024
 */
public interface IPersonService {

    Collection<Person> findAll();
}