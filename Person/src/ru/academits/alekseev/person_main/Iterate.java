package ru.academits.alekseev.person_main;

import java.util.Scanner;
import java.util.stream.DoubleStream;

public class Iterate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Сколько элементов нужно вычислить?");
        int size = scanner.nextInt();

        DoubleStream sqrtStream = DoubleStream.iterate(0, x -> x + 1)
                .map(Math::sqrt);

        sqrtStream
                .limit(size)
                .forEach(System.out::println);
    }
}
