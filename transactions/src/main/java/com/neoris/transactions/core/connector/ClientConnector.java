package com.neoris.transactions.core.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoris.transactions.client.connector.IClientConnector;
import com.neoris.transactions.client.exception.ExistException;
import com.neoris.transactions.client.exception.NotFoundException;
import com.neoris.transactions.vo.ClientVo;
import com.neoris.transactions.vo.ErrorResponseVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.neoris.transactions.client.common.TransactionsConstants.*;

/**
 * @author jyepez on 27/10/2024
 */
@Lazy
@Component
public class ClientConnector implements IClientConnector {

    @Value("${domain.name}")
    public String DOMAIN_NAME;

    @Override
    public ClientVo getByID(String clientID) {
        String url = getDomain().concat(URL_GET_CLIENT_BY_ID).concat(clientID);
        try {
            ResponseEntity<ClientVo> response = new RestTemplate().exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<ClientVo>() {
                    }
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            return handleClientErrorException(e);
        }
    }

    @Override
    public ClientVo getByIdentification(String identification) {
        String url = getDomain().concat(URL_GET_CLIENT_BY_IDENTIFICATION).concat(identification);
        try {
            ResponseEntity<ClientVo> response = new RestTemplate().exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<ClientVo>() {
                    }
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new ExistException();
        }
    }

    private ClientVo handleClientErrorException(HttpClientErrorException e) {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponseVo errorResponse;
        try {
            errorResponse = objectMapper.readValue(e.getResponseBodyAsString(), ErrorResponseVo.class);
        } catch (Exception ex) {
            throw new NotFoundException("Failed to parse error response: " + ex.getMessage());
        }
        throw new NotFoundException(errorResponse.getMessage());
    }

    private String getDomain(){
        return "NO_SET".equals(DOMAIN_NAME) ? DOMAIN_CLIENT : DOMAIN_NAME;
    }
}
