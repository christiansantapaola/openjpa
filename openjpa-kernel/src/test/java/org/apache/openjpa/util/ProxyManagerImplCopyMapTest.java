package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.LRUMap;
import org.apache.openjpa.lib.util.collections.LinkedMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ProxyManagerImplCopyMapTest {
    ProxyManager proxyManager;
    Map map;

    public ProxyManagerImplCopyMapTest(Map map) {
        this.map = map;
    }

    private static void assertMapsEqual(Map m1, Map m2) {
        if (m1 == null) {
            assertNull(m2);
            return;
        }
        if (m2 == null) {
            fail();
        }
        assertEquals(m1.size(), m2.size());
        assertEquals(m1, m2);
    }


    public static Map create(Map map) {
        if (map == null) {
            return null;
        }
        map.put(1, "1");
        map.put(99, "99");
        map.put(-2, "-2");
        map.put(50, "50");
        return map;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> testingSet() {
        ProxyManagerImpl pm = new ProxyManagerImpl();
        return Arrays.asList(new Object[][] {
                /* valid input */
                {create(new HashMap())},
                {create(new LinkedMap())},
                {create(new TreeMap())},
                /* empty map input */
                {new HashMap()},
                /* null input*/
                {null},
                /* proxy input */
                {pm.newMapProxy(HashMap.class, String.class, String.class, null, false)}

        });
    }

    @Before
    public void SetUp() {
        this.proxyManager = new ProxyManagerImpl();
    }

    @Test
    public void Test() {
        Map map = this.proxyManager.copyMap(this.map);
        assertMapsEqual(this.map, map);
    }

}
