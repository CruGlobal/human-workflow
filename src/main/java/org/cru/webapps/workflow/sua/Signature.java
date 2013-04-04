package org.cru.webapps.workflow.sua;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
//@UniqueConstraint(columnNames = "ssoGuid")
public class Signature {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "signature_seq")
    @SequenceGenerator(name = "signature_seq", sequenceName = "signature_seq")
    private Long id;
    private String ssoGuid;
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

    public Date getDateSigned() {
        return dateSigned;
    }

    public void setDateSigned(Date dateSigned) {
        this.dateSigned = dateSigned;
    }

}
