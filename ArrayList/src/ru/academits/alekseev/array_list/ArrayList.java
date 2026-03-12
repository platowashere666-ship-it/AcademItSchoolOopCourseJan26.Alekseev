package ru.academits.alekseev.array_list;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private int size;
    private E[] items;
    private int modCount;

    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList() {
        //noinspection unchecked
        items = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Вместимость списка не может быть < 0. Вместимость списка: " + capacity);
        }

        //noinspection unchecked
        items = (E[]) new Object[capacity];
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
        return indexOf(o) != -1;
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
                    throw new ConcurrentModificationException("Операция невозможна. Список изменился.");
                }

                if (!hasNext()) {
                    throw new NoSuchElementException("Операция невозможна. Список закончился.");
                }

                ++currentIndex;

                return items[currentIndex];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(items, size, a.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(items, 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E item) {
        ensureCapacityForAddition();

        items[size] = item;
        ++size;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index != -1) {
            remove(index);

            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndexForAddition(index);

        if (c.isEmpty()) {
            return false;
        }

        ensureCapacity(size + c.size());
        System.arraycopy(items, index, items, index + c.size(), size - index);

        int i = index;

        for (E item : c) {
            items[i] = item;
            ++i;
        }

        size += c.size();
        ++modCount;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemoved = false;

        for (int i = size - 1; i >= 0; --i) {
            if (c.contains(items[i])) {
                remove(i);
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRemoved = false;

        for (int i = size - 1; i >= 0; --i) {
            if (!c.contains(items[i])) {
                remove(i);
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        Arrays.fill(items, 0, size, null);

        size = 0;
        ++modCount;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);

        E oldItem = items[index];
        items[index] = element;

        return oldItem;
    }

    @Override
    public void add(int index, E element) {
        checkIndexForAddition(index);

        ensureCapacityForAddition();

        System.arraycopy(items, index, items, index + 1, size - index);

        items[index] = element;
        ++size;
        ++modCount;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedItem = items[index];

        System.arraycopy(items, index + 1, items, index, size - index - 1);

        items[size - 1] = null;
        --size;
        ++modCount;

        return removedItem;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; ++i) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; --i) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        //noinspection DataFlowIssue
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        //noinspection DataFlowIssue
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return List.of();
    }

    public void ensureCapacity(int capacity) {
        if (items.length < capacity) {
            items = Arrays.copyOf(items, capacity);
        }
    }

    private void ensureCapacityForAddition() {
        if (items.length == size) {
            int newCapacity = size == 0 ? DEFAULT_CAPACITY : items.length * 2;
            ensureCapacity(newCapacity);
        }
    }

    public void trimToSize() {
        if (items.length > size) {
            items = Arrays.copyOf(items, size);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < " + size + ". Индекс: " + index);
        }
    }

    private void checkIndexForAddition(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и <= " + size + ". Индекс: " + index);
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        int lastIndex = size - 1;

        for (int i = 0; i < lastIndex; ++i) {
            sb.append(items[i]).append(", ");
        }

        sb.append(items[lastIndex]).append(']');

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

        ArrayList<?> list = (ArrayList<?>) o;

        if (size != list.size) {
            return false;
        }

        for (int i = 0; i < size; ++i) {
            if (!Objects.equals(items[i], list.items[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = hash * prime + size;

        for (int i = 0; i < size; ++i) {
            hash = prime * hash + (items[i] == null ? 0 : items[i].hashCode());
        }

        return hash;
    }
}
