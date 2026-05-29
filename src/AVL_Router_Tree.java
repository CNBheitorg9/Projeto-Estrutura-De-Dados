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
        AVLNode z = y.getLeft();

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

    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    public AVLNode deleteNode(AVLNode node, int id) {
        if (node == null) {
            return node;
        }
        if (id < node.getRule().getId()) {
            node.setLeft(deleteNode(node.getLeft(), id));
        } else if (id > node.getRule().getId()) {
            node.setRight(deleteNode(node.getRight(), id));
        } else {
            if ((node.getLeft() == null) || (node.getRight() == null)) {
                AVLNode temp = null;
                if (temp == node.getLeft()) {
                    temp = node.getRight();
                } else {
                    temp = node.getLeft();
                }

                if ( temp == null){
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                AVLNode temp = minValueNode(node.getRight());
                node.setRule(temp.getRule());
                node.setRight(deleteNode(node.getRight(), temp.getRule().getId()));
            }
        }
        if (node == null){
            return node;
        }
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) +1);

        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.getLeft()) >= 0) {
            return rightRotate(node);
        }
        if (balance > 1 && getBalance(node.getLeft()) < 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.getRight()) <= 0) {
            return leftRotate(node);
        }
        if (balance < -1 && getBalance(node.getRight()) > 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }
    public void delete(int id) {
        root = deleteNode(root, id);
    }

    public boolean search(int id) {
        AVLNode current = root;
        while (current != null) {
            if (current.getRule().getId() == id) {
                return true;
            }
            if (id < current.getRule().getId()) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return false;
    }
}
