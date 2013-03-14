package org.cru.webapps.interrupt.sua;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateful
public class SignatureDao {

    @Inject
    EntityManager em;

    public Signature saveSignature(String ssoGuid) {
        final Signature signature = new Signature();

        signature.setSsoGuid(ssoGuid);

        em.persist(signature);

        return signature;
    }

    public boolean shouldInterrupt(String ssoGuid) {
        final List<Signature> resultList = em.createQuery("select s from Signature s where s.ssoGuid = :ssoGuid", Signature.class)
                .setParameter("ssoGuid", ssoGuid)
                .getResultList();
        return resultList.isEmpty();
    }

}