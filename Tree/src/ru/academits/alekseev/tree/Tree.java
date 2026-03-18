package ru.academits.alekseev.tree;

import java.util.*;
import java.util.function.Consumer;

public class Tree<E> {
    private TreeNode<E> root;
    private int size;
    private Comparator<E> comparator;

    public Tree() {
    }

    public Tree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    private int compare(E data1, E data2) {
        if (comparator != null) {
            return comparator.compare(data1, data2);
        }

        if (data1 == null && data2 == null) {
            return 0;
        }

        if (data1 == null || data2 == null) {
            return data1 == null ? -1 : 1;
        }

        if (!(data1 instanceof Comparable)) {
            throw new ClassCastException("Элементы должны реализовывать Comparable или иметь Comparator.");
        }

        //noinspection unchecked
        return ((Comparable<E>) data1).compareTo(data2);
    }

    public boolean add(E data) {
        TreeNode<E> newNode = new TreeNode<>(data);

        if (root == null) {
            root = newNode;
            ++size;

            return true;
        }

        TreeNode<E> currentNode = root;

        while (true) {
            int compareResult = compare(data, currentNode.getData());

            if (compareResult == 0) {
                return false;
            }

            if (compareResult < 0) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(newNode);
                    ++size;

                    return true;
                }

                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(newNode);
                    ++size;

                    return true;
                }

                currentNode = currentNode.getRight();
            }
        }
    }

    public boolean contains(E data) {
        if (root == null) {
            return false;
        }

        TreeNode<E> currentNode = root;

        while (currentNode != null) {
            int compareResult = compare(data, currentNode.getData());

            if (compareResult == 0) {
                return true;
            }

            if (compareResult < 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }

        return false;
    }

    public boolean remove(E data) {
        if (root == null) {
            return false;
        }

        TreeNode<E> rootParent = new TreeNode<>(null);
        rootParent.setLeft(root);

        TreeNode<E> currentNode = root;
        TreeNode<E> parentNode = rootParent;

        boolean isLeftChild = true;

        while (currentNode != null) {
            int compareResult = compare(data, currentNode.getData());

            if (compareResult == 0) {
                break;
            }

            parentNode = currentNode;

            if (compareResult < 0) {
                isLeftChild = true;
                currentNode = currentNode.getLeft();
            } else {
                isLeftChild = false;
                currentNode = currentNode.getRight();
            }
        }

        if (currentNode == null) {
            return false;
        }

        if (currentNode.getLeft() == null && currentNode.getRight() == null) {
            if (isLeftChild) {
                parentNode.setLeft(null);
            } else {
                parentNode.setRight(null);
            }
        } else if (currentNode.getLeft() == null || currentNode.getRight() == null) {
            TreeNode<E> child = (currentNode.getLeft() != null) ? currentNode.getLeft() : currentNode.getRight();

            if (isLeftChild) {
                parentNode.setLeft(child);
            } else {
                parentNode.setRight(child);
            }
        } else {
            TreeNode<E> minRightNode = currentNode.getRight();
            TreeNode<E> minRightParent = currentNode;

            while (minRightNode.getLeft() != null) {
                minRightParent = minRightNode;
                minRightNode = minRightNode.getLeft();
            }

            if (currentNode.getRight() == minRightNode) {
                minRightNode.setLeft(currentNode.getLeft());
            } else {
                minRightParent.setLeft(minRightNode.getRight());
                minRightNode.setLeft(currentNode.getLeft());
                minRightNode.setRight(currentNode.getRight());
            }

            if (isLeftChild) {
                parentNode.setLeft(minRightNode);
            } else {
                parentNode.setRight(minRightNode);
            }
        }

        root = rootParent.getLeft();
        --size;

        return true;
    }

    public int size() {
        return size;
    }

    public void traverseBreadthFirst(Consumer<E> consumer) {
        if (root == null || consumer == null) {
            return;
        }

        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<E> currentNode = queue.remove();
            consumer.accept(currentNode.getData());

            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }
    }

    public void traverseDepthFirst(Consumer<E> consumer) {
        if (root == null || consumer == null) {
            return;
        }

        Deque<TreeNode<E>> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<E> currentNode = stack.pop();
            consumer.accept(currentNode.getData());

            if (currentNode.getRight() != null) {
                stack.push(currentNode.getRight());
            }

            if (currentNode.getLeft() != null) {
                stack.push(currentNode.getLeft());
            }
        }
    }

    public void traverseDepthFirstRecursive(Consumer<E> consumer) {
        if (root == null || consumer == null) {
            return;
        }

        traverseDepthFirstRecursive(root, consumer);
    }

    private void traverseDepthFirstRecursive(TreeNode<E> currentNode, Consumer<E> consumer) {
        if (currentNode == null) {
            return;
        }

        traverseDepthFirstRecursive(currentNode.getLeft(), consumer);
        consumer.accept(currentNode.getData());
        traverseDepthFirstRecursive(currentNode.getRight(), consumer);
    }

    @Override
    public String toString() {
        if (root == null) {
            return "[]";
        }

        ArrayList<E> nodes = new ArrayList<>();
        traverseBreadthFirst(nodes::add);

        return nodes.toString();
    }
}