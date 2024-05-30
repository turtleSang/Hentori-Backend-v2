package com.thanksang.HentoriManager.controllers;

import com.thanksang.HentoriManager.dto.LoginDto;
import com.thanksang.HentoriManager.dto.RegisterDto;
import com.thanksang.HentoriManager.entity.UserEntity;
import com.thanksang.HentoriManager.error.LoginErrors;
import com.thanksang.HentoriManager.error.RegisterErrors;
import com.thanksang.HentoriManager.payload.AdminRequest;
import com.thanksang.HentoriManager.services.Imp.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class UserController {
    private UserServiceImp userServiceImp;

    @Autowired
    public UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;

    }

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public String hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> list = (List<GrantedAuthority>) authentication.getAuthorities();
        boolean check = false;
        for (GrantedAuthority authority: list
             ) {
            if (authority.getAuthority().equals("Manager")){
                check = true;
                break;
            }
        }
        if (check){
            return "Hello" + " Manager";
        }
        return "Hello Staff";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminRequest adminRequest, HttpServletRequest request) {
        String mess;
        HttpStatus httpStatus;
       try {
           UserEntity userEntity = userServiceImp.login(adminRequest);
           GrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRoleEnum().name());
           Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity.getId(),userEntity.getPassword(), Arrays.asList(authority));
           String header = request.getHeader("User-Agent");
           HttpSession session = request.getSession(true);
           session.setMaxInactiveInterval(43200);
           session.setAttribute("ID", authentication);
           session.setAttribute("Device", header);
           mess = "login successful";
           httpStatus = HttpStatus.OK;
       }catch (LoginErrors loginErrors){
           mess = loginErrors.getMessage();
           httpStatus =HttpStatus.BAD_REQUEST;
       }
        return new ResponseEntity<>(new LoginDto(mess), httpStatus);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AdminRequest adminRequest) {
        RegisterDto registerDto = new RegisterDto();
        HttpStatus httpStatus;
        try {
            userServiceImp.register(adminRequest);
            registerDto.setStatus(true);
            registerDto.setMess("register successful, please check email of manager to accept");
            httpStatus = HttpStatus.OK;
        } catch (RegisterErrors registerErrors) {
            httpStatus = HttpStatus.BAD_REQUEST;
            registerDto.setMess(registerErrors.getMessage());
            registerDto.setStatus(false);
        }
        return new ResponseEntity<>(registerDto, httpStatus);
    }

    @GetMapping("/confirm/{token}")
    public ResponseEntity<?> confirm(@PathVariable String token){
        RegisterDto registerDto = new RegisterDto();
        HttpStatus httpStatus;
        try {
            userServiceImp.confirm(token);
            registerDto.setMess("Account has register");
            registerDto.setStatus(true);
            httpStatus = HttpStatus.OK;
        }catch (RegisterErrors e){
            registerDto.setMess(e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(registerDto, httpStatus);

    }
}
