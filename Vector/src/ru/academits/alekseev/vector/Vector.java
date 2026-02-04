package ru.academits.alekseev.vector;

import java.util.Arrays;

public class Vector {
    private int n;
    private double[] components;

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Размерность вектора n должна быть > 0, n: " + n);
        }

        this.n = n;
        components = new double[n];
    }

    public Vector(Vector vector) {
        n = vector.n;
        components = Arrays.copyOf(vector.components, vector.n);
    }

    public Vector(double[] components) {
        if (components == null) {
            throw new IllegalArgumentException("Массив не может быть null!");
        }

        n = components.length;
        this.components = Arrays.copyOf(components, n);
    }

    public Vector(int n, double[] components) {
        if (n <= 0) {
            throw new IllegalArgumentException("Размерность вектора n должна быть > 0, n: " + n);
        }

        if (components == null) {
            throw new IllegalArgumentException("Массив не может быть null!");
        }

        this.n = n;
        this.components = Arrays.copyOf(components, n);
    }

    public int getSize() {
        return n;
    }

    @Override
    public String toString() {
        return Arrays.toString(components);
    }

    private void expandSize(Vector vector) {
        int maxSize = Math.max(n, vector.n);

        if (n < maxSize) {
            n = maxSize;
            components = Arrays.copyOf(components, n);
        }
    }

    public Vector add(Vector vector) {
        expandSize(vector);

        for (int i = 0; i < n; ++i) {
            if (i < vector.n) {
                components[i] += vector.components[i];
            }
        }

        return this;
    }

    public Vector subtract(Vector vector) {
        expandSize(vector);

        for (int i = 0; i < n; ++i) {
            if (i < vector.n) {
                components[i] -= vector.components[i];
            }
        }

        return this;
    }

    public Vector multiply(double scalar) {
        for (int i = 0; i < n; ++i) {
            components[i] *= scalar;
        }

        return this;
    }

    public Vector reverse() {
        for (int i = 0; i < n; ++i) {
            components[i] *= -1;
        }

        return this;
    }

    public double getLength() {
        double componentsSquaredSum = 0;

        for (int i = 0; i < n; ++i) {
            componentsSquaredSum += components[i] * components[i];
        }

        return Math.sqrt(componentsSquaredSum);
    }

    public double getComponent(int index) {
        if (index < 0 || index >= n) {
            throw new IllegalArgumentException("Индекс должен быть >= 0 и < n, индекс: " + index);
        }

        return components[index];
    }

    public void setComponent(int index, double component) {
        if (index < 0 || index >= n) {
            throw new IllegalArgumentException("Индекс должен быть >= 0 и < n, индекс: " + index);
        }

        components[index] = component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        return n == vector.n && Arrays.equals(components, vector.components);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Integer.hashCode(n);
        hash = prime * hash + Arrays.hashCode(components);
        return hash;
    }

    public static Vector getVectorsSum(Vector vector1, Vector vector2) {
        double[] components = new double[Math.max(vector1.n, vector2.n)];

        for (int i = 0; i < components.length; ++i) {
            if (i < vector1.n) {
                components[i] += vector1.components[i];
            }

            if (i < vector2.n) {
                components[i] += vector2.components[i];
            }
        }

        return new Vector(components);
    }

    public static Vector getVectorsDifference(Vector vector1, Vector vector2) {
        double[] components = new double[Math.max(vector1.n, vector2.n)];

        for (int i = 0; i < components.length; ++i) {
            if (i < vector1.n) {
                components[i] += vector1.components[i];
            }

            if (i < vector2.n) {
                components[i] -= vector2.components[i];
            }
        }

        return new Vector(components);
    }

    public static double getVectorsProduct(Vector vector1, Vector vector2) {
        int minVectorSize = Math.min(vector1.n, vector2.n);

        double scalar = 0;

        for (int i = 0; i < minVectorSize; ++i) {
            scalar += vector1.components[i] * vector2.components[i];
        }

        return scalar;
    }
}
