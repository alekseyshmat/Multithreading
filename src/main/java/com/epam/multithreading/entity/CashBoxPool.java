package com.epam.multithreading.entity;

import com.epam.multithreading.exception.ResourсeException;
import com.epam.multithreading.singleton.CashBoxList;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CashBoxPool<T> {
    private static CashBoxList cashBoxList = CashBoxList.getInstance();
    private static final int poolSize = cashBoxList.size();
    private final Semaphore semaphore = new Semaphore(poolSize, true);
    private final Queue<T> resources = new LinkedList<T>();

    public CashBoxPool(Queue<T> source) {
        resources.addAll(source);
    }

    public T getResource(long maxWaitMillis) throws ResourсeException {
        try {
            if (semaphore.tryAcquire(maxWaitMillis, TimeUnit.MILLISECONDS)) {
                T res = resources.poll();
                return res;
            }
        } catch (InterruptedException e) {
            throw new ResourсeException(e);
        }
        throw new ResourсeException(": превышено  время  ожидания");
    }

    public void returnResource(T res) {
        resources.add(res);
        semaphore.release();
    }

}
