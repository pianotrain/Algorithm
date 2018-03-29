package node_list_tree;

/**
 * Created by rsmno on 2018/3/6.
 */
public class Node {
    private int value;
    public Node next;
    public Node(int data){
        this.value = data;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
