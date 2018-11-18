package com.epam.multithreading.singleton;

import com.epam.multithreading.entity.CashBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CashBoxList {

    private static CashBoxList instance;
    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();

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
        lock.lock();
        try {
            cashBoxList.add(cashBox);
        } finally {
            lock.unlock();
        }
    }

    public Queue<CashBox> getCashBoxList() {
        lock.lock();
        try {
            return cashBoxList;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return cashBoxList.size();
    }
}
