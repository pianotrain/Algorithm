package node_list_tree;

/**
 * Created by rsmno on 2018/3/8.
 */
public class BinaryNode {
    private int value;
    private BinaryNode left;
    private BinaryNode right;

    public BinaryNode(int value) {
        this.value = value;
    }

    public BinaryNode(int value, BinaryNode left, BinaryNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BinaryNode getLeft() {
        return left;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public BinaryNode getRight() {
        return right;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }

    public static BinaryNode create(BinaryNode root, int data){
        if (root == null){
            return new BinaryNode(data);
        }
        if (root.getValue() <= data){
            if (root.getRight() == null){
                root.setRight(new BinaryNode(data));
            }else {
                create(root.right, data);
            }
        }else {
            if (root.getLeft() == null){
                root.setLeft(new BinaryNode(data));
            }else {
                create(root.left, data);
            }
        }
        return root;
    }


}
