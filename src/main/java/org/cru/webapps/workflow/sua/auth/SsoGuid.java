package org.cru.webapps.workflow.sua.auth;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class SsoGuid {
    private String ssoGuid;

    private SsoGuid(String ssoGuid) {
        this.ssoGuid = ssoGuid;
    }

    public static SsoGuid valueOf(String ssoGuid) {
        Preconditions.checkNotNull(ssoGuid, "SSO GUID cannot be null!");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(ssoGuid), "SSO GUID cannot be empty!");
        return new SsoGuid(ssoGuid);
    }

    public String toString() {
        return ssoGuid;
    }

    public String get() {
        return ssoGuid;
    }
}
