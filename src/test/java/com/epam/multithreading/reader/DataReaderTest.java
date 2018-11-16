package com.epam.multithreading.reader;

import com.epam.multithreading.exception.ReadingFileException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class DataReaderTest {

    private static final String VALID_PATH = "src/test/resources/testFile.txt";
    private DataReader dataReader;

    @BeforeClass
    public void setUp() {
        dataReader = new DataReader();
    }

    @DataProvider
    public static Object[][] dataForReadingLinesTestPositive() {
        return new Object[][]{
                {
                        Arrays.asList("4 20 10",  //input lines
                                "s 2 s5",
                                "7 200 65")
                }
        };
    }

    @Test(dataProvider = "dataForReadingLinesTestPositive")
    public void readingLinesTestPositive(List<String> expectedResult) throws ReadingFileException {
        List<String> actual = dataReader.readingLines(VALID_PATH);

        Assert.assertEquals(actual, expectedResult);
    }
}
