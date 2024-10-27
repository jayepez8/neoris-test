package com.neoris.clients.client.repository;

import com.neoris.clients.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author jyepez on 26/10/2024
 */
@Repository
public interface IClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByPersonIdentificationAndStatusIsTrue(String identification);
}