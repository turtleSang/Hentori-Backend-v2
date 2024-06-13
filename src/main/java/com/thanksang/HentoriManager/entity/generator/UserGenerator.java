package com.thanksang.HentoriManager.entity.generator;

import com.thanksang.HentoriManager.config.Constance;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class UserGenerator implements IdentifierGenerator, Configurable {
    private String nameID;


    @Override
    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) {
        IdentifierGenerator.super.configure(type, parameters, serviceRegistry);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) {
        int max = QueryGenerator.Query(session, o, nameID) + 1;
        String id = transfer(max);
        return id;
    }

    private String transfer(int id){
        int len = 1;
        int numCopy = id;
        while (numCopy >= 10){
            len++;
            numCopy /= 10;
        }
        StringBuilder idGenerate =new StringBuilder();
        for (int i = 0; i < Constance.lengthOfClientId - len; i++){
            idGenerate.append(0);
        }
        idGenerate.append(id);
        return idGenerate.toString();
    }
}
