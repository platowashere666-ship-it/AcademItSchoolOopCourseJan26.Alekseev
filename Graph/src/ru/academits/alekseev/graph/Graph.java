package ru.academits.alekseev.graph;

import java.util.*;
import java.util.function.Consumer;

public class Graph {
    private final int size;
    private final int[][] adjacencyMatrix;

    public Graph(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Размер графа должен быть >= 0. Размер: " + size);
        }

        this.size = size;
        adjacencyMatrix = new int[size][size];
    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }

    public void addEdge(int from, int to, int weight) {
        if (from < 0 || from >= size) {
            throw new IllegalArgumentException("Первая вершина должна быть >= 0 и < " + size + ". Первая вершина: " + from);
        }

        if (to < 0 || to >= size) {
            throw new IllegalArgumentException("Вторая вершина должна быть >= 0 и < " + size + ". Вторая вершина: " + to);
        }

        adjacencyMatrix[from][to] = weight;
    }

    public void traverseBreadthFirst(Consumer<Integer> consumer) {
        Objects.requireNonNull(consumer, "Consumer не может быть null.");

        if (size == 0) {
            return;
        }

        boolean[] visited = new boolean[size];
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < size; ++i) {
            if (visited[i]) {
                continue;
            }

            queue.add(i);
            visited[i] = true;

            while (!queue.isEmpty()) {
                int currentVertex = queue.remove();
                consumer.accept(currentVertex);

                for (int neighbor = 0; neighbor < size; ++neighbor) {
                    if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                        queue.add(neighbor);
                        visited[neighbor] = true;
                    }
                }
            }
        }
    }

    public void traverseDepthFirst(Consumer<Integer> consumer) {
        Objects.requireNonNull(consumer, "Consumer не может быть null.");

        if (size == 0) {
            return;
        }

        boolean[] visited = new boolean[size];
        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < size; ++i) {
            if (visited[i]) {
                continue;
            }

            stack.push(i);
            visited[i] = true;

            while (!stack.isEmpty()) {
                int currentVertex = stack.pop();
                consumer.accept(currentVertex);

                for (int neighbor = size - 1; neighbor >= 0; --neighbor) {
                    if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                        stack.push(neighbor);
                        visited[neighbor] = true;
                    }
                }
            }
        }
    }

    public void traverseDepthFirstRecursive(Consumer<Integer> consumer) {
        Objects.requireNonNull(consumer, "Consumer не может быть null.");

        if (size == 0) {
            return;
        }

        boolean[] visited = new boolean[size];

        for (int i = 0; i < size; ++i) {
            if (!visited[i]) {
                traverseDepthFirstRecursive(consumer, i, visited);
            }
        }
    }

    private void traverseDepthFirstRecursive(Consumer<Integer> consumer, int currentVertex, boolean[] visited) {
        visited[currentVertex] = true;
        consumer.accept(currentVertex);

        for (int neighbor = 0; neighbor < size; ++neighbor) {
            if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                traverseDepthFirstRecursive(consumer, neighbor, visited);
            }
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        return Arrays.deepToString(adjacencyMatrix);
    }
}
