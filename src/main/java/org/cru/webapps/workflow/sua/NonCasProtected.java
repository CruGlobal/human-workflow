package org.cru.webapps.workflow.sua;

import org.cru.webapps.workflow.sua.auth.SsoGuid;
import org.cru.webapps.workflow.sua.util.EncryptedProperties;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.Response.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
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
            returnStatus(BAD_REQUEST);
        }

        String configuredSecret = null;
        try {
            configuredSecret = encryptedProperties.getRequiredProperty("user." + serverId);
        } catch (IllegalStateException e) {
            returnStatus(UNAUTHORIZED);
        }
        if (!secret.equals(configuredSecret)) {
            returnStatus(UNAUTHORIZED);
        }

        return signatureDao.shouldSign(SsoGuid.valueOf(ssoGuid));
    }

    private void returnStatus(Status status) throws WebApplicationException {
        ResponseBuilder builder = status(status);
        builder.type("application/json");
        builder.entity(null);
        Response response = builder.build();
        throw new WebApplicationException(response);
    }
}
