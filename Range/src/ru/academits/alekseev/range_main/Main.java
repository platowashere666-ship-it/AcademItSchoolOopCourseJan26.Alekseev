package ru.academits.alekseev.range_main;

import ru.academits.alekseev.range.Range;

public class Main {
    public static void main(String[] args) {
        Range a = new Range(2.5, 8.0);
        Range b = new Range(5.0, 12.0);
        Range c = new Range(-3.0, 1.0);
        Range d = new Range(10.0, 15.0);

        System.out.printf("Длина A: %.2f%n", a.getLength());
        System.out.printf("Длина C: %.2f%n", c.getLength());

        System.out.println("Точка 4.0 входит в A?: " + a.isInside(4.0));
        System.out.println("Точка 1.5 входит в C?:  " + c.isInside(1.5));

        Range intersection1 = a.getRangeIntersection(b);
        Range intersection2 = b.getRangeIntersection(d);
        Range intersection3 = a.getRangeIntersection(d);

        Range[] union1 = a.getRangeUnion(intersection1);
        Range[] union2 = intersection2.getRangeUnion(intersection1);

        Range[] difference1 = a.getRangeDifference(b);
        Range[] difference2 = d.getRangeDifference(c);

        a.setFrom(7.2);
        a.setTo(10.5);

        System.out.println(a.getFrom());
        System.out.println(a.getTo());
    }
}