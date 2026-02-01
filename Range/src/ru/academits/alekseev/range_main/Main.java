package ru.academits.alekseev.range_main;

import ru.academits.alekseev.range.Range;

public class Main {
    public static void main(String[] args) {
        Range a = new Range(2.5, 8.0);
        Range b = new Range(5.0, 12.0);
        Range c = new Range(-3.0, 1.0);

        System.out.printf("Длина A: %.2f%n", a.getLength());
        System.out.printf("Длина C: %.2f%n", c.getLength());

        System.out.println("Точка 4.0 входит в A?: " + a.isInside(4.0));
        System.out.println("Точка 1.5 входит в C?:  " + c.isInside(1.5));

        Range intersection = a.getIntersection(b);
        System.out.println(intersection);

        Range[] union = a.getUnion(intersection);
        for (Range range : union) {
            System.out.println(range);
        }

        Range[] difference = a.getDifference(b);
        for (Range range : difference) {
            System.out.println(range);
        }

        a.setFrom(7.2);
        System.out.println(a.getFrom());

        a.setTo(10.5);
        System.out.println(a.getTo());
    }
}