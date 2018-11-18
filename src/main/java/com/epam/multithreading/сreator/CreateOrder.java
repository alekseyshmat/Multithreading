package com.epam.multithreading.сreator;

import com.epam.multithreading.entity.Client;
import com.epam.multithreading.singleton.CashBoxList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CreateOrder {
    private static final Logger LOGGER = LogManager.getLogger(CreateOrder.class);
    private CashBoxList cashBoxList = CashBoxList.getInstance();
//    private ClientList clientList = ClientList.getInstance();

    public List<List<Client>> create(int clients) {
        List<List<Client>> ordersList = new ArrayList<>();
        List<Client> tempList;

        int countCashBox = cashBoxList.size();
        int mod = clients % countCashBox;
        int ratio = clients / countCashBox;

        int currentCashBox = 0;
        int currentClient = 0;

        do {
            tempList = new ArrayList<>();
            int clientIndex = 0;
            while (clientIndex++ < ratio) {
                tempList.add(new Client());
                currentClient++;
            }

            if (currentCashBox == countCashBox - 1) {
                int i = ratio;
                while (i++ < ratio + mod) {
                    tempList.add(new Client());
                    currentClient++;
                }
            }
            ordersList.add(tempList);
            LOGGER.info("Create order #" + (currentCashBox + 1) +
                    " from " + tempList.size() + " person");
            currentCashBox++;
        } while (clients != currentClient);

        return ordersList;
    }
}
