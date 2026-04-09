package ru.academits.alekseev.temperature.view;

import ru.academits.alekseev.temperature.controller.Controller;
import ru.academits.alekseev.temperature.model.Converter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

public class DesktopView implements View {
    private final Converter converter;
    private Controller controller;
    private JLabel outputTemperatureLabel;

    public DesktopView(Converter converter) {
        if (converter == null) {
            throw new NullPointerException("Converter не может быть null.");
        }

        this.converter = converter;
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            controller.setInputTemperatureScale("Цельсий");
            controller.setOutputTemperatureScale("Цельсий");

            JFrame frame = new JFrame("Температурный конвертер");

            frame.setSize(800, 600);
            frame.setMinimumSize(new Dimension(800, 600));
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.LIGHT_GRAY);

            Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
            Border labelBorder = BorderFactory.createEmptyBorder(10, 40, 10, 40);

            JLabel chooseInputTemperatureScaleLabel = new JLabel("Выберите температурную шкалу:");
            chooseInputTemperatureScaleLabel.setFont(labelFont);
            chooseInputTemperatureScaleLabel.setBorder(labelBorder);
            panel.add(chooseInputTemperatureScaleLabel);

            Font buttonFont = new Font(Font.SERIF, Font.BOLD, 14);
            Border buttonBorder = BorderFactory.createEmptyBorder(10, 40, 10, 40);

            createScaleButtons(panel, true, buttonFont, buttonBorder);

            JLabel enterTemperatureLabel = new JLabel("Введите температуру:");
            enterTemperatureLabel.setFont(labelFont);
            enterTemperatureLabel.setBorder(labelBorder);
            panel.add(enterTemperatureLabel);

            JPanel inputTemperatureFieldWrapper = new JPanel(new BorderLayout());
            inputTemperatureFieldWrapper.setOpaque(false);
            inputTemperatureFieldWrapper.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

            JTextField inputTemperatureField = new JTextField(20);
            inputTemperatureField.setFont(labelFont);
            inputTemperatureField.setCaretColor(Color.WHITE);
            inputTemperatureField.setForeground(Color.WHITE);
            inputTemperatureField.setBackground(new Color(60, 60, 60));
            inputTemperatureField.setMargin(new Insets(12, 15, 12, 15));
            inputTemperatureField.setMaximumSize(new Dimension(600, 30));

            inputTemperatureFieldWrapper.add(inputTemperatureField, BorderLayout.CENTER);
            panel.add(inputTemperatureFieldWrapper);

            JLabel chooseOutputTemperatureScaleLabel = new JLabel("Выберите температурную шкалу результата:");
            chooseOutputTemperatureScaleLabel.setFont(labelFont);
            chooseOutputTemperatureScaleLabel.setBorder(labelBorder);
            panel.add(chooseOutputTemperatureScaleLabel);

            createScaleButtons(panel, false, buttonFont, buttonBorder);

            JButton convertButton = getConvertButton(inputTemperatureField, frame, buttonFont);
            panel.add(convertButton);

            outputTemperatureLabel = new JLabel();
            outputTemperatureLabel.setFont(labelFont);
            outputTemperatureLabel.setBorder(labelBorder);
            panel.add(outputTemperatureLabel);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    private void createScaleButtons(JPanel panel, boolean isInput, Font buttonFont, Border buttonBorder) {
        List<String> scales = controller.getAvailableTemperatureScales();
        ButtonGroup scaleButtonGroup = new ButtonGroup();

        String currentScale = isInput ? controller.getInputTemperatureScale() : controller.getOutputTemperatureScale();

        for (String scale : scales) {
            JRadioButton scaleButton = new JRadioButton(scale);
            scaleButton.setFont(buttonFont);
            scaleButton.setBorder(buttonBorder);
            scaleButton.setOpaque(false);
            scaleButton.setFocusPainted(false);


            if (scale.equals(currentScale)) {
                scaleButton.setSelected(true);
            }

            scaleButton.addActionListener(_ -> {
                if (isInput) {
                    controller.setInputTemperatureScale(scale);
                } else {
                    controller.setOutputTemperatureScale(scale);
                }
            });

            scaleButtonGroup.add(scaleButton);
            panel.add(scaleButton);
        }
    }

    private JButton getConvertButton(JTextField inputTemperatureField, JFrame frame, Font buttonFont) {
        JButton convertButton = new JButton("Конвертировать");

        convertButton.setFont(buttonFont);
        convertButton.setForeground(Color.WHITE);
        convertButton.setBackground(new Color(0, 122, 204));
        convertButton.setFocusPainted(false);
        convertButton.setBorderPainted(false);
        convertButton.setContentAreaFilled(true);
        convertButton.setMargin(new Insets(12, 40, 12, 40));

        convertButton.addActionListener(_ -> {
            try {
                double inputTemperature = Double.parseDouble(inputTemperatureField.getText());
                controller.convertTemperature(inputTemperature, controller.getOutputTemperatureScale());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Температура должна быть числом.", "Ошибка",
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

        outputTemperatureLabel.setText("Температура в шкале " + outputTemperatureScale + ": " + outputTemperature);
    }
}