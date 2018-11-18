package com.epam.multithreading.main;

import com.epam.multithreading.entity.CashBox;
import com.epam.multithreading.entity.Client;
import com.epam.multithreading.entity.Status;
import com.epam.multithreading.exception.ReadingFileException;
import com.epam.multithreading.parser.FileParser;
import com.epam.multithreading.reader.DataReader;
import com.epam.multithreading.singleton.CashBoxList;
import com.epam.multithreading.singleton.ClientList;
import com.epam.multithreading.сreator.CreateOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class DemoMultithreading {

    private CashBoxList cashBoxList = CashBoxList.getInstance();
    private ClientList clientList = ClientList.getInstance();
    private DataReader dataReader = new DataReader();
    private FileParser fileParser = new FileParser();
    private CreateOrder createOrder = new CreateOrder();

    public void create(String path) {
        List<String> values = null;
        try {
            values = dataReader.readingLines(path);
        } catch (ReadingFileException e) {
            e.printStackTrace();
        }
        List<Integer> inputData = fileParser.parsingLines(values);

        for (int index = 0; index < inputData.get(0); index++) {
            CashBox cashBox = new CashBox();
            cashBoxList.add(cashBox);
        }

        int countCashBox = inputData.get(0);
        int countClients = inputData.get(1);
        List<List<Client>> clients = createOrder.create(countClients, countCashBox);
        clientList.setClientList(clients);

        clientList.setCountPreOrderClient(inputData.get(2));

    }

    private List<Semaphore> createSemaphore(ClientList clientList) {
        List<Semaphore> semaphores = new ArrayList<>();

        for (int i = 0; i < clientList.sizeClientList(); i++) {
            semaphores.add(new Semaphore(1));
        }
        return semaphores;
    }

    public void startPreOrderThread(ClientList clientList) {
        List<Semaphore> semaphores = createSemaphore(clientList);
        int preOrderPerson = clientList.getCountPreOrderClient();

        IntStream.range(0, preOrderPerson).
                map(i -> new Random().nextInt(clientList.sizeClientList())).
                forEachOrdered(randomCashBox -> new Thread(
                        new Client(
                                semaphores,
                                randomCashBox,
                                Status.PREORDER)
                ).start());
    }

    public void startClientThread(ClientList clientList) {
        List<List<Client>> orders = clientList.getClientList();
        List<Semaphore> semaphores = createSemaphore(clientList);

        AtomicInteger currentClientId = new AtomicInteger();
        int maxValue = createOrder.getMaxPersonInOrder();

        while (currentClientId.get() != maxValue) {
            int countOrder = clientList.sizeClientList();

            for (int cashId = 0; cashId < countOrder; cashId++) {
                if (currentClientId.get() < clientList.getClientList().
                        get(cashId).size()) {
                    Client client = clientList.get(orders, cashId, currentClientId.get());

                    new Thread(
                            new Client(
                                    semaphores,
                                    cashId,
                                    client.getClientId(),
                                    Status.LIVEQUEUE)
                    ).start();
                }
            }
            currentClientId.getAndIncrement();
        }
    }
}
