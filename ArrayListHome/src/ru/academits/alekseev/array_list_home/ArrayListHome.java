package ru.academits.alekseev.array_list_home;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayListHome {
    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File("ArrayListHome/input.txt"))) {
            ArrayList<String> lines = new ArrayList<>();

            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            System.out.println(lines);
        }

        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

        for (int i = 0; i < numbers.size(); ++i) {
            if (numbers.get(i) % 2 == 0) {
                //noinspection SuspiciousListRemoveInLoop
                numbers.remove(i);
            }
        }

        System.out.println(numbers);

        ArrayList<Integer> numbersToCopy = new ArrayList<>(Arrays.asList(1, 5, 2, 1, 3, 5));
        ArrayList<Integer> copiedNumbers = new ArrayList<>();

        for (Integer numberToCopy : numbersToCopy) {
            if (!copiedNumbers.contains(numberToCopy)) {
                copiedNumbers.add(numberToCopy);
            }
        }

        System.out.println(copiedNumbers);
    }
}
