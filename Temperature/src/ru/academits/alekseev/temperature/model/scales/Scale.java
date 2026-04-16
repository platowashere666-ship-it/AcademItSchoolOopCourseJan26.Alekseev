package ru.academits.alekseev.temperature.model.scales;

public interface Scale {
    double convertToCelsiusScale(double temperature);

    double convertFromCelsiusScale(double temperature);

    String getName();
}
