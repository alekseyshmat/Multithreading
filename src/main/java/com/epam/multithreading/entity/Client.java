package com.epam.multithreading.entity;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Client implements Runnable {

    private Status status;
    private static AtomicInteger counter = new AtomicInteger(0);
    private long clientId;
    private List<Semaphore> sem;
    private int cashId;

    public Client() {
        clientId = counter.get();
        incrementId();
    }

    public Client(Status status, List<Semaphore> sem, long id, int cashId) {
        this.status = status;
        clientId = id;
        this.sem = sem;
        this.cashId = cashId;
    }

    public Client(Status status, List<Semaphore> sem, int cashId) {
        this.status = status;
        this.sem = sem;
        this.cashId = cashId;
    }

    public long getId() {
        return clientId;
    }

    @Override
    public void run() {
        try {
            if (status == Status.NORMALORDER) {
                sem.get(cashId).acquire();
                System.out.println("Client " + (getId() + 1) +
                        " JOIN cashBox " + (cashId + 1));
            } else if (status == Status.PREORDER) {
                sem.get(cashId).acquire();
                System.out.println("Client in cashBox #" + (cashId + 1) + " wait client with pre-order");
                System.out.println("Client with pre-order " +
                        " JOIN cashBox " + (cashId + 1));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (status == Status.NORMALORDER) {
                System.out.println("Client " + (getId() + 1) +
                        " OUT cashBox " + (cashId + 1));
                sem.get(cashId).release();
            } else if (status == Status.PREORDER) {
                System.out.println("Client with pre-order" +
                        " OUT cashBox " + (cashId + 1));
                sem.get(cashId).release();
            }
        }
    }

    @Override
    public String toString() {
        return "Client " + clientId;
    }

    private void incrementId() {
        counter.incrementAndGet();
    }
}
