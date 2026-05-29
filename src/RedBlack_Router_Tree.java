public class RedBlack_Router_Tree {

    private RBTNode root;
    private RBTNode TNULL;

    public RedBlack_Router_Tree() {
        TNULL = new RBTNode(null);
        TNULL.setColor(RBTNode.BLACK);
        TNULL.setLeft(TNULL);
        TNULL.setRight(TNULL);

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

    private RBTNode minimun(RBTNode node) {
        while (node.getLeft() != TNULL) {
            node = node.getLeft();
        }
        return node;
    }

    public void delete(int id){
        deleteNodeHelper(this.root, id);
    }

    private void deleteNodeHelper(RBTNode node, int id) {
        RBTNode z = TNULL;
        RBTNode x, y;
        while(node != TNULL){
            if(node.getRule().getId() == id){
                z = node;
            }
            if(node.getRule().getId() <= id){
                node = node.getRight();
            } else {
                node = node.getLeft();
            }
        }

        if(z == TNULL){
            return;
        }

        y = z;
        int OriginalColor = y.getColor();
        if(z.getLeft() == TNULL){
            x = z.getRight();
            rbTransplant(z, z.getRight());
        } else if(z.getRight() == TNULL){
            x = z.getLeft();
            rbTransplant(z, z.getLeft());
        } else {
            y = minimun(z.getRight());
            OriginalColor = y.getColor();
            x = y.getRight();
            if(y.getParent() == z){
                x.setParent(y);
            } else {
                rbTransplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }

            rbTransplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            y.setColor(z.getColor());
        }
        if(OriginalColor == RBTNode.BLACK){
            fixDelete(x);
        }
    }

    private void rbTransplant(RBTNode u, RBTNode v) {
        if(u.getParent() == null){
            root = v;
        }else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        v.setParent(u.getParent());
    }

    private void fixDelete(RBTNode x) {
        RBTNode s;
        while (x != root && x.getColor() == RBTNode.BLACK) {
            if(x == x.getParent().getLeft()){
                s = x.getParent().getRight();
                if(s.getColor() == RBTNode.RED){
                    s.setColor(RBTNode.BLACK);
                    x.getParent().setColor(RBTNode.RED);
                    leftRotate(x.getParent());
                    s = x.getParent().getRight();
                }
                if(s.getLeft().getColor() == RBTNode.BLACK && s.getRight().getColor() == RBTNode.BLACK){
                    s.setColor(RBTNode.RED);
                    x = x.getParent();
                } else {
                    if(s.getRight().getColor() == RBTNode.BLACK){
                        s.getLeft().setColor(RBTNode.BLACK);
                        s.setColor(RBTNode.RED);
                        rightRotate(s);
                        s = x.getParent().getRight();
                    }
                    s.setColor(x.getParent().getColor());
                    x.getParent().setColor(RBTNode.BLACK);
                    s.getRight().setColor(RBTNode.BLACK);
                    leftRotate(x.getParent());
                    x = root;
                }
            } else {
                s = x.getParent().getLeft();
                if(s.getColor() == RBTNode.RED){
                    s.setColor(RBTNode.BLACK);
                    x.getParent().setColor(RBTNode.RED);
                    rightRotate(x.getParent());
                    s = x.getParent().getLeft();
                }
                if(s.getRight().getColor() == RBTNode.BLACK && s.getLeft().getColor() == RBTNode.BLACK){
                    s.setColor(RBTNode.RED);
                    x = x.getParent();
                } else {
                    if(s.getLeft().getColor() == RBTNode.BLACK){
                        s.getRight().setColor(RBTNode.BLACK);
                        s.setColor(RBTNode.RED);
                        leftRotate(s);
                        s = x.getParent().getLeft();
                    }
                    s.setColor(x.getParent().getColor());
                    x.getParent().setColor(RBTNode.BLACK);
                    s.getLeft().setColor(RBTNode.BLACK);
                    rightRotate(x.getParent());
                    x = root;
                }
            }
        }
        x.setColor(RBTNode.BLACK);
    }
    public boolean search(int id) {
        RBTNode current = root;
        while (current != TNULL) {
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
