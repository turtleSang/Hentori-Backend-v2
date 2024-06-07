package com.thanksang.HentoriManager.controllers;

import com.thanksang.HentoriManager.dto.ClientBaseDto;
import com.thanksang.HentoriManager.error.ClientErrors;
import com.thanksang.HentoriManager.payload.AdminRequest;
import com.thanksang.HentoriManager.payload.BelowClientRequest;
import com.thanksang.HentoriManager.payload.ClientRequest;
import com.thanksang.HentoriManager.payload.UpperClientRequest;
import com.thanksang.HentoriManager.services.Imp.ClientServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    private ClientServiceImp clientServiceImp;

    @Autowired
    public ClientController(ClientServiceImp clientServiceImp) {
        this.clientServiceImp = clientServiceImp;
    }

    @PostMapping("/base")
    public ResponseEntity<?> responseEntity(@RequestBody ClientRequest clientRequest){
        HttpStatus httpStatus;
        String mess;
        try {
            mess = clientServiceImp.createClient(clientRequest);
            httpStatus = HttpStatus.OK;
        }catch (Exception e){
            httpStatus = HttpStatus.BAD_REQUEST;
            mess = e.getMessage();
        }

        return new ResponseEntity<>(mess, httpStatus);
    }

    @PostMapping("/upper/{id}")
    public ResponseEntity<?> createUpper(@RequestBody UpperClientRequest adminRequest, @PathVariable String id){
        HttpStatus httpStatus;
        String mess;
        try {
            clientServiceImp.updateUpper(adminRequest, id);
            mess = "Update upper successful";
            httpStatus = HttpStatus.OK;
        }catch (ClientErrors clientErrors){
            mess = clientErrors.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(mess, httpStatus);
    }

    @PostMapping("/below/{id}")
    public ResponseEntity<?> createBelow(@RequestBody BelowClientRequest belowClientRequest, @PathVariable String id){
        HttpStatus httpStatus;
        String mess;
        try {
            clientServiceImp.updateBelow(belowClientRequest, id);
            mess = "Update below successful";
            httpStatus = HttpStatus.OK;
        }catch (ClientErrors clientErrors){
            mess = clientErrors.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(mess, httpStatus);

    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam String name){
        HttpStatus httpStatus;
        List<ClientBaseDto> clientBaseDtoList = clientServiceImp.findAllByName(name);
        if (clientBaseDtoList.isEmpty()){
            httpStatus = HttpStatus.NOT_FOUND;
        }else {
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(clientBaseDtoList, httpStatus);
    }
}
