package com.neoris.clients.entity;

import com.neoris.clients.client.entity.Person;
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
class PersonTest {

    @InjectMocks
    private Person person;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        person = new Person();
    }

    @Test
    void testPrePersist() {
        person.prePersist();

        assertNotNull(person.getCreatedDate());
        assertEquals(LocalDateTime.now().getYear(), person.getCreatedDate().getYear());
    }

    @Test
    void testPersonGettersAndSetters() {
        person.setPersonID(1);
        person.setName("John Doe");
        person.setGender('M');
        person.setAge(30);
        person.setIdentification("123456789");
        person.setAddress("123 Main St");
        person.setPhone("555-1234");
        person.setCreatedBy("jyepez");
        person.setCreatedDate(LocalDateTime.of(2024, 10, 26, 12, 0));
        person.setModifiedBy("admin");
        person.setModifiedDate(LocalDateTime.of(2024, 10, 27, 10, 0));

        // Verificar que los valores son correctamente asignados
        assertEquals(1, person.getPersonID());
        assertEquals("John Doe", person.getName());
        assertEquals('M', person.getGender());
        assertEquals(30, person.getAge());
        assertEquals("123456789", person.getIdentification());
        assertEquals("123 Main St", person.getAddress());
        assertEquals("555-1234", person.getPhone());
        assertEquals("jyepez", person.getCreatedBy());
        assertEquals(LocalDateTime.of(2024, 10, 26, 12, 0), person.getCreatedDate());
        assertEquals("admin", person.getModifiedBy());
        assertEquals(LocalDateTime.of(2024, 10, 27, 10, 0), person.getModifiedDate());
    }
}