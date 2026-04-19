package ru.academits.alekseev.temperature.view;

import ru.academits.alekseev.temperature.controller.Controller;
import ru.academits.alekseev.temperature.model.Converter;
import ru.academits.alekseev.temperature.model.scales.Scale;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class DesktopView implements View {
    private final Converter converter;
    private Controller controller;
    private JLabel outputTemperatureLabel;

    private boolean isStarted;

    public DesktopView(Converter converter) {
        this.converter = Objects.requireNonNull(converter, "Converter не может быть null.");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = Objects.requireNonNull(controller, "Controller не может быть null");
    }

    @Override
    public void start() {
        if (isStarted) {
            throw new IllegalStateException("Ошибка. Метод уже был вызван.");
        }

        if (controller == null) {
            throw new NullPointerException("Controller не может быть null.");
        }

        isStarted = true;

        SwingUtilities.invokeLater(() -> {
            List<Scale> availableScales = controller.getAvailableScales();

            controller.setInputScale(availableScales.getFirst());
            controller.setOutputScale(availableScales.getFirst());

            JFrame frame = new JFrame("Температурный конвертер");

            frame.setSize(800, 600);
            frame.setMinimumSize(new Dimension(800, 600));
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.LIGHT_GRAY);
            panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

            Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
            Border labelBorder = BorderFactory.createEmptyBorder(10, 0, 10, 0);

            JLabel chooseInputTemperatureScaleLabel = new JLabel("Выберите температурную шкалу:");
            chooseInputTemperatureScaleLabel.setFont(labelFont);
            chooseInputTemperatureScaleLabel.setBorder(labelBorder);
            panel.add(chooseInputTemperatureScaleLabel);

            Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
            Border buttonBorder = BorderFactory.createEmptyBorder(10, 0, 10, 0);

            createScaleButtons(panel, true, buttonFont, buttonBorder);

            JLabel enterTemperatureLabel = new JLabel("Введите температуру:");
            enterTemperatureLabel.setFont(labelFont);
            enterTemperatureLabel.setBorder(labelBorder);
            panel.add(enterTemperatureLabel);

            JTextField inputTemperatureField = createInputTemperatureTextField(labelFont);
            inputTemperatureField.setMaximumSize(new Dimension(400, 36));
            panel.add(inputTemperatureField);

            JLabel chooseOutputTemperatureScaleLabel = new JLabel("Выберите температурную шкалу результата:");
            chooseOutputTemperatureScaleLabel.setFont(labelFont);
            chooseOutputTemperatureScaleLabel.setBorder(labelBorder);
            panel.add(chooseOutputTemperatureScaleLabel);

            createScaleButtons(panel, false, buttonFont, buttonBorder);

            JButton convertButton = createConvertButton(inputTemperatureField, frame, buttonFont);
            panel.add(convertButton);

            outputTemperatureLabel = new JLabel();
            outputTemperatureLabel.setFont(labelFont);
            outputTemperatureLabel.setBorder(labelBorder);
            panel.add(outputTemperatureLabel);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    private static JTextField createInputTemperatureTextField(Font labelFont) {
        JTextField inputTemperatureField = new JTextField(20);

        inputTemperatureField.setFont(labelFont);
        inputTemperatureField.setCaretColor(Color.WHITE);
        inputTemperatureField.setForeground(Color.WHITE);
        inputTemperatureField.setBackground(new Color(60, 60, 60));
        inputTemperatureField.setAlignmentX(Component.LEFT_ALIGNMENT);

        Border outerBorder = BorderFactory.createLineBorder(new Color(100, 100, 100), 1);
        Border innerBorder = BorderFactory.createEmptyBorder(8, 16, 8, 12);

        inputTemperatureField.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        return inputTemperatureField;
    }

    private void createScaleButtons(JPanel panel, boolean isInput, Font buttonFont, Border buttonBorder) {
        List<Scale> scales = controller.getAvailableScales();
        ButtonGroup scaleButtonGroup = new ButtonGroup();

        for (Scale scale : scales) {
            JRadioButton scaleButton = new JRadioButton(scale.getName());
            scaleButton.setFont(buttonFont);
            scaleButton.setBorder(buttonBorder);
            scaleButton.setOpaque(false);
            scaleButton.setFocusPainted(false);

            if (scale.equals(scales.getFirst())) {
                scaleButton.setSelected(true);
            }

            scaleButton.addActionListener(_ -> {
                if (isInput) {
                    controller.setInputScale(scale);
                } else {
                    controller.setOutputScale(scale);
                }
            });

            scaleButtonGroup.add(scaleButton);
            panel.add(scaleButton);
        }
    }

    private JButton createConvertButton(JTextField inputTemperatureField, JFrame frame, Font buttonFont) {
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
                double inputTemperature = Double.parseDouble(inputTemperatureField.getText().trim());
                controller.convert(inputTemperature);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Температура должна быть числом.", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return convertButton;
    }

    @Override
    public void temperatureConverted() {
        double outputTemperature = converter.getOutputTemperature();
        outputTemperatureLabel.setText("Результат" + ": " + outputTemperature);
    }
}