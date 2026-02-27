package ru.academits.alekseev.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class csv {
    public static void main(String[] args) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter("output.txt");
             Scanner scanner = new Scanner("csv/input.txt")) {
            writer.println("<table>");
            writer.println("<tr>");

        }
    }
}
