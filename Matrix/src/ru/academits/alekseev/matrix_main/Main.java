package ru.academits.alekseev.matrix_main;

import ru.academits.alekseev.matrix.Matrix;
import ru.academits.alekseev.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(4, 4);

        double[][] matrix2Components = {{3.5, 4.2}, {7.6, 9}};

        Matrix matrix2 = new Matrix(matrix2Components);

        Matrix matrix3 = new Matrix(matrix1);

        Vector[] vectors = new Vector[2];

        vectors[0] = new Vector(3);
        vectors[1] = new Vector(4);

        Matrix matrix4 = new Matrix(vectors);

        System.out.println("Матрица 1: " + matrix1);
        System.out.println("Матрица 2: " + matrix2);
        System.out.println("Матрица 3: " + matrix3);
        System.out.println("Матрица 4: " + matrix4);

        System.out.println("Равны ли матрицы 1 и 3? " + matrix1.equals(matrix3));

        System.out.println("Хэш матрицы 2: " + matrix2.hashCode());

        System.out.println("Кол-во строк матрицы 2: " + matrix2.getRowsCount());

        System.out.println("Кол-во столбцов матрицы 2: " + matrix2.getColumnsCount());

        double[] rowComponents = {2.2, 3.3, 4.4, 5.5};
        Vector row = new Vector(rowComponents);
        matrix1.setRow(0, row);

        System.out.println("Установим строку в матрицу 1 по индексу 0: " + matrix1);

        System.out.println("Строка по индексу 0 из матрицы 1: " + matrix1.getRow(0));

        System.out.println("Столбец по индексу 0 из матрицы 1: " + matrix1.getColumn(0));

        matrix1.setRow(1, row);
        matrix1.setRow(2, row);
        matrix1.setRow(3, row);
        System.out.println("Транспонируем матрицу 1: " + matrix1.transpose());

        System.out.println("Умножим матрицу 1 на 5: " + matrix1.multiply(5));

        double[] vectorComponents = {3.3, 4.4, 5, 10};
        Vector vectorToMultiply = new Vector(vectorComponents);

        System.out.println("Умножим матрицу 1 на вектор: " + matrix1.multiplyByVector(vectorToMultiply));

        System.out.println("Определитель матрицы 1: " + matrix1.getDeterminant());

        System.out.println("Сумма матрицы 1 и матрицы 3: " + Matrix.getSum(matrix1, matrix3));

        System.out.println("Разность матрицы 1 и матрицы 3: " + Matrix.getDifference(matrix1, matrix3));

        System.out.println("Произведение матрицы 1 и матрицы 3: " + Matrix.getMultiplication(matrix1, matrix3));
    }
}
