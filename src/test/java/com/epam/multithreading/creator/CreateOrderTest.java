package com.epam.multithreading.creator;

import com.epam.multithreading.сreator.CreateOrder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateOrderTest {

    private CreateOrder createOrder;

    @BeforeClass
    public void setUp() {
        createOrder = new CreateOrder();
    }

    @DataProvider(name = "dataForCreateTestPositive")
    public Object[][] dataForCreateTestPositive() {
        return new Object[][]{
                {
                        20,
                        3,
                        3
                }
        };
    }

    @Test(dataProvider = "dataForCreateTestPositive")
    public void createPositiveTest(int clients, int cashBox, int expectedSize) {
        int actualSize = createOrder.create(clients, cashBox).size();
        Assert.assertEquals(actualSize, expectedSize);
    }
}
