import java.util.Scanner;

class Stack<T> {
    private Node<T> firstNode = new Node<T> ();

    public T pop () {
        Node<T> newNode = firstNode;
        if (firstNode.next == null) {
            firstNode = null;
            return getData (newNode);
        }
        firstNode = firstNode.next;
        return getData (newNode);
    }

    public void push (T data) {
        Node<T> newNode = new Node<> ();
        newNode.data = data;
        if (firstNode != null)
            newNode.next = firstNode;
        firstNode = newNode;
    }

    private T getData (Node<T> node) {
        return node.data;
    }

    class Node<T> {
        T data;
        Node<T> next;
    }
}

public class genericStack {
    public static void main (String[] args) {
        Stack<String> stack = new Stack<String> ();
        stack.push ("abc");
        stack.push ("123");
        stack.pop ();
        System.out.println (stack.pop ());
    }
}