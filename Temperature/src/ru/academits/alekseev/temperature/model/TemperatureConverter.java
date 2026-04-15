package ru.academits.alekseev.temperature.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemperatureConverter implements Converter {
    private final List<ConverterListener> listeners = new ArrayList<>();
    private final List<Scale> temperatureScales = new ArrayList<>();

    private Scale inputScale;
    private Scale outputScale;

    private double outputTemperature;

    public TemperatureConverter() {
        addScale(new CelsiusScale());
        addScale(new FahrenheitScale());
        addScale(new KelvinScale());
    }

    @Override
    public void convert(double inputTemperature) {
        checkInputTemperatureScale();
        checkOutputTemperatureScale();

        double celsiusTemperature = inputScale.convertToDefaultScale(inputTemperature);
        outputTemperature = outputScale.convert(celsiusTemperature);

        notifyListeners();
    }

    @Override
    public List<Scale> getAvailableScales() {
        return List.copyOf(temperatureScales);
    }

    @Override
    public void setInputScale(Scale inputScale) {
        this.inputScale = Objects.requireNonNull(inputScale, "Входная шкала не может быть null.");
    }

    @Override
    public Scale getOutputScale() {
        return outputScale;
    }

    @Override
    public void setOutputScale(Scale outputScale) {
        this.outputScale = Objects.requireNonNull(outputScale, "Выходная шкала не может быть null.");
    }

    @Override
    public double getOutputTemperature() {
        return outputTemperature;
    }

    public void addScale(Scale scale) {
        temperatureScales.add(Objects.requireNonNull(scale, "Шкала не может быть null."));
    }

    @Override
    public void addConverterListener(ConverterListener listener) {
        listeners.add(Objects.requireNonNull(listener, "Listener не может быть null."));
    }

    private void notifyListeners() {
        for (ConverterListener listener : listeners) {
            listener.temperatureConverted();
        }
    }

    private void checkInputTemperatureScale() {
        if (inputScale == null) {
            throw new NullPointerException("Выберите исходную температурную шкалу.");
        }
    }

    private void checkOutputTemperatureScale() {
        if (outputScale == null) {
            throw new NullPointerException("Выберите температурную шкалу результата.");
        }
    }
}
