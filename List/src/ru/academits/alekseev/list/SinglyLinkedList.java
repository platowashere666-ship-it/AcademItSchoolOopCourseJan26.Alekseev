package ru.academits.alekseev.list;

public class SinglyLinkedList<T> {
    private ListItem<T> head;
    private int count;

    public SinglyLinkedList() {
        head = null;
        count = 0;
    }

    public ListItem<T> getHead() {
        return head;
    }

    public int getCount() {
        return count;
    }

    public T getData(int index) {
        checkIndex(index);

        ListItem<T> currentItem = head;

        for (int i = 0; i < index; ++i) {
            currentItem = currentItem.getNext();
        }

        return currentItem.getData();
    }

    public T setData(int index, T data) {
        checkIndex(index);

        ListItem<T> currentItem = head;

        for (int i = 0; i < index; ++i) {
            currentItem = currentItem.getNext();
        }

        T previousData = currentItem.getData();

        currentItem.setData(data);

        return previousData;
    }

    public T delete(int index) {
        checkIndex(index);

        if (index == 0) {
            return deleteFirst();
        }

        ListItem<T> currentItem = head;

        for (int i = 0; i < index - 1; ++i) {
            currentItem = currentItem.getNext();
        }

        ListItem<T> itemToRemove = currentItem.getNext();
        T removedData = itemToRemove.getData();

        currentItem.setNext(itemToRemove.getNext());

        count -= 1;

        return removedData;
    }

    public boolean delete(T data) {
        if (head == null) {
            return false;
        }

        if (head.getData().equals(data)) {
            head = head.getNext();
            count -= 1;

            return true;
        }

        ListItem<T> previousItem = head;
        ListItem<T> currentItem = head.getNext();

        while (currentItem != null) {
            if (currentItem.getData().equals(data)) {
                previousItem.setNext(currentItem.getNext());
                count -= 1;

                return true;
            }

            previousItem = currentItem;
            currentItem = currentItem.getNext();
        }

        return false;
    }

    public T deleteFirst() {
        T removedData = head.getData();
        head = head.getNext();
        count -= 1;

        return removedData;
    }

    public void addFirst(ListItem<T> newItem) {
        newItem.setNext(head);
        head = newItem;
        count += 1;
    }

    public void add(int index, ListItem<T> newItem) {
        checkIndexForAddition(index);

        if (index == 0) {
            addFirst(newItem);

            return;
        }

        ListItem<T> currentItem = head;

        for (int i = 0; i < index - 1; ++i) {
            currentItem = currentItem.getNext();
        }

        newItem.setNext(currentItem.getNext());
        currentItem.setNext(newItem);

        count += 1;
    }

    public void reverse() {
        ListItem<T> previousItem = null;
        ListItem<T> currentItem = head;
        ListItem<T> nextItem;

        while (currentItem != null) {
            nextItem = currentItem.getNext();

            currentItem.setNext(previousItem);

            previousItem = currentItem;
            currentItem = nextItem;
        }

        head = previousItem;
    }

    public SinglyLinkedList<T> copy() {
        if (head == null) {
            return new SinglyLinkedList<>();
        }

        ListItem<T> headCopy = new ListItem<>(head.getData());
        ListItem<T> currentItem = head.getNext();
        ListItem<T> currentItemCopy = headCopy;

        while (currentItem != null) {
            ListItem<T> newItem = new ListItem<>(currentItem.getData());
            currentItemCopy.setNext(newItem);
            currentItemCopy = newItem;
            currentItem = currentItem.getNext();
        }

        SinglyLinkedList<T> copy = new SinglyLinkedList<>();
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

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        ListItem<T> currentItem = head;

        while (currentItem.getNext() != null) {
            sb.append(currentItem.getData()).append(", ");

            currentItem = currentItem.getNext();
        }

        sb.append(currentItem.getData()).append(']');

        return sb.toString();
    }
}
