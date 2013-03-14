package org.cru.webapps.interrupt.sua;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
//@UniqueConstraint(columnNames = "ssoGuid")
public class Signature {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String ssoGuid;
    private Boolean signed;
    private Date dateSigned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSsoGuid() {
        return ssoGuid;
    }

    public void setSsoGuid(String ssoGuid) {
        this.ssoGuid = ssoGuid;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public Date getDateSigned() {
        return dateSigned;
    }

    public void setDateSigned(Date dateSigned) {
        this.dateSigned = dateSigned;
    }

}
