package ru.academits.alekseev.lesson_17;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите имя первого класса: ");
        String className1 = scanner.nextLine();

        System.out.println("Введите имя второго класса: ");
        String className2 = scanner.nextLine();

        Class<?> humanClass = Class.forName(className1);
        Class<?> dogClass = Class.forName(className2);

        Constructor<?> humanConstructor = humanClass.getConstructor(String.class, int.class);
        Constructor<?> dogConstructor = dogClass.getConstructor(String.class, int.class);

        Human human = (Human) humanConstructor.newInstance("Павел", 25);
        Dog dog = (Dog) dogConstructor.newInstance("Шарик", 4);

        System.out.println("Человек: " + human);
        System.out.println("Собака: " + dog);

        Field humanName = human.getClass().getDeclaredField("name");
        humanName.setAccessible(true);
        String value = (String) humanName.get(human);
        humanName.set(human, value + " Павельев");

        Field dogAge = dog.getClass().getDeclaredField("age");
        dogAge.setAccessible(true);
        int value2 = dogAge.getInt(dog);
        dogAge.set(dog, value2 + 1);

        System.out.println("Человек: " + human);
        System.out.println("Собака: " + dog);

        Method humanMethod = Human.class.getMethod("setAge", int.class);
        Method dogMethod = Dog.class.getMethod("setName", String.class);

        humanMethod.invoke(human, 34);
        dogMethod.invoke(dog, "Барбос");

        System.out.println("Человек: " + human);
        System.out.println("Собака: " + dog);
    }
}
