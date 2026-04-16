package ru.academits.alekseev.temperature.model.scales;

public class KelvinScale implements Scale {
    @Override
    public double convertToCelsiusScale(double temperature) {
        return temperature - 273.15;
    }

    @Override
    public double convertToOutputScale(double temperature) {
        return temperature + 273.15;
    }

    @Override
    public String getName() {
        return "Кельвин";
    }
}
