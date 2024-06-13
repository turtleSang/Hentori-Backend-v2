package com.thanksang.HentoriManager.services;

import com.thanksang.HentoriManager.config.Constance;
import com.thanksang.HentoriManager.entity.AccountEntity;
import com.thanksang.HentoriManager.error.AccountErrors;
import com.thanksang.HentoriManager.payload.AccountRequest;
import com.thanksang.HentoriManager.repository.AccountRepository;
import com.thanksang.HentoriManager.services.Imp.AccountServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements AccountServiceImp {
    private AccountRepository accountRepository;
    private ModelMapper modelMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createAccount(AccountRequest accountRequest) throws IllegalAccessException {
        Constance.checkNullField(accountRequest);
        Optional<AccountEntity> accountEntity = accountRepository.findById(accountRequest.getName());
        if (accountEntity.isPresent()){
            throw new AccountErrors("Account has exited");
        }
        try {
            AccountEntity accountEntityNew = modelMapper.map(accountRequest, AccountEntity.class);
            accountRepository.save(accountEntityNew);
        }catch (Exception e){
            throw  new AccountErrors(e.getMessage(), e.getCause());
        }
    }
}
