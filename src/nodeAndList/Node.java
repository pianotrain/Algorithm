package nodeAndList;

/**
 * 单向链表节点
 * create by renshengmiao on 2018/3/7 .
 */
public class Node {
    private int value;
    private Node next;
    public Node(int data){
        this.value = data;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
