package com.epam.multithreading.director;

import com.epam.multithreading.entity.Client;
import com.epam.multithreading.entity.Status;
import com.epam.multithreading.exception.ReadingFileException;
import com.epam.multithreading.parser.FileParser;
import com.epam.multithreading.reader.DataReader;
import com.epam.multithreading.singleton.ClientList;
import com.epam.multithreading.сreator.CreateOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoMultithreading {

    private ClientList clientList = ClientList.getInstance();
    private DataReader dataReader = new DataReader();
    private FileParser fileParser = new FileParser();
    private CreateOrder createOrder = new CreateOrder();

    public void createListClients(String path) {
        List<String> values = null;
        try {
            values = dataReader.readingLines(path);
        } catch (ReadingFileException e) {
            e.printStackTrace();
        }

        List<Integer> inputData = fileParser.parsingLines(values);

        int countCashBox = inputData.get(0);
        int countClients = inputData.get(1);
        int countPreOrderClient = inputData.get(2);

        List<List<Client>> clients = createOrder.create(countClients, countCashBox);

        clientList.setClientList(clients);
        clientList.setCountPreOrderClient(countPreOrderClient);
    }

    public void startPreOrderThread(ClientList clientList) {
        List<Semaphore> semaphores = createSemaphore(clientList);
        int preOrderPerson = clientList.getCountPreOrderClient();

        for (int currentClient = 0; currentClient < preOrderPerson; currentClient++) {
            int randomCashBox = new Random().nextInt(clientList.sizeClientList());
            new Thread(
                    new Client(
                            semaphores,
                            randomCashBox,
                            currentClient,
                            Status.PREORDER
                    )
            ).start();
        }
    }

    public void startLiveQueueThread(ClientList clientList) {
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
                                    Status.LIVEQUEUE
                            )
                    ).start();
                }
            }
            currentClientId.getAndIncrement();
        }
    }

    private List<Semaphore> createSemaphore(ClientList clientList) {
        List<Semaphore> semaphores = new ArrayList<>();

        for (int semaphore = 0; semaphore < clientList.sizeClientList(); semaphore++) {
            semaphores.add(new Semaphore(1));
        }
        return semaphores;
    }
}
