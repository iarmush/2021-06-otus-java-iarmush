package ru.otus.lesson.validation;

import ru.otus.lesson.model.Banknotes;
import ru.otus.lesson.operation.BundleOfBanknotes;
import ru.otus.lesson.operation.OperationService;

import java.util.Map;

public class ValidationServiceImpl implements ValidationService {
    private static final String NOT_ENOUGH_COUNT_OF_BANKNOTES = "Not enough count of banknotes";
    private static final String NON_MULTIPLE_SUM = "Sum doesn't multiple to %d";
    private static final String MIN_REQUESTED_SUM = "Min requested sum: %d. Requested: %d";
    private static final String NOT_ENOUGH_MONEY = "Not enough money for %s. Balance: %d";

    @Override
    public void validate(int requestedSum, OperationService operationService, BundleOfBanknotes bundleOfBanknotes) {
        int minAvailableSum = operationService.getMinAvailableSum();
        int atmBalance = operationService.getAtmBalance();

        checkMinAvailableSum(requestedSum, minAvailableSum);
        checkIsRequestedSumIsMultiple(requestedSum, minAvailableSum);
        checkIsMoneyEnough(requestedSum, atmBalance);
        checkIsValidCountOfBanknotes(bundleOfBanknotes.getBanknotesCountMap());
    }

    private void checkIsValidCountOfBanknotes(Map<Banknotes, Integer> countOfBanknotesMap) {
        if (countOfBanknotesMap.isEmpty()) {
            throw new IllegalArgumentException(NOT_ENOUGH_COUNT_OF_BANKNOTES);
        }
    }

    private void checkIsMoneyEnough(int requestedSum, int currentAmountOfMoney) {
        if (currentAmountOfMoney < requestedSum) {
            throw new IllegalArgumentException(String.format(NOT_ENOUGH_MONEY, requestedSum, currentAmountOfMoney));
        }
    }

    private void checkIsRequestedSumIsMultiple(int requestedSum, int minAvailableSum) {
        if (requestedSum % minAvailableSum > 0) {
            throw new IllegalArgumentException(String.format(NON_MULTIPLE_SUM, minAvailableSum));
        }
    }

    private void checkMinAvailableSum(int requestedSum, int minAvailableSum) {
        if (requestedSum < minAvailableSum) {
            throw new IllegalArgumentException(String.format(MIN_REQUESTED_SUM, minAvailableSum, requestedSum));
        }
    }
}
