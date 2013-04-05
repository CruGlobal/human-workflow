package org.cru.webapps.workflow.sua;

import org.cru.webapps.workflow.sua.auth.SsoGuid;
import org.cru.webapps.workflow.sua.util.EncryptedProperties;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.Response.*;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("non-cas/")
public class NonCasProtected {

    @Inject
    SignatureDao signatureDao;

    @Inject
    EncryptedProperties encryptedProperties;

    @Inject
    Logger logger;

    @GET
    @Path("/{ssoGuid}")
    public boolean requireRedirect(@HeaderParam("serverId") String serverId,
                                   @HeaderParam("serverSecret") String secret,
                                   @PathParam("ssoGuid") String ssoGuid) {
        // TODO remove log statements
        try {
            checkNotNull(serverId);
            checkNotNull(secret);
            checkNotNull(ssoGuid);
        } catch (NullPointerException e) {
            logger.info("*** bad request! ***");
            ResponseBuilder builder = status(Status.BAD_REQUEST);
            builder.type("application/json");
            builder.entity(null);
            Response response = builder.build();
            throw new WebApplicationException(response);
        }

        if (!secret.equals(encryptedProperties.getRequiredProperty("user." + serverId))) {
            logger.info("*** unauthorized! ***");
            throw new WebApplicationException(status(UNAUTHORIZED).build());
        }

        return signatureDao.shouldSign(SsoGuid.valueOf(ssoGuid));
    }
}
