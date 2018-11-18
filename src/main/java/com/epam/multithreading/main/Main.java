package com.epam.multithreading.main;

import com.epam.multithreading.singleton.ClientList;

public class Main {
    private static String PATH = "src/test/resources/test.txt";

    public static void main(String[] args) {
        DemoMultithreading demo = new DemoMultithreading();
        ClientList clientList = ClientList.getInstance();

        demo.create(PATH);
        demo.startClientThread(clientList);
        demo.startPreOrderThread(clientList);
    }
}
