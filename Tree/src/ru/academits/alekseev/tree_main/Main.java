package ru.academits.alekseev.tree_main;

import ru.academits.alekseev.tree.Tree;


public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree1 = new Tree<>();

        tree1.add(2);
        tree1.add(6);
        tree1.add(1);
        tree1.add(10);
        tree1.add(4);
        System.out.println("Создали дерево: " + tree1);

        System.out.println("Размер дерева: " + tree1.size());

        if (tree1.remove(6)) {
            System.out.println("Удалили элемент: " + tree1);
        }

        System.out.println("Дерево содержит элемент \"10\" ? " + tree1.contains(10));

        Tree<Integer> tree2 = new Tree<>((o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            } else if (o1 > o2) {
                return 1;
            } else {
                return -1;
            }
        });

        tree2.add(1);
        tree2.add(4);
        tree2.add(10);
        System.out.println("Обходим второе дерево в глубину: ");
        tree2.traverseDepthFirst(x -> System.out.print(x + " "));
        System.out.println();

        System.out.println("Обходим первое дерево в глубину (рекурсивно): ");
        tree1.traverseDepthFirstRecursive(x -> System.out.print(x + " "));
    }
}
