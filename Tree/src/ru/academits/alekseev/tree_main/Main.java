package ru.academits.alekseev.tree_main;

import ru.academits.alekseev.tree.BinarySearchTree;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree1 = new BinarySearchTree<>();

        binarySearchTree1.add(2);
        binarySearchTree1.add(6);
        binarySearchTree1.add(1);
        binarySearchTree1.add(10);
        binarySearchTree1.add(4);
        System.out.println("Создали дерево: " + binarySearchTree1);

        System.out.println("Размер дерева: " + binarySearchTree1.size());

        if (binarySearchTree1.remove(6)) {
            System.out.println("Удалили элемент: " + binarySearchTree1);
        }

        System.out.println("Дерево содержит элемент \"10\"? " + binarySearchTree1.contains(10));

        BinarySearchTree<Integer> binarySearchTree2 = new BinarySearchTree<>(Integer::compareTo);

        binarySearchTree2.add(1);
        binarySearchTree2.add(4);
        binarySearchTree2.add(10);

        System.out.println("Обходим второе дерево в глубину:");
        binarySearchTree2.traverseDepthFirst(x -> System.out.print(x + " "));
        System.out.println();

        System.out.println("Обходим первое дерево в глубину (рекурсивно):");
        binarySearchTree1.traverseDepthFirstRecursive(x -> System.out.print(x + " "));
    }
}
