package ru.academits.alekseev.lesson_23;

public class Main {
    public static void main(String[] args) {
        ProducerConsumerManager producerConsumerManager = new ProducerConsumerManager(4, 3, 10);
        producerConsumerManager.start();
    }
}
