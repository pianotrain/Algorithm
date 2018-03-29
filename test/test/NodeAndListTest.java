package test;

import node_list_tree.Node;
import node_list_tree.NodeUtils;
import org.junit.Test;

/**
 * Created by rsmno on 2018/3/6.
 */
public class NodeAndListTest {
    public Node initNode(){
        Node head = new Node(1);
        Node temp = head;
        for (int i = 2; i< 10; i ++){
            temp.next = new Node(i);
            temp = temp.next;
        }
        return head;
    }
    @Test
    public void removeLastKthNode(){
        Node head = initNode();
        printNodeList(head);
        head = NodeUtils.removeLastKthNode(head, 4);
        printNodeList(head);
    }

    @Test
    public void removeMidNodeTest(){
        Node head = initNode();
        NodeUtils.removeMidNode(head);
        printNodeList(head);
    }

    @Test
    public void removeNodeByRadioTset(){
        Node head = initNode();
        NodeUtils.removeByRadio(head, 1, 3);
        printNodeList(head);
        NodeUtils.removeByRadio(head, 5,8);
        printNodeList(head);
    }
    public void printNodeList(Node node){
        Node temp = node;
        while (temp != null){
            System.out.println(temp.getValue());
            temp = temp.next;
        }
    }

    @Test
    public void testIsPalindrome(){
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        System.out.println( NodeUtils.isPalindrome3(head) );
        printNodeList(head);
        head.next = new Node(2);
        head.next.next = new Node(1);
        head.next.next.next = new Node(2);
        System.out.println( NodeUtils.isPalindrome3(head) );
        printNodeList(head);
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        System.out.println( NodeUtils.isPalindrome3(head) );
        printNodeList(head);
        head.next.next.next.next = new Node(1);
        System.out.println( NodeUtils.isPalindrome3(head) );
        printNodeList(head);
        printNodeList(NodeUtils.reverse(head));
    }

    @Test
    public void selectSort(){
        Node node = initNode();
        Node head = new Node(10);
        head.next = node;
        printNodeList(NodeUtils.selectSort(head));
    }

    @Test
    public void insertNumTEst(){
        Node node = NodeUtils.insertNum(null, 1);
        Node node1 = NodeUtils.insertNum(node, 1);
        Node node2 = NodeUtils.insertNum(node1, 3);
        Node node3 = NodeUtils.insertNum(node2, 2);
        Node node4 = NodeUtils.insertNum(node3, 0);
        printRoundNode(node4);
    }

    //打印环形链表， 从环入口开始
    public void printRoundNode(Node head){
        if (head == null || head.next == null ){
            return;
        }
        Node fast = head.next.next;
        Node slow = head.next;
        while (fast != slow){
            fast = fast.next.next;
            slow = slow.next;
        }
        fast = head;
        while (fast != slow){
            fast = fast.next;
            slow = slow.next;
        }
        while (fast.next != slow){
            System.out.println(fast.getValue());
            fast = fast.next;
        }
        System.out.println(fast.getValue());
    }
}
