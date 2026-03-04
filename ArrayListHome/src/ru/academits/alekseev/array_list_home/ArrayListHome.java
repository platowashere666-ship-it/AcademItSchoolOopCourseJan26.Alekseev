package ru.academits.alekseev.array_list_home;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListHome {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ArrayListHome/input.txt"))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            System.out.println("Список строк из файла: " + lines);
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

        for (int i = numbers.size() - 1; i >= 0; i--) {
            if (numbers.get(i) % 2 == 0) {
                numbers.remove(i);
            }
        }

        System.out.println("Список нечётных чисел: " + numbers);

        ArrayList<Integer> numbers2 = new ArrayList<>(Arrays.asList(1, 5, 2, 1, 3, 5));
        ArrayList<Integer> uniqueNumbers = new ArrayList<>(numbers2.size());

        for (Integer number : numbers2) {
            if (!uniqueNumbers.contains(number)) {
                uniqueNumbers.add(number);
            }
        }

        System.out.println("Список уникальных чисел: " + uniqueNumbers);
    }
}
