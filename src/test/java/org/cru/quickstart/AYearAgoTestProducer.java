package org.cru.quickstart;

import javax.enterprise.inject.Produces;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.APRIL;

public class AYearAgoTestProducer {
    @Produces
    public Date aYearAgoTestProducer() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, APRIL, 9);
        return calendar.getTime();
    }
}
