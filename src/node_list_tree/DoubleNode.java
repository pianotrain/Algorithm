package node_list_tree;

/**
 * Created by rsmno on 2018/3/6.
 */
//双向链表
public class DoubleNode {
    private int value;
    public DoubleNode last;//直接前驱
    public DoubleNode next;//直接后继

    public DoubleNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
