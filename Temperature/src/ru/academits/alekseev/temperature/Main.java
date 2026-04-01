package ru.academits.alekseev.temperature;

import ru.academits.alekseev.temperature.controller.Controller;
import ru.academits.alekseev.temperature.model.Converter;
import ru.academits.alekseev.temperature.model.TemperatureConverter;
import ru.academits.alekseev.temperature.view.DesktopView;
import ru.academits.alekseev.temperature.view.View;

public class Main {
    public static void main(String[] args) {
        Converter converter = new TemperatureConverter();
        View view = new DesktopView(converter);
        Controller controller = new Controller(converter, view);
        controller.start();
    }
}
