package ru.academits.alekseev.temperature.model;

public class KelvinScale implements Scale {
    @Override
    public double convertToDefaultScale(double temperature) {
        return temperature - 273.15;
    }

    @Override
    public double convert(double temperature) {
        return temperature + 273.15;
    }

    @Override
    public String getName() {
        return "Кельвин";
    }
}
