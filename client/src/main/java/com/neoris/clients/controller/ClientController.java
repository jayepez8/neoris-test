package com.neoris.clients.controller;

import com.neoris.clients.client.service.IClientService;
import com.neoris.clients.vo.ClientIntVo;
import com.neoris.clients.vo.ClientPasswordVo;
import com.neoris.clients.vo.ClientVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static com.neoris.clients.client.common.ClientConstants.V1_API_VERSION;

/**
 * @author jyepez on 26/10/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("client" + V1_API_VERSION)
public class ClientController {

    private final IClientService clientService;

    @GetMapping()
    public ResponseEntity<Collection<ClientVo>> findAll() {
        Collection<ClientVo> response = this.clientService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientVo> findByID(@PathVariable("id") Integer clientID){
        ClientVo response = this.clientService.findClientVoByID(clientID);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("findByIdentification")
    public ResponseEntity<ClientVo> findByIdentification(@RequestParam() String identification){
        ClientVo response = this.clientService.findClientVoByIdentification(identification);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("findByIdentificationExt")
    public ResponseEntity<ClientIntVo> findByIdentificationExt(@RequestParam() String identification){
        ClientIntVo response = this.clientService.findByIdentificationExt(identification);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping()
    public ResponseEntity<ClientVo> create(@RequestBody @Valid ClientVo client){
        ClientVo response =this.clientService.create(client);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping()
    public ResponseEntity<ClientVo> update(@RequestBody @Valid ClientVo client){
        ClientVo response =this.clientService.update(client);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("updatePassword")
    public ResponseEntity<ClientVo> updatePassword(@RequestBody @Valid ClientPasswordVo client){
        ClientVo response =this.clientService.updatePassword(client);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam @Valid String identification){
        this.clientService.delete(identification);
        return ResponseEntity.ok().build();
    }
}
