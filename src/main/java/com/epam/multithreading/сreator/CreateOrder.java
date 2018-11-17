package com.epam.multithreading.сreator;

import com.epam.multithreading.singleton.CashBoxList;
import com.epam.multithreading.singleton.ClientList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CreateOrder {
    private static final Logger LOGGER = LogManager.getLogger(CreateOrder.class);
    private CashBoxList cashBoxList = CashBoxList.getInstance();
    private ClientList clientList = ClientList.getInstance();

    public List<List<Integer>> create() {
        List<List<Integer>> ordersList = new ArrayList<>();
        List<Integer> tempList;

        int countCashBox = cashBoxList.size();
        int countClient = clientList.sizeClientList();
        int mod = countClient % countCashBox;
        int ratio = countClient / countCashBox;

        int currentCashBox = 0;
        int currentClient = 0;

        do {
            tempList = new ArrayList<>();
            int clientIndex = 0;
            while (clientIndex < ratio) {
                tempList.add(currentClient++);
                clientIndex++;
            }

            if (currentCashBox == countCashBox - 1) {
                int i = ratio;
                while (i++ < ratio + mod) {
                    tempList.add(currentClient++);
                }
            }
            ordersList.add(tempList);
            LOGGER.info("Create order #" + (currentCashBox + 1) +
                    " from " + tempList.size() + " person");
            currentCashBox++;
        } while (countClient != currentClient);

        return ordersList;
    }
}
