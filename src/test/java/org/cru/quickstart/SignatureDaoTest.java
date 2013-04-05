package org.cru.quickstart;

import org.cru.webapps.workflow.Resources;
import org.cru.webapps.workflow.sua.AYearAgoProducer;
import org.cru.webapps.workflow.sua.Signature;
import org.cru.webapps.workflow.sua.SignatureDao;
import org.cru.webapps.workflow.sua.auth.SsoGuid;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SignatureDaoTest extends Arquillian {
    @Deployment
    public static WebArchive createTestArchive() {
        MavenDependencyResolver resolver = DependencyResolvers
                .use(MavenDependencyResolver.class)
                .loadMetadataFromPom("pom.xml");

        final JavaArchive guava = resolver.artifact("com.google.guava:guava:10.0.1")
                .resolveAs(JavaArchive.class).iterator().next();

        return ShrinkWrap.create(WebArchive.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("h2-ds.xml", "h2-ds.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsLibraries(guava)
                .addClasses(Resources.class,
                        Signature.class,
                        SignatureDao.class,
                        SsoGuid.class,
                        AYearAgoTestProducer.class);
    }

    @Inject
    SignatureDao signatureDao;

    @Test
    public void shouldSignSua() {
        final SsoGuid ssoGuid = SsoGuid.valueOf("neverSeenBefore");

        assertTrue(signatureDao.shouldSign(ssoGuid));
    }

    @Test
    public void shouldNotSignAgain() {
        final SsoGuid ssoGuid = SsoGuid.valueOf("abc-123");

        assertFalse(signatureDao.shouldSign(ssoGuid));
    }

    @Test
    public void shouldSignAgain() {
        final SsoGuid ssoGuid = SsoGuid.valueOf("old-987");

        assertTrue(signatureDao.shouldSign(ssoGuid));
    }

    @Test
    public void shouldSaveSignature() throws Exception {
        final SsoGuid ssoGuid = SsoGuid.valueOf("def-456");

        assertTrue(signatureDao.shouldSign(ssoGuid));

        signatureDao.saveSignature(ssoGuid);

        assertFalse(signatureDao.shouldSign(ssoGuid));
    }
}
