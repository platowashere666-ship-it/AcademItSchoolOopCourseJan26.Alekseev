package ru.academits.alekseev.graph_main;

import ru.academits.alekseev.graph.Graph;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        graph.addEdge(3, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 3);

        System.out.println("Создали граф: " + graph);

        System.out.println("Обходим граф в ширину:");
        graph.traverseBreadthFirst(System.out::println);

        System.out.println("Обходим граф в глубину:");
        graph.traverseDepthFirst(System.out::println);

        System.out.println("Обходим граф в глубину (рекурсивно):");
        graph.traverseDepthFirstRecursive(System.out::println);
    }
}
