package com.epam.multithreading.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Client implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(Client.class);
    private static AtomicInteger counter = new AtomicInteger(0);
    private List<Semaphore> semaphores;
    private int cashId;
    private int clientId;
    private Status status;

    public Client() {
        clientId = counter.get();
        incrementId();
    }

    public Client(List<Semaphore> semaphores, int cashId, int clientId, Status status) {
        this.semaphores = semaphores;
        this.cashId = cashId;
        this.clientId = clientId;
        this.status = status;
    }

    public int getClientId() {
        return clientId;
    }

    private void incrementId() {
        counter.incrementAndGet();
    }

    @Override
    public void run() {
        try {
            if (status == Status.LIVEQUEUE) {
                semaphores.get(cashId).acquire();
                LOGGER.info("Client " + (getClientId() + 1) +
                        " JOIN cashBox " + (cashId + 1));
            } else if (status == Status.PREORDER) {
                semaphores.get(cashId).acquire();
                LOGGER.info("Client in cashBox #" + (cashId + 1) + " wait client with pre-order");
                LOGGER.info("ONLINE Client " + (getClientId() + 1) + " with pre-order " +
                        " JOIN cashBox " + (cashId + 1));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Interrupted " + Thread.currentThread());
        } finally {
            if (status == Status.LIVEQUEUE) {
                LOGGER.info("Client " + (getClientId() + 1) +
                        " OUT cashBox " + (cashId + 1));
                semaphores.get(cashId).release();
            } else if (status == Status.PREORDER) {
                LOGGER.info("ONLINE Client " + (getClientId() + 1) + " with pre-order" +
                        " OUT cashBox " + (cashId + 1));
                semaphores.get(cashId).release();
            }
        }
    }

    @Override
    public String toString() {
        return "Client " + clientId;
    }
}
