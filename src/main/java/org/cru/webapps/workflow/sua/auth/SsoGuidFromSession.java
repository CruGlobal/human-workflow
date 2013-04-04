package org.cru.webapps.workflow.sua.auth;

import com.google.common.base.Preconditions;
import edu.yale.its.tp.cas.client.CASReceipt;
import edu.yale.its.tp.cas.client.filter.CASFilter;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestScoped
public class SsoGuidFromSession {

    @Inject
    HttpServletRequest request;

    @Produces
    public SsoGuid getSsoGuid() {
        if (isAuthenticatedViaCas(request)) {
            final Map attributes = getCasReceipt(request).getAttributes();
            Preconditions.checkState(!attributes.keySet().isEmpty(), "CAS Attributes is empty - is this application white listed?");
            return SsoGuid.valueOf((String) attributes.get("ssoGuid"));
        }
        else
            throw new RuntimeException("not auth");
    }


    private CASReceipt getCasReceipt(HttpServletRequest httpServletRequest) {
        return (CASReceipt) httpServletRequest.getSession().getAttribute(CASFilter.CAS_FILTER_RECEIPT);
    }

    private boolean isAuthenticatedViaCas(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession(false) == null)
            return false;
        clearGatwayFlag(httpServletRequest);
        return getCasReceipt(httpServletRequest) != null;
    }

    private void clearGatwayFlag(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute("edu.yale.its.tp.cas.client.filter.didGateway");
    }
}
