package ru.otus.lesson.operation;

import ru.otus.lesson.model.Banknotes;

import java.util.List;
import java.util.Optional;

public interface OperationService {
    Optional<BundleOfBanknotes> convertRequiredAmountToCountOfBanknotes(int requiredAmount);

    int putBanknotes(List<Banknotes> banknotesList);

    int getAtmBalance();

    int getMinAvailableSum();

    List<Banknotes> pullBanknotes(BundleOfBanknotes bundleOfBanknotes);
}
