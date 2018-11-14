package com.epam.multithreading.entity;

import com.epam.multithreading.exception.ResourсeException;

import java.util.concurrent.atomic.AtomicInteger;

public class Client implements Runnable {

    private static AtomicInteger counter = new AtomicInteger(0);
    private int clientId;
    private State cashboxState;

    private boolean reading;

    private CashBoxPool<CashBox> pool;

    public Client() {
    }

    public Client(CashBoxPool<CashBox> pool) {
        this.pool = pool;
        clientId = counter.get();
        incrementId();
    }


    public long getId() {
        return clientId;
    }

    @Override
    public void run() {
        CashBox cashBox = null;
        try {
            cashBox = pool.getResource(5000);
            reading = true;
            System.out.println("Client " + (getId() + 1) + " JOIN cashBox " + (cashBox.getCashBoxId() + 1));
            cashBox.using();
        } catch (ResourсeException e) {
            System.out.println("Client " + (getId() + 1) + " lost ->" + e.getMessage());
        } finally {
            if (cashBox != null) {
                reading = false;
                System.out.println("Client " + (getId() + 1) + " OUT cashBox " + (cashBox.getCashBoxId() + 1));
                pool.returnResource(cashBox);
            }
        }
    }

    public boolean isReading() {
        return reading;
    }

    @Override
    public String toString() {
        return "Client " + clientId;
    }

    private void incrementId() {
        counter.incrementAndGet();
    }


}
