package ru.academits.alekseev.tree;

import java.util.*;

public class Tree<E extends Comparable<E>> {
    private TreeNode<E> root;
    private int size;

    public Tree() {
    }

    public boolean add(E data) {
        if (root == null) {
            root = new TreeNode<>(data);
            ++size;

            return true;
        }

        TreeNode<E> currentNode = root;

        while (true) {
            int dataCompareResult = data.compareTo(currentNode.getData());

            if (dataCompareResult == 0) {
                return false;
            }

            if (dataCompareResult < 0) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(new TreeNode<>(data));
                    ++size;

                    return true;
                }

                currentNode = currentNode.getLeft();

                continue;
            }

            if (currentNode.getRight() == null) {
                currentNode.setRight(new TreeNode<>(data));
                ++size;

                return true;
            }

            currentNode = currentNode.getRight();
        }
    }

    public boolean contains(E data) {
        if (root == null) {
            return false;
        }

        TreeNode<E> currentNode = root;

        while (true) {
            int dataCompareResult = data.compareTo(currentNode.getData());

            if (dataCompareResult == 0) {
                return true;
            }

            if (dataCompareResult < 0) {
                if (currentNode.getLeft() == null) {
                    return false;
                }

                currentNode = currentNode.getLeft();

                continue;
            }

            if (currentNode.getRight() == null) {
                return false;
            }

            currentNode = currentNode.getRight();
        }
    }

    public boolean remove(E data) {
        if (root == null) {
            return false;
        }

        if (root.getData().equals(data)) {
            return removeRoot();
        }

        TreeNode<E> currentNode = root;
        TreeNode<E> parentNode = currentNode;

        while (currentNode != null && !currentNode.getData().equals(data)) {
            parentNode = currentNode;

            currentNode = data.compareTo(currentNode.getData()) < 0 ? currentNode.getLeft() : currentNode.getRight();
        }

        if (currentNode == null) {
            return false;
        }

        if (currentNode.getLeft() == null && currentNode.getRight() == null) {
            if (parentNode.getLeft() == currentNode) {
                parentNode.setLeft(null);
            } else {
                parentNode.setRight(null);
            }

            --size;

            return true;
        }

        if (currentNode.getLeft() == null) {
            if (parentNode.getLeft() == currentNode) {
                parentNode.setLeft(currentNode.getRight());
            } else {
                parentNode.setRight(currentNode.getRight());
            }

            --size;

            return true;
        }

        if (currentNode.getRight() == null) {
            if (parentNode.getLeft() == currentNode) {
                parentNode.setLeft(currentNode.getLeft());
            } else {
                parentNode.setRight(currentNode.getLeft());
            }

            --size;

            return true;
        }

        TreeNode<E> parentOfMinRightNode = currentNode;
        TreeNode<E> minRightNode = currentNode.getRight();

        while (minRightNode.getLeft() != null) {
            parentOfMinRightNode = minRightNode;
            minRightNode = minRightNode.getLeft();
        }

        if (parentOfMinRightNode != currentNode) {
            parentOfMinRightNode.setLeft(minRightNode.getRight());

        }

        minRightNode.setLeft(currentNode.getLeft());
        minRightNode.setRight(currentNode.getRight());

        if (parentNode.getLeft() == currentNode) {
            parentNode.setLeft(minRightNode);
        } else {
            parentNode.setRight(minRightNode);
        }

        --size;

        return true;
    }

    public boolean removeRoot() {
        if (root.getLeft() == null && root.getRight() == null) {
            root = null;
        } else if (root.getLeft() == null) {
            root = root.getRight();
        } else if (root.getRight() == null) {
            root = root.getLeft();
        } else {
            TreeNode<E> parentOfMinRightNode = root;
            TreeNode<E> minRightNode = root.getRight();

            while (minRightNode.getLeft() != null) {
                parentOfMinRightNode = minRightNode;
                minRightNode = minRightNode.getLeft();
            }

            if (parentOfMinRightNode != root) {
                parentOfMinRightNode.setLeft(minRightNode.getRight());
            }

            minRightNode.setLeft(root.getLeft());
            minRightNode.setRight(root.getRight());

            root = minRightNode;
        }

        --size;

        return true;
    }

    public int size() {
        return size;
    }

    public ArrayList<E> bypassInWidth() {
        ArrayList<E> list = new ArrayList<>();

        if (root == null) {
            return list;
        }

        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<E> currentNode = queue.poll();
            list.add(currentNode.getData());

            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }

        return list;
    }

    public ArrayList<E> bypassInDepth() {
        ArrayList<E> list = new ArrayList<>();

        if (root == null) {
            return list;
        }

        Deque<TreeNode<E>> stack = new ArrayDeque<>();
        TreeNode<E> currentNode = root;

        while (!stack.isEmpty() || currentNode != null) {
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.getLeft();
            }

            currentNode = stack.pop();
            list.add(currentNode.getData());
            currentNode = currentNode.getRight();
        }

        return list;
    }

    private ArrayList<E> bypassInDepthWithRecursion(TreeNode<E> startingNode) {
        ArrayList<E> list = new ArrayList<>();

        if (startingNode == null) {
            return list;
        }

        list.addAll(bypassInDepthWithRecursion(startingNode.getLeft()));
        list.addAll(bypassInDepthWithRecursion(startingNode.getRight()));
        list.add(startingNode.getData());

        return list;
    }
}