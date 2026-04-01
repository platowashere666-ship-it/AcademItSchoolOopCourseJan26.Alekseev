package ru.academits.alekseev.temperature.model;

import java.util.ArrayList;
import java.util.List;

public class TemperatureConverter implements Converter {
    private final List<ConverterListener> listeners = new ArrayList<>();

    private double celsiusTemperature;
    private double fahrenheitTemperature;
    private double kelvinTemperature;

    private String inputTemperatureScale;
    private String outputTemperatureScale;

    @Override
    public void convertToCelsius(double inputTemperature) {
        checkInputTemperatureScale();

        if (inputTemperatureScale.equals("Цельсий")) {
            celsiusTemperature = inputTemperature;
        } else if (inputTemperatureScale.equals("Фаренгейт")) {
            celsiusTemperature = (inputTemperature - 32) / 1.8;
        } else {
            celsiusTemperature = inputTemperature - 273.15;
        }

        notifyListeners();
    }

    @Override
    public void convertToFahrenheit(double inputTemperature) {
        checkInputTemperatureScale();

        if (inputTemperatureScale.equals("Фаренгейт")) {
            fahrenheitTemperature = inputTemperature;
        } else if (inputTemperatureScale.equals("Цельсий")) {
            fahrenheitTemperature = (inputTemperature * 1.8) + 32;
        } else {
            fahrenheitTemperature = (inputTemperature - 273.15) * 1.8 + 32;
        }

        notifyListeners();
    }

    @Override
    public void convertToKelvin(double inputTemperature) {
        checkInputTemperatureScale();

        if (inputTemperatureScale.equals("Кельвин")) {
            kelvinTemperature = inputTemperature;
        } else if (inputTemperatureScale.equals("Цельсий")) {
            kelvinTemperature = inputTemperature + 273.15;
        } else {
            kelvinTemperature = (inputTemperature - 32) * 0.56 + 273.15;
        }

        notifyListeners();
    }

    @Override
    public void setInputTemperatureScale(String inputTemperatureScale) {
        this.inputTemperatureScale = inputTemperatureScale;
    }

    @Override
    public void setOutputTemperatureScale(String outputTemperatureScale) {
        this.outputTemperatureScale = outputTemperatureScale;
    }

    @Override
    public String getOutputTemperatureScale() {
        return outputTemperatureScale;
    }

    @Override
    public double getOutputTemperature() {
        if (outputTemperatureScale.equals("Цельсий")) {
            return celsiusTemperature;
        } else if (outputTemperatureScale.equals("Фаренгейт")) {
            return fahrenheitTemperature;
        } else {
            return kelvinTemperature;
        }
    }

    @Override
    public void addConverterListener(ConverterListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (ConverterListener listener : listeners) {
            listener.temperatureConverted();
        }
    }

    private void checkInputTemperatureScale() {
        if (inputTemperatureScale == null) {
            throw new NullPointerException("Выберите исходную температурную шкалу.");
        }
    }
}
