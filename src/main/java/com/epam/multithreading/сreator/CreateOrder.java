package com.epam.multithreading.сreator;

import com.epam.multithreading.entity.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateOrder {

    private static final Logger LOGGER = LogManager.getLogger(CreateOrder.class);
    private int maxPersonInOrder;

    public List<List<Client>> create(int clients, int cashBox) {
        List<List<Client>> ordersList = new ArrayList<>();
        List<Client> tempOrderList;

        int remainderOfDividing = clients % cashBox;
        int clientsInCashBox = clients / cashBox;

        AtomicInteger currentCashBox = new AtomicInteger();
        AtomicInteger currentClient = new AtomicInteger();

        while (clients != currentClient.get()) {
            tempOrderList = new ArrayList<>();
            AtomicInteger clientIndex = new AtomicInteger();

            while (clientIndex.getAndIncrement() < clientsInCashBox) {
                tempOrderList.add(new Client());
                currentClient.getAndIncrement();
            }

            if (currentCashBox.get() == cashBox - 1) {
                AtomicInteger index = new AtomicInteger(clientsInCashBox);

                while (index.getAndIncrement() < clientsInCashBox + remainderOfDividing) {
                    tempOrderList.add(new Client());
                    currentClient.getAndIncrement();
                }
            }

            ordersList.add(tempOrderList);
            LOGGER.info("Create order #" + (currentCashBox.get() + 1) +
                    " from " + tempOrderList.size() + " person");
            currentCashBox.getAndIncrement();

            calculateMaxPersonInOrder(tempOrderList.size());
        }

        return ordersList;
    }

    private void calculateMaxPersonInOrder(int listValue) {
        if (maxPersonInOrder < listValue) {
            maxPersonInOrder = listValue;
        }
    }

    public int getMaxPersonInOrder() {
        return maxPersonInOrder;
    }
}
