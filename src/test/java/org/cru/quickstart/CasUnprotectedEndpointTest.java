package org.cru.quickstart;

import org.cru.webapps.interrupt.App;
import org.cru.webapps.interrupt.Resources;
import org.cru.webapps.interrupt.sua.NonCasProtected;
import org.cru.webapps.interrupt.sua.Signature;
import org.cru.webapps.interrupt.sua.SignatureDao;
import org.cru.webapps.interrupt.sua.auth.SsoGuid;
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

import java.util.Collection;

public class CasUnprotectedEndpointTest extends Arquillian {
    @Deployment
    public static WebArchive createTestArchive() {
        MavenDependencyResolver resolver = DependencyResolvers
                .use(MavenDependencyResolver.class)
                .loadMetadataFromPom("pom.xml");

        final String casClientMvn = "org.ccci:casclient-ccci:2.1.0-20120214-SNAPSHOT";
        final String guavaMvn = "com.google.guava:guava:10.0.1";

        final Collection<JavaArchive> javaArchives = resolver.artifacts(casClientMvn, guavaMvn)
                .resolveAs(JavaArchive.class);

        return ShrinkWrap.create(WebArchive.class, "non-cas-endpoint.war")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("h2-ds.xml", "h2-ds.xml")
                .addAsWebInfResource("web.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsLibraries(javaArchives)
                .addClasses(Resources.class, App.class, NonCasProtected.class, Signature.class, SignatureDao.class, SsoGuid.class);
    }

    @Test
    public void shouldInterrupt() throws Exception {
        ClientRequest request = new ClientRequest("http://localhost:8080/non-cas-endpoint/api/non-cas/aoeu");
        request.accept("application/json");
        final ClientResponse<Boolean> response = request.get(Boolean.class);
        Assert.assertEquals(response.getStatus(), 200);

        Assert.assertTrue(response.getEntity());
    }
}
