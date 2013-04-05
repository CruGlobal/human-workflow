package org.cru.webapps.workflow.sua;

import org.cru.webapps.workflow.sua.auth.SsoGuid;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateful
public class SignatureDao {

    @Inject
    EntityManager em;

    @Inject
    Date aYearAgo;

    public Signature saveSignature(SsoGuid ssoGuid) {
        final Signature signature = new Signature();

        signature.setSsoGuid(ssoGuid.get());
        signature.setDateSigned(new Date());

        em.persist(signature);

        return signature;
    }

    public List<Signature> validSignatures(SsoGuid ssoGuid) {

        final String qlString = "select s from Signature s " +
                "where s.ssoGuid = :ssoGuid " +
                "and s.dateSigned > :aYearAgo " +
                "and s.dateSigned is not null ";
        return em.createQuery(qlString, Signature.class)
                .setParameter("ssoGuid", ssoGuid.get())
                .setParameter("aYearAgo", aYearAgo)
                .getResultList();
    }

    public boolean shouldSign(SsoGuid ssoGuid) {
        return validSignatures(ssoGuid).isEmpty();
    }

}
