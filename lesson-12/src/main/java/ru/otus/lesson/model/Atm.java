package ru.otus.lesson.model;

import java.util.List;

public interface Atm {
    void pushMoney(List<Banknotes> banknotesList);

    void pullMoney(int amountRequestOfMoney);

    void printBalance();
}
