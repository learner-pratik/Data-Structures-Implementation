public class RedBlackTree {

    class Node {
        int val;
        Node left, right, parent;
        char color;

        Node(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
            this.color = 'R';
            this.parent = null;
        }
    }

    public Node root;

    // method to perform left rotation
    Node leftRotate(Node node) {
        Node a = node.right;
        Node b = a.left;
        a.left = node;
        node.right = b;
        node.parent = a;
        if (b != null) b.parent = node;
        return a;
    }

    // method to perform right rotate
    Node rightRotate(Node node) {
        Node a = node.left;
        Node b = a.right;
        a.right = node;
        node.left = b;
        node.parent = a;
        if (b != null) b.parent = node;
        return a;
    }

    // Insertion algorithm
    // 1. Add a red node by performing normal bst insert
    // 2. If node added is root, change color to black (black height of tree increases by 1)
    // 3. Let added node be X. If X's par is not black and X is not root
    //  a. If X's uncle is red, do the following
    //      i. Change par and uncle to black
    //      ii. Change grandpar to red
    //      iii. Change X = X's grandpar and repeat steps 2 and 3
    //  b. If X's uncle is black, there can be four configurations for X, X's par P and X's grandpar G
    //      i. Left Left Case (p is the left child of g and x is left child of p)
    //      ii. Left Right Case (p is the left child of g and x is the right child of p)
    //      iii. Right Right Case (Mirror of i)
    //      iv. Right Left Case (Mirror of ii)

    // flags to check if perform rotation
    boolean ll = false, lr = false, rr = false, rl = false;
    private Node insertHelper(Node root, int val) {
        
        // flag to check if red red conflict has occured
        boolean rFlag = false;

        if (root == null) return new Node(val);
        else if (val < root.val) {
            root.left = insertHelper(root.left, val);
            root.left.parent = root;
            if (root != this.root) {
                if (root.left.color == 'R' && root.color == 'R') rFlag = true;
            }
        } else {
            root.right = insertHelper(root.right, val);
            root.right.parent = root;
            if (root != this.root) {
                if (root.right.color == 'R' && root.color == 'R') rFlag = true;
            }
        }

        // check if rotation needs to be performed
        if (this.ll) { // left left rotation
            root = leftRotate(root);
            root.color = 'B';
            root.left.color = 'R';
            this.ll = false;
        } else if (this.lr) { // left right rotation
            root.right = rightRotate(root.right);
            root.right.parent = root;
            root = leftRotate(root);
            root.color = 'B';
            root.left.color = 'R';
            this.lr = false;
        } else if (this.rr) { // right right rotation
            root = rightRotate(root);
            root.color = 'B';
            root.right.color = 'R';
            this.rr = false;
        } else if (this.rl) { // right left rotation
            root.left = leftRotate(root.left);
            root.left.parent = root;
            root = rightRotate(root);
            root.color = 'B';
            root.right.color = 'R';
            this.rl = false;
        }

        if (rFlag) {
            // check if root is right or left child of its parent
            if (root.parent.right == root) {
                // if nodes uncle is black
                if (root.parent.left == null || root.parent.left.color == 'B') {
                    if (root.left != null) lr = true;
                    else ll = true;
                } else { // if nodes uncle is red
                    root.parent.left.color = 'B';
                    root.color = 'B';
                    if (root.parent != this.root) root.parent.color = 'R';
                }
            } else {
                // if nodes uncle is black
                if (root.parent.right == null || root.parent.right.color == 'B') {
                    if (root.right != null) rl = true;
                    else rr = true;
                } else { // if nodes uncle is red
                    root.parent.right.color = 'B';
                    root.color = 'B';
                    if (root.parent != this.root) root.parent.color = 'R';
                }
            }
            rFlag = false;
        }

        return root;
    }

    public void insert(int val) {
        if (this.root == null) {
            this.root = new Node(val);
            root.color = 'B';
        } else this.root = insertHelper(this.root, val);
    }

    public void inorderTraverse(Node root) {
        if (root == null) return;
        inorderTraverse(root.left);
        System.out.print(root.val+", ");
        inorderTraverse(root.right);
    }

    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        for (int i = 1; i <= 10; i++) {
            rbt.insert(i);
        }
        rbt.inorderTraverse(rbt.root);
    }
}