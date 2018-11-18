package com.epam.multithreading.main;

import com.epam.multithreading.entity.CashBox;
import com.epam.multithreading.entity.Client;
import com.epam.multithreading.exception.ReadingFileException;
import com.epam.multithreading.parser.FileParser;
import com.epam.multithreading.reader.DataReader;
import com.epam.multithreading.singleton.CashBoxList;
import com.epam.multithreading.singleton.ClientList;
import com.epam.multithreading.сreator.AddingInformationToLists;
import com.epam.multithreading.сreator.CreateOrder;

import java.util.List;

public class DemoMultithreading {

    private CashBoxList cashBoxList = CashBoxList.getInstance();
    private ClientList clientList = ClientList.getInstance();
    private DataReader dataReader = new DataReader();
    private FileParser fileParser = new FileParser();
    private CreateOrder createOrder = new CreateOrder();

    public void create(String path) {

//        AddingInformationToLists addingInformationToLists = new AddingInformationToLists();
        List<String> values = null;
        try {
            values = dataReader.readingLines(path);
        } catch (ReadingFileException e) {
            e.printStackTrace();
        }
        List<Integer> inputData = fileParser.parsingLines(values);
//        addingInformationToLists.createList(inputData);

        for (int index = 0; index < inputData.get(0); index++) {
            CashBox cashBox = new CashBox();
            cashBoxList.add(cashBox);
        }

        int countClients = inputData.get(1);
        List<List<Client>> clients = createOrder.create(countClients);
        clientList.setClientList(clients);

        clientList.setCountPreOrderClient(inputData.get(2));

    }
}
