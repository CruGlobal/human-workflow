package org.cru.quickstart;

import org.cru.webapps.workflow.App;
import org.cru.webapps.workflow.Resources;
import org.cru.webapps.workflow.sua.NonCasProtected;
import org.cru.webapps.workflow.sua.Signature;
import org.cru.webapps.workflow.sua.SignatureDao;
import org.cru.webapps.workflow.sua.auth.SsoGuid;
import org.cru.webapps.workflow.sua.util.EncryptedProperties;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.Collection;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.testng.Assert.assertEquals;

public class CasUnprotectedEndpointTest extends Arquillian {
    @Deployment
    public static WebArchive createTestArchive() {
        MavenDependencyResolver resolver = DependencyResolvers
                .use(MavenDependencyResolver.class)
                .loadMetadataFromPom("pom.xml");

        final String casClientMvn = "org.ccci:casclient-ccci:2.1.0-20120214-SNAPSHOT";
        final String ccciUtil = "org.ccci:util:3-SNAPSHOT";
        final String guavaMvn = "com.google.guava:guava:10.0.1";

        final Collection<JavaArchive> javaArchives = resolver.artifacts(casClientMvn, guavaMvn, ccciUtil)
                .resolveAs(JavaArchive.class);

        return ShrinkWrap.create(WebArchive.class, "non-cas-endpoint.war")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("h2-ds.xml", "h2-ds.xml")
                .addAsWebInfResource("web.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsResource("default.properties")
                .addAsLibraries(javaArchives)
                .addClasses(Resources.class,
                        App.class,
                        EncryptedProperties.class,
                        NonCasProtected.class,
                        Signature.class,
                        SignatureDao.class,
                        SsoGuid.class);
    }

    @Test
    public void shouldBeBadRequest() throws Exception {
        ClientRequest request = new ClientRequest("http://localhost:8080/non-cas-endpoint/api/non-cas/aoeu");
        request.accept("application/json");
        final ClientResponse<Boolean> response = request.get(Boolean.class);
        assertEquals(response.getStatus(), BAD_REQUEST.getStatusCode());
    }


    @Test
    public void shouldBeUnauthorized() throws Exception {
        ClientRequest request = new ClientRequest("http://localhost:8080/non-cas-endpoint/api/non-cas/aoeu");
        request.accept("application/json");
        request.header("serverId", "wrong");
        request.header("serverSecret", "wrong");
        final ClientResponse<Boolean> response = request.get(Boolean.class);
        assertEquals(response.getStatus(), UNAUTHORIZED.getStatusCode());
    }
}
