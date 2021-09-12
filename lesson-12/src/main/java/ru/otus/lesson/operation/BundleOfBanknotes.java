package ru.otus.lesson.operation;

import ru.otus.lesson.model.Banknotes;

import java.util.HashMap;
import java.util.Map;

public class BundleOfBanknotes {
    private final Map<Banknotes, Integer> banknotesCountMap = new HashMap<>();

    public void addCountOfBanknote(Banknotes banknotes, Integer countOfBanknotes) {
        banknotesCountMap.put(banknotes, countOfBanknotes);
    }

    public Map<Banknotes, Integer> getBanknotesCountMap() {
        return banknotesCountMap;
    }
}
