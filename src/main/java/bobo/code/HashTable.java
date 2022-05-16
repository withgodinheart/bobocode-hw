package bobo.code;

import java.util.List;
import java.util.Objects;

public class HashTable<T> {

    public static void main(String[] args) {
        var names = List.of("Andrii", "Serhii", "Nazar", "Taras", "Stas", "Yurii", "Tetiana", "Valerii", "Victoriai");
        var namesTable = new HashTable<String>();
        names.forEach(namesTable::add);
        namesTable.printTable();
        namesTable.resize(20);
        namesTable.printTable();
    }

    private static final int DEFAULT_SIZE = 4;
    private float loadFactor = 0.75f;
    private int size = 0;

    @SuppressWarnings("unchecked")
    private Node<T>[] table = new Node[DEFAULT_SIZE];

    /**
     * Adds an element to the hash table. Does not support duplicate elements.
     *
     * @param element
     * @return true if it was added
     */
    public boolean add(final T element) {
        Objects.requireNonNull(element);
        final int index = findIndex(element, table.length);
        var node = table[index];

        if (node == null) {
            table[index] = new Node<>(element);
        } else {

            if (node.element.equals(element)) {
                return false;
            }

            while (node.next != null) {
                if (node.element.equals(element)) {
                    return false;
                }

                node = node.next;
            }

            node.next = new Node<>(element);
        }

        size++;
        autoResizeCheck();

        return true;
    }

    /**
     * Prints a hash table according to the following format
     * 0: Andrii -> Taras
     * 1: Start
     * 2: Serhii
     * ...
     */
    public void printTable() {
        for (int i = 0; i < table.length; i++) {
            System.out.print(i + ": ");

            Node<T> node = table[i];
            if (node != null) {
                while (node.next != null) {
                    System.out.print(node.element + " -> ");
                    node = node.next;
                }
                System.out.println(node.element);
            } else {
                System.out.println();
            }
        }
    }

    /**
     * Creates a new underlying table with a given size and add all elements to the new table.
     *
     * @param newSize
     */
    @SuppressWarnings("unchecked")
    public void resize(final int newSize) {
        System.out.println("Resizing from " + table.length + " to " + newSize);
        final Node<T>[] newTable = new Node[newSize];

        for (Node<T> tNode : table) {
            if (tNode != null) {
                int index = findIndex(tNode.element, newSize);
                newTable[index] = tNode;
            }
        }
        table = newTable;
    }

    private int findIndex(final T element, final int length) {
        return Math.abs(element.hashCode()) % length;
    }

    private void autoResizeCheck() {
        if ((float) size / table.length >= loadFactor) {
            resize(table.length * 2);
        }
    }

    public void setLoadFactor(final float loadFactor) {
        this.loadFactor = loadFactor;
    }

    public int getSize() {
        return size;
    }
}
