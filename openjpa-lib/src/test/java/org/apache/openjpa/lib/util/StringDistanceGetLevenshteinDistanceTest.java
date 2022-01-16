package org.apache.openjpa.lib.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class StringDistanceGetLevenshteinDistanceTest {
    private String subject;
    private String tester;
    private int expectedResult;

    public StringDistanceGetLevenshteinDistanceTest(String s, String t, int expectedResult) {
        this.subject = s;
        this.tester = t;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testingSet() {
        return Arrays.asList(new Object[][]{
                {"", "", 0},
                {"", "abc", 3},
                {"abc", "", 3},
                {"kitten", "sitting", 3},
                {"equals", "equals", 0}
        });
    }

    @Test
    public void Test() {
        int result = StringDistance.getLevenshteinDistance(subject, tester);
        Assert.assertEquals(expectedResult, result);
    }
}
