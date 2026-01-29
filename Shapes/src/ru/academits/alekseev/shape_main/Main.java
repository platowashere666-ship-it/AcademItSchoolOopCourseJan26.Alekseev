package ru.academits.alekseev.shape_main;

import ru.academits.alekseev.shape.*;

import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[7];

        shapes[0] = new Square(10);
        shapes[1] = new Rectangle(8, 10);
        shapes[2] = new Circle(7.5);
        shapes[3] = new Triangle(6.7, 5.4, 2.3, 4.5, 7.9, 4.3);
        shapes[4] = new Square(8);
        shapes[5] = new Rectangle(3, 6.7);
        shapes[6] = new Circle(12);

        Shape maxAreaShape = getMaxArea(shapes);
        Shape secondMaxPerimeterShape = getSecondMaxPerimeter(shapes);

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
            System.out.println("Фигуры одного типа!");
        } else {
            System.out.println("Фигуры разные по типу!");
        }
    }

    private static Shape getMaxArea(Shape[] shapes) {
        Arrays.sort(shapes, Comparator.comparingDouble(Shape::getArea).reversed());
        return shapes[0];
    }

    private static Shape getSecondMaxPerimeter(Shape[] shapes) {
        Arrays.sort(shapes, Comparator.comparingDouble(Shape::getPerimeter).reversed());
        return shapes[1];
    }
}
