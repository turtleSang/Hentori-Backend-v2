package com.thanksang.HentoriManager.controllers;

import com.thanksang.HentoriManager.error.AccountErrors;
import com.thanksang.HentoriManager.payload.AccountRequest;
import com.thanksang.HentoriManager.services.Imp.AccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private AccountServiceImp accountServiceImp;

    @Autowired
    public AccountController(AccountServiceImp accountServiceImp) {
        this.accountServiceImp = accountServiceImp;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AccountRequest accountRequest){
        HttpStatus httpStatus;
        String mess ;
        try {
            accountServiceImp.createAccount(accountRequest);
            mess = "create account successful";
            httpStatus = HttpStatus.OK;
        }catch (AccountErrors | IllegalAccessException errors){
            mess = errors.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(mess, httpStatus);
    }


}
