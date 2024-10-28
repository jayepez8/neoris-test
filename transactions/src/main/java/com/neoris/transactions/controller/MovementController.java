package com.neoris.transactions.controller;

import com.neoris.transactions.client.service.IMovementService;
import com.neoris.transactions.vo.MovementVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static com.neoris.transactions.client.common.TransactionsConstants.V1_API_VERSION;

/**
 * @author jyepez on 19/7/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("movement" + V1_API_VERSION)
public class MovementController {

    private final IMovementService movementService;

    @GetMapping()
    public ResponseEntity<Collection<MovementVo>> findAll() {
        Collection<MovementVo> response = this.movementService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("findByAccountNumber/{accountNumber}")
    public ResponseEntity<Collection<MovementVo>> findByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        Collection<MovementVo> response = this.movementService.findByAccountNumber(accountNumber);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping()
    public ResponseEntity<MovementVo> create(@RequestBody @Valid MovementVo movement){
        MovementVo response =this.movementService.create(movement);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("updateAmount")
    public ResponseEntity<MovementVo> updateAmount(@RequestParam String accountNumber,@RequestParam Double amount){
        MovementVo response =this.movementService.updateAmount(accountNumber,amount);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam String accountNumber){
        this.movementService.delete(accountNumber);
        return ResponseEntity.ok().build();
    }
}