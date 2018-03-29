package others;

import java.util.Comparator;

/**
 * 设计一个没有扩容负担的堆结构
 * 1.可以生成小根堆，也可以生成大根堆
 * 2.包含getHead方法，返回当前堆顶值 O(1)
 * 3.getSize, 返回堆大小 O(1)
 * 4.add(x)， 添加元素，操作后依然是大根堆/小根堆 O(logN)
 * 5.popHead， 删除并返回堆顶的值，操作后依然是大根堆/小根堆 O(logN)
 * Created by rsmno on 2018/3/24.
 */
public class MyHeap<K> {

    private Node<K> head;
    private Node<K> last;
    private long size;
    private Comparator<K> comp;

    public MyHeap(Comparator<K> compara) {
        head = null;
        last = null;
        size = 0;
        this.comp = compara;
    }

    public  K getHead(){
        return head == null? null: head.value;
    }

    public long getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0 ;
    }

    //添加一个节点到堆中
    public MyHeap<K> add(K value){
        Node<K> kNode = new Node<>(value);
        if (size == 0){
            head = kNode;
            last = kNode;
            size ++;
            return this;
        }
        Node<K> last = this.last;
        Node<K> parent = last.parent;
        //找到正确的位置并插入新节点
        while (parent != null && last != parent.left){
            last = parent;
            parent = last.parent;
        }
        Node<K> nodeToAdd = null;
        if (parent == null){
            nodeToAdd = mostLeft(head);
            nodeToAdd.left = kNode;
            kNode.parent = nodeToAdd;
        }else if (parent.right == null){
            parent.right = kNode;
            kNode.parent = parent;
        }else {
            nodeToAdd = mostLeft(parent.right);
            nodeToAdd.left = kNode;
            kNode.parent = nodeToAdd;
        }
        this.last = kNode;
        //建堆及调整过程
        heapInsertModify();
        size ++;
        return this;
    }

    public K popHead(){
        if (size == 0){
            return null;
        }
        Node<K> res = head;
        if (size == 1){
            head = null;
            last = null;
            size --;
            return res.value;
        }
        Node<K> oldLast = popLastAndSetPreviousLast();
        //如果弹出的堆尾节点后，堆的大小等于1的处理
        if (size == 1){
            head = oldLast;
            last = oldLast;
            return res.value;
        }
        //如果弹出堆尾节点后，堆的大小大于1的处理
        Node<K> headLeft = res.left;
        Node<K> headRight = res.right;
        oldLast.left = headLeft;
        if (headLeft!= null){
            headLeft.parent = oldLast;
        }
        oldLast.right = headRight;
        if(headRight != null){
            headRight.parent = oldLast;
        }
        res.left = null;
        res.right = null;
        head = oldLast;
        //堆的调整过程
        heapify(oldLast);
        return res.value;
    }
    //建堆及调整过程
    private void heapInsertModify(){
        Node<K> last = this.last;
        Node<K> parent = last.parent;
        if (parent != null && comp.compare(last.value , parent.value) < 0){
            this.last = parent;
        }
        while (parent != null && comp.compare(last.value, parent.value) < 0){
            swapClosedTwoNodes(last, parent);
            parent = last.parent;
        }
        if (head.parent != null){
            head = head.parent;
        }
    }

    private void heapify(Node<K> node){
        Node<K> left = node.left;
        Node<K> right = node.right;
        Node<K> most = node;
        while (left != null){
            if (left != null && comp.compare(left.value, most.value) < 0){
                most = left;
            }
            if (right != null && comp.compare(right.value, most.value) < 0){
                most = right;
            }
            if (most != node){
                swapClosedTwoNodes(most, node);
            }else {
                break;
            }
            left = node.left;
            right = node.right;
            most = node;
        }
        if (node.parent == last){
            this.last = node;
        }
        while (node.parent != null){
            node = node.parent;
        }
        this.head = node;
    }

    //交换相邻的两个节点
    private void swapClosedTwoNodes(Node<K> node, Node<K> parent){
        if (node == null || parent == null){
            return;
        }
        Node<K> parentParent = parent.parent;
        Node<K> parentLeft = parent.left;
        Node<K> parentRight = parent.right;
        Node<K> nodeLeft = node.left;
        Node<K> nodeRigth = node.right;
        node.parent = parentParent;
        if (parentParent != null){
            if (parent == parentParent.left){
                parentParent.left = node;
            }else {
                parentParent.right = node;
            }
        }
        parent.parent = node;
        if(nodeLeft != null){
            nodeLeft.parent = parent;
        }
        if (nodeRigth != null){
            nodeRigth.parent = parent;
        }
        if (node == parent.left){
            node.left = parent;
            node.right = parentRight;
            if (parentRight != null){
                parentRight.parent = node;
            }
        }else {
            node.left = parentLeft;
            node.right = parent;
            if (parentLeft != null){
                parentLeft.parent = node;
            }
        }
        parent.left = nodeLeft;
        parent.right = nodeRigth;
    }


    //找到以node为头的子树中，最左的节点
    private Node<K> mostLeft(Node<K> node){
        while (node.left != null){
            node = node.left;
        }
        return node;
    }

    //找到以node为头的子树中，最右的节点
    private Node<K> mostRight(Node<K> node){
        while (node.right != null){
            node = node.right;
        }
        return node;
    }

    //在树中弹出堆尾节点后，找到原来的倒数第二个节点设置成新的堆尾节点
    private Node<K> popLastAndSetPreviousLast(){
        Node<K> node = last;
        Node<K> parent = node.parent;
        while (parent != null && node != parent.right){
            node = parent;
            parent = node.parent;
        }
        if (parent == null){
            node = last;
            parent = node.parent;
            node.parent = null;
            if (node == parent.left){
                parent.left = null;
            }else {
                parent.right = null;
            }
            last = mostRight(head);
        }else {
            Node<K> newLast = mostRight(parent.left);
            node = last;
            parent = node.parent;
            node.parent = null;
            if (node == parent.left){
                parent.left = null;
            }else {
                parent.right = null;
            }
            last = newLast;
        }
        size --;
        return node;
    }

    protected class Node<K>{
        public K value;
        public Node<K> left;
        public Node<K> right;
        public Node<K> parent;

        public Node(K value) {
            this.value = value;
        }
    }
}
