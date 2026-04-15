package ru.academits.alekseev.lesson_22.task_2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 2; ++i) {
            Thread thread = new Thread(() -> {
                for (int j = 1; j <= 100; ++j) {
                    synchronized (numbers) {
                        numbers.add(j);
                    }
                }
            });

            threads.add(thread);
        }

        threads.forEach(Thread::start);

        System.out.println("Старт");

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException _) {
        }

        System.out.println("Размер списка: " + numbers.size());
        System.out.println(numbers);
    }
}
