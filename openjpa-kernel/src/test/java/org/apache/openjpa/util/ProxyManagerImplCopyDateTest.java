package org.apache.openjpa.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ProxyManagerImplCopyDateTest {
    ProxyManager proxyManager;
    Date orig;

    public ProxyManagerImplCopyDateTest(Date orig) {
        this.orig = orig;
    }

    @Before
    public void SetUp() {
        this.proxyManager = new ProxyManagerImpl();
    }

    public static Date setTime(Date date, long time) {
        date.setTime(time);
        return date;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testingSet() {
        ProxyManagerImpl pm = new ProxyManagerImpl();
        return Arrays.asList(new Object[][]{
                /* valid input */
                {new Date(1999)},
                //{new java.sql.Date(1999)},
                {new Timestamp(1999)},
                /* proxy input */
                {pm.newDateProxy(Date.class)},
                {setTime(((Date) pm.newDateProxy(Date.class)), 1999)},
                /* invalid input */
                {null}
        });
    }

    @Test
    public void Test() {
        Date origCopy = proxyManager.copyDate(orig);
        Assert.assertEquals(orig, origCopy);
    }

}
