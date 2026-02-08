package ru.academits.alekseev.matrix;

import ru.academits.alekseev.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private final Vector[] components;

    public Matrix(int rowsCount, int columnsCount) {
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
        this.components = new Vector[components.length];

        for (int i = 0; i < components.length; ++i) {
            this.components[i] = new Vector(components[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        components = new Vector[vectors.length];

        for (int i = 0; i < components.length; ++i) {
            components[i] = new Vector(vectors[i]);
        }
    }

    public int[] getSize() {
        return new int[]{components.length, components[0].getSize()};
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

        if (vector.getSize() != components[0].getSize()) {
            throw new IllegalArgumentException("Размерность вектора должна быть " + components[0].getSize() + "! " +
                    "Размерность: " + vector.getSize());
        }

        components[rowIndex] = new Vector(vector);
    }

    public Vector getColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= components[0].getSize()) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < "
                    + components[0].getSize() + "! Индекс: " + columnIndex);
        }

        double[] columnComponents = new double[components.length];

        for (int i = 0; i < components.length; ++i) {
            columnComponents[i] = components[i].getComponent(columnIndex);
        }

        return new Vector(columnComponents);
    }

    public Matrix transpose() {
        double[][] transposedComponents = new double[components[0].getSize()][components.length];

        for (int i = 0; i < components.length; ++i) {
            for (int j = 0; j < components[0].getSize(); ++j) {
                transposedComponents[j][i] = components[i].getComponent(j);
            }
        }

        return new Matrix(transposedComponents);
    }

    public Matrix multiply(double scalar) {
        for (int i = 0; i < components.length; ++i) {
            components[i] = this.components[i].multiply(scalar);
        }

        return this;
    }

    public double getDeterminant() {
        if (components.length != components[0].getSize()) {
            throw new IllegalStateException("Определитель можно вычислить только для квадратной матрицы");
        }

        return calculateDeterminantGauss(new Matrix(this));
    }

    private static double calculateDeterminantGauss(Matrix matrix) {
        double determinant = 1.0;

        final double EPSILON = 1e-10;

        for (int i = 0; i < matrix.components.length; ++i) {
            int maxRow = i;

            for (int j = i + 1; j < matrix.components.length; ++j) {
                if (Math.abs(matrix.components[j].getComponent(i)) >
                        Math.abs(matrix.components[maxRow].getComponent(i))) {
                    maxRow = j;
                }
            }

            if (Math.abs(matrix.components[maxRow].getComponent(i)) < EPSILON) {
                return 0.0;
            }

            if (maxRow != i) {
                Vector temp = matrix.components[i];
                matrix.components[i] = matrix.components[maxRow];
                matrix.components[maxRow] = temp;

                determinant *= -1;
            }

            determinant *= matrix.components[i].getComponent(i);

            for (int j = i + 1; j < matrix.components.length; ++j) {
                double factor = matrix.components[j].getComponent(i) / matrix.components[i].getComponent(i);

                for (int k = i; k < matrix.components.length; ++k) {
                    matrix.components[j].setComponent(j,
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

        for (int i = 0; i < components.length - 1; i++) {
            sb.append(components[i]).append(", ");
        }

        sb.append(components[components.length - 1]).append('}');

        return sb.toString();
    }

    public Vector multiplyByVector(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("Вектор не может быть null!");
        }

        if (vector.getSize() != this.components[0].getSize()) {
            throw new IllegalArgumentException("Размерность вектора должна совпадать с " + components[0].getSize() +
                    "! Размерность вектора: " + vector.getSize());
        }

        double[] components = new double[this.components.length];

        for (int i = 0; i < components.length; ++i) {
            double productionSum = 0;

            for (int j = 0; j < this.components[0].getSize(); ++j) {
                productionSum += this.components[i].getComponent(j) * vector.getComponent(j);
            }

            components[i] = productionSum;
        }

        return new Vector(components);
    }

    public Matrix add(Matrix matrix) {
        if (matrix == null) {
            throw new NullPointerException("Матрица не может быть null!");
        }

        if (!Arrays.equals(getSize(), matrix.getSize())) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!" +
                    "Размеры матриц: " + Arrays.toString(getSize()) + " и " + Arrays.toString(matrix.getSize()));
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

        if (!Arrays.equals(getSize(), matrix.getSize())) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!" +
                    "Размеры матриц: " + Arrays.toString(getSize()) + " и " + Arrays.toString(matrix.getSize()));
        }

        for (int i = 0; i < components.length; ++i) {
            components[i].subtract(matrix.components[i]);
        }

        return this;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null || matrix2 == null) {
            throw new NullPointerException("Матрицы не могут быть null!");
        }

        if (!Arrays.equals(matrix1.getSize(), matrix2.getSize())) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!" +
                    "Размеры матриц: " + Arrays.toString(matrix1.getSize())
                    + " и " + Arrays.toString(matrix2.getSize()));
        }

        Matrix matrix1Copy = new Matrix(matrix1);

        return matrix1Copy.add(matrix2);
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null || matrix2 == null) {
            throw new NullPointerException("Матрицы не могут быть null!");
        }

        if (!Arrays.equals(matrix1.getSize(), matrix2.getSize())) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!" +
                    "Размеры матриц: " + Arrays.toString(matrix1.getSize())
                    + " и " + Arrays.toString(matrix2.getSize()));
        }

        Matrix matrix1Copy = new Matrix(matrix1);

        return matrix1Copy.subtract(matrix2);
    }

    public static Matrix getProduction(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null || matrix2 == null) {
            throw new NullPointerException("Матрицы не могут быть null!");
        }

        if (matrix1.components[0].getSize() != matrix2.components.length) {
            throw new IllegalArgumentException("Кол-во столбов первой матрицы должно быть " +
                    "равно кол-ву строк второй матрицы!");
        }

        double[][] components = new double[matrix1.components.length][matrix2.components[0].getSize()];

        for (int i = 0; i < matrix1.components.length; ++i) {
            for (int j = 0; j < matrix2.components[0].getSize(); ++j) {
                for (int k = 0; k < matrix1.components[0].getSize(); ++k) {
                    components[i][j] += matrix1.components[i].getComponent(k) * matrix2.components[k].getComponent(j);
                }
            }
        }

        return new Matrix(components);
    }
}
