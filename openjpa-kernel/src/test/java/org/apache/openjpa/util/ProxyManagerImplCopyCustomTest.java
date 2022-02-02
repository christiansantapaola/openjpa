package org.apache.openjpa.util;

import org.apache.openjpa.enhance.PersistenceCapable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import javax.swing.text.ParagraphView;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ProxyManagerImplCopyCustomTest {
    private ProxyManager proxyManager;
    private Object customObject;
    private boolean proxyable;

    public ProxyManagerImplCopyCustomTest(Object customObject, boolean proxyable) {
        this.customObject = customObject;
        this.proxyable = proxyable;
    }

    /**
     * Used to test custom bean handling.
     */
    public static class CustomBean {
        private String _string;
        private int _number;

        public String getString() {
            return _string;
        }

        public void setString(String str) {
            _string = str;
        }

        public int getNumber() {
            return _number;
        }

        public void setNumber(int number) {
            _number = number;
        }

        @Override
        public boolean equals(Object o) {

            // If the object is compared with itself then return true
            if (o == this) {
                return true;
            }
            if (o == null) {
                return false;
            }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
            if (!(o instanceof CustomBean)) {
                return false;
            }

            // typecast o to Complex so that we can compare data members
            CustomBean c = (CustomBean) o;

            // Compare the data members and return accordingly
            if (this._string != null && c._string != null) {
                return this._string.equals(c._string) && this._number == c._number;
            } else {
                return this._string == null && c._string == null && this._number == c._number;
            }
        }

    }

    /**
     * Used to non-proxyable custom bean handling.
     */
    public static class NonproxyableBean extends CustomBean {

        public NonproxyableBean(long x) {
            // single non-default, non-copy constructor
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testingSet() {
        ProxyManagerImpl pm = new ProxyManagerImpl();
        return Arrays.asList(new Object[][] {
                /* valid input */
                {pm.newCollectionProxy(ArrayList.class, null, null, false), true},
                {pm.newCalendarProxy(GregorianCalendar.class, TimeZone.getTimeZone("CST")), true},
                {pm.newMapProxy(HashMap.class, String.class, String.class, null, false), true},
                {pm.newDateProxy(Date.class), true},
                {pm.newCustomProxy(Integer.class, false), true},
                {new LinkedList<>(), true},
                {new HashMap<>(), true},
                {new Date(1999), true},
                {new GregorianCalendar(), true},
                {new CustomBean(), true},
                /* invalid input */
                {new NonproxyableBean(0), false},
                {Mockito.mock(PersistenceCapable.class), false},
                {null, false}
        });
    }





    @Before
    public void setUp() {
        this.proxyManager = new ProxyManagerImpl();
    }

    @Test
    public void Test() {
        Object object = this.proxyManager.copyCustom(this.customObject);
        if (this.proxyable) {
            Assert.assertEquals(this.customObject, object);
        } else {
            Assert.assertNull(object);
        }
    }

}
