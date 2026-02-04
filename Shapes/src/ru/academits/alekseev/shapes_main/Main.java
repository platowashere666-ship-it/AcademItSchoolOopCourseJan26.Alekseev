package ru.academits.alekseev.shapes_main;

import ru.academits.alekseev.shapes.*;
import ru.academits.alekseev.shapes_comparators.ShapeAreaComparator;
import ru.academits.alekseev.shapes_comparators.ShapePerimeterComparator;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Square(6), new Rectangle(8, 5.5),
                new Circle(7.5), new Triangle(1, 6, 2.5, 10, 4, 12),
                new Square(2)
        };

        Shape maxAreaShape = getMaxAreaShape(shapes);
        Shape secondMaxPerimeterShape = getSecondMaxPerimeterShape(shapes);

        System.out.printf("Фигура с наибольшей площадью: %s%n", maxAreaShape.toString());
        System.out.printf("Высота: %.2f%n", maxAreaShape.getHeight());
        System.out.printf("Ширина: %.2f%n", maxAreaShape.getWidth());
        System.out.printf("Площадь: %.2f%n", maxAreaShape.getArea());
        System.out.printf("Периметр: %.2f%n", maxAreaShape.getPerimeter());
        System.out.printf("Хэш: %d%n%n", maxAreaShape.hashCode());

        System.out.printf("Вторая по периметру фигура: %s%n", secondMaxPerimeterShape.toString());
        System.out.printf("Высота: %.2f%n", secondMaxPerimeterShape.getHeight());
        System.out.printf("Ширина: %.2f%n", secondMaxPerimeterShape.getWidth());
        System.out.printf("Площадь: %.2f%n", secondMaxPerimeterShape.getArea());
        System.out.printf("Периметр: %.2f%n", secondMaxPerimeterShape.getPerimeter());
        System.out.printf("Хэш: %d%n%n", secondMaxPerimeterShape.hashCode());

        if (maxAreaShape.equals(secondMaxPerimeterShape)) {
            System.out.println("Фигуры одинаковы!");
        } else {
            System.out.println("Фигуры разные!");
        }
    }

    private static Shape getMaxAreaShape(Shape[] shapes) {
        Arrays.sort(shapes, new ShapeAreaComparator());

        return shapes[shapes.length - 1];
    }

    private static Shape getSecondMaxPerimeterShape(Shape[] shapes) {
        Arrays.sort(shapes, new ShapePerimeterComparator());

        return shapes[shapes.length - 2];
    }
}
