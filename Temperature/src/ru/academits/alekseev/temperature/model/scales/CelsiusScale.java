package ru.academits.alekseev.temperature.model.scales;

public class CelsiusScale implements Scale {
    @Override
    public double convertToCelsiusScale(double temperature) {
        return temperature;
    }

    @Override
    public double convertToOutputScale(double temperature) {
        return temperature;
    }

    @Override
    public String getName() {
        return "Цельсий";
    }
}