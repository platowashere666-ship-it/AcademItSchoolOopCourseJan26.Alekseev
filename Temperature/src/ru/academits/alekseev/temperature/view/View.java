package ru.academits.alekseev.temperature.view;

import ru.academits.alekseev.temperature.controller.Controller;
import ru.academits.alekseev.temperature.model.ConverterListener;

public interface View extends ConverterListener {
    void start();

    void setController(Controller controller);
}
