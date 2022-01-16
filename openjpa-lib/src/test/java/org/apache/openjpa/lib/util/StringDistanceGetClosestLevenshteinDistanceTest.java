package org.apache.openjpa.lib.util;

import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class StringDistanceGetClosestLevenshteinDistanceTest {
    private String str;
    private Collection<String> candidates;
    private int threshold;
    private String expectedResult;

    public StringDistanceGetClosestLevenshteinDistanceTest(String str, Collection<String> candidates, int threshold, String expectedResult) {
        this.str = str;
        this.candidates = candidates;
        this.threshold = threshold;
        this.expectedResult = expectedResult;
    }

    private static Collection<String> getCollectionFromArray(String[] candidates) {
        return Arrays.stream(candidates).collect(Collectors.toList());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testingSet() {
        String[] testCandidates = {"", "sitting", "Saturday", "saturday", "abc"};
        Collection<String> candidates = getCollectionFromArray(testCandidates);
        Collection<String> empty = new LinkedList<>();
        return Arrays.asList(new Object[][]{
                {"", null, 0, null},
                {"", empty, 0, null},
                {"", candidates, 0, ""},
                {"cde", candidates, -1, null},
                {"cde", candidates, Integer.MAX_VALUE, ""},
                {"sitting", candidates, 0, "sitting"},
                {"Saturday", candidates, 0, "Saturday"},
                {"saturday", candidates, 0, "saturday"},
                {"abc", candidates, 0, "abc"},
                {"baturday", candidates, 1, "Saturday"},
                {"", candidates, 8, ""},

        });
    }


    @Test
    public void Test() {
        String result = StringDistance.getClosestLevenshteinDistance(str, candidates, threshold);
        Assert.assertEquals(expectedResult, result);
    }
}
