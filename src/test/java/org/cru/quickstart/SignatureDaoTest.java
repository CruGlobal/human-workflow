package org.cru.quickstart;

import org.cru.webapps.interrupt.Resources;
import org.cru.webapps.interrupt.sua.Signature;
import org.cru.webapps.interrupt.sua.SignatureDao;
import org.cru.webapps.interrupt.sua.auth.SsoGuid;
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
                .addClasses(Resources.class, Signature.class, SignatureDao.class, SsoGuid.class);
    }

    @Inject
    SignatureDao signatureDao;

    @Test
    public void shouldInterrupt() {
        final SsoGuid ssoGuid = SsoGuid.valueOf("neverSeenBefore");

        Assert.assertTrue(signatureDao.shouldSign(ssoGuid));
    }

    @Test
    public void shouldNotInterrupt() {
        final SsoGuid ssoGuid = SsoGuid.valueOf("abc-123");

        Assert.assertFalse(signatureDao.shouldSign(ssoGuid));
    }

    @Test
    public void shouldInterruptOld() {
        final SsoGuid ssoGuid = SsoGuid.valueOf("old-987");

        Assert.assertTrue(signatureDao.shouldSign(ssoGuid));
    }

    @Test
    public void shouldSaveSignature() throws Exception {
        final SsoGuid ssoGuid = SsoGuid.valueOf("def-456");

        Assert.assertTrue(signatureDao.shouldSign(ssoGuid));

        signatureDao.saveSignature(ssoGuid);

        Assert.assertFalse(signatureDao.shouldSign(ssoGuid));
    }
}
