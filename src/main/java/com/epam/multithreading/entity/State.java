package com.epam.multithreading.entity;

public interface State {
    void preOrder(CashBox cashBox);

    void normalOrder(CashBox cashBox);
}
