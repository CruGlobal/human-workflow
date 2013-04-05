package org.cru.webapps.workflow.sua;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import java.util.Calendar;
import java.util.Date;

@Stateful
@RequestScoped
public class AYearAgoProducer {

    @Produces
    public Date aYearAgo() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }
}
