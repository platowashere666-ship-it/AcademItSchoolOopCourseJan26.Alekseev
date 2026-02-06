package ru.academits.alekseev.vector_main;

import ru.academits.alekseev.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(4);
        Vector vector2 = new Vector(new double[]{2.4, 5, 6.6});
        Vector vector3 = new Vector(5, new double[]{0.6, 2, 4.8, 7.5, 9});
        Vector vector4 = new Vector(vector2);

        System.out.println("Вектор 1: " + vector1);
        System.out.println("Вектор 2: " + vector2);
        System.out.println("Вектор 3: " + vector3);
        System.out.println("Вектор 4: " + vector4);

        System.out.println("Равны ли вектор 2 и вектор 4: " + vector2.equals(vector4));

        System.out.println("Хэш вектора 1: " + vector1.hashCode());

        System.out.println("Размер вектора 3: " + vector3.getSize());

        System.out.println("Длина вектора 1: " + vector1.getLength());

        System.out.println("Изменение компоненты вектора 2 по индексу 2 на 5.6");
        vector2.setComponent(2, 5.6);

        System.out.println("Компонента вектора 2 по индексу 2: " + vector2.getComponent(2));

        System.out.println("Сумма вектора 1 и 2 без изменений: " + Vector.getSum(vector1, vector2));
        System.out.println("Вектор 1: " + vector1);

        System.out.println("Сумма вектора 1 и 2 с изменением: " + vector1.add(vector2));
        System.out.println("Вектор 1: " + vector1);

        System.out.println("Разность вектора 3 и 4 без изменений: " + Vector.getDifference(vector3, vector4));
        System.out.println("Вектор 3: " + vector3);

        System.out.println("Разность вектора 3 и 4 с изменением: " + vector3.subtract(vector4));
        System.out.println("Вектор 3: " + vector3);

        System.out.println("Умножение вектора 2 на скаляр 4.5: " + vector2.multiply(4.5));

        System.out.println("Разворот вектора 2: " + vector2.reverse());

        System.out.println("Скалярное произведение векторов 3 и 4: " + Vector.getScalarProduct(vector3, vector4));
    }
}
