package ru.academits.alekseev.temperature.model;

import java.util.List;

public interface Converter {
    void convert(double inputTemperature);

    List<Scale> getAvailableScales();

    void setInputScale(Scale inputScale);

    Scale getOutputScale();

    void setOutputScale(Scale outputScale);

    double getOutputTemperature();

    void addConverterListener(ConverterListener listener);
}
