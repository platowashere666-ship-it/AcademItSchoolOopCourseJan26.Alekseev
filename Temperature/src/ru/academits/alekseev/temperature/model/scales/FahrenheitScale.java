package ru.academits.alekseev.temperature.model.scales;

public class FahrenheitScale implements Scale {
    @Override
    public double convertToCelsiusScale(double temperature) {
        return (temperature - 32) / 1.8;
    }

    @Override
    public double convertFromCelsiusScale(double temperature) {
        return (temperature * 1.8) + 32;
    }

    @Override
    public String getName() {
        return "Фаренгейт";
    }
}
