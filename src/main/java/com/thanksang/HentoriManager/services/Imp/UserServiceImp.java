package com.thanksang.HentoriManager.services.Imp;

import com.thanksang.HentoriManager.entity.UserEntity;
import com.thanksang.HentoriManager.payload.AdminRequest;


public interface UserServiceImp {
    void register(AdminRequest adminRequest);
    void confirm(String token);
    UserEntity login(AdminRequest adminRequest);
    UserEntity find(String name);
}
