package org.cru.webapps.interrupt.sua;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class SignatureDao {

    @Inject
    EntityManager em;

    public boolean shouldInterrupt(String ssoGuid) {
        final List<Signature> resultList = em.createQuery("select s from Signature s where s.ssoGuid = :ssoGuid", Signature.class)
                .setParameter("ssoGuid", ssoGuid)
                .getResultList();
        return resultList.isEmpty();
    }

}
