package org.cru.webapps.interrupt;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@ApplicationPath("api/")
public class App extends javax.ws.rs.core.Application {

    @GET
    @Path("{ssoGuid}")
    public boolean shouldInterrupt(@PathParam("ssoGuid") String ssoGuid) {
        return true;
    }

}
