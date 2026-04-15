package ru.academits.alekseev.temperature.model;

public class FahrenheitScale implements Scale {
    @Override
    public double convertToDefaultScale(double temperature) {
        return (temperature - 32) / 1.8;
    }

    @Override
    public double convert(double temperature) {
        return (temperature * 1.8) + 32;
    }

    @Override
    public String getName() {
        return "Фаренгейт";
    }
}
