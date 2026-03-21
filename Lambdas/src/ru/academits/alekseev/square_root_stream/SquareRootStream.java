package ru.academits.alekseev.square_root_stream;

import java.util.Scanner;
import java.util.stream.DoubleStream;

public class SquareRootStream {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Программа выводит последовательность квадратных корней натуральных чисел.");
        System.out.println("Сколько элементов последовательности вывести?");
        int count = scanner.nextInt();

        DoubleStream sqrtStream = DoubleStream.iterate(0, x -> x + 1)
                .map(Math::sqrt);

        sqrtStream
                .limit(count)
                .forEach(System.out::println);
    }
}
