package com.epam.multithreading.singleton;

import com.epam.multithreading.entity.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientList {

    private static ClientList instance;
    private List<List<Client>> clientList = new ArrayList<>();
    private int countPreOrderClient;

    public void setClientList(List<List<Client>> clientList) {
        this.clientList = clientList;
    }

    public List<List<Client>> getClientList() {
        return clientList;
    }

    private static AtomicBoolean initialized = new AtomicBoolean(false);

    private static Lock lock = new ReentrantLock();

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

    public void addClient(Client client, int indexOrder) {
        lock.lock();
        try {
            clientList.get(indexOrder).add(client);
        } finally {
            lock.unlock();
        }
    }

    public Client get(int indexOrder, int indexClient) {
        lock.lock();
        try {
            return clientList.get(indexOrder).get(indexClient);
        } finally {
            lock.unlock();
        }
    }

    public Client get(List<List<Client>> client, int indexOrd, int indexClient){
        lock.lock();
        try {
            return client.get(indexOrd).get(indexClient);
        } finally {
            lock.unlock();
        }
    }

    public void deleteClient(int indexOrder, int indexClient){
        lock.lock();
        try {
            clientList.remove(get(indexOrder,indexClient));
        } finally {
            lock.unlock();
        }
    }

    public int sizeClientList() {
        return clientList.size();
    }

    public int getCountPreOrderClient() {
        return countPreOrderClient;
    }

    public void setCountPreOrderClient(int countPreOrderClient) {
        this.countPreOrderClient = countPreOrderClient;
    }
}
