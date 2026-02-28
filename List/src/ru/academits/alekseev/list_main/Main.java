package ru.academits.alekseev.list_main;

import ru.academits.alekseev.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        Integer data1 = 4;
        Integer data2 = 57;
        Integer data3 = 10;

        list.addFirst(data1);
        System.out.println("Добавили первый элемент: " + list);

        list.add(1, data2);
        list.add(2, data3);
        System.out.println("Добавили элементы по индексу: " + list);

        list.deleteFirst();
        System.out.println("Удалили первый элемент: " + list);

        Integer deletedData = list.delete(1);
        System.out.println("Удалили элемент по индексу: " + list);
        System.out.println("Удалённое значение: " + deletedData);

        if (list.delete(data2)) {
            System.out.println("Удалили элемент по значению: " + list);
        }

        list.addFirst(data1);
        list.add(1, data2);
        list.add(2, data3);
        System.out.println("Восстановили список: " + list);

        SinglyLinkedList<Integer> listCopy = list.copy();
        System.out.println("Копия списка: " + listCopy);

        list.reverse();
        System.out.println("Развернули список: " + list);

        System.out.println("Первый элемент списка: " + list.getFirst());
        System.out.println("Размер списка: " + list.getCount());
        System.out.println("Вывели значение по индексу 2: " + list.getData(2));

        Integer changedData = list.setData(2, deletedData);
        System.out.println("Изменили значение по индексу 2: " + list);
        System.out.println("Изменённое значение: " + changedData);
    }
}
