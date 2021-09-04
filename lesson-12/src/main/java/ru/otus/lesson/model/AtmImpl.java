package ru.otus.lesson.model;

import ru.otus.lesson.operation.OperationService;
import ru.otus.lesson.operation.BundleOfBanknotes;
import ru.otus.lesson.validation.ValidationService;

import java.util.List;
import java.util.Optional;

public class AtmImpl implements Atm {
    private final ValidationService validationService;
    private final OperationService operationService;

    public AtmImpl(ValidationService validationService, OperationService operationService) {
        this.validationService = validationService;
        this.operationService = operationService;
    }

    @Override
    public void pushMoney(List<Banknotes> banknotesList) {
        System.out.println("Deposit: " + operationService.putBanknotes(banknotesList));
    }

    @Override
    public void pullMoney(int requestedAmount) {
        System.out.println("Requested sum: " + requestedAmount);
        Optional<BundleOfBanknotes> bundleOfBanknotes = operationService.convertRequiredAmountToCountOfBanknotes(requestedAmount);
        BundleOfBanknotes count = bundleOfBanknotes.orElse(new BundleOfBanknotes());
        validationService.validate(requestedAmount, operationService, count);

        List<Banknotes> banknotes = operationService.pullBanknotes(count);
        System.out.println("Count of banknotes: " + banknotes.size());
    }

    @Override
    public void printBalance() {
        System.out.println("Balance: " + operationService.getAtmBalance());
    }
}
