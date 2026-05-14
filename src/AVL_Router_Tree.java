public class AVL_Router_Tree {
    private AVLNode root;

    public AVL_Router_Tree() {
        this.root = null;
    }

    private int height(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.getHeight();
    }

    private int getBalance(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return height(node.getLeft()) - height(node.getRight());
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.getLeft();
        AVLNode z = x.getRight();

        x.setRight(y);
        y.setLeft(z);

        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) +1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) +1);

        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.getRight();
        AVLNode z = x.getLeft();

        y.setLeft(x);
        x.setRight(z);

        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) +1);
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) +1);

        return y;
    }

    public void insert(PacketRule rule) {
        root = insertNode(root, rule);
    }

    private AVLNode insertNode(AVLNode node, PacketRule rule) {
        if (node == null) {
            return new AVLNode(rule);
        }

        int compareResult = rule.compareTo(node.getRule());

        if (compareResult < 0) {
            node.setLeft(insertNode(node.getLeft(), rule));
        } else if (compareResult > 0) {
            node.setRight(insertNode(node.getRight(), rule));
        } else {
            return node;
        }
        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));

        int balance = getBalance(node);

        if (balance > 1 && rule.compareTo(node.getLeft().getRule()) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && rule.compareTo(node.getRight().getRule()) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && rule.compareTo(node.getLeft().getRule()) > 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        if (balance < -1 && rule.compareTo(node.getRight().getRule()) < 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }
}
