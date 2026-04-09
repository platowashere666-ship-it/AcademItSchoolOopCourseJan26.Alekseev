package ru.academits.alekseev.temperature.view;

import ru.academits.alekseev.temperature.controller.Controller;
import ru.academits.alekseev.temperature.model.Converter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView implements View {
    private final Converter converter;
    private Controller controller;

    public ConsoleView(Converter converter) {
        if (converter == null) {
            throw new IllegalArgumentException("Converter не может быть null.");
        }

        this.converter = converter;
    }

    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);

        for (; ; ) {
            try {
                System.out.println("Выберите температурную шкалу (1 - Цельсий, 2 - Фаренгейт, 3 - Кельвин):");
                int inputTemperatureIndex = scanner.nextInt();

                if (inputTemperatureIndex == 1) {
                    controller.setInputTemperatureScale("Цельсий");
                    System.out.println("Введите температуру в градусах Цельсия:");
                } else if (inputTemperatureIndex == 2) {
                    controller.setInputTemperatureScale("Фаренгейт");
                    System.out.println("Введите температуру в градусах Фаренгейта:");
                } else if (inputTemperatureIndex == 3) {
                    controller.setInputTemperatureScale("Кельвин");
                    System.out.println("Введите температуру в градусах Кельвина:");
                } else {
                    throw new InputMismatchException();
                }

                double inputTemperature = scanner.nextDouble();

                System.out.println("Выберите температурную шкалу результата (1 - Цельсий, 2 - Фаренгейт, 3 - Кельвин):");
                int outputTemperatureIndex = scanner.nextInt();

                if (outputTemperatureIndex == 1) {
                    controller.setOutputTemperatureScale("Цельсий");
                } else if (outputTemperatureIndex == 2) {
                    controller.setOutputTemperatureScale("Фаренгейт");
                } else if (outputTemperatureIndex == 3) {
                    controller.setOutputTemperatureScale("Кельвин");
                } else {
                    throw new InputMismatchException();
                }

                controller.convertTemperature(inputTemperature, controller.getOutputTemperatureScale());
            } catch (InputMismatchException e) {
                System.out.println("Разрешено вводить только числа (1, 2 и 3 - для выбора шкалы).");
                scanner.next();
            }
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void temperatureConverted() {
        double outputTemperature = converter.getOutputTemperature();
        String outputTemperatureScale = controller.getOutputTemperatureScale();

        if (outputTemperatureScale.equals("Цельсий")) {
            System.out.println("Температура в шкале Цельсия: " + outputTemperature);
        } else if (outputTemperatureScale.equals("Фаренгейт")) {
            System.out.println("Температура в шкале Фаренгейта: " + outputTemperature);
        } else {
            System.out.println("Температура в шкале Кельвина: " + outputTemperature);
        }
    }
}
