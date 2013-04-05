package org.cru.webapps.workflow.sua.util;

import com.google.common.base.Preconditions;
import org.ccci.util.properties.CcciProperties;
import org.ccci.util.properties.PropertiesWithFallback;

import javax.enterprise.context.ApplicationScoped;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

@ApplicationScoped
public class EncryptedProperties {
    private Properties properties;

    public EncryptedProperties() {
        CcciProperties.PropertyEncryptionSetup encryptionSetup = new CcciProperties.PropertyEncryptionSetup("kc62qrNjN58Q");
        String propsFilename = "humanWorkflow.properties";
        properties = new PropertiesWithFallback(encryptionSetup, false,
                "/apps/apps-config/" + propsFilename,
                "/ora/apps-config/" + propsFilename,
                "/default.properties");
    }

    public String getRequiredProperty(String propertyKey) {
        String property = properties.getProperty(propertyKey);
        Preconditions.checkState(property != null, "unable to find property value for " + propertyKey);
        return property;
    }

    public URL getURL(String key, String theDefault) {
        String wsdl = properties.getProperty(key, theDefault);
        try {
            return new URL(wsdl);
        } catch (MalformedURLException e) {
            throw new RuntimeException("WSDL URL is malformed", e);
        }
    }
}
