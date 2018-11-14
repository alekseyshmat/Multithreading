package com.epam.multithreading.main;

import com.epam.multithreading.entity.CashBox;
import com.epam.multithreading.entity.CashBoxPool;
import com.epam.multithreading.entity.Client;
import com.epam.multithreading.exception.ReadingFileException;
import com.epam.multithreading.parser.FileParser;
import com.epam.multithreading.reader.DataReader;
import com.epam.multithreading.singleton.CashBoxList;
import com.epam.multithreading.singleton.ClientList;
import com.epam.multithreading.сreator.CreateRestaurant;

public class Main {

    public static void main(String[] args) {
        CashBoxList cashBoxList = CashBoxList.getInstance();
        ClientList clientList = ClientList.getInstance();

        String path = "E:\\EPAM\\TASK3\\freeCashBox\\src\\test\\resources\\test.txt";
        DataReader dataReader = new DataReader();
        FileParser fileParser = new FileParser();
        CreateRestaurant createRestaurant = new CreateRestaurant();
        try {
            createRestaurant.createList(fileParser.parsingLines(dataReader.readingLines(path)));
        } catch (ReadingFileException e) {
            e.printStackTrace();
        }

        CashBoxPool<CashBox> pool = new CashBoxPool<>(cashBoxList.getCashBoxList());
        for (int i = 0; i < clientList.size(); i++) {
            new Thread(new Client(pool)).start();
        }
    }
}
