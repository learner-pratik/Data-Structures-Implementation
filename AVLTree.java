public class AVLTree {
    
    class Node {
        int value, height;
        Node left, right;

        Node(int value) {
            this.value = value;
            height = 1;
            left = right = null;
        }
    }

    Node root;

    void inOrderTraverse(Node root) {
        if (root == null) return;
        inOrderTraverse(root.left);
        System.out.print(root.value+", ");
        inOrderTraverse(root.right);
    }

    int getHeight(Node node) {
        if (node == null) return 0;
        return node.height;
    }

    int maxVal(int a, int b) {
        return a > b ? a : b;
    }

    int getBalance(Node node) {
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    Node leftRotate(Node node) {
        Node t1 = node.right;
        Node t2 = t1.left;

        t1.left = node;
        node.right = t2;

        node.height = maxVal(getHeight(node.left), getHeight(node.right))+1;
        t1.height = maxVal(getHeight(t1.left), getHeight(t1.right))+1;

        return t1;
    }

    Node rightRotate(Node node) {
        Node t1 = node.left;
        Node t2 = t1.right;

        t1.right = node;
        node.left = t2;

        node.height = maxVal(getHeight(node.left), getHeight(node.right))+1;
        t1.height = maxVal(getHeight(t1.left), getHeight(t1.right))+1;

        return t1;
    }

    Node insertNode(Node node, Integer val) {
        if (node == null)
            return new Node(val);

        // 1. Perform BST Insert
        if (val < node.value)
            node.left = insertNode(node.left, val);
        else if (val > node.value)
            node.right = insertNode(node.right, val);
        else // duplicate values not allowed
            return node;

        // 2. Update height of node
        node.height = maxVal(getHeight(node.left), getHeight(node.right))+1;

        // 3. check balance of the nodee);
        int bal = getBalance(node);

        // 4.  if un-balance
        // left left case
        if (bal > 1 && val < node.left.value)
            return rightRotate(node);
        // right right case
        else  if (bal < -1 && val > node.right.value)
            return leftRotate(node);
        // left right case
        else if (bal > 1 && val > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        } // right left case
        else if (bal < -1 && val < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // 5. no un-balance
        return node;
    }

    Node maxValueNode(Node node) {
        Node curr = node;
        while (curr.right != null) curr = curr.right;
        return curr;
    }

    Node deleteNode(Node node, int val) {
        // 1. Perform standard BST delete
        if (node == null)
            return node;
        
        if (val < node.value) // node to be deleted lies in left subtree
            node.left = deleteNode(node.left, val);
        else if (val > node.value) // node to be deleted lies in right subtree
            node.right = deleteNode(node.right, val);
        else { // this is the node to be deleted
            
            // if node has only 0 or 1 child
            if (node.left == null || node.right == null) {
                Node temp = null;
                if (node.left == null) temp = node.right;
                else temp  = node.right;
                node = temp;
            } // node has both child, replace with smallest predecessor
            else {
                Node temp = maxValueNode(node.left);
                node.value = temp.value;
                node.left = deleteNode(temp, val);
            }
        }

        // if tree has only one node, return
        if (node == null) return node;

        // 2. Update height of current node
        node.height = maxVal(getHeight(node.left), getHeight(node.right))+1;

        // 3. Get balance factor of the node
        int bal = getBalance(node);

        // if unbalanced, there are 4 cases
        if (bal > 1 && val < node.left.value) // left-left case
            return rightRotate(node);
        else if (bal < -1 && val > node.right.value) // right-right case
            return leftRotate(node);
        else if (bal > 1 && val > node.left.value) { // left-right case
            node.left = leftRotate(node.left);
            return rightRotate(node);
        } else if (bal < -1 && val < node.right.value) { // right-left case
            node.right = rightRotate(node.right);
            return leftRotate(node);
        } 
        
        // not unbalanced
        return node;
    }

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();

        avlTree.root = avlTree.insertNode(null, 1);
        avlTree.root = avlTree.insertNode(avlTree.root, 2);
        avlTree.root = avlTree.insertNode(avlTree.root, 3);
        avlTree.root = avlTree.insertNode(avlTree.root, 4);
        avlTree.root = avlTree.insertNode(avlTree.root, 5);
        avlTree.root = avlTree.insertNode(avlTree.root, 6);
        avlTree.root = avlTree.insertNode(avlTree.root, 7);
        avlTree.root = avlTree.insertNode(avlTree.root, 8);
        avlTree.root = avlTree.insertNode(avlTree.root, 9);
        avlTree.root = avlTree.insertNode(avlTree.root, 10);

        avlTree.inOrderTraverse(avlTree.root);
        System.out.println();

        avlTree.root = avlTree.deleteNode(avlTree.root, 3);
        avlTree.inOrderTraverse(avlTree.root);
    }

}
