package com.thanksang.HentoriManager.services;

import com.google.gson.Gson;
import com.thanksang.HentoriManager.config.Constance;
import com.thanksang.HentoriManager.config.JwtHelper;
import com.thanksang.HentoriManager.config.RoleTypeEnum;
import com.thanksang.HentoriManager.entity.UserEntity;
import com.thanksang.HentoriManager.error.LoginErrors;
import com.thanksang.HentoriManager.error.RegisterErrors;
import com.thanksang.HentoriManager.payload.AdminRequest;
import com.thanksang.HentoriManager.repository.UserRepository;
import com.thanksang.HentoriManager.services.Imp.EmailServiceImp;
import com.thanksang.HentoriManager.services.Imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService implements UserServiceImp {
    private PasswordEncoder encoder;
    private UserRepository userRepository;
    private Gson gson;
    private JwtHelper jwtHelper;
    private EmailServiceImp emailServiceImp;

    @Autowired
    public UserService(PasswordEncoder encoder,
                       UserRepository userRepository,
                       Gson gson,
                       EmailServiceImp emailServiceImp,
                       JwtHelper jwtHelper) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.gson = gson;
        this.emailServiceImp = emailServiceImp;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public void register(AdminRequest adminRequest) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(adminRequest.getUsername());
        if (userEntity.isPresent()) {
            throw new RegisterErrors("Username has existed");
        }

        if (adminRequest.getUsername().matches(Constance.regexUsername)
                && adminRequest.getPassword().matches(Constance.regexPassword)){
            Optional<RoleTypeEnum> roleEnum = Arrays.stream(RoleTypeEnum.values())
                    .filter(role-> role.getCode().equals(adminRequest.getRoleCode()))
                    .findFirst();
            if (!roleEnum.isPresent()){
                throw new RegisterErrors("Role not existed");
            }
            try {
                String passwordEncoder = encoder.encode(adminRequest.getPassword());
                UserEntity userEntity1 = new UserEntity();
                userEntity1.setPassword(passwordEncoder);
                userEntity1.setUsername(adminRequest.getUsername());
                userEntity1.setRoleEnum(roleEnum.get());
                String json = gson.toJson(userEntity1);
                String token = jwtHelper.createJwt(json);
                emailServiceImp.sendSimpleEmail(token, adminRequest.getUsername());
            } catch (Exception e){
                throw new RegisterErrors(e.getMessage(), e.getCause());
            }
        }else {
            throw new RegisterErrors("Username or Password are not in correct format");
        }
    }

    @Override
    public void confirm(String token) {
        if (token.trim().isEmpty()){
            throw new RegisterErrors("Token is empty");
        }
        try {
            String json = jwtHelper.loadJwt(token);
            UserEntity userEntity = gson.fromJson(json, UserEntity.class);
            userRepository.save(userEntity);
        }catch (Exception e){
            throw new RegisterErrors(e.getMessage(), e.getCause());
        }
    }

    @Override
    public UserEntity login(AdminRequest adminRequest) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(adminRequest.getUsername());
        if (!userEntityOptional.isPresent()){
            throw new LoginErrors("Username not exited");
        }
        if (!encoder.matches(adminRequest.getPassword(), userEntityOptional.get().getPassword())){
            throw new LoginErrors("Password is incorrect");
        }
        return userEntityOptional.get();
    }

    @Override
    public UserEntity find(String name) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(name);
        if (userEntity.isPresent()){
            return userEntity.get();
        }
        return null;
    }

}
