package org.cru.webapps.workflow.sua.auth;

import edu.yale.its.tp.cas.client.CASReceipt;
import edu.yale.its.tp.cas.client.filter.CASFilter;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
public class SsoGuidFromSession {

    @Inject
    HttpServletRequest request;

    @Produces
    public SsoGuid getSsoGuid() {
        if (isAuthenticatedViaCas(request))
            return SsoGuid.valueOf((String) getCasReceipt(request).getAttributes().get("ssoGuid"));
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
