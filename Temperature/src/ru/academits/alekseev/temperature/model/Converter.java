package ru.academits.alekseev.temperature.model;

public interface Converter {
    void convertToCelsius(double inputTemperature);

    void convertToFahrenheit(double inputTemperature);

    void convertToKelvin(double inputTemperature);

    void setInputTemperatureScale(String inputTemperatureScale);

    void setOutputTemperatureScale(String outputTemperatureScale);

    String getOutputTemperatureScale();

    double getOutputTemperature();

    void addConverterListener(ConverterListener listener);
}
