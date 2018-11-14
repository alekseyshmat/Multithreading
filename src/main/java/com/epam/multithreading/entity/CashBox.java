package com.epam.multithreading.entity;

import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CashBox {

    private static AtomicInteger counter = new AtomicInteger();
    private static Lock addLock = new ReentrantLock();
    private static Lock removeLock = new ReentrantLock();
    private int cashBoxId;
    private Queue<Client> clientQueue;

    public CashBox() {
        cashBoxId = counter.get();
        incrementId();
    }

    public int getCashBoxId() {
        return cashBoxId;
    }

    public void setCashBoxId(int cashBoxId) {
        this.cashBoxId = cashBoxId;
    }

    public void using() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "CashBoxId " + cashBoxId;
    }

    private void incrementId() {
        counter.incrementAndGet();
    }
}
