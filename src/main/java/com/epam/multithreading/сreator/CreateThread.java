package com.epam.multithreading.сreator;

import com.epam.multithreading.entity.Client;
import com.epam.multithreading.entity.Status;
import com.epam.multithreading.singleton.ClientList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateThread {
    public CreateThread() {
    }

    public Thread createPreOrderThread(ClientList clientList, AtomicInteger currentPreOrderClient) {
        List<Semaphore> semaphores = createSemaphore(clientList);
        int randomCashBox = new Random().nextInt(clientList.sizeClientList());
        return new Thread(
                new Client(semaphores, randomCashBox, currentPreOrderClient.get(), Status.PREORDER));
    }

    public Thread createLiveQueueThread(ClientList clientList, int cashId, AtomicInteger currentLiveClientId) {
        List<Semaphore> semaphores = createSemaphore(clientList);
        Client client = clientList.get(clientList.getClientList(), cashId, currentLiveClientId.get());

        return new Thread(
                new Client(semaphores, cashId, client.getClientId(), Status.LIVEQUEUE));
    }

    private List<Semaphore> createSemaphore(ClientList clientList) {
        List<Semaphore> semaphores = new ArrayList<>();
        for (int semaphore = 0; semaphore < clientList.sizeClientList(); semaphore++) {
            semaphores.add(new Semaphore(1));
        }
        return semaphores;
    }
}
