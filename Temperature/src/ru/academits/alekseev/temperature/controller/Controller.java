package ru.academits.alekseev.temperature.controller;

import ru.academits.alekseev.temperature.model.Converter;
import ru.academits.alekseev.temperature.model.scales.Scale;
import ru.academits.alekseev.temperature.view.View;

import java.util.List;
import java.util.Objects;

public class Controller {
    private final Converter converter;
    private final View view;

    public Controller(Converter converter, View view) {
        this.converter = Objects.requireNonNull(converter, "Converter не может быть null.");
        this.view = Objects.requireNonNull(view, "View не может быть null.");

        view.setController(this);
        converter.addConverterListener(view);
    }

    public void start() {
        view.start();
    }

    public List<Scale> getAvailableScales() {
        return converter.getAvailableScales();
    }

    public void setInputScale(Scale inputTemperatureScale) {
        converter.setInputScale(inputTemperatureScale);
    }

    public void setOutputScale(Scale outputTemperatureScale) {
        converter.setOutputScale(outputTemperatureScale);
    }

    public void convert(double inputTemperature) {
        converter.convert(inputTemperature);
    }
}
