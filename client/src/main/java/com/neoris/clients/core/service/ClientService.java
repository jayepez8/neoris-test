package com.neoris.clients.core.service;

import com.neoris.clients.client.entity.Client;
import com.neoris.clients.client.entity.Person;
import com.neoris.clients.client.exception.ExistException;
import com.neoris.clients.client.exception.NotFoundException;
import com.neoris.clients.client.exception.PersistException;
import com.neoris.clients.client.mapper.ClientMapper;
import com.neoris.clients.client.repository.IClientRepository;
import com.neoris.clients.client.service.IClientService;
import com.neoris.clients.client.service.IPersonService;
import com.neoris.clients.vo.ClientPasswordVo;
import com.neoris.clients.vo.ClientVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author jyepez on 26/10/2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final IPersonService personService;

    @Override
    public Collection<ClientVo> findAll() {
        List<Client> clients = this.clientRepository.findAll();
        return this.clientMapper.totoClientVoCollection(clients);
    }

    @Override
    public Client findByID(Integer clientID){
        return this.clientRepository.findById(clientID)
                .orElseThrow(() -> new NotFoundException("No client found with the id "+clientID));
    }

    @Override
    public Client findByIdentification(String identification) {
        return this.clientRepository.findByPersonIdentificationAndStatusIsTrue(identification)
                .orElseThrow(() -> new NotFoundException("No client found with the identification "+identification));
    }

    @Override
    public ClientVo findClientVoByIdentification(String identification) {
        return this.clientMapper.toClientVo(findByIdentification(identification));
    }

    @Override
    public ClientVo findClientVoByID(Integer clientID) {
        return this.clientMapper.toClientVo(findByID(clientID));
    }

    @Override
    public ClientVo create(ClientVo clientVo) {
        if(this.personService.existsPerson(clientVo.getIdentification())){
            throw new ExistException("Error the person already exists");
        }
        try {
            Client client = this.clientMapper.toClient(clientVo);
            Person person = client.getPerson();
            person.setCreatedBy(clientVo.getCreatedBy());
            Person personSave = this.personService.createPerson(person);
            client.setPerson(personSave);
            Client clientSave = this.clientRepository.save(client);
            return this.clientMapper.toClientVo(clientSave);
        }catch (Exception e){
            throw new PersistException("A problem occurred, the client could not be saved");
        }
    }

    @Override
    public ClientVo update(ClientVo clientVo) {
        Client existingClient = findByIdentification(clientVo.getIdentification());
        try {
            clientMapper.updateClientFromVo(clientVo, existingClient);
            existingClient.setModifiedBy(clientVo.getCreatedBy());
            Client clientUpdated = clientRepository.save(existingClient);
            return this.clientMapper.toClientVo(clientUpdated);
        }catch (Exception e){
            throw new PersistException("A problem occurred, the client could not be updated");
        }
    }

    @Override
    public ClientVo updatePassword(ClientPasswordVo clientVo) {
        Client existingClient = findByIdentification(clientVo.getIdentification());
        try {
            existingClient.setPassword(clientVo.getPassword());
            Client clientUpdated = clientRepository.save(existingClient);
            return this.clientMapper.toClientVo(clientUpdated);
        }catch (Exception e){
            throw new PersistException("A problem occurred, the client password could not be updated");
        }
    }

    @Override
    public void delete(String identification) {
        Client existingClient = findByIdentification(identification);
        try {
            existingClient.setStatus(Boolean.FALSE);
            clientRepository.save(existingClient);
        }catch (Exception e){
            throw new PersistException("A problem occurred, the client could not be deleted");
        }
    }
}
