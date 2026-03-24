package ru.academits.alekseev.tree;

import java.util.*;
import java.util.function.Consumer;

public class BinaryTree<E> {
    private TreeNode<E> root;
    private int size;
    private Comparator<? super E> comparator;

    public BinaryTree() {
    }

    public BinaryTree(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    private int compare(E data1, E data2) {
        if (comparator != null) {
            return comparator.compare(data1, data2);
        }

        if (data1 == null && data2 == null) {
            return 0;
        }

        if (data1 == null) {
            return -1;
        }

        if (data2 == null) {
            return 1;
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
            int comparisonResult = compare(data, currentNode.getData());

            if (comparisonResult < 0) {
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
            int comparisonResult = compare(data, currentNode.getData());

            if (comparisonResult == 0) {
                return true;
            }

            if (comparisonResult < 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }

        return false;
    }

    private void replaceChild(TreeNode<E> parentNode, TreeNode<E> replacementNode, boolean isLeftChild) {
        if (parentNode == null) {
            root = replacementNode;
        } else if (isLeftChild) {
            parentNode.setLeft(replacementNode);
        } else {
            parentNode.setRight(replacementNode);
        }
    }

    public boolean remove(E data) {
        if (root == null) {
            return false;
        }

        TreeNode<E> currentNode = root;
        TreeNode<E> parentNode = null;

        boolean isLeftChild = false;

        while (currentNode != null) {
            int comparisonResult = compare(data, currentNode.getData());

            if (comparisonResult == 0) {
                break;
            }

            parentNode = currentNode;

            if (comparisonResult < 0) {
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

        if (currentNode.getLeft() == null || currentNode.getRight() == null) {
            TreeNode<E> child = (currentNode.getLeft() != null) ? currentNode.getLeft() : currentNode.getRight();
            replaceChild(parentNode, child, isLeftChild);
        } else {
            TreeNode<E> minLeftNode = currentNode.getRight();
            TreeNode<E> minLeftNodeParent = currentNode;

            while (minLeftNode.getLeft() != null) {
                minLeftNodeParent = minLeftNode;
                minLeftNode = minLeftNode.getLeft();
            }

            if (minLeftNodeParent != currentNode) {
                minLeftNodeParent.setLeft(minLeftNode.getRight());
                minLeftNode.setRight(currentNode.getRight());
            }

            minLeftNodeParent.setLeft(currentNode.getLeft());
            replaceChild(parentNode, minLeftNode, isLeftChild);
        }

        --size;

        return true;
    }

    public int size() {
        return size;
    }

    public void traverseBreadthFirst(Consumer<E> consumer) {
        if (consumer == null) {
            throw new NullPointerException("Consumer не может быть null.");
        }

        if (root == null) {
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
        if (consumer == null) {
            throw new NullPointerException("Consumer не может быть null.");
        }

        if (root == null) {
            return;
        }

        Deque<TreeNode<E>> stack = new LinkedList<>();
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
        if (consumer == null) {
            throw new NullPointerException("Consumer не может быть null.");
        }

        if (root == null) {
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

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        traverseBreadthFirst(x -> {
            if (sb.length() > 1) {
                sb.append(", ");
            }

            sb.append(x);
        });

        sb.append(']');

        return sb.toString();
    }
}