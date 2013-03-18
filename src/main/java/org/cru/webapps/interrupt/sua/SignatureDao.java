package org.cru.webapps.interrupt.sua;

import org.cru.webapps.interrupt.sua.auth.SsoGuid;

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

    public Signature saveSignature(SsoGuid ssoGuid) {
        final Signature signature = new Signature();

        signature.setSsoGuid(ssoGuid.get());
        signature.setDateSigned(new Date());

        em.persist(signature);

        return signature;
    }

    public boolean shouldSign(SsoGuid ssoGuid) {
        final Calendar date = Calendar.getInstance();
        date.add(Calendar.YEAR, -1);

        final String qlString = "select s from Signature s " +
                "where s.ssoGuid = :ssoGuid " +
                "and s.dateSigned > :aYearAgo " +
                "and s.dateSigned is not null ";
        final List<Signature> resultList = em.createQuery(qlString, Signature.class)
                .setParameter("ssoGuid", ssoGuid.get())
                .setParameter("aYearAgo", date.getTime())
                .getResultList();

        return resultList.isEmpty();
    }

}
