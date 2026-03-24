package ru.academits.alekseev.tree_main;

import ru.academits.alekseev.tree.BinaryTree;

public class Main {
    public static void main(String[] args) {
        BinaryTree<Integer> binaryTree1 = new BinaryTree<>();

        binaryTree1.add(2);
        binaryTree1.add(6);
        binaryTree1.add(1);
        binaryTree1.add(10);
        binaryTree1.add(4);
        System.out.println("Создали дерево: " + binaryTree1);

        System.out.println("Размер дерева: " + binaryTree1.size());

        if (binaryTree1.remove(6)) {
            System.out.println("Удалили элемент: " + binaryTree1);
        }

        System.out.println("Дерево содержит элемент \"10\" ?" + binaryTree1.contains(10));

        BinaryTree<Integer> binaryTree2 = new BinaryTree<>(Integer::compareTo);

        binaryTree2.add(1);
        binaryTree2.add(4);
        binaryTree2.add(10);

        System.out.println("Обходим второе дерево в глубину: ");
        binaryTree2.traverseDepthFirst(x -> System.out.print(x + " "));
        System.out.println();

        System.out.println("Обходим первое дерево в глубину (рекурсивно): ");
        binaryTree1.traverseDepthFirstRecursive(x -> System.out.print(x + " "));
    }
}
