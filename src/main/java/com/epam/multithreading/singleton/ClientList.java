package com.epam.multithreading.singleton;

import com.epam.multithreading.entity.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientList {

    private static ClientList instance;
    private List<Client> clientList = new ArrayList<>();
    private int preOrderClientList;
    private static AtomicBoolean initialized;

    private static Lock lock = new ReentrantLock();

    private ClientList() {
    }

    public static ClientList getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new ClientList();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

   /* public static ClientList getInstance() {
        if (!initialized.get()) {
            try {
                lock.lock();
                if (!initialized.get()) {
                    instance = new ClientList();
//                    instance.init();
                    initialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }*/

    public void addClient(Client client) {
        lock.lock();
        try {
            clientList.add(client);
        } finally {
            lock.unlock();
        }
    }


    public Client get(int index) {
        lock.lock();
        try {
            return clientList.get(index);
        } finally {
            lock.unlock();
        }
    }

    public int sizeClientList() {
        return clientList.size();
    }

    public int getPreOrderClientList() {
        return preOrderClientList;
    }

    public void setPreOrderClientList(int preOrderClientList) {
        this.preOrderClientList = preOrderClientList;
    }
}
