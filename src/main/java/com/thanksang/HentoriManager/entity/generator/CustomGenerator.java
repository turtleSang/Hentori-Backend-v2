package com.thanksang.HentoriManager.entity.generator;

import com.thanksang.HentoriManager.config.Constance;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class CustomGenerator implements IdentifierGenerator, Configurable {
    private String nameID;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        int max = QueryGenerator.Query(session, obj, nameID);
        return transferClientID(max);
    }

    @Override
    public void configure(Type type, Properties properties,
                          ServiceRegistry serviceRegistry) throws MappingException {
        nameID = properties.getProperty("nameID");
    }


    private String transferClientID(int number){
        int length = 1;
        int numCopy = number;
        while (numCopy > 10){
            length++;
            numCopy/=10;
        }
        StringBuilder id = new StringBuilder(nameID);
        id.append('-');
        for (int i = 0; i < Constance.lengthOfClientId - length; i++) {
            id.append(0);
        }
        id.append(number);
        return id.toString();

    }
}
