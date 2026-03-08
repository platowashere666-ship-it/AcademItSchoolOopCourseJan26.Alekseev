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

    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Размер хэш-таблицы должен быть > 0. Размер: " + capacity);
        }

        //noinspection unchecked
        lists = (ArrayList<E>[]) new ArrayList[capacity];
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
        int index = getIndex(o);

        return lists[index] != null && lists[index].contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int currentItemIndex = -1;
            private int itemsPassed = 0;
            private final int initialModCount = modCount;

            @Override
            public boolean hasNext() {
                return itemsPassed < size;
            }

            @Override
            public E next() {
                if (initialModCount != modCount) {
                    throw new ConcurrentModificationException("Операция невозможна. Хэш-таблица изменилась.");
                }

                if (!hasNext()) {
                    throw new NoSuchElementException("Операция невозможна. Хэш-таблица закончилась.");
                }

                ++itemsPassed;

                for (ArrayList<E> list : lists) {
                    if (list != null && !list.isEmpty()) {
                        ++currentItemIndex;

                        if (currentItemIndex < list.size()) {
                            return list.get(currentItemIndex);
                        } else {
                            currentItemIndex = -1;
                        }
                    }
                }

                throw new NoSuchElementException("Операция невозможна. Хэш-таблица закончилась.");
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                for (E item : list) {
                    array[i] = item;
                    ++i;
                }
            }
        }

        return array;
    }

    @Override
    public boolean add(E o) {
        int index = getIndex(o);

        if (lists[index] == null) {
            lists[index] = new ArrayList<>();
        }

        lists[index].add(o);
        ++size;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o);

        if (lists[index] == null || !lists[index].remove(o)) {
            return false;
        }

        --size;
        ++modCount;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }

        for (E item : c) {
            add(item);
        }

        return true;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (ArrayList<E> list : lists) {
            if (list != null) {
                list.clear();
            }
        }

        size = 0;
        ++modCount;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRemoved = false;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                int oldSize = list.size();

                if (list.retainAll(c)) {
                    isRemoved = true;
                    size -= (oldSize - list.size());
                }
            }
        }

        if (isRemoved) {
            ++modCount;
        }

        return isRemoved;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }

        boolean isRemoved = false;

        for (ArrayList<E> list : lists) {
            if (list != null) {
                int oldSize = list.size();

                if (list.removeAll(c)) {
                    isRemoved = true;
                    size -= (oldSize - list.size());
                }
            }
        }

        if (isRemoved) {
            ++modCount;
        }

        return isRemoved;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Object[] array = toArray();

        if (a.length < size) {
            //noinspection unchecked
            return Arrays.copyOf(array, size, (Class<? extends T[]>) a.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(array, 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    private int getIndex(Object o) {
        if (o == null) {
            return 0;
        }

        return Math.abs(o.hashCode() % lists.length);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('{');

        boolean isFirst = true;

        for (ArrayList<E> list : lists) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(", ");
            }

            if (list != null && !list.isEmpty()) {
                sb.append(list);
            } else {
                sb.append("[]");
            }
        }

        sb.append('}');

        return sb.toString();
    }
}