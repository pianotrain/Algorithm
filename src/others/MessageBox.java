package others;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsmno on 2018/3/24.
 */
public class MessageBox {

    private Map<Integer, Node> headMap ;
    private Map<Integer, Node> tailMap;
    private int lastPrint;

    public MessageBox() {
        headMap = new HashMap<>();
        tailMap = new HashMap<>();
        lastPrint = 0;
    }

    public MessageBox receive(int n){
        if (n < 1 || headMap.containsKey(n) || tailMap.containsKey(n)){
            return this;
        }
        Node cur = new Node(n);
        headMap.put(n,cur);
        tailMap.put(n, cur);
        if (tailMap.containsKey(n - 1)){
            tailMap.get(n - 1).next = cur;
            tailMap.remove(n - 1);
        }
        if (headMap.containsKey(n + 1)){
            cur.next = headMap.get(n + 1);
            tailMap.remove(n);
            headMap.remove(n + 1);
        }
        if (headMap.containsKey(lastPrint + 1)){
            print();
        }
        return this;
    }

    public void print(){
        Node node = headMap.get(++ lastPrint);
        headMap.remove(lastPrint);
        while (node != null){
            System.out.print(node.value + "  ");
            node = node.next;
            lastPrint ++;
        }
        tailMap.remove(-- lastPrint);
        System.out.println();
    }

    protected  class Node{
        int value;
        Node next;

        public Node(int value){
            this.value = value;
        }
    }

}
