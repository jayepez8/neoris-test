package com.neoris.transactions.client.connector;

import com.neoris.transactions.vo.ClientVo;

/**
 * @author jyepez on 27/10/2024
 */
public interface IClientConnector {

    ClientVo getByID(String clientID);

    ClientVo getByIdentification(String identification);
}
