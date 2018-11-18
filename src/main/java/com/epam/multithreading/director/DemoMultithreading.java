package com.epam.multithreading.director;

import com.epam.multithreading.entity.Client;
import com.epam.multithreading.exception.ReadingFileException;
import com.epam.multithreading.parser.FileParser;
import com.epam.multithreading.reader.DataReader;
import com.epam.multithreading.singleton.ClientList;
import com.epam.multithreading.сreator.CreateOrder;
import com.epam.multithreading.сreator.CreateThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DemoMultithreading {

    private static final Logger LOGGER = LogManager.getLogger(DemoMultithreading.class);
    private static AtomicInteger counter = new AtomicInteger(0);
    private static Lock lockGet = new ReentrantLock();

    private ClientList clientList = ClientList.getInstance();
    private DataReader dataReader = new DataReader();
    private FileParser fileParser = new FileParser();
    private CreateOrder createOrder = new CreateOrder();
    private CreateThread createThread = new CreateThread();

    private Thread[] threads;

    public void createListClients(String path) {
        List<String> values = null;
        try {
            values = dataReader.readingLines(path);
        } catch (ReadingFileException e) {
            LOGGER.error("File is not found");
        }

        List<Integer> inputData = fileParser.parsingLines(values);

        int countCashBox = inputData.get(0);
        int countClients = inputData.get(1);
        int countPreOrderClient = inputData.get(2);

        List<List<Client>> clients = createOrder.create(countClients, countCashBox);

        clientList.setClientList(clients);
        clientList.setCountPreOrderClient(countPreOrderClient);
        threads = new Thread[countClients + countPreOrderClient];
    }


    public void startApplication(ClientList clientList) {
        List<List<Client>> orders = clientList.getClientList();

        AtomicInteger currentLiveClientId = new AtomicInteger();
        AtomicInteger currentPreOrderClient = new AtomicInteger();
        int maxValue = createOrder.getMaxPersonInOrder();

        while (currentLiveClientId.get() != maxValue) {
            int countOrder = clientList.sizeClientList();
            int preOrderPerson = clientList.getCountPreOrderClient();

            for (int cashId = 0; cashId < countOrder; cashId++) {
                if (currentLiveClientId.get() < orders.get(cashId).size()) {
                    threads[incrementId()] =
                            createThread.createLiveQueueThread(clientList, cashId, currentLiveClientId);

                    if (currentPreOrderClient.get() != preOrderPerson) {
                        threads[incrementId()] =
                                createThread.createPreOrderThread(clientList, currentPreOrderClient);
                        currentPreOrderClient.getAndIncrement();
                    }
                }
            }
            currentLiveClientId.getAndIncrement();
        }
        startThreads(threads);
    }

    private void startThreads(Thread[] threads) {
        for (Thread thread : threads) {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.error("Interrupted " + Thread.currentThread());
            }
        }
    }

    private int incrementId() {
        lockGet.lock();
        try {
            return counter.getAndIncrement();
        } finally {
            lockGet.unlock();
        }
    }
}
