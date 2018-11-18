package com.epam.multithreading.main;

import com.epam.multithreading.entity.Client;
import com.epam.multithreading.entity.Status;
import com.epam.multithreading.singleton.ClientList;
import com.epam.multithreading.сreator.CreateOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        DemoMultithreading demo = new DemoMultithreading();
        String path = "src/test/resources/test.txt";
        demo.create(path);


        ClientList clientList = ClientList.getInstance();
        List<Semaphore> semaphores = new ArrayList<>();
        for (int i = 0; i < clientList.sizeClientList(); i++) {
            semaphores.add(new Semaphore(1));
        }

        int maxValue = CreateOrder.getMax();
        List<List<Client>> q = clientList.getClientList();
        int ind = 0;
        while (ind != maxValue) {
            for (int i = 0; i < clientList.sizeClientList(); i++) {
                if (ind < clientList.getClientList().get(i).size()) {
                    new Thread(new Client(Status.NORMALORDER, semaphores, clientList.get(q, i, ind).getId(), i)).start();
                }
            }
            ind++;
        }

        for (int i = 0; i < clientList.getCountPreOrderClient(); i++) {

            int randomCashBox = new Random().nextInt(clientList.sizeClientList());
            new Thread(new Client(Status.PREORDER, semaphores, randomCashBox)).start();
        }
    }
}
