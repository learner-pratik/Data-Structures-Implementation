import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree {
    
    class Node {
        int val;
        Node left;
        Node right;
        
        Node(int val) {
            this.val = val;
            left = null;
            right= null;
        }
    }

    Node root;
    
    /*
            4
          2   6
        1  3 5  7
     */
    void buildTree() {
        root = new Node(4);
        root.left = new Node(2);
        root.right = new Node(6);
        root.left.left = new Node(1);
        root.left.right = new Node(3);
        root.right.left = new Node(5);
        root.right.right = new Node(7);
        // root.right.right.right = new Node(8);
    }

    void inOrderTraverse(Node root) {
        if (root == null) return;
        inOrderTraverse(root.left);
        System.out.print(root.val+", ");
        inOrderTraverse(root.right);
    }

    void preOrderTraverse(Node root) {
        if (root == null) return;
        System.out.print(root.val+", ");
        preOrderTraverse(root.left);
        preOrderTraverse(root.right);
    }

    void postOrderTraverse(Node root) {
        if (root == null) return;
        postOrderTraverse(root.left);
        postOrderTraverse(root.right);
        System.out.print(root.val+", ");
    }

    void levelOrderTraverse(Node root) {
        if (root == null) return;

        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        bfsTraverse(queue);
    }

    void bfsTraverse(Queue<Node> queue) {
        if (queue.isEmpty()) return;
        Node root = queue.poll();
        System.out.print(root.val+", ");
        if (root.left != null) queue.add(root.left);
        if (root.right != null) queue.add(root.right);
        bfsTraverse(queue);
    }
    
    void recursiveInsertNode(Node temp, Integer v) {
        if (root == null) {
            root = new Node(v);
            return;
        }
        if (v < temp.val) {
            if (temp.left == null) {
                temp.left = new Node(v);
                return;
            }
            recursiveInsertNode(temp.left, v);
        }
        else {
            if (temp.right == null) {
                temp.right = new Node(v);
                return;
            }
            recursiveInsertNode(temp.right, v);
        }
    }

    void insertNode(Node temp, int v) {
        if (root == null) {
            root = new Node(v);
            return;
        }
        while (true) {
            if (v < temp.val) {
                if (temp.left == null) {
                    temp.left = new Node(v);
                    return;
                }
                temp = temp.left;
            } else {
                if (temp.right == null) {
                    temp.right = new Node(v);
                    return;
                }
                temp = temp.right;
            }
        }
    }

    void deleteNode(Node root, Integer val) {
        if (root == null) return;

        // search node to be deleted
        Node temp = root, parent = null;
        while (temp.val != val) {
            parent = temp;
            if (val < temp.val) temp = temp.left;
            else temp = temp.right;
        }

        // temp is leaf node
        if (temp.left == null && temp.right == null) {
            if (parent.left == temp) parent.left = null;
            else parent.right = null;
            return;
        }

        // temp has only one child
        if (temp.left == null) {
            if (parent.left == temp) parent.left = temp.right;
            else parent.right = temp.right;
            return;
        } else if (temp.right == null) {
            if (parent.left == temp) parent.left = temp.left;
            else parent.right = temp.left;
            return;
        }

        // temp has both children, replace with predecessor
        Node pre = temp.left, par = temp;
        while (pre.right != null) {
            par = pre;
            pre = pre.right;
        }
        if (par == temp) par.left = pre.left;
        else par.right = pre.left;
        temp.val = pre.val;
        return;
    }

    // use successor
    void deleteNode2(Node root, Integer v) {
        if (root == null) return;

        // find node to be deleted
        Node temp = root, par = null;
        while (temp.val != v) {
            par = temp;
            if (v < temp.val) temp = temp.left;
            else temp = temp.right;
        }

        // node has zero or one child
        if (temp.left == null) {
            // temp is root
            if (par == null) {
                temp = temp.right;
                return;
            }
            if (par.left == temp) par.left = temp.right;
            else par.right = temp.right;
            return;
        } else if (temp.right == null) {
            // temp is root
            if (par == null) {
                temp = temp.left;
                return;
            }
            if (par.left == temp) par.left = temp.left;
            else par.right = temp.left;
        }
        
        // node has both childs, replace with successor
        Node par1 = temp, successor = temp.right;
        while (successor.left != null) {
            par1 = successor;
            successor = successor.left;
        }
        if (par1 == temp) par1.right = successor.right;
        else par1.left = successor.right;
        temp.val = successor.val;
        return;
    }

    int maxPredecessorVal(Node t) {
        int mxVal = t.val;
        while(t.right != null) {
            t = t.right;
            mxVal = t.val;
        }
        return mxVal;
    }

    Node recursiveDeleteNode(Node temp, Integer v) {
        if (temp == null) return temp;

        if (v < temp.val) { // v lies on left subtree
            temp.left = recursiveDeleteNode(temp.left, v);
        } else if (v > temp.val) { // v lies on right subtree
            temp.right = recursiveDeleteNode(temp.right, v);
        } else { // this is the node to be deleted
            
            // temp has zero or one children
            if (temp.left == null) return temp.right;
            else if (temp.right == null) return temp.left;
            
            else { // temp has both children, replace with predecessor
                temp.val = maxPredecessorVal(temp.left);
                temp.left = recursiveDeleteNode(temp.left, temp.val);
            }
        }
        return temp;
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.buildTree();
        
        bst.recursiveDeleteNode(bst.root, 7);
        bst.inOrderTraverse(bst.root);
    }
}
