package com.epam.multithreading.сreator;

import com.epam.multithreading.entity.CashBox;
import com.epam.multithreading.entity.Client;
import com.epam.multithreading.singleton.CashBoxList;
import com.epam.multithreading.singleton.ClientList;

import java.util.List;

public class AddingInformationToLists {

    private CashBoxList cashBoxList = CashBoxList.getInstance();
    private ClientList clientList = ClientList.getInstance();

    public void createList(List<Integer> dataList) {
        for (int index = 0; index < dataList.get(0); index++) {
            CashBox cashBox = new CashBox();
            cashBoxList.add(cashBox);
        }

        for (int index = 0; index < dataList.get(1); index++) {
            Client client = new Client();

//            clientList.addClient(client);
        }

        clientList.setCountPreOrderClient(dataList.get(2));
    }
}
