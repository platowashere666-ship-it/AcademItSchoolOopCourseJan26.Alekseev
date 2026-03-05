package ru.academits.alekseev.array_list_main;

import ru.academits.alekseev.array_list.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list1 = new ArrayList<>();

        list1.add(5);
        list1.add(8);
        list1.add(10);
        System.out.println("Создали список: " + list1);

        ArrayList<Integer> list2 = new ArrayList<>(list1.size());

        list2.addAll(list1);
        System.out.println("Создали второй список: " + list2);

        list1.removeAll(list2);
        System.out.println("Удалили элементы первого списка: " + list1);
    }
}
