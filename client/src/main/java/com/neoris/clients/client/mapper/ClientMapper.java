package com.neoris.clients.client.mapper;

import com.neoris.clients.client.entity.Client;
import com.neoris.clients.vo.ClientIntVo;
import com.neoris.clients.vo.ClientVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;

/**
 * @author jyepez on 26/10/2024
 */
@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.identification", target = "identification")
    @Mapping(source = "person.address", target = "address")
    @Mapping(source = "person.phone", target = "phone")
    ClientVo toClientVo(Client client);

    Collection<ClientVo> totoClientVoCollection(Collection<Client> clients);

    @Mapping(source = "name", target = "person.name")
    @Mapping(source = "identification", target = "person.identification")
    @Mapping(source = "address", target = "person.address")
    @Mapping(source = "phone", target = "person.phone")
    Client toClient(ClientVo clientVo);

    @Mapping(source = "name", target = "person.name")
    @Mapping(source = "address", target = "person.address")
    @Mapping(source = "phone", target = "person.phone")
    void updateClientFromVo(ClientVo clientVo, @MappingTarget Client client);

    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.identification", target = "identification")
    @Mapping(source = "person.address", target = "address")
    ClientIntVo toClientIntVo(Client client);
}
