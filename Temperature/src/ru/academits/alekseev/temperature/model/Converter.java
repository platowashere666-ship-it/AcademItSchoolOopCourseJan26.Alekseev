package ru.academits.alekseev.temperature.model;

import java.util.List;

public interface Converter {
    void convertToCelsius(double inputTemperature);

    void convertToFahrenheit(double inputTemperature);

    void convertToKelvin(double inputTemperature);

    List<String> getAvailableTemperatureScales();

    String getInputTemperatureScale();

    void setInputTemperatureScale(String inputTemperatureScale);

    String getOutputTemperatureScale();

    void setOutputTemperatureScale(String outputTemperatureScale);

    double getOutputTemperature();

    void addConverterListener(ConverterListener listener);
}
