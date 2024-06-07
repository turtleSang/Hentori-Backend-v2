package com.thanksang.HentoriManager.services;

import com.thanksang.HentoriManager.config.Constance;
import com.thanksang.HentoriManager.dto.ClientBaseDto;
import com.thanksang.HentoriManager.entity.BelowClientEntity;
import com.thanksang.HentoriManager.entity.ClientEntity;
import com.thanksang.HentoriManager.entity.UpperClientEntity;
import com.thanksang.HentoriManager.error.ClientErrors;
import com.thanksang.HentoriManager.payload.BelowClientRequest;
import com.thanksang.HentoriManager.payload.ClientRequest;
import com.thanksang.HentoriManager.payload.UpperClientRequest;
import com.thanksang.HentoriManager.repository.BelowClientRepository;
import com.thanksang.HentoriManager.repository.ClientRepository;
import com.thanksang.HentoriManager.repository.UpperClientRepository;
import com.thanksang.HentoriManager.services.Imp.ClientServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements ClientServiceImp {
    private ClientRepository clientRepository;
    private ModelMapper modelMapper;
    private UpperClientRepository upperClientRepository;
    private BelowClientRepository belowClientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository,
                         ModelMapper modelMapper,
                         UpperClientRepository upperClientRepository,
                         BelowClientRepository belowClientRepository) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.upperClientRepository = upperClientRepository;
        this.belowClientRepository = belowClientRepository;
    }

    @Override
    public String createClient(ClientRequest clientRequest) {
        if (!clientRequest.getPhoneNumber().matches(Constance.regexPhoneNumber)){
            throw new ClientErrors("Phone number incorrect format");
        }

        Optional<ClientEntity> clientEntity = clientRepository.findByPhoneNumber(clientRequest.getPhoneNumber());
        if (clientEntity.isPresent()){
            throw new ClientErrors("Phone number belong different client");
        }
        try {
            ClientEntity clientEntityNew = modelMapper.map(clientRequest, ClientEntity.class);
            return clientRepository.save(clientEntityNew).getId();
        }catch (Exception e){
            throw new ClientErrors(e.getMessage(), e.getCause());
        }

    }

    @Override
    public void getClientByPhone(String phoneNumber) {

    }

    @Override
    public List<ClientBaseDto> findAllByName(String name) {
        Pageable pageable = PageRequest.of(1, Constance.pageSizeClientSearch, Sort.by("name"));
        List<ClientEntity> clientEntities = clientRepository.findAllByName(name, pageable);
        List<ClientBaseDto> clientBaseDtoList = new ArrayList<>();
        for (ClientEntity clientEntity: clientEntities
             ) {
            ClientBaseDto clientBaseDto = modelMapper.map(clientEntity, ClientBaseDto.class);
            clientBaseDtoList.add(clientBaseDto);
        }
        return clientBaseDtoList;
    }

    @Override
    public ClientBaseDto findById(String id) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        if (clientEntity.isPresent()){
            ClientBaseDto clientBaseDto = modelMapper.map(clientEntity, ClientBaseDto.class);
            return clientBaseDto;
        }
        return null;
    }

    @Override
    public void updateUpper(UpperClientRequest upperClientRequest, String id) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        if (clientEntity.isPresent()){
            try {
                UpperClientEntity upperClientEntity = modelMapper.map(upperClientRequest, UpperClientEntity.class);
                upperClientEntity.setClientEntity(clientEntity.get());
                upperClientRepository.save(upperClientEntity);
            }catch (Exception e){
                throw new ClientErrors(e.getMessage(), e.getCause());
            }
        }else {
            throw new ClientErrors("client id is not exits");
        }
    }

    @Override
    public void updateBelow(BelowClientRequest belowClientRequest, String id) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        if (clientEntity.isPresent()){
            try {
                BelowClientEntity belowClientEntity = modelMapper.map(belowClientRequest, BelowClientEntity.class);
                belowClientEntity.setClientEntity(clientEntity.get());
                belowClientRepository.save(belowClientEntity);
            }catch (Exception e){
                throw new ClientErrors(e.getMessage(), e.getCause());
            }
        }else {
            throw new ClientErrors("client id is not exits");
        }
    }

}
