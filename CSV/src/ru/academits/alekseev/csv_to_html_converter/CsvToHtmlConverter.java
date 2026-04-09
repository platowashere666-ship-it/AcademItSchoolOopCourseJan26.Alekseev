package ru.academits.alekseev.csv_to_html_converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvToHtmlConverter {
    public void convert(String csvFilePath, String htmlFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
             PrintWriter writer = new PrintWriter(htmlFilePath)) {
            writer.println("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Таблица</title>
                        <meta charset="UTF-8">
                    </head>
                    <body>
                    <table border="1">""");

            boolean isInQuotes = false;

            String line;

            while ((line = reader.readLine()) != null) {
                if (!isInQuotes) {
                    writer.println("    <tr>");
                    writer.print("        <td>");
                }

                for (int i = 0; i < line.length(); ++i) {
                    char c = line.charAt(i);

                    if (c == '>') {
                        writer.print("&gt;");
                    } else if (c == '<') {
                        writer.print("&lt;");
                    } else if (c == '&') {
                        writer.print("&amp;");
                    } else if (c == ',') {
                        if (isInQuotes) {
                            writer.print(c);
                        } else {
                            writer.print("""
                                    </td>
                                            <td>""");
                        }
                    } else if (c == '"') {
                        if (!isInQuotes) {
                            isInQuotes = true;
                        } else if (i < line.length() - 1 && line.charAt(i + 1) == '"') {
                            writer.print("\"");
                            ++i;
                        } else {
                            isInQuotes = false;
                        }
                    } else {
                        writer.print(c);
                    }
                }

                if (!isInQuotes) {
                    writer.println("""
                            </td>
                                </tr>""");
                } else {
                    writer.print("<br>");
                }
            }

            writer.println("""
                    </table>
                    </body>
                    </html>""");
        }
    }
}
