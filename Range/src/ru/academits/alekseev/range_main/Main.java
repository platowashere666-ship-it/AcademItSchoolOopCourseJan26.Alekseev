package ru.academits.alekseev.range_main;

import ru.academits.alekseev.range.Range;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(2.5, 8.0);
        System.out.println("Первый диапазон: " + range1);

        Range range2 = new Range(5.0, 12.0);
        System.out.println("Второй диапазон: " + range2);

        Range range3 = new Range(-3.0, 1.0);
        System.out.println("Третий диапазон: " + range3);

        System.out.printf("Длина первого диапазона: %.2f%n", range1.getLength());
        System.out.printf("Длина третьего диапазона: %.2f%n", range3.getLength());

        System.out.println("Точка 4.0 входит в первый диапазон?: " + range1.isInside(4.0));
        System.out.println("Точка 1.5 входит в третий диапазон?:  " + range3.isInside(1.5));

        Range intersection = range1.getIntersection(range2);
        System.out.println("Пересечение первого и второго диапазона: " + intersection);

        Range[] union = range1.getUnion(intersection);
        System.out.println("Объедение первого диапазона и пересечения: " + Arrays.toString(union));

        Range[] difference = range1.getDifference(range2);

        if (difference.length == 0) {
            System.out.println("Разность первого и второго диапазона равна нулю");
        } else {
            System.out.println("Разность первого и второго диапазона: " + Arrays.toString(difference));
        }

        range1.setFrom(7.2);
        System.out.println("Первая точка первого диапазона: " + range1.getFrom());

        range1.setTo(10.5);
        System.out.println("Вторая точка первого диапазона: " + range1.getTo());
    }
}