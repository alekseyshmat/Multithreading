package com.epam.multithreading.entity;

import com.epam.multithreading.exception.ResourсeException;

import java.util.concurrent.atomic.AtomicInteger;

public class Client implements Runnable {

    private static AtomicInteger counter = new AtomicInteger(0);
    private int clientId;
    private State cashboxState;
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    private boolean reading;

    private CashBoxPool<CashBox> pool;

    public Client() {
    }

    public Client(CashBoxPool<CashBox> pool, Status status) {
        this.pool = pool;
        this.status = status;
        clientId = counter.get();
        incrementId();
    }


    public long getId() {
        return clientId;
    }

    public void setCashboxState(State cashboxState) {
        this.cashboxState = cashboxState;
    }

    @Override
    public void run() {
        CashBox cashBox = null;
        try {
            if (status == Status.NORMALORDER) {
                cashBox = pool.getResource(7000);
                reading = true;
                System.out.println("Client " + (getId() + 1) + " JOIN cashBox " + (cashBox.getCashBoxId() + 1));
                cashBox.using();
            } else if (status == Status.PREORDER) {
                cashBox = pool.getResource(7000);
                reading = true;
                cashBox.using();
            }
        } catch (ResourсeException e) {
            System.out.println("Client " + (getId() + 1) + " lost ->" + e.getMessage());
        } finally {
            if (cashBox != null && status == Status.NORMALORDER) {
                reading = false;
                System.out.println("Client " + (getId() + 1) + " OUT cashBox " + (cashBox.getCashBoxId() + 1));
                pool.returnResource(cashBox);
            }else if (cashBox != null && status == Status.PREORDER){
                reading = false;
                System.out.println("Client " + (getId() + 1) + " WITHOUT QUEUE OUT cashBox " + (cashBox.getCashBoxId() + 1));
                pool.returnResource(cashBox);
            }
        }
    }

    public void preOrder() {

    }

    public void addOrder(CashBox cashBox) throws ResourсeException {

    }

    public void remove(CashBox cashBox) {
        if (cashBox != null) {
            reading = false;
            System.out.println("Client " + (getId() + 1) + " OUT cashBox " + (cashBox.getCashBoxId() + 1));
            pool.returnResource(cashBox);
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
