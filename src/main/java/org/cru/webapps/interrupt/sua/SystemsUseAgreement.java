package org.cru.webapps.interrupt.sua;

import com.google.gson.Gson;
import org.cru.webapps.interrupt.sua.auth.SsoGuid;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("sua/")
public class SystemsUseAgreement {

    @Inject
    SsoGuid ssoGuid;

    @Inject
    SignatureDao signatureDao;

    @GET
    @Path("license")
    public String getLicense() {

        // haven't decided what's best to return from here
        //   * HTML of license page
        //   * String of license location
        //   * Location header? Not sure if that would even work

        return "";
    }

    @POST
    @Path("signature")
    public String signAgreement() {

        signatureDao.saveSignature(ssoGuid);

        return "OK";
    }

    @GET
    @Path("signature")
    public String mustSignAgreement() {
        return new Gson().toJson(signatureDao.shouldSign(ssoGuid));
    }

    @GET
    public String test() {
        return ssoGuid.get();
    }

}
