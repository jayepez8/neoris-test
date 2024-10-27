package com.neoris.transactions.controller;

import com.neoris.transactions.client.service.IAccountService;
import com.neoris.transactions.vo.AccountVo;
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
@RequestMapping("account" + V1_API_VERSION)
public class AccountController {

    private final IAccountService accountService;

    @GetMapping()
    public ResponseEntity<Collection<AccountVo>> findAll() {
        Collection<AccountVo> response = this.accountService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountVo> findByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        AccountVo response = this.accountService.findByAccountNumberExt(accountNumber);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping()
    public ResponseEntity<AccountVo> create(@RequestBody @Valid AccountVo account){
        AccountVo response =this.accountService.create(account);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping()
    public ResponseEntity<AccountVo> update(@RequestParam String accountNumber,@RequestBody @Valid AccountVo account){
        AccountVo response =this.accountService.update(accountNumber,account);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("updateAccountType")
    public ResponseEntity<AccountVo> updateAccountType(@RequestParam String accountNumber,@RequestParam String accountType){
        AccountVo response =this.accountService.updateAccountType(accountNumber,accountType);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("updateInitialBalance")
    public ResponseEntity<AccountVo> updateInitialBalance(@RequestParam String accountNumber,@RequestParam Double initialBalance){
        AccountVo response =this.accountService.updateInitialBalance(accountNumber,initialBalance);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam @Valid String accountNumber){
        this.accountService.delete(accountNumber);
        return ResponseEntity.ok().build();
    }
}
