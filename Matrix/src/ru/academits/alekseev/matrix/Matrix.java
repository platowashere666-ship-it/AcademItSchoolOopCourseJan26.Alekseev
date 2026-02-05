package ru.academits.alekseev.matrix;

import ru.academits.alekseev.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private final int n;
    private final int m;
    private final double[][] components;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        this.components = new double[n][m];
    }

    public Matrix(Matrix matrix) {
        n = matrix.n;
        m = matrix.m;
        components = new double[n][m];

        for (int i = 0; i < n; ++i) {
            components[i] = Arrays.copyOf(matrix.components[i], m);
        }
    }

    public Matrix(double[][] components) {
        n = components.length;
        m = components[0].length;
        this.components = new double[n][m];

        for (int i = 0; i < n; ++i) {
            this.components[i] = Arrays.copyOf(components[i], m);
        }
    }

    public Matrix(Vector[] vectors) {
        int maxVectorSize = 0;

        for (Vector vector : vectors) {
            if (vector.getSize() > maxVectorSize) {
                maxVectorSize = vector.getSize();
            }
        }

        n = vectors.length;
        m = maxVectorSize;
        components = new double[n][m];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                components[i][j] = j < vectors[i].getSize() ? vectors[i].getComponent(j) : 0.0;
            }
        }
    }

    public int[] getSize() {
        return new int[]{n, m};
    }

    public Vector getRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= n) {
            throw new IllegalArgumentException("Индекс должен быть > 0 и <= n! Индекс: " + rowIndex);
        }

        double[] rowComponents = Arrays.copyOf(components[rowIndex], m);

        return new Vector(rowComponents);
    }

    public void setRow(int rowIndex, Vector vector) {
        if (rowIndex < 0 || rowIndex >= n) {
            throw new IllegalArgumentException("Индекс должен быть >= 0 и < n! Индекс: " + rowIndex);
        }

        if (vector == null) {
            throw new IllegalArgumentException("Вектор не может быть null!");
        }

        if (vector.getSize() != m) {
            throw new IllegalArgumentException("Размерность вектора должна быть " + m + "! " +
                    "Размерность: " + vector.getSize());
        }

        for (int i = 0; i < m; ++i) {
            components[rowIndex][i] = vector.getComponent(i);
        }
    }

    public Vector getColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= m) {
            throw new IllegalArgumentException("Индекс должен быть >= 0 и < m! Индекс: " + columnIndex);
        }

        double[] columnComponents = new double[n];

        for (int i = 0; i < n; ++i) {
            columnComponents[i] = components[i][columnIndex];
        }

        return new Vector(columnComponents);
    }

    public Matrix transpose() {
        double[][] transposedComponents = new double[m][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                transposedComponents[j][i] = components[i][j];
            }
        }

        return new Matrix(transposedComponents);
    }

    public Matrix multiply(double scalar) {
        double[][] components = new double[n][m];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                components[i][j] = this.components[i][j] * scalar;
            }
        }

        return new Matrix(components);
    }

    public double getDeterminant() {
        if (n != m) {
            throw new IllegalStateException("Определитель можно вычислить только для квадратной матрицы");
        }

        return calculateDeterminantGauss(new Matrix(this));
    }

    private static double calculateDeterminantGauss(Matrix matrix) {
        double determinant = 1.0;

        final double EPSILON = 1e-10;

        for (int i = 0; i < matrix.n; ++i) {
            int maxRow = i;

            for (int j = i + 1; j < matrix.n; ++j) {
                if (Math.abs(matrix.components[j][i]) > Math.abs(matrix.components[maxRow][i])) {
                    maxRow = j;
                }
            }

            if (Math.abs(matrix.components[maxRow][i]) < EPSILON) {
                return 0.0;
            }

            if (maxRow != i) {
                double[] temp = matrix.components[i];
                matrix.components[i] = matrix.components[maxRow];
                matrix.components[maxRow] = temp;

                determinant *= -1;
            }

            determinant *= matrix.components[i][i];

            for (int j = i + 1; j < matrix.n; ++j) {
                double factor = matrix.components[j][i] / matrix.components[i][i];

                for (int k = i; k < matrix.n; ++k) {
                    matrix.components[j][k] -= factor * matrix.components[i][k];
                }
            }
        }

        return determinant;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");

        for (int i = 0; i < n; i++) {
            sb.append("{ ");

            for (int j = 0; j < m; j++) {
                sb.append(components[i][j]);

                if (j < m - 1) {
                    sb.append(", ");
                }
            }

            sb.append(" }");

            if (i < n - 1) {
                sb.append(", ");
            }
        }

        sb.append(" }");

        return sb.toString();
    }

    public Vector multiplyByVector(Vector vector) {
        if (vector == null) {
            throw new IllegalArgumentException("Вектор не может быть null!");
        }

        if (vector.getSize() != this.m) {
            throw new IllegalArgumentException("Размерность вектора должна совпадать с кол-вом столбцов матрицы!" +
                    " Размерность вектора: " + vector.getSize());
        }

        double[] components = new double[n];

        for (int i = 0; i < n; ++i) {
            double productionSum = 0;

            for (int j = 0; j < m; ++j) {
                productionSum += this.components[i][j] * vector.getComponent(j);
            }

            components[i] = productionSum;
        }

        return new Vector(components);
    }

    public Matrix add(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Матрица не может быть null!");
        }

        if (n != matrix.n || m != matrix.m) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!" +
                    "Размеры второй матрицы: " + Arrays.toString(matrix.getSize()));
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                components[i][j] += matrix.components[i][j];
            }
        }

        return this;
    }

    public Matrix subtract(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Матрица не может быть null!");
        }

        if (n != matrix.n || m != matrix.m) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!" +
                    "Размеры второй матрицы: " + Arrays.toString(matrix.getSize()));
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                components[i][j] -= matrix.components[i][j];
            }
        }

        return this;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null || matrix2 == null) {
            throw new IllegalArgumentException("Матрицы не могут быть null!");
        }

        if (matrix1.n != matrix2.n || matrix1.m != matrix2.m) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!");
        }

        double[][] components = new double[matrix1.n][matrix1.m];

        for (int i = 0; i < matrix1.n; ++i) {
            for (int j = 0; j < matrix1.m; ++j) {
                components[i][j] = matrix1.components[i][j] + matrix2.components[i][j];
            }
        }

        return new Matrix(components);
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null || matrix2 == null) {
            throw new IllegalArgumentException("Матрицы не могут быть null!");
        }

        if (matrix1.n != matrix2.n || matrix1.m != matrix2.m) {
            throw new IllegalArgumentException("Матрицы должны быть одинаковой размерности!");
        }

        double[][] components = new double[matrix1.n][matrix1.m];

        for (int i = 0; i < matrix1.n; ++i) {
            for (int j = 0; j < matrix1.m; ++j) {
                components[i][j] = matrix1.components[i][j] - matrix2.components[i][j];
            }
        }

        return new Matrix(components);
    }

    public static Matrix getProduction(Matrix matrix1, Matrix matrix2) {
        if (matrix1 == null || matrix2 == null) {
            throw new IllegalArgumentException("Матрицы не могут быть null!");
        }

        if (matrix1.m != matrix2.n) {
            throw new IllegalArgumentException("Кол-во столбов первой матрицы должно быть " +
                    "равно кол-ву строк второй матрицы!");
        }

        double[][] components = new double[matrix1.n][matrix2.m];

        for (int i = 0; i < matrix1.n; ++i) {
            for (int j = 0; j < matrix2.m; ++j) {
                for (int k = 0; k < matrix1.m; ++k) {
                    components[i][j] += matrix1.components[i][k] * matrix2.components[k][j];
                }
            }
        }

        return new Matrix(components);
    }
}
