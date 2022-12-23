public class DoubleLinkedList {
    
    class Node {
        int val;
        Node prev;
        Node next;

        Node(int val) {
            this.val = val;
            prev = next = null;
        }
    }

    Node head, tail;

    void createDoubleLinkedList(int N) {
        Node temp = null;
        head = null;
        tail = null;
        for (int i = 0; i < N; i++) {
            Node node = new Node(i+1);
            if (temp != null) {
                temp.next = node;
                node.prev = temp;
            }
            temp = node;
            if (head == null) head = node;
            if (i == N-1) tail = node;
        }
    }

    void recursiveTraverseTail(Node tailNode) {
        if (tailNode == null) return;
        System.out.print(tailNode.val + " ");
        recursiveTraverseTail(tailNode.prev);
    }

    void recursiveTraverseHead(Node headNode) {
        if (headNode == null) return;
        System.out.print(headNode.val+" ");
        recursiveTraverseHead(headNode.next);
    }

    void insertBefore(Node temp, Node newNode, int v) {
        if (head.val == v) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            return;
        }
        if (temp.next.val == v) {
            temp.next.prev = newNode;
            newNode.prev = temp;
            newNode.next = temp.next;
            temp.next = newNode;
            return;
        }
        insertBefore(temp.next, newNode, v);
    }

    void insertAfter(Node temp, Node newNode, int v) {
        if (tail.val == v) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            return;
        }
        if (temp.val == v) {
            temp.next.prev = newNode;
            newNode.next = temp.next;
            newNode.prev = temp;
            temp.next = newNode;
            return;
        }
        insertAfter(temp.next, newNode, v);
    }

    void deleteNode(Node temp, int v) {
        if (head.val == v) {
            head.next.prev = null;
            head = head.next;
            return;
        }
        if (tail.val == v) {
            tail.prev.next = null;
            tail = tail.prev;
            return;
        }
        if (temp.val == v) {
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
            return;
        }
        deleteNode(temp.next, v);
    }

    int middleNode(Node start, Node end) {
        if (start == end) return start.val;
        return middleNode(start.next, end.prev);
    }

    public static void main(String[] args) {
        DoubleLinkedList dll = new DoubleLinkedList();
        dll.createDoubleLinkedList(9);
        dll.recursiveTraverseHead(dll.head);
        System.out.println();

        // Node a = dll.new Node(9);
        // dll.insertBefore(dll.head, a, 1);
        // Node b = dll.new Node(10);
        // dll.insertBefore(dll.head, b, 7);
        // dll.recursiveTraverseHead(a);

        // Node a = dll.new Node(9);
        // dll.insertAfter(dll.head, a, 8);
        // Node b = dll.new Node(10);
        // dll.insertAfter(dll.head, b, 6);
        // dll.recursiveTraverseHead(dll.head);

        // dll.deleteNode(dll.head, 1);
        // dll.deleteNode(dll.head, 10);
        // dll.deleteNode(dll.head, 6);
        // dll.recursiveTraverseHead(dll.head);

        System.out.println(dll.middleNode(dll.head, dll.tail));
    }
}
