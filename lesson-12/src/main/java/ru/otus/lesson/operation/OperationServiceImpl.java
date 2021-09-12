package ru.otus.lesson.operation;

import ru.otus.lesson.model.Banknotes;
import ru.otus.lesson.banknotes.BanknotesService;

import java.util.List;
import java.util.Optional;

public class OperationServiceImpl implements OperationService {
    private final BanknotesService banknotesService;

    public OperationServiceImpl(BanknotesService banknotesService) {
        this.banknotesService = banknotesService;
    }

    @Override
    public int putBanknotes(List<Banknotes> banknotesList) {
        return banknotesService.putBanknotes(banknotesList);
    }

    @Override
    public int getMinAvailableSum() {
        return banknotesService.giveMinAvailableSum();
    }

    @Override
    public int getAtmBalance() {
        return banknotesService.getCountOfBanknotesMap().entrySet()
                .stream()
                .map(banknotesIntegerEntry -> banknotesIntegerEntry.getKey().getDenomination() * banknotesIntegerEntry.getValue())
                .reduce(0, Integer::sum);
    }

    @Override
    public Optional<BundleOfBanknotes> convertRequiredAmountToCountOfBanknotes(int requestedAmountOfMoney) {
        BundleOfBanknotes bundleOfBanknotes = new BundleOfBanknotes();
        Banknotes[] banknotes = Banknotes.values();
        for (Banknotes banknote : banknotes) {
            int denomination = banknote.getDenomination();
            int requestCountOfBanknotes = requestedAmountOfMoney / denomination;
            int currentCountOfBanknotes = banknotesService.giveCountOfBanknotes(banknote);
            if (requestCountOfBanknotes > currentCountOfBanknotes) {
                bundleOfBanknotes.addCountOfBanknote(banknote, currentCountOfBanknotes);
                requestedAmountOfMoney -= denomination * currentCountOfBanknotes;
            } else {
                bundleOfBanknotes.addCountOfBanknote(banknote, requestCountOfBanknotes);
                requestedAmountOfMoney -= requestCountOfBanknotes * denomination;
            }
            if (requestedAmountOfMoney == 0) {
                break;
            }
        }

        return requestedAmountOfMoney == 0 ? Optional.of(bundleOfBanknotes) : Optional.empty();
    }

    @Override
    public List<Banknotes> pullBanknotes(BundleOfBanknotes bundleOfBanknotes) {
        return banknotesService.pullBanknotes(bundleOfBanknotes);
    }
}
