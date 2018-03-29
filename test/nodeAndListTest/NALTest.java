package nodeAndListTest;

import nodeAndList.DoubleNode;
import nodeAndList.Node;
import nodeAndList.NodeAndListAlogicUtils;
import org.junit.Test;

/**
 * create by renshengmiao on 2018/3/7 .
 */
public class NALTest {

    public Node initNode(int length){
        Node head = new Node(1);
//        Node head = new Node(5);
        Node temp = head;
        for (int i = 2; i <= length; i++){
            Node node = new Node(i);
            temp.setNext(node);
            temp = node;
        }
        return head;
    }

    public void  printNodeList(Node head){
        Node temp = head;
        while (temp != null ){
            System.out.println(temp.getValue());
            temp = temp.getNext();
        }
    }

    @Test
    public void testReverserList(){
        Node head = initNode(10);
        head = NodeAndListAlogicUtils.reverseList(head);
        printNodeList(head);
    }

    public DoubleNode initDoubleNode(int length){
        DoubleNode head = new DoubleNode(1);
        DoubleNode last = head;
        for (int i = 2; i<= length; i ++){
            DoubleNode doubleNode = new DoubleNode(i);
            doubleNode.setLast(last);
            last.setNext(doubleNode);
            last = doubleNode;
        }
        return head;
    }

    public void printDoubleNode(DoubleNode head){
        DoubleNode temp = head;
        DoubleNode last = null;
        System.out.println("print Down");
        while (temp != null){
            System.out.println(temp.getValue());
            last = temp;
            temp = temp.getNext();
        }
        temp = last;
        System.out.println("print Up");
        while (temp != null){
            System.out.println(temp.getValue());
            temp =temp.getLast();
        }
    }

    @Test
    public void testReverseDoubleList(){
        DoubleNode head = initDoubleNode(10);
        printDoubleNode(head);
        head = NodeAndListAlogicUtils.reverseDoubleList(head);
        printDoubleNode(head);
    }

    @Test
    public void testReversePartOfList(){
        Node head = initNode(10);
        Node node = NodeAndListAlogicUtils.reversePartOfNode(head, 2, 6);
        printNodeList(node);
        Node node1 = NodeAndListAlogicUtils.reversePartOfNode(node, 1, 8);
        printNodeList(node1);
    }

    @Test
    public void testJosephusKill111(){
        Node head = initNode(100000);
        Node last = head;
        while (last.getNext() != null){
            last = last.getNext();
        }
        //末节点连接头节点
        last.setNext(head);
//        printRoundNode(head);
        Node node = NodeAndListAlogicUtils.josephusKillPlus(head, 5000);
        printRoundNode(node);
    }

    public void printRoundNode(Node head){
        Node last = head;
        System.out.println("round 1 head : " + last.getValue());
        while (last.getNext() != head){
            System.out.println(last.getValue());
            last = last.getNext();
        }
        System.out.println(last.getValue());
        last = head;
        System.out.println("round 2 head : " + last.getValue());
        while (last.getNext() != head){
            System.out.println(last.getValue());
            last = last.getNext();
        }
        System.out.println(last.getValue());
    }

    @Test
    public void testJosephusPlus(){
        System.out.println( NodeAndListAlogicUtils.getLive(1122,2) );
        System.out.println( NodeAndListAlogicUtils.getLivePlus(1122, 2));
        System.out.println( NodeAndListAlogicUtils.getLive2(1122,2) );
        System.out.println( NodeAndListAlogicUtils.getLive(1122,3) );
        System.out.println( NodeAndListAlogicUtils.getLivePlus(1122, 3));
        System.out.println( NodeAndListAlogicUtils.getLive(27,3) );
        System.out.println( NodeAndListAlogicUtils.getLivePlus(27, 3));
        System.out.println( NodeAndListAlogicUtils.getLive(100000,5000) );
        System.out.println( NodeAndListAlogicUtils.getLivePlus(100000, 5000));
        System.out.println( NodeAndListAlogicUtils.getLive(100000,2) );
        System.out.println( NodeAndListAlogicUtils.getLivePlus(100000, 2));
        //plus只对m == 2 成立
    }

    @Test
    public void partitionTest(){
        int[] arr = {1,2,5,8,7,4,1,5,6,8,9,5};
        arr = NodeAndListAlogicUtils.partitionArr(arr, 5);
        for (int i = 0; i < arr.length; i ++){
            System.out.println(arr[i]);
        }
    }

    @Test
    public void partitionNodeTest(){
        Node head = initNode(10);
        head.setValue(5);
        head.getNext().setValue(13);
        Node node = NodeAndListAlogicUtils.listPartitonNode(head, 11);
        printNodeList(node);
    }
    @Test
    public void addList1Test(){
        Node head1 = initNode(8);
        Node head2 = initNode(5);
        printNodeList(NodeAndListAlogicUtils.addList1(head1,head2));
    }

    @Test
    public void testLoopIntersection(){
        Node n1 = null;
        Node n2 = null;
        System.out.println(n1 == n2);
    }

    @Test
    public void testReverseKNodes(){
        Node head = initNode(10);
        head = NodeAndListAlogicUtils.reverseKNodes1(head, 4);
        printNodeList(head);
        head = NodeAndListAlogicUtils.reverseKNode2(head, 4);
        printNodeList(head);
    }

    @Test
    public void testRemoveRep(){
        Node node = initNode(3);
        Node node1 = new Node(3);
        node1.setNext(new Node(3));
        node1.getNext().setNext(node);
        Node head = initNode(2);
        head.getNext().setNext(node1);
//        printNodeList(head);
        NodeAndListAlogicUtils.removeRep2(head);
        printNodeList(head);
        System.out.println();
        Node node2 = new Node(1);
        node2.setNext(new Node(1));
        NodeAndListAlogicUtils.removeRep2(node2);
        printNodeList(node2);
    }

    @Test
    public void testRemoveValue(){
        Node node2 = new Node(1);
        node2.setNext(new Node(1));
//        node2.getNext().setNext(new Node(2));
        node2.getNext().getNext().setNext(new Node(1));
        Node node = NodeAndListAlogicUtils.removeValue2(node2, 1);
        printNodeList(node);
        Node node1 = initNode(10);
        printNodeList(NodeAndListAlogicUtils.removeValue2(node1, 2));
        Node node3 = new Node(1);
        node3.setNext(node1);
        printNodeList( NodeAndListAlogicUtils.removeValue2(node3, 1) );
    }

    @Test
    public void testMerge(){
        Node head1 = initNode(5);
        Node head2 = initNode(6);
        printNodeList(NodeAndListAlogicUtils.merge2(head1, head2));
    }

    @Test
    public void testMergeLR(){
        Node head1 = initNode(9);
        Node head2 = initNode(6);
        printNodeList(NodeAndListAlogicUtils.mergeLR(head1));
    }
}
