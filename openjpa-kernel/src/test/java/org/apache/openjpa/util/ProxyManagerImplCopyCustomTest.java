package org.apache.openjpa.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.swing.text.ParagraphView;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ProxyManagerImplCopyCustomTest {
    private ProxyManager proxyManager;
    private Object customObject;

    public ProxyManagerImplCopyCustomTest(Object customObject) {
        this.customObject = customObject;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testingSet() {
        ProxyManagerImpl pm = new ProxyManagerImpl();
        return Arrays.asList(new Object[][] {
                /* valid input */
                {pm.newCollectionProxy(ArrayList.class, null, null, false)},
                {pm.newCalendarProxy(GregorianCalendar.class, TimeZone.getTimeZone("CST"))},
                {pm.newMapProxy(HashMap.class, String.class, String.class, null, false)},
                {pm.newDateProxy(Date.class)},
                {pm.newCustomProxy(Integer.class, false)},
                {Arrays.asList(1,2,3,4)},
                {new HashMap<>()},
                {new Date(1999)},
                {new GregorianCalendar()},
                /* invalid input */
                {null}
        });
    }





    @Before
    public void setUp() {
        this.proxyManager = new ProxyManagerImpl();
    }

    @Test
    public void Test() {
        Object object = this.proxyManager.copyCustom(this.customObject);
        Assert.assertEquals(this.customObject, object);
    }

}
