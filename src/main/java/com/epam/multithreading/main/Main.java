package com.epam.multithreading.main;

import com.epam.multithreading.entity.CashBox;
import com.epam.multithreading.entity.Client;
import com.epam.multithreading.entity.Status;
import com.epam.multithreading.singleton.CashBoxList;
import com.epam.multithreading.singleton.ClientList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        DemoMultithreading demo = new DemoMultithreading();
        String path = "src/test/resources/test.txt";
        demo.create(path);

       /* List<Integer> listRandom = new ArrayList<>();
        int countOfPreOrderClients = clientList.getCountPreOrderClient();
        while (listRandom.size() < countOfPreOrderClients) {
            int number = new Random().nextInt(clientList.sizeClientList());
            if (!listRandom.contains(number)) {
                listRandom.add(number);
            }
        }*/

        ClientList clientList = ClientList.getInstance();
        List<Semaphore> semaphores = new ArrayList<>();

        for (int i = 0; i < clientList.sizeClientList(); i++) {
            semaphores.add(new Semaphore(1));
        }

        List<List<Client>> q = clientList.getClientList();
        int ind = 0;
        do {
            for (int i = 0; i < clientList.sizeClientList(); i++) {
                new Thread(new Client(Status.NORMALORDER, semaphores, clientList.get(q, i, ind).getId(), i)).start();
            }
            ind++;

        } while (ind != 3);

    }
}
