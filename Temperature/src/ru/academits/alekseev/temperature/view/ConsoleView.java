package ru.academits.alekseev.temperature.view;

import ru.academits.alekseev.temperature.controller.Controller;
import ru.academits.alekseev.temperature.model.Converter;
import ru.academits.alekseev.temperature.model.scales.Scale;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleView implements View {
    private final Converter converter;
    private Controller controller;

    public ConsoleView(Converter converter) {
        this.converter = Objects.requireNonNull(converter, "Converter не может быть null");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = Objects.requireNonNull(controller, "Controller не может быть null");
    }

    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                Scale inputScale = chooseScale(scanner, "Выберите входную температурную шкалу:");
                controller.setInputScale(inputScale);

                System.out.printf("Введите температуру в шкале %s: ", inputScale.getName());
                double inputTemperature = scanner.nextDouble();

                Scale outputScale = chooseScale(scanner, "Выберите шкалу результата:");
                controller.setOutputScale(outputScale);

                controller.convert(inputTemperature);
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите число.");
                break;
            }
        }
    }

    private Scale chooseScale(Scanner scanner, String prompt) {
        List<Scale> scales = controller.getAvailableScales();

        System.out.println(prompt);

        for (int i = 0; i < scales.size(); i++) {
            System.out.printf("%d - %s%n", i + 1, scales.get(i).getName());
        }

        System.out.print("Ваш выбор: ");
        int choice = scanner.nextInt();

        if (choice < 1 || choice > scales.size()) {
            throw new IllegalArgumentException("Неверный номер шкалы. Выберите число от 1 до " + scales.size());
        }

        return scales.get(choice - 1);
    }

    @Override
    public void temperatureConverted() {
        double outputTemperature = converter.getOutputTemperature();

        System.out.println("Результат: " + outputTemperature);
    }
}