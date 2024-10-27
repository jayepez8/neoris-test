package com.neoris.clients.client.repository;

import com.neoris.clients.client.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jyepez on 26/10/2024
 */
@Repository
public interface IPersonRepository extends JpaRepository<Person, Integer> {

    Boolean existsByIdentification(String identification);
}
