package ru.academits.alekseev.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размерность вектора n должна быть > 0, n: " + size);
        }

        components = new double[size];
    }

    public Vector(Vector vector) {
        components = Arrays.copyOf(vector.components, vector.components.length);
    }

    public Vector(double[] components) {
        if (components == null) {
            throw new NullPointerException("Массив не может быть null!");
        }

        if (components.length == 0) {
            throw new IllegalArgumentException("Массив не может быть размера 0!");
        }

        this.components = Arrays.copyOf(components, components.length);
    }

    public Vector(int size, double[] components) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размерность вектора n должна быть > 0, n: " + size);
        }

        if (components == null) {
            throw new NullPointerException("Массив не может быть null!");
        }

        if (components.length == 0) {
            throw new IllegalArgumentException("Массив не может быть размера 0!");
        }

        this.components = Arrays.copyOf(components, size);
    }

    public int getSize() {
        return components.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (int i = 0; i < components.length; ++i) {
            sb.append(components[i]);

            if (i < components.length - 1) {
                sb.append(", ");
            }
        }

        sb.append("}");

        return sb.toString();
    }

    private void expandSize(Vector vector) {
        if (components.length < vector.components.length) {
            components = Arrays.copyOf(components, vector.components.length);
        }
    }

    public Vector add(Vector vector) {
        expandSize(vector);

        for (int i = 0; i < vector.components.length; ++i) {
            components[i] += vector.components[i];
        }

        return this;
    }

    public Vector subtract(Vector vector) {
        expandSize(vector);

        for (int i = 0; i < vector.components.length; ++i) {
            components[i] -= vector.components[i];
        }

        return this;
    }

    public Vector multiply(double scalar) {
        for (int i = 0; i < components.length; ++i) {
            components[i] *= scalar;
        }

        return this;
    }

    public Vector reverse() {
        return multiply(-1);
    }

    public double getLength() {
        double componentsSquaredSum = 0;

        for (double component : components) {
            componentsSquaredSum += component * component;
        }

        return Math.sqrt(componentsSquaredSum);
    }

    public double getComponent(int index) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < " + components.length
                    + ", индекс: " + index);
        }

        return components[index];
    }

    public void setComponent(int index, double component) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < " + components.length
                    + ", индекс: " + index);
        }

        components[index] = component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Vector vector = (Vector) o;

        return components.length == vector.components.length && Arrays.equals(components, vector.components);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Integer.hashCode(components.length);
        hash = prime * hash + Arrays.hashCode(components);
        return hash;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        return vector1.add(vector2);
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        return vector1.subtract(vector2);
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        int minVectorSize = Math.min(vector1.components.length, vector2.components.length);

        double scalarProduct = 0;

        for (int i = 0; i < minVectorSize; ++i) {
            scalarProduct += vector1.components[i] * vector2.components[i];
        }

        return scalarProduct;
    }
}
