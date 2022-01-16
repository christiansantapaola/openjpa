package org.apache.openjpa.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.*;


@RunWith(Parameterized.class)
public class ProxyManagerImplCopyCalendarTest {
    ProxyManager proxyManager;
    Calendar calendar;

    public ProxyManagerImplCopyCalendarTest(Calendar calendar) {
        this.calendar = calendar;
    }

    private static Calendar populate(Calendar cal) {
        cal.setTimeInMillis(1999);
        cal.setTimeZone(TimeZone.getTimeZone("CST"));
        return cal;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testingSet() {
        ProxyManagerImpl pm = new ProxyManagerImpl();
        return Arrays.asList(new Object[][]{
                /* valid input */
                {populate(new GregorianCalendar())},
                {pm.newCalendarProxy(GregorianCalendar.class, TimeZone.getTimeZone("CST"))},
                {null}
        });
    }


    private static void assertCalendarsEqual(Calendar c1, Calendar c2) {
        Assert.assertEquals(c1, c2);
    }


    @Before
    public void setUp() {
        this.proxyManager = new ProxyManagerImpl();
    }

    @Test
    public void Test() {
        Calendar calendar = this.proxyManager.copyCalendar(this.calendar);
        assertCalendarsEqual(this.calendar, calendar);
    }

}

