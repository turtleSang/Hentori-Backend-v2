package com.thanksang.HentoriManager.entity.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.util.stream.Stream;

public class QueryGenerator {
    public static int Query(SharedSessionContractImplementor session, Object o, String nameID) {
        String query = String.format("select %s from %s", session.getEntityPersister(o.getClass().getName(), o).getIdentifierPropertyName(),o.getClass().getSimpleName());
        Stream<String> ids = session.getSession().createQuery(query, String.class).stream();
        int max = ids.map(s -> s.replace(nameID+"-","")).mapToInt(Integer::parseInt).max().orElse(0);
        return max;
    }
}
