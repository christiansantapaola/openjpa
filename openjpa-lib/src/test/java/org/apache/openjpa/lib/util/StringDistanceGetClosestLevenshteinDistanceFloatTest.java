package org.apache.openjpa.lib.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class StringDistanceGetClosestLevenshteinDistanceFloatTest {
    private String str;
    private Collection<String> candidates;
    private float threshold;
    private String expectedResult;

    public StringDistanceGetClosestLevenshteinDistanceFloatTest(String str, Collection<String> candidates, float threshold, String expectedResult) {
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
                {null, null, 0.0f, null},
                {null, candidates, 0.0f, null},
                {"", null, 0.0f, null},
                {"", empty, 0.0f, null},
                {"", candidates, 0.0f, ""},
                {"cde", candidates, -1.0f, null},
                {"cde", candidates, Float.MAX_VALUE, ""},
                {"sitting", candidates, 0.0f, "sitting"},
                {"Saturday", candidates, 0.0f, "Saturday"},
                {"saturday", candidates, 0.0f, "saturday"},
                {"abc", candidates, 0.0f, "abc"},
                {"baturday", candidates, 1.0f, "Saturday"},
                {"baturday", candidates, 1.5f, "Saturday"},
                {"", candidates, 8.0f, ""},
                {"", candidates, Float.NaN, ""},
                {"notInTestCandidates", candidates, Float.NaN, null},
                {"notInTestCandidates", candidates, Float.POSITIVE_INFINITY, "saturday"},
                {"notInTestCandidates", candidates, Float.NEGATIVE_INFINITY, null},


        });
    }

    @Test
    public void Test() {
        String result = StringDistance.getClosestLevenshteinDistance(str, candidates, threshold);
        Assert.assertEquals(expectedResult, result);
    }
}
