package com.epam.multithreading.main;

import com.epam.multithreading.entity.CashBox;
import com.epam.multithreading.entity.CashBoxPool;
import com.epam.multithreading.entity.Client;
import com.epam.multithreading.entity.Status;
import com.epam.multithreading.exception.ReadingFileException;
import com.epam.multithreading.parser.FileParser;
import com.epam.multithreading.reader.DataReader;
import com.epam.multithreading.singleton.CashBoxList;
import com.epam.multithreading.singleton.ClientList;
import com.epam.multithreading.сreator.CreateRestaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        CashBoxList cashBoxList = CashBoxList.getInstance();
        ClientList clientList = ClientList.getInstance();

        String path = "src/test/resources/test.txt";

        DataReader dataReader = new DataReader();
        FileParser fileParser = new FileParser();

        CreateRestaurant createRestaurant = new CreateRestaurant();
        try {
            createRestaurant.createList(fileParser.parsingLines(dataReader.readingLines(path)));
        } catch (ReadingFileException e) {
            e.printStackTrace();
        }

        List<Integer> listRandom = new ArrayList<>();
        int countOfPreOrderClients = clientList.getPreOrderClientList();
        while (listRandom.size() < countOfPreOrderClients) {
            int number = new Random().nextInt(clientList.sizeClientList());
            if (!listRandom.contains(number)) {
                listRandom.add(number);
            }
        }


        CashBoxPool<CashBox> pool = new CashBoxPool<>(cashBoxList.getCashBoxList());
        for (int i = 0; i < clientList.sizeClientList(); i++) {
            if (listRandom.contains(i)) {
                new Thread(new Client(pool, Status.PREORDER)).start();
            } else {
                new Thread(new Client(pool, Status.NORMALORDER)).start();
            }
        }
    }
}
