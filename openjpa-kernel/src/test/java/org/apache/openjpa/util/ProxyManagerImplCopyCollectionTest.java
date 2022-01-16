package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.collections.LinkedMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ProxyManagerImplCopyCollectionTest {
    ProxyManager proxyManager;
    Collection collection;

    public ProxyManagerImplCopyCollectionTest(Collection collection) {
        this.collection = collection;
    }

    private static void assertListsEqual(List l1, List l2) {
        if (l1 == null) {
            assertNull(l2);
            return;
        }
        if (l2 == null) {
            assertNull(l1);
            return;
        }
        assertEquals(l1.size(), l2.size());
        for (int i = 0; i < l1.size(); i++)
            assertTrue(l1.get(i) + " != " + l2.get(i), l1.get(i) == l2.get(i));
    }


    public static Collection create(Collection collection) {
        if (collection == null) {
            return null;
        }
        /* populate with random data.*/
        collection.add(1);
        collection.add("foo");
        collection.add(99L);
        collection.add("bar");
        collection.add((short) 50);
        return collection;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> testingSet() {
        ProxyManagerImpl pm = new ProxyManagerImpl();
        return Arrays.asList(new Object[][] {
                /* valid input */
                {create(new LinkedList())},
                {create(new ArrayList())},
                {pm.newCollectionProxy(ArrayList.class, null, null, false)},
                {null},

        });
    }

    @Before
    public void SetUp() {
        this.proxyManager = new ProxyManagerImpl();
    }

    @Test
    public void Test() {
        Collection collection = this.proxyManager.copyCollection(this.collection);
        assertListsEqual((List) this.collection, (List) collection);
    }

}
