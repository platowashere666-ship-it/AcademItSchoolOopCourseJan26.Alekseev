package ru.academits.alekseev.shape;

public class Rectangle implements Shape {
    private final double sideALength;
    private final double sideBLength;

    public Rectangle(double sideALength, double sideBLength) {
        this.sideALength = sideALength;
        this.sideBLength = sideBLength;
    }

    @Override
    public double getWidth() {
        return sideALength;
    }

    @Override
    public double getHeight() {
        return sideBLength;
    }

    @Override
    public double getArea() {
        return sideALength * sideBLength;
    }

    @Override
    public double getPerimeter() {
        return 2 * (sideALength + sideBLength);
    }

    @Override
    public String toString() {
        return "Прямоугольник";
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Double.hashCode(sideALength);
        hash = prime * hash + Double.hashCode(sideBLength);
        return hash;
    }

    @Override
    public boolean equals(Shape shape) {
        return shape.getClass() == this.getClass();
    }
}
