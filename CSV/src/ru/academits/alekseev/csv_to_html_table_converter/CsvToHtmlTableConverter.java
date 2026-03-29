package ru.academits.alekseev.csv_to_html_table_converter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CsvToHtmlTableConverter {
    public static void main(String[] args) throws IOException {
        StringBuilder table = new StringBuilder();
        table.append("<table>").append(System.lineSeparator());

        try (BufferedReader reader = new BufferedReader(
                new FileReader("CSV/src/ru/academits/alekseev/csv_to_html_table_converter/input.csv"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                table.append("  <tr>");

            }
        }
    }
}
