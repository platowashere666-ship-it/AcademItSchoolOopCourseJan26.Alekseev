package ru.academits.alekseev.lambdas.person;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Person> persons = List.of(
                new Person("Павел", 28),
                new Person("Павел", 19),
                new Person("Илья", 33),
                new Person("Евгений", 21),
                new Person("Наталья", 17),
                new Person("Александра", 14)
        );

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

        if (minors.isEmpty()) {
            System.out.println("Людей младше 18 нет.");
        } else {
            System.out.println("Список людей младше 18: " + minors);

            double minorsAverageAge = minors.stream()
                    .collect(Collectors.averagingInt(Person::getAge));

            System.out.println("Их средний возраст: " + minorsAverageAge);
        }

        Map<String, Double> averageAgesByNames = persons.stream()
                .collect(Collectors.groupingBy(
                        Person::getName,
                        Collectors.averagingInt(Person::getAge)
                ));

        System.out.println("Группировка людей по имени и среднему возрасту: " + averageAgesByNames);

        System.out.println("Люди возрастом от 20 до 45:");
        persons.stream()
                .filter(p -> p.getAge() >= 20 && p.getAge() <= 45)
                .sorted(Comparator.comparingInt(Person::getAge).reversed())
                .map(Person::getName)
                .forEach(System.out::println);
    }
}
