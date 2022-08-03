package com.bobocode.hw21;

// Create a doubly-linked list and print it in the reversed order  using  iteration.
public class DoublyLinkedList {

    public static void main(String[] args) {
        var tail = createLinkedListReturnTail(4, 3, 9, 1);
        printReversedFromTail(tail); // should print "1 <-> 9 <-> 3 <-> 4"
    }

    public static <T> Node<T> createLinkedListReturnTail(T... elements) {
        var tail = new Node<>(elements[0]);
        for (int i = 1; i < elements.length; i++) {
            tail.next = new Node<>(elements[i]);
            tail.next.prev = tail;

            tail = tail.next;
        }

        return tail;
    }

    public static <T> void printReversedFromTail(Node<T> tail) {
        System.out.print(tail.element);
        tail = tail.prev;
        while (tail != null) {
            System.out.print(" <-> ");
            System.out.print(tail.element);
            tail = tail.prev;
        }
    }

    public static <T> Node<T> createLinkedList(T... elements) {
        final var head = new Node<>(elements[0]);

        var current = head;
        for (int i = 1; i < elements.length; i++) {
            current.next = new Node<>(elements[i]);
            current.next.prev = current;

            current = current.next;
        }

        return head;
    }

    public static <T> void printReversed(Node<T> node) {
        System.out.print(node.element);
        node = node.next;
        while (node != null) {
            System.out.print(" <-> ");
            System.out.print(node.element);
            node = node.next;
        }
    }

    static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        public Node(T element) {
            this.element = element;
        }
    }
}
