package ru.academits.alekseev.matrix;

import ru.academits.alekseev.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private final Vector[] components;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0 || columnsCount <= 0) {
            throw new IllegalArgumentException("Матрица должна иметь хотя бы 1 строку и 1 столбец! Кол-во строк: " +
                    rowsCount + " Кол-во столбцов: " + columnsCount);
        }

        components = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; ++i) {
            components[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        components = new Vector[matrix.components.length];

        for (int i = 0; i < components.length; ++i) {
            components[i] = new Vector(matrix.components[i]);
        }
    }

    public Matrix(double[][] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("Матрица должна иметь хотя бы 1 строку!" +
                    " Кол-во строк: " + components.length);
        }

        int maxColumnsCount = 0;

        for (double[] row : components) {
            if (maxColumnsCount < row.length) {
                maxColumnsCount = row.length;
            }
        }

        if (maxColumnsCount == 0) {
            throw new IllegalArgumentException("Матрица должна иметь хотя бы 1 столбец! Кол-во столбцов: " +
                    maxColumnsCount);
        }

        this.components = new Vector[components.length];

        for (int i = 0; i < components.length; ++i) {
            this.components[i] = new Vector(maxColumnsCount, components[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Массив векторов не может быть пустым!");
        }

        int maxVectorSize = 0;

        for (Vector vector : vectors) {
            if (maxVectorSize < vector.getSize()) {
                maxVectorSize = vector.getSize();
            }
        }

        components = new Vector[vectors.length];

        for (int i = 0; i < components.length; ++i) {
            if (vectors[i].getSize() < maxVectorSize) {
                Vector extendedVector = new Vector(maxVectorSize);

                for (int j = 0; j < vectors[i].getSize(); ++j) {
                    extendedVector.setComponent(j, vectors[i].getComponent(j));
                }

                components[i] = extendedVector;
            }

            components[i] = new Vector(vectors[i]);
        }
    }

    public int getRowsCount() {
        return components.length;
    }

    public int getColumnsCount() {
        return components[0].getSize();
    }

    public Vector getRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= components.length) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < " +
                    components.length + "! Индекс: " + rowIndex);
        }

        return new Vector(components[rowIndex]);
    }

    public void setRow(int rowIndex, Vector vector) {
        if (rowIndex < 0 || rowIndex >= components.length) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < "
                    + components.length + "! Индекс: " + rowIndex);
        }

        if (vector == null) {
            throw new NullPointerException("Вектор не может быть null!");
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Размерность вектора должна быть " + getColumnsCount() + "! " +
                    "Размерность: " + vector.getSize());
        }

        components[rowIndex] = new Vector(vector);
    }

    public Vector getColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= getColumnsCount()) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < "
                    + getColumnsCount() + "! Индекс: " + columnIndex);
        }

        double[] columnComponents = new double[components.length];

        for (int i = 0; i < components.length; ++i) {
            columnComponents[i] = components[i].getComponent(columnIndex);
        }

        return new Vector(columnComponents);
    }

    public Matrix transpose() {
        for (int i = 0; i < components.length; ++i) {
            for (int j = 0; j < getColumnsCount(); ++j) {
                components[j].setComponent(i, components[i].getComponent(j));
            }
        }

        return this;
    }

    public Matrix multiply(double scalar) {
        for (Vector component : components) {
            component.multiply(scalar);
        }

        return this;
    }

    public double getDeterminant() {
        if (components.length != getColumnsCount()) {
            throw new IllegalStateException("Определитель можно вычислить только для квадратной матрицы!");
        }

        return calculateDeterminantGauss(new Matrix(this));
    }

    private static double calculateDeterminantGauss(Matrix matrix) {
        double determinant = 1.0;

        final double EPSILON = 1e-10;

        for (int i = 0; i < matrix.components.length; ++i) {
            int maxRowIndex = i;

            for (int j = i + 1; j < matrix.components.length; ++j) {
                if (Math.abs(matrix.components[j].getComponent(i)) >
                        Math.abs(matrix.components[maxRowIndex].getComponent(i))) {
                    maxRowIndex = j;
                }
            }

            if (Math.abs(matrix.components[maxRowIndex].getComponent(i)) < EPSILON) {
                return 0.0;
            }

            if (maxRowIndex != i) {
                Vector temp = matrix.components[i];
                matrix.components[i] = matrix.components[maxRowIndex];
                matrix.components[maxRowIndex] = temp;

                determinant *= -1;
            }

            determinant *= matrix.components[i].getComponent(i);

            for (int j = i + 1; j < matrix.components.length; ++j) {
                double factor = matrix.components[j].getComponent(i) / matrix.components[i].getComponent(i);

                for (int k = i; k < matrix.components.length; ++k) {
                    matrix.components[j].setComponent(k,
                            matrix.components[j].getComponent(k)
                                    - factor * matrix.components[i].getComponent(k));
                }
            }
        }

        return determinant;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');

        int lastComponentIndex = components.length - 1;

        for (int i = 0; i < lastComponentIndex; i++) {
            sb.append(components[i]).append(", ");
        }

        sb.append(components[lastComponentIndex]).append('}');

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

        Matrix matrix = (Matrix) o;

        return Arrays.deepEquals(this.components, matrix.components);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Arrays.deepHashCode(components);
        return hash;
    }

    public Vector multiplyByVector(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("Вектор не может быть null!");
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Размерность вектора должна совпадать с " + getColumnsCount() +
                    "! Размерность вектора: " + vector.getSize());
        }

        double[] components = new double[this.components.length];

        for (int i = 0; i < components.length; ++i) {
            components[i] = Vector.getScalarProduct(this.components[i], vector);
        }

        return new Vector(components);
    }

    public Matrix add(Matrix matrix) {
        if (matrix == null) {
            throw new NullPointerException("Матрица не может быть null!");
        }

        if (components.length != matrix.components.length || getColumnsCount() != matrix.getColumnsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!" +
                    "Размеры первой матрицы: " + components.length + ", " + getColumnsCount() +
                    " Размеры второй матрицы: " + matrix.components.length + ", " + matrix.getColumnsCount());
        }

        for (int i = 0; i < components.length; ++i) {
            components[i].add(matrix.components[i]);
        }

        return this;
    }

    public Matrix subtract(Matrix matrix) {
        if (matrix == null) {
            throw new NullPointerException("Матрица не может быть null!");
        }

        if (components.length != matrix.components.length || getColumnsCount() != matrix.getColumnsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!" +
                    "Размеры первой матрицы: " + components.length + ", " + getColumnsCount() +
                    " Размеры второй матрицы: " + matrix.components.length + ", " + matrix.getColumnsCount());
        }

        for (int i = 0; i < components.length; ++i) {
            components[i].subtract(matrix.components[i]);
        }

        return this;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null) {
            throw new NullPointerException("Матрица 1 не может быть null!");
        }

        if (matrix2 == null) {
            throw new NullPointerException("Матрица 2 не может быть null!");
        }

        if (matrix1.components.length != matrix2.components.length ||
                matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!" +
                    "Размеры первой матрицы: " + matrix1.components.length + ", " + matrix1.getColumnsCount() +
                    " Размеры второй матрицы: " + matrix2.components.length + ", " + matrix2.getColumnsCount());
        }

        Matrix matrix1Copy = new Matrix(matrix1);

        return matrix1Copy.add(matrix2);
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null) {
            throw new NullPointerException("Матрица 1 не может быть null!");
        }

        if (matrix2 == null) {
            throw new NullPointerException("Матрица 2 не может быть null!");
        }

        if (matrix1.components.length != matrix2.components.length ||
                matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!" +
                    "Размеры первой матрицы: " + matrix1.components.length + ", " + matrix1.getColumnsCount() +
                    " Размеры второй матрицы: " + matrix2.components.length + ", " + matrix2.getColumnsCount());
        }

        Matrix matrix1Copy = new Matrix(matrix1);

        return matrix1Copy.subtract(matrix2);
    }

    public static Matrix getMultiplication(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null) {
            throw new NullPointerException("Матрица 1 не может быть null!");
        }

        if (matrix2 == null) {
            throw new NullPointerException("Матрица 2 не может быть null!");
        }

        if (matrix1.getColumnsCount() != matrix2.components.length) {
            throw new IllegalArgumentException("Кол-во столбов первой матрицы должно быть " +
                    "равно кол-ву строк второй матрицы!");
        }

        double[][] components = new double[matrix1.components.length][matrix2.getColumnsCount()];

        for (int i = 0; i < matrix1.components.length; ++i) {
            for (int j = 0; j < matrix2.getColumnsCount(); ++j) {
                for (int k = 0; k < matrix1.getColumnsCount(); ++k) {
                    components[i][j] += matrix1.components[i].getComponent(k) * matrix2.components[k].getComponent(j);
                }
            }
        }

        return new Matrix(components);
    }
}
