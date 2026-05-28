public class RBTNode {
    public static final int RED = 0;
    public static final int BLACK = 1;

    private PacketRule rule;
    private RBTNode parent;
    private RBTNode left;
    private RBTNode right;
    private int color;

    public RBTNode(PacketRule rule) {
        this.rule = rule;
        this.color = RED;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public PacketRule getRule() {
        return rule;
    }

    public void setRule(PacketRule rule) {
        this.rule = rule;
    }

    public RBTNode getParent() {
        return parent;
    }

    public void setParent(RBTNode parent) {
        this.parent = parent;
    }

    public RBTNode getLeft() {
        return left;
    }

    public void setLeft(RBTNode left) {
        this.left = left;
    }

    public RBTNode getRight() {
        return right;
    }

    public void setRight(RBTNode right) {
        this.right = right;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
