package org.cru.webapps.interrupt;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

public class Resources {

    @PersistenceContext
    private EntityManager em;

    @Produces
    public EntityManager getEm() {
        return em;
    }

    @Produces
    public Logger getLogger(InjectionPoint ip) {
        String category = ip.getMember().getDeclaringClass().getName();
        return Logger.getLogger(category);
    }
}
