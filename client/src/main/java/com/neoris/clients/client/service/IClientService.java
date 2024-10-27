package com.neoris.clients.client.service;

import com.neoris.clients.client.entity.Client;
import com.neoris.clients.vo.ClientPasswordVo;
import com.neoris.clients.vo.ClientVo;

import java.util.Collection;

/**
 * @author jyepez on 26/10/2024
 */
public interface IClientService {

    Collection<ClientVo> findAll();

    Client findByID(Integer clientID);

    Client findByIdentification(String identification);

    ClientVo findClientVoByIdentification(String identification);

    ClientVo findClientVoByID(Integer clientID);

    ClientVo create(ClientVo clientVo);

    ClientVo update(ClientVo clientVo);

    ClientVo updatePassword(ClientPasswordVo clientVo);

    void delete(String identification);
}
