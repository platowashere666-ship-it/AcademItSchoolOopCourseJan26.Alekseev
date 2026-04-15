package ru.academits.alekseev.lesson_22.task_1;

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new PrintTask());

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Исполнение продолжено");
    }
}
