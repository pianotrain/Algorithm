package bTree;

/**
 * 这是一个平衡二叉树的节点类
 * create by renshengmiao on 2018/3/9 .
 */
public class BinaryTree {

    private int data;
    private BinaryTree left;
    private BinaryTree right;

    public BinaryTree(int data) {
        this.data = data;
    }

    //递归生成平衡二叉树
    public static BinaryTree createBinaryTree(BinaryTree root,int data){
        if (root == null){
            return new BinaryTree(data);
        }else {
            if (root.getData() < data){
                if (root.right == null){
                    root.right = new BinaryTree(data);
                }else {
                    createBinaryTree(root.right, data);
                }
            }else {
                if (root.left == null){
                    root.left = new BinaryTree(data);
                }else {
                    createBinaryTree(root.left, data);
                }
            }
            return root;
        }
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public BinaryTree getLeft() {
        return left;
    }

    public void setLeft(BinaryTree left) {
        this.left = left;
    }

    public BinaryTree getRight() {
        return right;
    }

    public void setRight(BinaryTree right) {
        this.right = right;
    }
}
