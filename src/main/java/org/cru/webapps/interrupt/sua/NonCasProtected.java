package org.cru.webapps.interrupt.sua;

import org.cru.webapps.interrupt.sua.auth.SsoGuid;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("non-cas/")
public class NonCasProtected {

    @Inject
    SignatureDao signatureDao;

    @GET
    @Path("/{ssoGuid}")
    public boolean shouldInterrupt(@PathParam("ssoGuid") String ssoGuid) {
        //TODO secure with encrypted properties
        return signatureDao.shouldSign(SsoGuid.valueOf(ssoGuid));
    }
}
