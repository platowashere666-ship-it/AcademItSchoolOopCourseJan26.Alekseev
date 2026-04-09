package ru.academits.alekseev.csv_to_html_converter_main;

import ru.academits.alekseev.csv_to_html_converter.CsvToHtmlConverter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("""
                    Передано неправильное кол-во аргументов.
                    Необходимо передавать 2 аргумента - путь к исходному CSV-файлу и путь к HTML-файлу.
                    Пример: java ru.academits.alekseev.csv_to_html_converter_main.Main input.csv output.html""");

            return;
        }

        try {
            CsvToHtmlConverter csvToHtmlConverter = new CsvToHtmlConverter();
            csvToHtmlConverter.convert(args[0], args[1]);
        } catch (FileNotFoundException e) {
            System.out.println("Входной файл не найден: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при конвертации: " + e.getMessage());
        }
    }
}
