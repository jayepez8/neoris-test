package com.neoris.clients.entity;

import com.neoris.clients.client.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author jyepez on 27/10/2024
 */
class ClientTest {


    @InjectMocks
    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
    }

    @Test
    void testPrePersist() {
        client.prePersist();
        assertEquals(Boolean.TRUE, client.getStatus());
        assertNotNull(client.getCreatedDate());
        assertEquals(LocalDateTime.now().getYear(), client.getCreatedDate().getYear());
    }

    @Test
    void testClientGettersAndSetters() {
        client.setClientID(1);
        client.setPassword("password123");
        client.setStatus(Boolean.TRUE);
        client.setCreatedBy("jyepez");
        client.setCreatedDate(LocalDateTime.of(2024, 10, 26, 12, 0));
        client.setModifiedBy("admin");
        client.setModifiedDate(LocalDateTime.of(2024, 10, 27, 10, 0));

        assertEquals(1, client.getClientID());
        assertEquals("password123", client.getPassword());
        assertEquals(Boolean.TRUE, client.getStatus());
        assertEquals("jyepez", client.getCreatedBy());
        assertEquals(LocalDateTime.of(2024, 10, 26, 12, 0), client.getCreatedDate());
        assertEquals("admin", client.getModifiedBy());
        assertEquals(LocalDateTime.of(2024, 10, 27, 10, 0), client.getModifiedDate());
    }
}
