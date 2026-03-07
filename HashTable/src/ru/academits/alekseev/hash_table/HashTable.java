package ru.academits.alekseev.hash_table;

import java.util.*;

import ru.academits.alekseev.array_list.ArrayList;

public class HashTable<E> implements Collection<E> {
    private final ArrayList<E>[] lists;
    private int size;
    private int modCount;

    public HashTable() {
        //noinspection unchecked
        lists = (ArrayList<E>[]) new ArrayList[10];
    }

    public HashTable(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер таблицы должен быть > 0. Размер: " + size);
        }
        //noinspection unchecked
        lists = (ArrayList<E>[]) new ArrayList[size];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (isEmpty()) {
            return false;
        }

        int objectIndex = getIndex(o);

        return lists[objectIndex] != null && lists[objectIndex].contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int currentIndex = -1;
            private final int initialModCount = modCount;

            @Override
            public boolean hasNext() {
                return currentIndex + 1 < size;
            }

            @Override
            public E next() {
                if (initialModCount != modCount) {
                    throw new ConcurrentModificationException("Операция невозможна. Таблица изменилась.");
                }

                if (!hasNext()) {
                    throw new NoSuchElementException("Операция невозможна. Таблица закончилась.");
                }

                ++currentIndex;

                //noinspection unchecked
                return (E) lists[currentIndex];
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];

        for (ArrayList<E> list : lists) {
            if (list != null) {
                for (int i = 0; i < list.size(); ++i) {
                    array[i] = list.get(i);
                }
            }
        }

        return array;
    }

    @Override
    public boolean add(Object o) {
        int objectIndex = getIndex(o);

        if (lists[objectIndex] == null) {
            lists[objectIndex] = new ArrayList<>();
        }

        //noinspection unchecked
        lists[objectIndex].add((E) o);
        ++size;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (isEmpty()) {
            return false;
        }

        int objectIndex = getIndex(o);

        if (lists[objectIndex] == null || !lists[objectIndex].remove(o)) {
            return false;
        }

        --size;
        ++modCount;

        return true;
    }

    @Override
    public boolean addAll(Collection c) {
        if (c.isEmpty()) {
            return false;
        }

        boolean isAdded = false;

        for (Object element : c) {
            if (add(element)) {
                isAdded = true;
            }
        }

        return isAdded;
    }

    @Override
    public void clear() {
        for (ArrayList<E> list : lists) {
            list.clear();
        }

        size = 0;
        modCount++;
    }

    @Override
    public boolean retainAll(Collection c) {
        if (c.isEmpty()) {
            return false;
        }

        boolean isRemoved = false;

        for (ArrayList<E> list : lists) {
            //noinspection SuspiciousMethodCalls
            if (list.retainAll(c)) {
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    @Override
    public boolean removeAll(Collection c) {
        if (c.isEmpty()) {
            return false;
        }

        boolean isRemoved = false;

        for (Object element : c) {
            if (remove(element)) {
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] toArray(Object[] a) {
        Object[] array = toArray();

        if (a.length < size) {
            return Arrays.copyOf(array, size, a.getClass());
        }

        System.arraycopy(array, 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    private int getIndex(Object o) {
        return Math.abs(o.hashCode() % lists.length);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('{');

        for (int i = 0; i < lists.length - 1; ++i) {
            if (lists[i] != null) {
                sb.append(lists[i]).append(", ");
            } else {
                sb.append("[], ");
            }
        }

        sb.append(lists[lists.length - 1]).append('}');

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        //noinspection unchecked
        HashTable<E> table = (HashTable<E>) o;

        return containsAll(table);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash += hash * prime + Arrays.hashCode(toArray());

        return hash;
    }
}