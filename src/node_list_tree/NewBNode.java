package node_list_tree;

/**
 * Created by rsmno on 2018/3/12.
 */
public class NewBNode {
    private int data;
    private NewBNode left;
    private NewBNode right;
    private NewBNode parent;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public NewBNode getLeft() {
        return left;
    }

    public void setLeft(NewBNode left) {
        this.left = left;
    }

    public NewBNode getRight() {
        return right;
    }

    public void setRight(NewBNode right) {
        this.right = right;
    }

    public NewBNode getParent() {
        return parent;
    }

    public void setParent(NewBNode parent) {
        this.parent = parent;
    }

    public NewBNode(int data) {
        this.data = data;
    }
}
