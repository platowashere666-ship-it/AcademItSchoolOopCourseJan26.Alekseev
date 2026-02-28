package ru.academits.alekseev.list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class SinglyLinkedList<E> {
    private ListItem<E> head;
    private int count;

    public SinglyLinkedList() {
    }

    public int getCount() {
        return count;
    }

    public E getData(int index) {
        checkIndex(index);

        if (index == 0) {
            return head.getData();
        }

        return getItem(index).getData();
    }

    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Операция невозможна - список пуст.");
        }

        return head.getData();
    }

    public E setData(int index, E data) {
        checkIndex(index);

        ListItem<E> currentItem = getItem(index);

        E oldData = currentItem.getData();

        currentItem.setData(data);

        return oldData;
    }

    public E delete(int index) {
        checkIndex(index);

        if (index == 0) {
            return deleteFirst();
        }

        ListItem<E> previousItem = getItem(index - 1);
        ListItem<E> itemToDelete = previousItem.getNext();
        previousItem.setNext(itemToDelete.getNext());

        --count;

        return itemToDelete.getData();
    }

    public boolean delete(E data) {
        if (head == null) {
            return false;
        }

        if (Objects.equals(head.getData(), data)) {
            deleteFirst();

            return true;
        }

        ListItem<E> previousItem = head;
        ListItem<E> currentItem = head.getNext();

        while (currentItem != null) {
            if (Objects.equals(currentItem.getData(), data)) {
                previousItem.setNext(currentItem.getNext());
                --count;

                return true;
            }

            previousItem = currentItem;
            currentItem = currentItem.getNext();
        }

        return false;
    }

    public E deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException("Операция невозможна - список пуст.");
        }

        E dataToDelete = head.getData();
        head = head.getNext();
        --count;

        return dataToDelete;
    }

    public void addFirst(E data) {
        head = new ListItem<>(data);
        ++count;
    }

    public void add(int index, E data) {
        checkIndexForAddition(index);

        if (index == 0) {
            addFirst(data);

            return;
        }

        ListItem<E> previousItem = getItem(index - 1);
        ListItem<E> newItem = new ListItem<>(data, previousItem.getNext());

        previousItem.setNext(newItem);
        ++count;
    }

    public void reverse() {
        ListItem<E> previousItem = null;
        ListItem<E> currentItem = head;

        while (currentItem != null) {
            ListItem<E> nextItem = currentItem.getNext();

            currentItem.setNext(previousItem);

            previousItem = currentItem;
            currentItem = nextItem;
        }

        head = previousItem;
    }

    public SinglyLinkedList<E> copy() {
        if (head == null) {
            return new SinglyLinkedList<>();
        }

        ListItem<E> headCopy = new ListItem<>(head.getData());
        ListItem<E> currentItem = head.getNext();
        ListItem<E> currentItemCopy = headCopy;

        while (currentItem != null) {
            ListItem<E> newItem = new ListItem<>(currentItem.getData());
            currentItemCopy.setNext(newItem);
            currentItemCopy = newItem;
            currentItem = currentItem.getNext();
        }

        SinglyLinkedList<E> copy = new SinglyLinkedList<>();
        copy.head = headCopy;
        copy.count = count;

        return copy;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < " + count + ". Индекс: " + index);
        }
    }

    private void checkIndexForAddition(int index) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и <= " + count + ". Индекс: " + index);
        }
    }

    private ListItem<E> getItem(int index) {
        ListItem<E> currentItem = head;

        for (int i = 0; i < index; ++i) {
            currentItem = currentItem.getNext();
        }

        return currentItem;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        ListItem<E> currentItem = head;

        while (currentItem.getNext() != null) {
            sb.append(currentItem.getData()).append(", ");

            currentItem = currentItem.getNext();
        }

        sb.append(currentItem.getData()).append(']');

        return sb.toString();
    }
}
