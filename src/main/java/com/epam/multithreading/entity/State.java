package com.epam.multithreading.entity;

public interface State {
    void preOrder(Client client);

    void changeCashBox(Client client);

    void removeFromCashBox(Client client);
}
