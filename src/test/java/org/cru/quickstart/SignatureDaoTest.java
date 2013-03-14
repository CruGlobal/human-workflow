package org.cru.quickstart;

import org.cru.webapps.interrupt.Resources;
import org.cru.webapps.interrupt.sua.Signature;
import org.cru.webapps.interrupt.sua.SignatureDao;
import org.cru.webapps.interrupt.sua.SystemsUseAgreement;
import org.cru.webapps.interrupt.sua.auth.SsoGuid;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
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

        return ShrinkWrap.create(WebArchive.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("h2-ds.xml", "h2-ds.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addClasses(Resources.class, Signature.class, SignatureDao.class);
    }

    @Inject
    SignatureDao signatureDao;

    @Test
    public void shouldInterrupt() {
        String ssoGuid = "neverSeenBefore";

        Assert.assertTrue(signatureDao.shouldInterrupt(ssoGuid));
    }

    @Test
    public void shouldNotInterrupt() {
        String ssoGuid = "abc-123";

        Assert.assertFalse(signatureDao.shouldInterrupt(ssoGuid));
    }
}
