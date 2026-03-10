package ru.academits.alekseev.hash_table_main;

import ru.academits.alekseev.hash_table.HashTable;

public class Main {
    public static void main(String[] args) {
        HashTable<Integer> hashTable1 = new HashTable<>();

        hashTable1.add(10);
        hashTable1.add(185);
        hashTable1.add(643);
        System.out.println("Создали хэш-таблицу: " + hashTable1);

        HashTable<Integer> hashTable2 = new HashTable<>(hashTable1.size());

        hashTable2.add(10);
        hashTable2.add(185);
        hashTable2.add(643);
        System.out.println("Создали вторую хэш-таблицу: " + hashTable2);
    }

}
