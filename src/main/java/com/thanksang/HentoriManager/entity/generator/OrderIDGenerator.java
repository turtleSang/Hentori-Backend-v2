package com.thanksang.HentoriManager.entity.generator;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class OrderIDGenerator implements IdentifierGenerator, Configurable {
    private String nameID;

    @Override
    public void configure(Type type, Properties properties,
                          ServiceRegistry serviceRegistry) throws MappingException {
        nameID = properties.getProperty("nameID");
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) {
        int max = QueryGenerator.Query(session,o, nameID) + 1;
        return nameID + max;
    }

}
