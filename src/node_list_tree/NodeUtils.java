package node_list_tree;

import java.util.Queue;
import java.util.Stack;

/**
 * Created by rsmno on 2018/3/6.
 */
public class NodeUtils {
    /**
     * 删除链表中倒数第k个节点
     *
     * @param node
     * @param k
     * @return
     */
    public static Node removeLastKthNode(Node node, int k) {
        if (node == null || k < 1) {
            //无效操作
            return node;
        }
        Node temp = node;
        while (temp != null) {
            temp = temp.next;
            //每next一步减1
            k--;
        }
        if (k > 0) {
            //k > 链表的长度， 直接返回
            return node;
        } else if (k == 0) {
            // k为链表的第一个元素
            node = node.next;
            return node;
        } else {
            //k为链表中的某一个元素
            temp = node;
            while (++k != 0) {
                //找到要删除节点的前一个节点
                temp = temp.next;
            }
            //删除节点
            temp.next = temp.next.next;
            return node;
        }
    }

    /**
     * 双向链表，删除倒数第kth个节点
     * 要注意last指针的重连
     *
     * @param head
     * @param kth
     * @return
     */
    public DoubleNode removeLastKthDoubleNode(DoubleNode head, int kth) {
        if (head == null || kth < 1) {
            return head;
        }
        DoubleNode temp = head;
        while (temp != null) {
            kth--;
            temp = temp.next;
        }
        if (kth > 0) {
            return head;
        } else if (kth == 0) {
            head = head.next;
            head.last = null;
            return head;
        } else {
            temp = head;
            while (++kth != 0) {
                temp = temp.next;
                temp.last = null;
            }
            temp.next = temp.next.next;
            if (temp.next != null) {
                temp.next.last = temp;
            }
            return head;
        }
    }

    public static Node removeMidNode(Node head) {
        if (head == null || head.next == null) {
            //一个节点，或null
            return head;
        }
        if (head.next.next == null) {
            //两个节点,删除第一个
            return head.next;
        }
        //大于两个节点
        Node pre = head;//每次移动一步
        Node cur = head.next.next;//每次移动两步
        while (cur.next != null && cur.next.next != null) {
            pre = pre.next;
            cur = cur.next.next;
        }
        pre.next = pre.next.next;
        return head;
    }

    /**
     * 删除a/b处的节点 ，如果链表长度为7， a=5， b = 7，  删除（7 * 5）/7 = 5处的节点
     *
     * @param head
     * @param a
     * @param b
     * @return
     */
    public static Node removeByRadio(Node head, int a, int b) {
        if (a < 1 || a > b) {
            return head;
        }
        Node temp = head;
        int n = 0;
        while (temp != null) {
            temp = temp.next;
            n++;
        }
        int num = (int) Math.ceil(((double) (n * a)) / ((double) b));
        if (num == 1) {
            head = head.next;
        }
        if (num > 1) {
            temp = head;
            while (--num != 1) {
                temp = temp.next;
            }
            temp.next = temp.next.next;
        }
        return head;
    }

    /**
     * 判断一个链表是否是回文结构，方法一， S : O(N）
     *
     * @param head
     * @return
     */
    public static boolean isPalindrome1(Node head) {
        Stack<Node> stack = new Stack<>();
        Node temp = head;
        while (temp != null) {
            stack.push(temp);
            temp = temp.next;
        }
        temp = head;
        while (temp != null) {
            if (temp.getValue() != stack.pop().getValue()) {
                return false;
            }
            temp = temp.next;
        }
        return true;
    }

    /**
     * 方法2 空间复杂度为方法1的一半， 右半部分压入栈
     *
     * @param head
     * @return
     */
    public static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Stack<Node> stack = new Stack<>();
        Node right = head.next;
        Node cur = head;
        while (cur.next != null && cur.next.next != null) {
            cur = cur.next.next;
            right = right.next;
        }
        while (right != null) {
            stack.push(right);
            right = right.next;
        }
        while (!stack.isEmpty()) {
            if (stack.pop().getValue() != head.getValue()) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static boolean isPalindrome3(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node mid = head;
        Node cur = head;
        while (cur.next != null && cur.next.next != null) {
            cur = cur.next.next; // 结尾
            mid = mid.next;  //中部
        }
        cur = mid.next; // 右部第一个
        mid.next = null; //切断mid 指向右部第一个的连接， 避免回环链表
        Node next = null;
        //反转右部链表，链表的结尾指向mid
        while (cur != null) {
            next = cur.next;
            cur.next = mid;
            mid = cur;
            cur = next;
        }
        next = mid;//mid现在在原链表的结尾
        cur = head;//第一个节点
        boolean res = true;
        //检查回文
        while (mid != null && cur != null) {
            if (mid.getValue() != cur.getValue()) {
                res = false;
            }
            mid = mid.next;
            cur = cur.next;
        }
        //恢复链表
        cur = next;
        next = null;
        Node pre = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return res;
    }

    //反转一个链表
    public static Node reverse(Node head) {
        Node cur = head;
        Node pre = null;
        Node next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 将一个二叉树收集到队列中
     *
     * @param head
     * @param queue
     */
    public static void inOrderToQueue(BinaryNode head, Queue<BinaryNode> queue) {
        if (head == null) {
            return;
        }
        inOrderToQueue(head.getLeft(), queue);
        queue.offer(head);
        inOrderToQueue(head.getRight(), queue);
    }

    /**
     * 将搜索二叉树变成有序的双向链表， 然后让最大值节点（最末）的right指针指向最小值节点（最首）， 并返回最大值节点
     * 将双向链表的尾节点连接头节点之后再返回，可以直接得到链表两端，省去遍历之后再得到两端的麻烦
     * 最后将返回的结果的right指针设置为null就可以得到有序的双向链表
     *
     * @param head
     * @return
     */
    public static BinaryNode process(BinaryNode head) {
        if (head == null) {
            return null;
        }
        BinaryNode leftEnd = process(head.getLeft());
        BinaryNode rightEnd = process(head.getRight());
        BinaryNode leftStart = leftEnd == null ? null : leftEnd.getRight();
        BinaryNode rightStart = rightEnd == null ? null : rightEnd.getRight();
        if (leftStart != null && rightStart != null) {
            leftEnd.setRight(head);
            head.setLeft(leftEnd);
            rightEnd.setLeft(head);
            head.setRight(rightEnd);
            rightEnd.setRight(leftStart);
            return rightEnd;
        } else if (leftStart != null) {
            leftEnd.setRight(head);
            head.setLeft(leftEnd);
            head.setRight(leftStart);
            return head;
        } else if (rightStart != null) {
            head.setRight(rightStart);
            head.setLeft(null);
            rightEnd.setRight(head);
            return rightEnd;
        } else {
            head.setLeft(null);
            head.setRight(head);
            return head;
        }
    }

    /**
     * 选择排序， T:O(N^2) S:O(1)
     * @param head
     * @return
     */
    public static Node selectSort(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node newHead = null;
        Node tempHead = null;
        while (head != null) {
            Node cur = head;
            Node curPre = null;
            Node tempMin = head;
            Node minPre = null;
            Node minNext = head.next;
            while (cur != null) {
                if (cur.getValue() < tempMin.getValue()) {
                    minPre = curPre;
                    tempMin = cur;
                    minNext = cur.next;
                }
                curPre = cur;
                cur = cur.next;
            }
            if (minPre != null) {
                minPre.next = minNext;
            }
            newHead = newHead == null ? tempMin : newHead;
            if (tempHead == null) {
                tempHead = tempMin;
            } else {
                tempHead.next = tempMin;
                tempHead = tempMin;
            }
            if (head == tempMin) {
                head = head.next;
            }
            tempMin.next = null;
        }
        return newHead;
    }

    /**
     * 一种怪异的节点删除方式 T:O(1)
     * 如果节点是尾节点，则这种方法是无法做到删除的
     * 将node的值设置为node.next 的值， 再将node.next删掉
     * 工程上会出问题， 节点可能保存很重要的信息，或者根本不允许改变节点的值
     * @param node
     */
    public  static void  removeNodeWired(Node node){
        if (node == null){
            return;
        }
        Node next = node.next;
        if (next == null){
            throw new RuntimeException("can not remove last node!");
        }
        node.setValue(next.getValue());
        node.next = next.next;
    }

    /**
     * 向有序的环形单链表中插入新节点
     * 环形链表从头结点开始不降序，插入后也不降序， 并返会节点开始也不降序的头结点
     * @param head
     * @param num
     * @return
     */
    public static Node insertNum(Node head, int num){
        Node node = new Node(num);
        if (head == null){
            node.next = node;
            return node;
        }
        Node cur = head.next;
        Node pre = head;
        while (cur != head){
            if (cur.getValue() >= num && pre.getValue() <= num){
                break;
            }
            pre = cur;
            cur = cur.next;
        }
        pre.next = node;
        node.next = cur;
        return num < head.getValue() ? node : head;
    }
}
