package nodeAndList;

/**
 * 这是一个双向链表的节点类
 * create by renshengmiao on 2018/3/7 .
 */
public class DoubleNode {
    //双向连表
    private  int value;
    private DoubleNode last;//前驱节点
    private DoubleNode next;//后继节点

    public DoubleNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public DoubleNode getLast() {
        return last;
    }

    public void setLast(DoubleNode last) {
        this.last = last;
    }

    public DoubleNode getNext() {
        return next;
    }

    public void setNext(DoubleNode next) {
        this.next = next;
    }
}
