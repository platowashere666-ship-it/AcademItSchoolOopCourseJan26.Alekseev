package ru.academits.alekseev.temperature.model;

import ru.academits.alekseev.temperature.model.scales.CelsiusScale;
import ru.academits.alekseev.temperature.model.scales.FahrenheitScale;
import ru.academits.alekseev.temperature.model.scales.KelvinScale;
import ru.academits.alekseev.temperature.model.scales.Scale;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TemperatureConverter implements Converter {
    private final List<ConverterListener> listeners = new ArrayList<>();
    private final List<Scale> temperatureScales;

    private Scale inputScale;
    private Scale outputScale;

    private double outputTemperature;

    public TemperatureConverter(List<Scale> temperatureScales) {
        this.temperatureScales = Objects.requireNonNull(List.copyOf(temperatureScales),
                "Список шкал не может быть null.");
    }

    @Override
    public void convert(double inputTemperature) {
        double celsiusTemperature = inputScale.convertToCelsiusScale(inputTemperature);
        outputTemperature = outputScale.convertToOutputScale(celsiusTemperature);

        notifyListeners();
    }

    @Override
    public List<Scale> getAvailableScales() {
        if (temperatureScales.isEmpty()) {
            return List.of(new CelsiusScale(), new FahrenheitScale(), new KelvinScale());
        }

        return List.copyOf(temperatureScales);
    }

    @Override
    public void setInputScale(Scale inputScale) {
        this.inputScale = Objects.requireNonNull(inputScale, "Входная шкала не может быть null.");
    }

    @Override
    public void setOutputScale(Scale outputScale) {
        this.outputScale = Objects.requireNonNull(outputScale, "Выходная шкала не может быть null.");
    }

    @Override
    public double getOutputTemperature() {
        return outputTemperature;
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
}
