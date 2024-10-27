package com.neoris.clients.client.repository;

import com.neoris.clients.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jyepez on 26/10/2024
 */
@Repository
public interface IClientRepository extends JpaRepository<Client, Integer> {
}