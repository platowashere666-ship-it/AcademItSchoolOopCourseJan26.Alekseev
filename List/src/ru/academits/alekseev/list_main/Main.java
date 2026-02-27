package ru.academits.alekseev.list_main;

import ru.academits.alekseev.list.ListItem;
import ru.academits.alekseev.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        ListItem<Integer> item1 = new ListItem<>(4);
        ListItem<Integer> item2 = new ListItem<>(57);
        ListItem<Integer> item3 = new ListItem<>(10);

        list.addFirst(item1);
        System.out.println("Добавили первый элемент: " + list);

        list.add(1, item2);
        list.add(2, item3);
        System.out.println("Добавили элементы по индексу: " + list);

        list.deleteFirst();
        System.out.println("Удалили первый элемент: " + list);

        Integer removedData = list.delete(1);
        System.out.println("Удалили элемент по индексу: " + list);
        System.out.println("Удалённое значение: " + removedData);

        Integer data = 57;

        if (list.delete(data)) {
            System.out.println("Удалили элемент по значению: " + list);
        }

        list.addFirst(item1);
        list.add(1, item2);
        list.add(2, item3);
        System.out.println("Восстановили список: " + list);

        SinglyLinkedList<Integer> listCopy = list.copy();
        System.out.println("Копия списка: " + listCopy);

        list.reverse();
        System.out.println("Развернули список: " + list);

        System.out.println("Первый элемент списка: " + list.getHead());
        System.out.println("Размер списка: " + list.getCount());
        System.out.println("Вывели значение по индексу 2: " + list.getData(2));

        Integer changedData = list.setData(2, removedData);
        System.out.println("Изменили значение по индексу 2: " + list);
        System.out.println("Изменённое значение: " + changedData);
    }
}
