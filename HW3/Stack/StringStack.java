import java.util.Scanner;

public class StringStack {
    public static void main (String[] args) {
        Stack stack = new Stack ();
        stack.push ("abc");
        stack.push ("123");
        stack.pop ();
        System.out.println (stack.pop ());
    }

    public static class Stack {
        private Node firstNode = new Node ();

        private void push (String data) {
            Node newNode = new Node ();
            newNode.data = data;
            if (firstNode == null)
                firstNode = newNode;
            else {
                newNode.next = firstNode;
                firstNode = newNode;
            }
        }

        private String pop () {
            Node newNode = firstNode;
            if (firstNode.next == null) {
                firstNode = null;
                return newNode.data;
            }
            firstNode = firstNode.next;
            return newNode.data;
        }

        private static class Node {
            String data;
            Node next;
        }
    }
}