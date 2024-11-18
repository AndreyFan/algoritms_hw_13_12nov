package de.telran.hw_13_12nov;

import java.util.Stack;

public class MainCheckingBST {
    // Метод для проверки корректности BST
    public static boolean isValidBST(TreeNode root) {
        if (root == null) return true;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode prev = null;
        TreeNode curr = root;

        while (!stack.isEmpty() || curr != null) {
            // Идем влево до конца
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();

            // Проверяем порядок
            if (prev != null && curr.val <= prev.val) {
                return false;
            }
            prev = curr;
            curr = curr.right;
        }
        return true;
    }

    // Метод для исправления некорректного BST
    public static void recoverTree(TreeNode root) {
        if (root == null) return;

        TreeNode[] swapped = new TreeNode[2];
        TreeNode prev = null;
        TreeNode curr = root;

        Stack<TreeNode> stack = new Stack<>();

        while (!stack.isEmpty() || curr != null) {
            // Идем влево до конца
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();

            // Найти нарушения
            if (prev != null && curr.val < prev.val) {
                if (swapped[0] == null) {
                    swapped[0] = prev;
                }
                swapped[1] = curr;
            }
            prev = curr;
            curr = curr.right;
        }

        // Меняем значения местами
        if (swapped[0] != null && swapped[1] != null) {
            int temp = swapped[0].val;
            swapped[0].val = swapped[1].val;
            swapped[1].val = temp;
        }
    }

    // Вспомогательный метод для печати дерева в виде "значение - левый - правый"
    public static void printTree(TreeNode root) {
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.println(node.val + " - " +
                    (node.left != null ? node.left.val : "null") + " - " +
                    (node.right != null ? node.right.val : "null"));

            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
    }

    public static void main(String[] args) {
        // Создаем некорректное дерево
        TreeNode root = new TreeNode(13);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(6); // Ошибка (6 > 5)
        root.left.right = new TreeNode(3); // Ошибка (3 < 5)

        System.out.println("До исправления:");
        printTree(root);
        System.out.println("\nКорректно ли дерево BST? " + isValidBST(root));

        recoverTree(root);

        System.out.println("\nПосле исправления:");
        printTree(root);
        System.out.println("\nКорректно ли дерево BST? " + isValidBST(root));

    }
}

