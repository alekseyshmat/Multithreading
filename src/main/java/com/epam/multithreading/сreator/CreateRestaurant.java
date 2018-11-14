package com.epam.multithreading.сreator;

import com.epam.multithreading.entity.CashBox;
import com.epam.multithreading.entity.Client;
import com.epam.multithreading.singleton.CashBoxList;
import com.epam.multithreading.singleton.ClientList;

import java.util.List;

public class CreateRestaurant {

    private CashBoxList cashBoxList = CashBoxList.getInstance();
    private ClientList clientList = ClientList.getInstance();

    public boolean createList(List<List<Integer>> dataList) {
        for (List<Integer> data : dataList) {
            for (int index = 0; index < data.get(0); index++) {
                CashBox cashBox = new CashBox();
                cashBoxList.add(cashBox);
            }

            for (int index = 0; index < data.get(1); index++) {
                Client client = new Client();
                clientList.add(client);
            }
        }
        return true;
    }
}
