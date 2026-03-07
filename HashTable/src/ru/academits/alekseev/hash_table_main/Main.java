package ru.academits.alekseev.hash_table_main;

import ru.academits.alekseev.hash_table.HashTable;

public class Main {
    public static void main(String[] args) {
        HashTable<Integer> table1 = new HashTable<>();

        table1.add(10);
        table1.add(185);
        table1.add(643);
        System.out.println("Создали таблицу: " + table1);

        HashTable<Integer> table2 = new HashTable<>(table1.size());

        table2.add(10);
        table2.add(185);
        table2.add(643);
        System.out.println("Создали вторую таблицу: " + table2);
    }
}
