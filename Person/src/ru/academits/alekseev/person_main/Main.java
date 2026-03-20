package ru.academits.alekseev.person_main;

import ru.academits.alekseev.person.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Павел", 28));
        persons.add(new Person("Павел", 19));
        persons.add(new Person("Илья", 33));
        persons.add(new Person("Евгений", 21));
        persons.add(new Person("Наталья", 17));
        persons.add(new Person("Александра", 14));
        System.out.println("Список людей: " + persons);

        List<String> distinctNamesList = persons.stream()
                .map(Person::getName)
                .distinct()
                .toList();

        String distinctNamesString = distinctNamesList.stream()
                .collect(Collectors.joining(", ", "Имена: ", "."));
        System.out.println(distinctNamesString);

        List<Person> minors = persons.stream()
                .filter(p -> p.getAge() < 18)
                .toList();

        double minorsAverageAge = minors.stream()
                .mapToDouble(Person::getAge)
                .average()
                .orElse(0);

        System.out.println("Список людей младше 18: " + minors);
        System.out.println("Их средний возраст: " + minorsAverageAge);

        Map<String, Double> personsByAverageAge = persons.stream()
                .collect(Collectors.groupingBy(
                        Person::getName,
                        Collectors.averagingDouble(Person::getAge)
                ));

        System.out.println("Группировка людей по имени и среднему возрасту: " + personsByAverageAge);

        System.out.println("Люди возрастом от 20 до 45:");
        persons.stream()
                .filter(p -> p.getAge() >= 20 && p.getAge() <= 45)
                .sorted(Comparator.comparingInt(Person::getAge).reversed())
                .map(Person::getName)
                .forEach(System.out::println);
    }
}
