package com.thanksang.HentoriManager.services.Imp;

import com.thanksang.HentoriManager.dto.ClientBaseDto;
import com.thanksang.HentoriManager.entity.ClientEntity;
import com.thanksang.HentoriManager.payload.BelowClientRequest;
import com.thanksang.HentoriManager.payload.ClientRequest;
import com.thanksang.HentoriManager.payload.UpperClientRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientServiceImp {
    String createClient(ClientRequest clientRequest);
    void getClientByPhone(String phoneNumber);
    List<ClientBaseDto> findAllByName(String name);
    ClientBaseDto findById(String id);
    void updateUpper(UpperClientRequest upperClientRequest, String id);
    void updateBelow(BelowClientRequest belowClientRequest, String id);
}
