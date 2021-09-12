package ru.otus.lesson.validation;

import ru.otus.lesson.operation.OperationService;
import ru.otus.lesson.operation.BundleOfBanknotes;

public interface ValidationService {

    void validate(int requestedAmountOfMoney, OperationService operationService, BundleOfBanknotes bundleOfBanknotes);
}
