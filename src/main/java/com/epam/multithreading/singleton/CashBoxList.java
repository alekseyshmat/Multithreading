package com.epam.multithreading.singleton;

import com.epam.multithreading.entity.CashBox;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CashBoxList {

    private static CashBoxList instance;
    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();
    private static Lock lockAdd = new ReentrantLock();

    private LinkedList<CashBox> cashBoxList = new LinkedList<>();

    private CashBoxList() {
    }

    public static CashBoxList getInstance() {
        if (!initialized.get()) {
            try {
                lock.lock();
                if (!initialized.get()) {
                    instance = new CashBoxList();
                    initialized.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void add(CashBox cashBox) {
        lockAdd.lock();
        try {
            cashBoxList.add(cashBox);
        } finally {
            lockAdd.unlock();
        }
    }

    public int size() {
        return cashBoxList.size();
    }
}
