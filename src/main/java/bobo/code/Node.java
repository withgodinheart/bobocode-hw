package bobo.code;

public class Node<T> {
    T element;
    Node<T> next;

    public Node(T element) {
        this.element = element;
    }
}
