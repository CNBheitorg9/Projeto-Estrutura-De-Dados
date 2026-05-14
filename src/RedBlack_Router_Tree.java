public class RedBlack_Router_Tree {

    private RBTNode root;
    private RBTNode TNULL;

    public RedBlack_Router_Tree() {
        TNULL = new RBTNode(null);
        TNULL.setColor(RBTNode.BLACK);

        root = TNULL;
    }

    private void leftRotate(RBTNode x) {
        RBTNode y = x.getRight();
        x.setRight(y.getLeft());

        if (y.getLeft() != TNULL) {
            y.getLeft().setParent(x);
        }

        y.setParent(x.getParent());
        if (x.getParent() == null) {
            this.root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setLeft(x);
        x.setParent(y);
    }

    private void rightRotate(RBTNode x) {
        RBTNode y = x.getLeft();
        x.setLeft(y.getRight());

        if (y.getRight() != TNULL) {
            y.getRight().setParent(x);
        }

        y.setParent(x.getParent());
        if (x.getParent() == null) {
            this.root = y;
        } else if (x == x.getParent().getRight()) {
            x.getParent().setRight(y);
        } else {
            x.getParent().setLeft(y);
        }
        y.setRight(x);
        x.setParent(y);
    }

    public void insert(PacketRule rule) {
        RBTNode node = new RBTNode(rule);
        node.setParent(null);
        node.setColor(RBTNode.RED);
        node.setLeft(TNULL);
        node.setRight(TNULL);

        RBTNode y = null;
        RBTNode x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.getRule().compareTo(x.getRule()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        node.setParent(y);
        if (y == null) {
            root = node;
        } else if (node.getRule().compareTo(y.getRule()) < 0) {
            y.setLeft(node);
        } else {
            y.setRight(node);
        }

        if (node.getParent() == null) {
            node.setColor(RBTNode.BLACK);
            return;
        }
        if (node.getParent().getParent() == null) {
            return;
        }

        fixInsert(node);
    }

    private void fixInsert(RBTNode k) {
        RBTNode u;
        while (k.getParent().getColor() == RBTNode.RED) {
            if (k.getParent() == k.getParent().getParent().getRight()) {
                u = k.getParent().getParent().getLeft();

                if (u.getColor() == RBTNode.RED) {
                    u.setColor(RBTNode.BLACK);
                    k.getParent().setColor(RBTNode.BLACK);
                    k.getParent().getParent().setColor(RBTNode.RED);
                    k = k.getParent().getParent();
                } else {
                    if (k == k.getParent().getLeft()) {
                        k = k.getParent();
                        rightRotate(k);
                    }
                    k.getParent().setColor(RBTNode.BLACK);
                    k.getParent().getParent().setColor(RBTNode.RED);
                    leftRotate(k.getParent().getParent());
                }
            } else {
                u = k.getParent().getParent().getRight();
                if (u.getColor() == RBTNode.RED) {
                    u.setColor(RBTNode.BLACK);
                    k.getParent().setColor(RBTNode.BLACK);
                    k.getParent().getParent().setColor(RBTNode.RED);
                    k = k.getParent().getParent();
                } else {
                    if (k == k.getParent().getRight()) {
                        k = k.getParent();
                        leftRotate(k);
                    }
                    k.getParent().setColor(RBTNode.BLACK);
                    k.getParent().getParent().setColor(RBTNode.RED);
                    rightRotate(k.getParent().getParent());
                }
            }
            if (k == root){
                break;
            }
        }
        root.setColor(RBTNode.BLACK);
    }
}
