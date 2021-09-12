package ru.otus.lesson.banknotes;

import ru.otus.lesson.model.Banknotes;
import ru.otus.lesson.operation.BundleOfBanknotes;

import java.util.List;
import java.util.Map;

public interface BanknotesService {

    int putBanknotes(List<Banknotes> banknotesList);

    List<Banknotes> pullBanknotes(BundleOfBanknotes bundleOfBanknotes);

    int giveCountOfBanknotes(Banknotes banknotes);

    int giveMinAvailableSum();

    Map<Banknotes, Integer> getCountOfBanknotesMap();
}
