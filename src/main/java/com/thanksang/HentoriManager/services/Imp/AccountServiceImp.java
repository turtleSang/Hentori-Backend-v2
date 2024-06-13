package com.thanksang.HentoriManager.services.Imp;

import com.thanksang.HentoriManager.payload.AccountRequest;

public interface AccountServiceImp {
    void createAccount(AccountRequest accountRequest) throws IllegalAccessException;
}
