package org.cru.webapps.interrupt.sua;

import org.cru.webapps.interrupt.sua.auth.SsoGuid;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Stateful
public class SignatureDao {

    @Inject
    EntityManager em;

    public Signature saveSignature(SsoGuid ssoGuid) {
        final Signature signature = new Signature();

        signature.setSsoGuid(ssoGuid.get());
        signature.setDateSigned(new Date());

        em.persist(signature);

        return signature;
    }

    public boolean shouldSign(SsoGuid ssoGuid) {
        //TODO add expiration time here
        final List<Signature> resultList = em.createQuery("select s from Signature s " +
                "where s.ssoGuid = :ssoGuid", Signature.class)
                .setParameter("ssoGuid", ssoGuid)
                .getResultList();
        return resultList.isEmpty();
    }

}
