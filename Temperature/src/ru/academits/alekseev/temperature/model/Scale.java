package ru.academits.alekseev.temperature.model;

public interface Scale {
    double convertToDefaultScale(double temperature);

    double convert(double temperature);

    String getName();
}
