package ru.otus.lesson.model;

public enum Banknotes {
    FIVE_THOUSAND(5000),
    TWO_THOUSAND(2000),
    ONE_THOUSAND(1000),
    FIVE_HUNDRED(500),
    TWO_HUNDRED(200),
    ONE_HUNDRED(100);

    private final int denomination;

    Banknotes(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return this.denomination;
    }
}
