package com.epam.multithreading.parser;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FIleParserTest {

    private FileParser fileParser;

    @BeforeClass
    public void setUp() {
        fileParser = new FileParser();
    }

    @DataProvider(name = "dataForParsingLinesTestPositive")
    public Object[][] dataForParsingLinesTestPositive() {
        return new Object[][]{
                {
                        Arrays.asList("4 200", "7 600"),
                        Arrays.asList(                                              //expected list
                                Arrays.asList(4, 200),
                                Arrays.asList(7, 600)
                        )
                }
        };
    }

    @DataProvider(name = "dataForParsingLinesTestNegative")
    public Object[][] dataForParsingLinesTestNegative() {
        return new Object[][]{
                {
                        Arrays.asList("-5 s2", "4 5 6"),
                        Collections.emptyList()
                }
        };
    }

    @Test(dataProvider = "dataForParsingLinesTestPositive")
    public void parsingLinesTestPositive(List<String> lines, List<List<Integer>> expectedResult) {
        List<List<Integer>> actual = fileParser.parsingLines(lines);

        Assert.assertEquals(actual, expectedResult);
    }

    @Test(dataProvider = "dataForParsingLinesTestNegative")
    public void parsingLinesTestNegative(List<String> lines, List<List<Integer>> expectedResult) {
        List<List<Integer>> actual = fileParser.parsingLines(lines);

        Assert.assertEquals(actual, expectedResult);
    }
}
