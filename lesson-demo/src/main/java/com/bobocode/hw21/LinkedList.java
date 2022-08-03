package com.bobocode.hw21;

import java.util.Objects;

// Create a linked list and print it in the reversed order using recursion [15 min]
public class LinkedList {
    public static void main(String[] args) {
        var head = createLinkedList(4, 3, 9, 1);
        printReversedRecursively(head); // should print "1 <- 9 <- 3 <- 4"
    }

    public static <T> Node<T> createLinkedList(T... elements) {
        final var head = new Node<>(Objects.requireNonNull(elements[0]));
        var current = head;
        for (int i = 1; i < elements.length; i++) {
            current.next = new Node<>(elements[i]);
            current = current.next;
        }

        return head;
    }

    public static <T> void printReversedRecursively(Node<T> head) {
        if (head.next != null) {
            printReversedRecursively(head.next);
            System.out.print(" <- ");
        }
        System.out.print(head.element);
    }

    static class Node<T> {
        T element;
        Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }
}
