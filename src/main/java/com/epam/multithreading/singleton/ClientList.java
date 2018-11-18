package com.epam.multithreading.singleton;

import com.epam.multithreading.entity.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientList {

    private static ClientList instance;
    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static Lock lockGet = new ReentrantLock();
    private static Lock lock = new ReentrantLock();

    private List<List<Client>> clientList = new ArrayList<>();
    private int countPreOrderClient;

    private ClientList() {
    }

    public static ClientList getInstance() {
        if (!initialized.get()) {
            try {
                lock.lock();
                if (!initialized.get()) {
                    instance = new ClientList();
                    initialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Client get(List<List<Client>> client, int indexOrd, int indexClient) {
        lockGet.lock();
        try {
            return client.get(indexOrd).get(indexClient);
        } finally {
            lockGet.unlock();
        }
    }

    public int sizeClientList() {
        return clientList.size();
    }

    public List<List<Client>> getClientList() {
        return clientList;
    }

    public void setClientList(List<List<Client>> clientList) {
        this.clientList = clientList;
    }

    public int getCountPreOrderClient() {
        return countPreOrderClient;
    }

    public void setCountPreOrderClient(int countPreOrderClient) {
        this.countPreOrderClient = countPreOrderClient;
    }
}
