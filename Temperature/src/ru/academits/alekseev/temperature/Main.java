package ru.academits.alekseev.temperature;

import ru.academits.alekseev.temperature.controller.Controller;
import ru.academits.alekseev.temperature.model.Converter;
import ru.academits.alekseev.temperature.model.TemperatureConverter;
import ru.academits.alekseev.temperature.model.scales.Scale;
import ru.academits.alekseev.temperature.view.DesktopView;
import ru.academits.alekseev.temperature.view.View;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Scale> temperatureScales = new ArrayList<>();
        Converter converter = new TemperatureConverter(temperatureScales);
        View view = new DesktopView(converter);
        Controller controller = new Controller(converter, view);
        controller.start();
    }
}
