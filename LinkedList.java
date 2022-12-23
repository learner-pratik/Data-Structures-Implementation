public class LinkedList {
    
    class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
            this.next = null;
        }
    }

    Node root;

    private Node createLinkedList(int N) {
        Node head = null, temp = null;

        for (int i = 0; i < N; i++) {
            Node node = new Node(i+1);
            if (temp != null) temp.next = node;
            temp = node;
            if (head == null) head = node;
        }

        return head;
    }

    void recursiveInsertAfter(Node head, Node newNode, int val) {
        if (head.val == val) {
            newNode.next = head.next;
            head.next = newNode;
            return;
        }
        recursiveInsertAfter(head.next, newNode, val);
    }

    void insertAfter(Node head, Node newNode, int val) {
        while (head != null) {
            if (head.val == val) {
                newNode.next = head.next;
                head.next = newNode;
                break;
            }
            head = head.next;
        }
    }

    void recursiveInsertBefore(Node head, Node newNode, int val) {
        if (head.val == val) {
            newNode.next = head;
            head = newNode;
            root = newNode;
            return;
        }
        if (head.next.val == val) {
            newNode.next = head.next;
            head.next = newNode;
            return;
        }
        recursiveInsertBefore(head.next, newNode, val);
    }

    void insertBefore(Node head, Node newNode, int val) {
        if (head.val == val) {
            newNode.next = head;
            head = newNode;
            root = newNode;
            return;
        }
        while (head.next != null) {
            if (head.next.val == val) {
                newNode.next = head.next;
                head.next = newNode;
                return;
            }
            head = head.next;
        }
    }

    void recursiveDeleteNode(Node head, int val) {
        if (head.val == val) {
            head = head.next;
            root = head;
            return;
        }
        if (head.next.val == val) {
            head.next = head.next.next;
            return;
        }
        recursiveDeleteNode(head.next, val);
    }

    void deleteNode(Node head, int val) {
        if (head.val == val) {
            head = head.next;
            root = head;
            return;
        }
        while (head.next != null) {
            if (head.next.val == val) {
                head.next = head.next.next;
                return;
            }
            head = head.next; 
        }
    }

    int maxValue(Node head) {
        if (head.next == null) return head.val;
        int mx = maxValue(head.next);
        return head.val > mx ? head.val : mx;
    }

    int sumOfNodes(Node head) {
        if (head.next == null) return head.val;
        return head.val + sumOfNodes(head.next);
    }

    void recursiveTraverse(Node root) {
        if (root == null) return;
        System.out.print(root.val+" ");
        recursiveTraverse(root.next);
    }

    int middleNode(Node slow, Node fast) {
        while (fast != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow.val;
    }

    Node reverseList(Node head) {
        if (head.next == null) {
            root = head;
            return head;
        }
        Node temp = reverseList(head.next);
        temp.next = head;
        head.next = null;
        return head;
    }

    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        
        ll.root = ll.createLinkedList(7);
        ll.recursiveTraverse(ll.root);
        System.out.println();
        
        // Node x = ll.new Node(9);
        // ll.recursiveInsertAfter(root, x, 4);
        // ll.recursiveTraverse(root);
        // System.out.println();

        // Node y = ll.new Node(10);
        // ll.insertAfter(root, y, 5);
        // ll.recursiveTraverse(root);
        // System.out.println();

        // Node a = ll.new Node(8);
        // ll.insertBefore(ll.root, a, 1);
        // Node b = ll.new Node(9);
        // ll.insertBefore(ll.root, b, 5);
        // ll.recursiveTraverse(ll.root);
        // System.out.println();

        // ll.deleteNode(ll.root, 5);
        // ll.deleteNode(ll.root, 1);
        // ll.recursiveTraverse(ll.root);
        // System.out.println();

        // System.out.println(ll.middleNode(ll.root, ll.root.next));

        ll.reverseList(ll.root);
        ll.recursiveTraverse(ll.root);
    }
}
