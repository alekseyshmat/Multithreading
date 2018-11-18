package com.epam.multithreading.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class CashBox{

    private static AtomicInteger counter = new AtomicInteger();
    private int cashBoxId;

    public CashBox() {
        cashBoxId = counter.get();
        incrementId();
    }

    public int getCashBoxId() {
        return cashBoxId;
    }


    @Override
    public String toString() {
        return "CashBoxId " + cashBoxId;
    }

    private void incrementId() {
        counter.incrementAndGet();
    }
}
