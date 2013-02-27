package org.cru.quickstart.webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloWorld {
    @GET
    public String getStarted() {
        return "Hello World!";
    }
}
