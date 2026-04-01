package ru.academits.alekseev.temperature.controller;

import ru.academits.alekseev.temperature.model.Converter;
import ru.academits.alekseev.temperature.view.View;

public class Controller {
    private final Converter converter;
    private final View view;

    public Controller(Converter converter, View view) {
        if (converter == null) {
            throw new IllegalArgumentException("Converter не может быть null.");
        }

        if (view == null) {
            throw new IllegalArgumentException("View не может быть null.");
        }

        this.converter = converter;
        this.view = view;

        view.setController(this);
        converter.addConverterListener(view);
    }

    public void start() {
        view.start();
    }

    public void setInputTemperatureScale(String inputTemperatureScale) {
        converter.setInputTemperatureScale(inputTemperatureScale);
    }

    public void setOutputTemperatureScale(String outputTemperatureScale) {
        converter.setOutputTemperatureScale(outputTemperatureScale);
    }

    public String getOutputTemperatureScale() {
        return converter.getOutputTemperatureScale();
    }

    public void convertTemperature(double InputTemperature, String outputTemperatureScale) {
        if (outputTemperatureScale == null) {
            throw new NullPointerException("Выберите температурную шкалу результата.");
        }

        if (outputTemperatureScale.equals("Цельсий")) {
            converter.convertToCelsius(InputTemperature);
        } else if (outputTemperatureScale.equals("Фаренгейт")) {
            converter.convertToFahrenheit(InputTemperature);
        } else {
            converter.convertToKelvin(InputTemperature);
        }
    }
}
