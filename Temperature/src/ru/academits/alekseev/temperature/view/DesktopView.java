package ru.academits.alekseev.temperature.view;

import ru.academits.alekseev.temperature.controller.Controller;
import ru.academits.alekseev.temperature.model.Converter;

import javax.swing.*;
import java.awt.*;

public class DesktopView implements View {
    private final Converter converter;
    private Controller controller;
    private JLabel outputTemperatureLabel;

    public DesktopView(Converter converter) {
        if (converter == null) {
            throw new IllegalArgumentException("Converter не может быть null.");
        }

        this.converter = converter;
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Temperature Converter");

            frame.setSize(800, 600);
            frame.setMinimumSize(new Dimension(800, 600));
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel chooseInputTemperatureScaleLabel = new JLabel("Выберите температурную шкалу:");
            panel.add(chooseInputTemperatureScaleLabel);

            JRadioButton celsiusInputButton = new JRadioButton("Цельсий");

            celsiusInputButton.addActionListener(_ ->
                    controller.setInputTemperatureScale(celsiusInputButton.getText()));

            JRadioButton fahrenheitInputButton = new JRadioButton("Фаренгейт");
            fahrenheitInputButton.addActionListener(_ ->
                    controller.setInputTemperatureScale(fahrenheitInputButton.getText()));

            JRadioButton kelvinInputButton = new JRadioButton("Кельвин");
            kelvinInputButton.addActionListener(_ ->
                    controller.setInputTemperatureScale(kelvinInputButton.getText()));

            ButtonGroup inputTemperatureScaleButtonGroup = new ButtonGroup();
            inputTemperatureScaleButtonGroup.add(celsiusInputButton);
            inputTemperatureScaleButtonGroup.add(fahrenheitInputButton);
            inputTemperatureScaleButtonGroup.add(kelvinInputButton);

            panel.add(celsiusInputButton);
            panel.add(fahrenheitInputButton);
            panel.add(kelvinInputButton);

            JLabel enterTemperatureLabel = new JLabel("Введите температуру:");
            panel.add(enterTemperatureLabel);

            JTextField inputTemperatureField = new JTextField();
            inputTemperatureField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            panel.add(inputTemperatureField);

            JLabel chooseOutputTemperatureScaleLabel = new JLabel("Выберите температурную шкалу результата:");
            panel.add(chooseOutputTemperatureScaleLabel);

            JRadioButton celsiusOutputButton = new JRadioButton("Цельсий");
            celsiusOutputButton.addActionListener(_ ->
                    controller.setOutputTemperatureScale(celsiusOutputButton.getText()));

            JRadioButton fahrenheitOutputButton = new JRadioButton("Фаренгейт");
            fahrenheitOutputButton.addActionListener(_ ->
                    controller.setOutputTemperatureScale(fahrenheitOutputButton.getText()));

            JRadioButton kelvinOutputButton = new JRadioButton("Кельвин");
            kelvinOutputButton.addActionListener(_ ->
                    controller.setOutputTemperatureScale(kelvinOutputButton.getText()));

            ButtonGroup outputTemperatureScaleButtonGroup = new ButtonGroup();
            outputTemperatureScaleButtonGroup.add(celsiusOutputButton);
            outputTemperatureScaleButtonGroup.add(fahrenheitOutputButton);
            outputTemperatureScaleButtonGroup.add(kelvinOutputButton);

            panel.add(celsiusOutputButton);
            panel.add(fahrenheitOutputButton);
            panel.add(kelvinOutputButton);

            JButton convertButton = getConvertButton(inputTemperatureField, frame);
            panel.add(convertButton);

            outputTemperatureLabel = new JLabel();
            panel.add(outputTemperatureLabel);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    private JButton getConvertButton(JTextField inputTemperatureField, JFrame frame) {
        JButton convertButton = new JButton("Конвертировать");

        convertButton.addActionListener(_ -> {
            try {
                double inputTemperature = Double.parseDouble(inputTemperatureField.getText());
                controller.convertTemperature(inputTemperature, controller.getOutputTemperatureScale());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Температура должна быть числом.", "ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return convertButton;
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
            outputTemperatureLabel.setText("Температура в шкале Цельсия: " + outputTemperature);
        } else if (outputTemperatureScale.equals("Фаренгейт")) {
            outputTemperatureLabel.setText("Температура в шкале Фаренгейта: " + outputTemperature);
        } else {
            outputTemperatureLabel.setText("Температура в шкале Кельвина: " + outputTemperature);
        }
    }
}
