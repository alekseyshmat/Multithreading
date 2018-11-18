package com.epam.multithreading.creator;

import com.epam.multithreading.entity.Client;
import com.epam.multithreading.сreator.CreateOrder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
                        Arrays.asList(
                                Collections.singletonList("Client 0, Client 1, Client 2, Client 3, Client 4, Client 5"),
                                Arrays.asList(6, 7, 8, 9, 10, 11),
                                Arrays.asList(12, 13, 14, 15, 16, 17, 18, 19)
                        )                                        //expected list

                }
        };
    }

    @Test(dataProvider = "dataForCreateTestPositive")
    public void createPositiveTest(int clients, int cashBox, List<List<Client>> expectedList) {
        List<List<Client>> actualList = createOrder.create(clients, cashBox);
        Assert.assertEquals(actualList, expectedList);
    }
}
