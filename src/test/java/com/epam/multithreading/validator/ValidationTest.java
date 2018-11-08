package com.epam.multithreading.validator;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ValidationTest {

    private Validation validation;

    @BeforeClass
    public void setUp() {
        validation = new Validation();
    }

    @DataProvider(name = "dataForIsValidPositiveTest")
    public Object[][] dataForIsValidPositiveTest() {
        return new Object[][]{
                {
                        "4 5"   //input line
                }
        };
    }

    @DataProvider(name = "dataForIsValidNegativeTest")
    public Object[][] dataForIsValidNegativeTest() {
        return new Object[][]{
                {
                        "45 s2"
                },
                {
                        "-6 4"
                },
                {
                        "5"
                }
        };
    }

    @Test(dataProvider = "dataForIsValidPositiveTest")
    public void isValidPositiveTest(String lines) {
        boolean actual = validation.isValid(lines);

        Assert.assertTrue(actual);
    }

    @Test(dataProvider = "dataForIsValidNegativeTest")
    public void isValidNegativeTest(String lines) {
        boolean actual = validation.isValid(lines);

        Assert.assertFalse(actual);
    }
}
