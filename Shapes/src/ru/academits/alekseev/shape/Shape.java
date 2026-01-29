package ru.academits.alekseev.shape;

public interface Shape {
    double getWidth();

    double getHeight();

    double getArea();

    double getPerimeter();

    boolean equals(Shape shape);
}
