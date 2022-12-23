import java.util.LinkedList;
import java.util.Queue;

class BinaryTree {
    
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

    class Node1 {
        char val;
        Node1 left;
        Node1 right;
        
        Node1(char val) {
            this.val = val;
            left = null;
            right= null;
        }
    }

    /*
            1
          2    3
        4   5  6  7
    */
    Node buildTree() {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);
        root.left.left.left = new Node(8);
        // root.left.left.right = new Node(9);
        return root;
    }

    Node1 buildTree1() {
        Node1 root = new Node1('+');
        root.left = new Node1('x');
        root.right = new Node1('x');
        root.left.left = new Node1('2');
        root.left.right = new Node1('-');
        root.left.right.left = new Node1('a');
        root.left.right.right = new Node1('1');
        root.right.left = new Node1('3');
        root.right.right = new Node1('7');
        return root;
    }

    Node siblingNode(Node root, Integer val) {
        if (root == null) return null;
        if (root.val == val) return null;

        Node temp = null;
        if (root.left != null) {
            if (root.left.val == val) return root.right;
            temp = siblingNode(root.left, val);
        }
        if (temp == null && root.right != null) {
            if (root.right.val == val) return root.left;
            temp = siblingNode(root.right, val);
        }
        return temp;
    }

    Node parentNode(Node root, Integer val) {
        if (root == null) return null;
        if (root.val == val) return null;

        Node temp = null;
        if (root.left != null) {
            if (root.left.val == val) return root;
            temp = parentNode(root.left, val);
        }
        if (temp == null && root.right != null) {
            if (root.right.val == val) return root;
            temp = parentNode(root.right, val);
        }
        return temp;
    }

    int treeHeight(Node root) {
        if (root.left == null && root.right == null) return 0;
        int lh = 0, rh = 0;
        if (root.left != null) lh = treeHeight(root.left);
        if (root.right != null) rh = treeHeight(root.right);
        return 1 + Math.max(lh, rh);

        // if (root == null) return -1;
        // return 1 + Math.max(treeHeight(root.left), treeHeight(root.right));
    }

    int maxNodeValue(Node root) {
        if (root.left == null && root.right == null) return root.val;
        int lv = 0, rv = 0;
        if (root.left != null) lv = maxNodeValue(root.left);
        if (root.right != null) rv = maxNodeValue(root.right);
        return Math.max(root.val, Math.max(lv, rv));
    }
  
    int countNodes(Node root) {
        if (root == null) return 0;
        return 1+countNodes(root.left)+countNodes(root.right);
    }

    int leafNodesCnt(Node root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return leafNodesCnt(root.left) + leafNodesCnt(root.right);
    }

    void inOrderTraversal(Node root) {
        if (root == null) return;
        inOrderTraversal(root.left);
        System.out.print(root.val+" ");
        inOrderTraversal(root.right);
    }

    void preOrderTraversal(Node root) {
        if (root == null) return;
        System.out.print(root.val+" ");
        preOrderTraversal(root.left);
        preOrderTraversal(root.right);
    }

    void postOrderTraversal(Node root) {
        if (root == null) return;
        postOrderTraversal(root.left);
        postOrderTraversal(root.right);
        System.out.print(root.val+" ");
    }

    void levelOrderTraversal(Node root) {
        if (root == null) return;
        
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()) {
            Node temp = queue.poll();
            System.out.print(temp.val+" ");
            if (temp.left != null) queue.add(temp.left);
            if (temp.right != null) queue.add(temp.right);
        }
        return;
    }

    void insertNode(Node root, int key) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while(true) {
            Node temp = queue.poll();
            if (temp.left == null) {
                temp.left = new Node(key);
                return;
            } else if (temp.right == null) {
                temp.right = new Node(key);
                return;
            } 
            if (temp.left != null) queue.add(temp.left);
            if (temp.right != null) queue.add(temp.right);
        }
    }

    void inOrderArithmetic(Node1 root) {
        if (root.left != null) {
            System.out.print('(');
            inOrderArithmetic(root.left);
        }
        System.out.print(root.val);
        if (root.right != null) {
            inOrderArithmetic(root.right);
            System.out.print(')');
        }
    }

    public static void main(String[] args) {
        BinaryTree bTree = new BinaryTree();
        Node root = bTree.buildTree();
        /*
                1
              2    3
            4   5  6  7
        */

        // bTree.inOrderTraversal(root);
        // System.out.println();
        // bTree.preOrderTraversal(root);
        // System.out.println();
        // bTree.postOrderTraversal(root);
        // System.out.println();
        // bTree.levelOrderTraversal(root);
        // System.out.println();

        // System.out.println(bTree.countNodes(root));
        // System.out.println(bTree.leafNodesCnt(root));
        // System.out.println(bTree.siblingNode(root, 4).val);
        // System.out.println(bTree.parentNode(root, 6).val);
        // System.out.println(bTree.treeHeight(root));
        // System.out.println(bTree.maxNodeValue(root));

        bTree.insertNode(root, 9);
        bTree.levelOrderTraversal(root);
    }
}