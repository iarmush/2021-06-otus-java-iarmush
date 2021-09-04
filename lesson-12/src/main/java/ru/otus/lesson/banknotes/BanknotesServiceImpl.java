package ru.otus.lesson.banknotes;

import ru.otus.lesson.model.Banknotes;
import ru.otus.lesson.operation.BundleOfBanknotes;

import java.util.*;
import java.util.stream.Collectors;

public class BanknotesServiceImpl implements BanknotesService {
    private static final String ATM_IS_EMPTY = "ATM is empty";
    private static final int DEFAULT_COUNT_OF_EACH_BANKNOTE = 10;
    private final Map<Banknotes, Queue<Banknotes>> banknotesListMap = new HashMap<>();

    public BanknotesServiceImpl() {
        Banknotes[] values = Banknotes.values();
        for (Banknotes banknotes : values) {
            Queue<Banknotes> banknotesList = new LinkedList<>();
            for (int i = 0; i < DEFAULT_COUNT_OF_EACH_BANKNOTE; i++) {
                banknotesList.add(banknotes);
            }
            banknotesListMap.put(banknotes, banknotesList);
        }
    }

    @Override
    public Map<Banknotes, Integer> getCountOfBanknotesMap() {
        return banknotesListMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, banknotesQueueEntry -> banknotesQueueEntry.getValue().size()));
    }

    @Override
    public int putBanknotes(List<Banknotes> banknotesList) {
        int sum = 0;
        for (Banknotes banknotes : banknotesList) {
            banknotesListMap.get(banknotes).add(banknotes);
            sum += banknotes.getDenomination();
        }
        return sum;
    }

    @Override
    public List<Banknotes> pullBanknotes(BundleOfBanknotes bundleOfBanknotes) {
        LinkedList<Banknotes> banknotesToOutput = new LinkedList<>();
        Map<Banknotes, Integer> collectionOfMoney = bundleOfBanknotes.getBanknotesCountMap();
        for (Map.Entry<Banknotes, Integer> banknotesIntegerEntry : collectionOfMoney.entrySet()) {
            for (int i = 0; i < banknotesIntegerEntry.getValue(); i++) {
                Queue<Banknotes> atmBanknotesList = banknotesListMap.get(banknotesIntegerEntry.getKey());
                transferBanknotesFromAtmToOutput(atmBanknotesList, banknotesToOutput);
            }
        }

        return banknotesToOutput;
    }

    @Override
    public int giveCountOfBanknotes(Banknotes banknotes) {
        return banknotesListMap.get(banknotes).size();
    }

    @Override
    public int giveMinAvailableSum() {
        return banknotesListMap.entrySet()
                .stream()
                .filter(banknotesQueueEntry -> banknotesQueueEntry.getValue().size() > 0)
                .map(banknotesQueueEntry -> banknotesQueueEntry.getKey().getDenomination())
                .min(Integer::compareTo)
                .orElseThrow(() -> new IllegalStateException(ATM_IS_EMPTY));
    }

    private void transferBanknotesFromAtmToOutput(Queue<Banknotes> atmBanknotesList, Queue<Banknotes> outputBanknotesList) {
        Banknotes banknotes = atmBanknotesList.remove();
        outputBanknotesList.add(banknotes);
    }
}
