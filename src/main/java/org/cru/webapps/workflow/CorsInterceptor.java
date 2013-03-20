package org.cru.webapps.workflow;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.spi.interception.MessageBodyWriterContext;
import org.jboss.resteasy.spi.interception.MessageBodyWriterInterceptor;

import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Path("/")
@Provider
@ServerInterceptor
public class CorsInterceptor implements MessageBodyWriterInterceptor {
    @Inject
    Logger logger;

    @Override
    public void write(MessageBodyWriterContext messageBodyWriterContext) throws IOException, WebApplicationException {
        logger.info("writing");
        messageBodyWriterContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        messageBodyWriterContext.proceed();
    }

    @OPTIONS
    @Path("/{path:.*}")
    public Response handleCORSRequest(
            @HeaderParam("Access-Control-Request-Method") final String requestMethod,
            @HeaderParam("Access-Control-Request-Headers") final String requestHeaders) {
        logger.info("handling");
        final Response.ResponseBuilder retValue = Response.ok();

        if (requestHeaders != null)
            retValue.header("Access-Control-Allow-Headers", requestHeaders);

        if (requestMethod != null)
            retValue.header("Access-Control-Allow-Methods", requestMethod);

        retValue.header("Access-Control-Allow-Origin", "*");

        return retValue.build();
    }
}