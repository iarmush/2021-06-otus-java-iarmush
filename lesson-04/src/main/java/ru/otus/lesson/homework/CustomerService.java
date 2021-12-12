package ru.otus.lesson.homework;


import java.util.*;

public class CustomerService {
    private final TreeMap<Customer, String> treeMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> firstEntry = treeMap.firstEntry();
        Customer customer = firstEntry.getKey();

        return new AbstractMap.SimpleEntry<>(new Customer(customer.getId(), customer.getName(), customer.getScores()),
                firstEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Optional<Map.Entry<Customer, String>> higherEntry = Optional.ofNullable(treeMap.higherEntry(customer));

        return higherEntry.map(entry -> {
            Customer higherCustomer = entry.getKey();
            return new AbstractMap.SimpleEntry<>
                    (new Customer(higherCustomer.getId(), higherCustomer.getName(), higherCustomer.getScores()),
                            entry.getValue());
        }).orElse(null);
    }

    public void add(Customer customer, String data) {
        treeMap.put(customer, data);
    }
}
