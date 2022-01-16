package org.apache.openjpa.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ProxyManagerImplCopyArrayTest {
    ProxyManager proxyManager;
    Object orig;
    Exception expectedException;

    public ProxyManagerImplCopyArrayTest(Object orig, Exception expectedException) {
        this.orig = orig;
        this.expectedException = expectedException;
    }

    @Before
    public void SetUp() {
        this.proxyManager = new ProxyManagerImpl();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testingSet() {
        return Arrays.asList(new Object[][] {
                /* valid input */
                {new Integer[]{1, 2, 3, 4, 5}, null},
                {new Double[]{1.0, 2.0, 3.0, 4.0, 5.0}, null},
                {new String[]{"abc", "def", "ghi", "jkl", "mno"}, null},
                /* empty input */
                {new Integer[]{}, null},
                {new Double[]{}, null},
                {new String[]{}, null},
                /* invalid input */
                {new Object(), new UnsupportedException()},
                {null, null}
        });
    }

    @Test
    public void Test() {
        try {
            Object origCopy = proxyManager.copyArray(orig);
            Assert.assertArrayEquals((Object[]) orig, (Object[]) origCopy);
        } catch (UnsupportedException e) {
            Assert.assertEquals(this.expectedException.getClass(), e.getClass());
        }

    }

}
