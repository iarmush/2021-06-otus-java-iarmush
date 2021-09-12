package ru.otus.lesson;

import ru.otus.lesson.model.Atm;
import ru.otus.lesson.model.AtmImpl;
import ru.otus.lesson.model.Banknotes;
import ru.otus.lesson.operation.OperationService;
import ru.otus.lesson.operation.OperationServiceImpl;
import ru.otus.lesson.banknotes.BanknotesService;
import ru.otus.lesson.banknotes.BanknotesServiceImpl;
import ru.otus.lesson.validation.ValidationServiceImpl;
import ru.otus.lesson.validation.ValidationService;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Banknotes> banknotes = getPersonBanknotes();

        BanknotesService banknotesService = new BanknotesServiceImpl();
        OperationService operationService = new OperationServiceImpl(banknotesService);
        ValidationService validationService = new ValidationServiceImpl();

        Atm atm = new AtmImpl(validationService, operationService);
        atm.printBalance();

        atm.pushMoney(banknotes);
        atm.printBalance();

        atm.pullMoney(51400);
        atm.printBalance();
    }

    private static List<Banknotes> getPersonBanknotes() {
        List<Banknotes> banknotes = new LinkedList<>();
        banknotes.add(Banknotes.FIVE_THOUSAND);
        banknotes.add(Banknotes.ONE_THOUSAND);
        banknotes.add(Banknotes.FIVE_HUNDRED);
        banknotes.add(Banknotes.FIVE_HUNDRED);
        banknotes.add(Banknotes.TWO_HUNDRED);
        banknotes.add(Banknotes.ONE_HUNDRED);
        banknotes.add(Banknotes.ONE_HUNDRED);

        return banknotes;
    }
}
