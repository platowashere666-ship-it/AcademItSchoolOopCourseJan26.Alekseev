package ru.academits.alekseev.temperature.model;

public class CelsiusScale implements Scale {
    @Override
    public double convertToDefaultScale(double temperature) {
        return temperature;
    }

    @Override
    public double convert(double temperature) {
        return temperature;
    }

    @Override
    public String getName() {
        return "Цельсий";
    }
}