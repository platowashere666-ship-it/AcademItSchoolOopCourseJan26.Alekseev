package ru.academits.alekseev.tree_main;

import ru.academits.alekseev.tree.Tree;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();

        tree.add(2);
        tree.add(4);
        tree.add(7);
        tree.add(10);
        System.out.println("Создали бинарное дерево: " + tree.bypassInWidth());

        if (tree.remove(4)) {
            System.out.println("Удалили элемент: " + tree.bypassInDepth());
        }

        if (tree.contains(10)) {
            System.out.println("Дерево содержит число \"10\".");
        }
    }
}
