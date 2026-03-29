package ru.academits.alekseev.lesson_16;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try (BufferedInputStream input = new BufferedInputStream(
                new FileInputStream("Tasks/src/ru/academits/alekseev/lesson_16/input.txt"));
             BufferedOutputStream output = new BufferedOutputStream(
                     new FileOutputStream("Tasks/src/ru/academits/alekseev/lesson_16/output.txt"));
             PrintWriter writer = new PrintWriter("Tasks/src/ru/academits/alekseev/lesson_16/output2.txt")) {
            int read;
            int offset = 0;
            byte[] res = new byte[1000000];

            while ((read = input.read(res, offset, res.length - offset)) != -1) {
                offset += read;
            }

            output.write(res);

            writer.println("Начала подсчёта");
            writer.print("Подсчёт: ");

            for (int i = 1; i <= 100; ++i) {
                writer.printf("Строка %s%n", i);
            }
        }
    }
}
