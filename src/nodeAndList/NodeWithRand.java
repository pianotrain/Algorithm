package nodeAndList;

/**
 * 这是一个单向链表节点, 但是有一个rand指针随机指向链表中的某个节点
 * create by renshengmiao on 2018/3/8 .
 */
public class NodeWithRand {
    private int value;
    private NodeWithRand next;
    private NodeWithRand rand;

    public NodeWithRand(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public NodeWithRand getNext() {
        return next;
    }

    public void setNext(NodeWithRand next) {
        this.next = next;
    }

    public NodeWithRand getRand() {
        return rand;
    }

    public void setRand(NodeWithRand rand) {
        this.rand = rand;
    }
}
