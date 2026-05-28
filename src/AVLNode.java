public class AVLNode {
    private PacketRule rule;
    private AVLNode left;
    private AVLNode right;
    private int height;

    public AVLNode(PacketRule rule) {
        this.rule = rule;
        this.left = null;
        this.right = null;
        this.height = 1;
    }

    public PacketRule getRule() {
        return rule;
    }

    public void setRule(PacketRule rule) {
        this.rule = rule;
    }

    public AVLNode getLeft() {
        return left;
    }

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public AVLNode getRight() {
        return right;
    }

    public void setRight(AVLNode right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
