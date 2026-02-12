package ru.academits.alekseev.matrix;

import ru.academits.alekseev.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0) {
            throw new IllegalArgumentException("Матрица должна иметь хотя бы 1 строку. Кол-во строк: " + rowsCount);
        }

        if (columnsCount <= 0) {
            throw new IllegalArgumentException("Матрица должна иметь хотя бы 1 столбец. Кол-во столбцов: " + columnsCount);
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; ++i) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < rows.length; ++i) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] rows) {
        if (rows.length == 0) {
            throw new IllegalArgumentException("Матрица должна иметь хотя бы 1 строку." +
                    " Кол-во строк: " + rows.length);
        }

        int maxColumnsCount = 0;

        for (double[] row : rows) {
            if (maxColumnsCount < row.length) {
                maxColumnsCount = row.length;
            }
        }

        if (maxColumnsCount == 0) {
            throw new IllegalArgumentException("Матрица должна иметь хотя бы 1 столбец. Кол-во столбцов: " +
                    maxColumnsCount);
        }

        this.rows = new Vector[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            this.rows[i] = new Vector(maxColumnsCount, rows[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Массив векторов не может быть пустым.");
        }

        int maxVectorSize = 0;

        for (Vector vector : vectors) {
            if (maxVectorSize < vector.getSize()) {
                maxVectorSize = vector.getSize();
            }
        }

        rows = new Vector[vectors.length];

        for (int i = 0; i < rows.length; ++i) {
            if (vectors[i].getSize() == maxVectorSize) {
                rows[i] = new Vector(vectors[i]);
            } else {
                rows[i] = new Vector(maxVectorSize);
                rows[i].add(vectors[i]);
            }
        }
    }

    private static void checkSize(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows.length != matrix2.rows.length || matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности." +
                    "Размеры первой матрицы: " + matrix1.rows.length + ", " + matrix1.getColumnsCount() +
                    ". Размеры второй матрицы: " + matrix2.rows.length + ", " + matrix2.getColumnsCount());
        }
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getSize();
    }

    public Vector getRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < " +
                    rows.length + "! Индекс: " + rowIndex);
        }

        return new Vector(rows[rowIndex]);
    }

    public void setRow(int rowIndex, Vector vector) {
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < "
                    + rows.length + ". Индекс: " + rowIndex);
        }

        if (vector == null) {
            throw new NullPointerException("Вектор не может быть null.");
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Размерность вектора должна быть " + getColumnsCount() + ". " +
                    "Размерность: " + vector.getSize());
        }

        rows[rowIndex] = new Vector(vector);
    }

    public Vector getColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= getColumnsCount()) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < "
                    + getColumnsCount() + ". Индекс: " + columnIndex);
        }

        double[] column = new double[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            column[i] = rows[i].getComponent(columnIndex);
        }

        return new Vector(column);
    }

    public Matrix transpose() {
        int columnsCount = getColumnsCount();

        Vector[] transposedRows = new Vector[columnsCount];

        for (int i = 0; i < columnsCount; ++i) {
            transposedRows[i] = getColumn(i);
        }

        rows = transposedRows;

        return this;
    }

    public Matrix multiply(double scalar) {
        for (Vector row : rows) {
            row.multiply(scalar);
        }

        return this;
    }

    public double getDeterminant() {
        if (rows.length != getColumnsCount()) {
            throw new IllegalStateException("Определитель можно вычислить только для квадратной матрицы." +
                    " Кол-во строк: " + rows.length + ". Кол-во столбцов: " + getColumnsCount());
        }

        return calculateDeterminantGauss(new Matrix(this));
    }

    private static double calculateDeterminantGauss(Matrix matrix) {
        double determinant = 1.0;

        final double EPSILON = 1e-10;

        for (int i = 0; i < matrix.rows.length; ++i) {
            int maxRowIndex = i;

            for (int j = i + 1; j < matrix.rows.length; ++j) {
                if (Math.abs(matrix.rows[j].getComponent(i)) >
                        Math.abs(matrix.rows[maxRowIndex].getComponent(i))) {
                    maxRowIndex = j;
                }
            }

            if (Math.abs(matrix.rows[maxRowIndex].getComponent(i)) < EPSILON) {
                return 0.0;
            }

            if (maxRowIndex != i) {
                Vector temp = matrix.rows[i];
                matrix.rows[i] = matrix.rows[maxRowIndex];
                matrix.rows[maxRowIndex] = temp;

                determinant *= -1;
            }

            determinant *= matrix.rows[i].getComponent(i);

            for (int j = i + 1; j < matrix.rows.length; ++j) {
                double factor = matrix.rows[j].getComponent(i) / matrix.rows[i].getComponent(i);

                for (int k = i; k < matrix.rows.length; ++k) {
                    matrix.rows[j].setComponent(k,
                            matrix.rows[j].getComponent(k)
                                    - factor * matrix.rows[i].getComponent(k));
                }
            }
        }

        return determinant;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');

        int lastComponentIndex = rows.length - 1;

        for (int i = 0; i < lastComponentIndex; i++) {
            sb.append(rows[i]).append(", ");
        }

        sb.append(rows[lastComponentIndex]).append('}');

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

        return Arrays.equals(rows, matrix.rows);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Arrays.hashCode(rows);
        return hash;
    }

    public Vector multiplyByVector(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("Вектор не может быть null.");
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Размерность вектора должна совпадать с " + getColumnsCount() +
                    ". Размерность вектора: " + vector.getSize());
        }

        double[] multiplicationResult = new double[rows.length];

        for (int i = 0; i < multiplicationResult.length; ++i) {
            multiplicationResult[i] = Vector.getScalarProduct(rows[i], vector);
        }

        return new Vector(multiplicationResult);
    }

    public Matrix add(Matrix matrix) {
        if (matrix == null) {
            throw new NullPointerException("Матрица не может быть null.");
        }

        checkSize(this, matrix);

        for (int i = 0; i < rows.length; ++i) {
            rows[i].add(matrix.rows[i]);
        }

        return this;
    }

    public Matrix subtract(Matrix matrix) {
        if (matrix == null) {
            throw new NullPointerException("Матрица не может быть null.");
        }

        checkSize(this, matrix);

        for (int i = 0; i < rows.length; ++i) {
            rows[i].subtract(matrix.rows[i]);
        }

        return this;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null) {
            throw new NullPointerException("Матрица 1 не может быть null.");
        }

        if (matrix2 == null) {
            throw new NullPointerException("Матрица 2 не может быть null.");
        }

        checkSize(matrix1, matrix2);

        Matrix matrix1Copy = new Matrix(matrix1);

        return matrix1Copy.add(matrix2);
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null) {
            throw new NullPointerException("Матрица 1 не может быть null.");
        }

        if (matrix2 == null) {
            throw new NullPointerException("Матрица 2 не может быть null.");
        }

        checkSize(matrix1, matrix2);

        Matrix matrix1Copy = new Matrix(matrix1);

        return matrix1Copy.subtract(matrix2);
    }

    public static Matrix getMultiplication(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null) {
            throw new NullPointerException("Матрица 1 не может быть null.");
        }

        if (matrix2 == null) {
            throw new NullPointerException("Матрица 2 не может быть null.");
        }

        if (matrix1.getColumnsCount() != matrix2.rows.length) {
            throw new IllegalArgumentException("Кол-во столбов первой матрицы должно быть " +
                    "равно кол-ву строк второй матрицы.");
        }

        double[][] multiplicationResult = new double[matrix1.rows.length][matrix2.getColumnsCount()];

        for (int i = 0; i < matrix1.rows.length; ++i) {
            for (int j = 0; j < matrix2.getColumnsCount(); ++j) {
                for (int k = 0; k < matrix1.getColumnsCount(); ++k) {
                    multiplicationResult[i][j] += matrix1.rows[i].getComponent(k) * matrix2.rows[k].getComponent(j);
                }
            }
        }

        return new Matrix(multiplicationResult);
    }
}
