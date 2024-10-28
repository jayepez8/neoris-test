package com.neoris.clients.service;

import com.neoris.clients.client.entity.Client;
import com.neoris.clients.client.entity.Person;
import com.neoris.clients.client.exception.ExistException;
import com.neoris.clients.client.exception.NotFoundException;
import com.neoris.clients.client.mapper.ClientMapper;
import com.neoris.clients.client.repository.IClientRepository;
import com.neoris.clients.client.service.IPersonService;
import com.neoris.clients.core.service.ClientService;
import com.neoris.clients.vo.ClientVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author jyepez on 27/10/2024
 */
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private IClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private IPersonService personService;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private ClientVo clientVo;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setClientID(1);
        client.setPassword("password");
        client.setStatus(true);

        clientVo = new ClientVo();
        clientVo.setIdentification("1234567890");
        clientVo.setCreatedBy("user");

        Person person = new Person();
        person.setIdentification(clientVo.getIdentification());

        client.setPerson(person);
    }

    @Test
    void findByID_ShouldReturnClient_WhenClientExists() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        Client result = clientService.findByID(1);
        assertEquals(client, result);
    }

    @Test
    void findByID_ShouldThrowNotFoundException_WhenClientNotExists() {
        when(clientRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> clientService.findByID(1));
    }

    @Test
    void create_ShouldThrowExistException_WhenPersonExists() {
        when(personService.existsPerson("12345")).thenReturn(true);
        clientVo.setIdentification("12345");
        assertThrows(ExistException.class, () -> clientService.create(clientVo));
    }

    @Test
    void create_ShouldSaveAndReturnClientVo_WhenClientIsNew() {
        when(personService.existsPerson(clientVo.getIdentification())).thenReturn(false);
        when(clientMapper.toClient(clientVo)).thenReturn(client);
        when(personService.createPerson(any(Person.class))).thenReturn(client.getPerson());
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.toClientVo(client)).thenReturn(clientVo);

        ClientVo result = clientService.create(clientVo);

        verify(clientRepository).save(client);
        assertEquals(clientVo, result);
    }

    @Test
    void delete_ShouldSetStatusToFalse_WhenClientExists() {
        when(clientRepository.findByPersonIdentificationAndStatusIsTrue("12345")).thenReturn(Optional.of(client));
        clientService.delete("12345");
        verify(clientRepository).save(client);
        assertFalse(client.getStatus());
    }

    @Test
    void findByIdentification_ShouldReturnClient_WhenClientExists() {
        when(clientRepository.findByPersonIdentificationAndStatusIsTrue("12345")).thenReturn(Optional.of(client));
        Client result = clientService.findByIdentification("12345");
        assertEquals(client, result);
    }

    @Test
    void findByIdentification_ShouldThrowNotFoundException_WhenClientNotExists() {
        when(clientRepository.findByPersonIdentificationAndStatusIsTrue("12345")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> clientService.findByIdentification("12345"));
    }
}
