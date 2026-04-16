package ru.academits.alekseev.temperature.model;

import ru.academits.alekseev.temperature.model.scales.Scale;

import java.util.List;

public interface Converter {
    void convert(double inputTemperature);

    List<Scale> getAvailableScales();

    void setInputScale(Scale inputScale);

    void setOutputScale(Scale outputScale);

    double getOutputTemperature();

    void addConverterListener(ConverterListener listener);
}
