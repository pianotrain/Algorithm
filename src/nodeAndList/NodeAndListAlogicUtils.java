package nodeAndList;

import java.util.*;

/**
 * 链表相关算法
 * create by renshengmiao on 2018/3/7 .
 */
public class NodeAndListAlogicUtils {

    /**
     * 反转单向连表
     * @param head 表头节点
     * @return
     */
    public static Node reverseList(Node head){
        Node pre = null; //前一个节点
        Node next = null;   //后一个节点
        //当前节点不为空
        while (head != null){
            //获得下一个节点
            next = head.getNext();
            //当前节点的后继节点设为前一个节点
            head.setNext(pre);
            //前驱节点后移
            pre = head;
            //当前节点后移
            head = next;
        }
        return pre;
    }

    /**
     * 反转双向连表
     * @param head
     * @return
     */
    public static DoubleNode reverseDoubleList(DoubleNode head){
        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null){
            next = head.getNext();
            head.setNext(pre);
            head.setLast(next);
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 反转一个连表的一部分
     * @param head
     * @param from
     * @param to
     * @return
     */
    public static Node reversePartOfNode(Node head, int from , int to){
        if (from < 1 || from > to){
            return head;
        }
        int len = 0;
        Node temp = head;
        Node fPre = null;
        Node tNext = null;
        while (temp != null){
            len ++;
            //找到from的前一个节点和 to的后一个节点
            fPre = len == from - 1 ? temp : fPre;
            tNext = len == to + 1? temp : tNext;
            temp = temp.getNext();
        }
        if (to > len){
            return head;
        }
        //这一步将form 节点和 to + 1节点连接
        Node pre = tNext;
        //开始反转from - to的连表
        Node cur = fPre != null ? fPre.getNext() : head;
        Node next = null;
        while (!cur.equals(tNext)){
            next = cur.getNext();
            cur.setNext(pre);
            pre = cur;
            cur = next;
        }
        if (fPre != null) {
            //反转不包括头结点
            //将from - 1 与 to 节点连接, 返回原头节点
            fPre.setNext(pre);
            return head;
        }else {
            //from = 1, 反转包括头结点, 返回反转后的节点, 即to节点
            return pre;
        }
    }

    /**
     * 约瑟夫问题,普通解法, T:O(n * m)
     * @param head 循环连表头
     * @param m  从头开始依次报数, 报到m就杀死(从链表中删除)
     * @return 最后留下的一个节点
     */
    public static Node josephusKill(Node head, int m){
        if (head == null || head.getNext() == null || m < 1){
            return head;
        }
        Node last = head;
        //找到链表的最后一个节点
        while (last.getNext() != head){
            last = last.getNext();
        }
        int count = 0;
        while (last != head){
            if (++count == m){
                //删除节点, last不变, head后移
                last.setNext(head.getNext());
                head = head.getNext();
                count = 0;
            }else {
                //不删除, last 和 head 都后移
                last = head;
                head = head.getNext();
            }
        }
        return head;
    }

    /**
     * 约瑟夫问题, 进阶求解, T:O(N)
     * @param head
     * @param m
     * @return
     */
    public static Node josephusKillPlus(Node head, int m){
        if (head == null || head.getNext() == null || m < 1){
            return head;
        }
        Node last = head;
        int len = 1;//链表长度
        //找到链表的最后一个节点
        while (last.getNext() != head){
            last = last.getNext();
            len ++;
        }
        int live = getLive(len, m);
        while (--live != 0){
            head = head.getNext();
        }
        head.setNext(head);
        return head;
    }

    //f(1)=0;
    //f(i)=(f[i-1]+m)%i;(i>1)
    public static  int getLive(int len, int m){
        int s = 0;
        for (int i = 2; i <= len; i ++){
            s = (s + m) %i;
        }
        return s + 1;
    }

    public static int getLive2(int len, int m){
        if (len == 1){
            return 1;
        }
        return (getLive2(len - 1, m) + m - 1) % len + 1;
    }

    /**
     * 规律求解 T: O(log.m- len) //貌似只对m == 2成立
     * 1.找出令 len = m ^ a + b 成立的最大的a, 记为a_max
     * 2.求 b = len - m ^ a_max
     * 3.最后存活的序号为 (m * b) % len + 1
     * @param len
     * @param m
     * @return
     */
    public static int getLivePlus(int len, int m){
        int temp = len;
        int a = 0;
        while (temp > 0){
            temp /= m;
            a ++;
        }
        int b = len - ((int)Math.pow(m, a - 1));
        return (m * b) % len + 1;
    }

    /**
     * 数组类似partition调整
     * @param arr
     * @param pivot 中枢, 指定值
     * @return 数组前一部分小于 pivot , 中间等于pivot, 后面大于pivot, 但和原数组顺序会不同
     */
    public static int[] partitionArr(int[] arr, int pivot){
        if (arr == null || arr.length < 1){
            return arr;
        }
        int small = -1;
        int big = arr.length ;
        int index = 0;
        while (index < big){
            if (arr[index] < pivot){
                swap(arr, ++ small, index ++);
            }else if (arr[index] == pivot){
                index ++;
            }else {
                swap(arr, -- big, index );
            }
        }
        return arr;
    }

    private static void swap(int[] arr, int index1, int index2){
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    /**
     * 将链表按某一指定值划分成左边小, 中间相等, 右边大的形势, 并且不改变每一部分中原来的顺序
     * 将原链表依次划分为三部分, 再连接起来
     * @param head
     * @param pivot
     * @return
     */
    public static Node listPartitonNode(Node head, int pivot){
        if (head == null || head.getNext() == null){
            return head;
        }
        Node smallStart = null;
        Node smallEnd = null;
        Node equalStart = null;
        Node equalEnd = null;
        Node bigStart = null;
        Node bigEnd = null;
        Node next = null;
        //划分链表, Start保留各个链表的头
        while (head != null){
            next = head.getNext();
            head.setNext(null);
            if (head.getValue() < pivot){
                if (smallStart == null){
                    smallStart = head;
                    smallEnd = head;
                }else {
                    smallEnd.setNext(head);
                    smallEnd = head;
                }
            }
            else if (head.getValue() == pivot){
                if (equalStart == null){
                    equalStart = head;
                    equalEnd = head;
                }else {
                    equalEnd.setNext(head);
                    equalEnd = head;
                }
            }
            else {
                if (bigStart == null){
                    bigStart = head;
                    bigEnd = head;
                }else {
                    bigEnd.setNext(head);
                    bigEnd = head;
                }
            }
            head = next;
        }
        //重新连接
        if (smallStart != null){
            if (equalStart != null){
                smallEnd.setNext(equalStart);
            }else  if (bigStart != null){
                smallEnd.setNext(bigStart);
            }
        }
        if (equalStart != null){
            equalEnd.setNext(bigStart);
        }
        return smallStart != null ? smallStart : equalStart!= null? equalStart : bigStart;
    }

    /**
     * 拷贝一个含有随机指针的链表, 利用hashmap, 额外 S:O(N)
     * @param head
     * @return
     */
    public static NodeWithRand copyNodeWithRand1(NodeWithRand head){
        HashMap<NodeWithRand, NodeWithRand> map = new HashMap<>();
        NodeWithRand cur = head;
        //遍历一次, 生成对应的副本
        while (cur != null){
            map.put(cur, new NodeWithRand(cur.getValue()));
            cur = cur.getNext();
        }
        cur = head;
        //遍历第二次, 为副本设置对应的节点指针
        while (cur != null){
            map.get(cur).setNext(map.get(cur.getNext()));
            map.get(cur).setRand(map.get(cur.getRand()));
            cur = cur.getNext();
        }
        return map.get(head);
    }

    /**
     * 拷贝一个含有随机指针的链表, T:O(N), S:O(1)
     * 先将每个节点的副本插入到节点和下一个节点之间, 1->2->3->null ----> 1->1'->2->2'->3->3'->null
     * 再设置rand,副本的rand指针等于 原来节点的rand指针的下一个,
     * 再拆分成两个链表
     * @param head
     * @return
     */
    public static NodeWithRand copyNodeWithRand2(NodeWithRand head){
        if (head == null){
            return null;
        }
        NodeWithRand cur = head;
        NodeWithRand next = null;
        //拷贝并插入节点
        while (cur != null){
            next = cur.getNext();
            NodeWithRand copyCur = new NodeWithRand(cur.getValue());
            cur.setNext(copyCur);
            copyCur.setNext(next);
            cur = next;
        }
        cur = head;
        NodeWithRand copyCur = head.getNext();
        next = null;
        //设置rand指针
        while (cur != null){
            next = copyCur.getNext();
            if (cur.getRand() != null){
                copyCur.setRand(cur.getRand().getNext());
            }
            cur = next;
            copyCur = next != null? next.getNext() : null;
        }
        cur = head;
        NodeWithRand copyHead = head.getNext();
        copyCur = head.getNext();
        next = null;
        //拆分
        while (cur != null){
            next = copyCur.getNext();
            cur.setNext(next);
            copyCur.setNext(next != null ? next.getNext() : null);
            cur = next;
            copyCur = next != null? next.getNext() : null;
        }
        return copyHead;
    }

    /**
     * 两个单链表生成相加链表, 1->1->1->null + 1->1->null == 1-> 2->2->null, 链表的节点值在0-9之间
     * @param head1
     * @param head2
     * @return
     */
    public static Node addList1(Node head1, Node head2){
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        Node cur = head1;
        while (cur != null){
            stack1.push(cur);
            cur = cur.getNext();
        }
        cur = head2;
        while (cur != null){
            stack2.push(cur);
            cur = cur.getNext();
        }
        Node pre = null;
        Node node = null;
        int ca = 0;
        int value= 0;
        while (!stack1.isEmpty() || !stack2.isEmpty()){
            value = (stack1.isEmpty()? 0 : stack1.pop().getValue())
                    + (stack2.isEmpty() ? 0 : stack2.pop().getValue());
            pre = node;
            node = new Node((value + ca) % 10);
            node.setNext(pre);
            ca = (ca + value) / 10;
        }
        if (ca > 0){
            pre = node;
            node = new Node(ca);
            node.setNext(pre);
        }
        return node;
    }

    /**
     * 利用链表逆序求解, 可以省掉用栈的空间
     * @param head1
     * @param head2
     * @return
     */
    public static Node addList2(Node head1, Node head2){
        // TODo
        reverseList(head1);
        reverseList(head2);
        return head1;
    }

    /**
     * 判断两个链表是否相交, 链表可能有环
     * @param head1
     * @param head2
     * @return
     */
    public static Node getIntersection(Node head1, Node head2){
        //判断是否有环
        Node loopStart1 = getLoopStartNode(head1);
        Node loopStart2 = getLoopStartNode(head2);
        if (loopStart1 == null && loopStart2 == null){
            //两个链表都无环
            return getIntersectionOfNoLoopLists(head1, head2);
        }else if (loopStart1 != null && loopStart2 != null){
            //两个链表都有环
            return getIntersectionOfLoopLists(head1,loopStart1,head2, loopStart2);
        }else {
            //一个无环,一个有环, 必定不会相交
            return null;
        }
    }
    /**
     * 判断链表是否有回环, 有就返回第一个进入回环的节点, 没有返回null
     * @param head
     * @return
     */
    public static Node getLoopStartNode(Node head){
        if (head == null || head.getNext() == null || head.getNext().getNext() == null){
            return null;
        }
        //slow每次移动一步, fast每次移动两步, 如果有回环, fast和slow必定会相遇
        Node slow = head.getNext();
        Node fast = head.getNext().getNext();
        //判断回环
        while (slow != fast ){
            if (fast.getNext() == null || fast.getNext().getNext() == null){
                //不回环
                return null;
            }
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        //fast和slow相遇后, fast指向头, 每次移动一步, slow保持移动一步,如果再次相遇,即为环的入口节点
        fast = head;
        while (slow != fast){
            slow = slow.getNext();
            fast = fast.getNext();
        }
        return fast;
    }

    /**
     * 判断两个无环链表是否有交点, 有就返回第一个交点, 没有返回null
     * @param head1
     * @param head2
     * @return
     */
    public static Node getIntersectionOfNoLoopLists(Node head1, Node head2){
        if (head1 == null || head2 == null){
            return null;
        }
        Node cur = head1;
        int len1 = 0;
        Node end1 = null;
        while (cur != null){
            end1 = cur;
            len1 ++;
            cur = cur.getNext();
        }
        cur = head2;
        Node end2 = null;
        while (cur != null){
            end2 = cur;
            len1 --;
            cur = cur.getNext();
        }
        if (end1 != end2){
            return null;
        }
        end1 = len1 > 0 ? head1 : head2;
        end2 = end1 == head1 ? head2 : head1;
        len1 = Math.abs(len1);
        while (len1 > 0){
            end1 = end1.getNext();
            len1 --;
        }
        while (end1 != end2){
            end1 = end1.getNext();
            end2 = end2.getNext();
        }
        return end1;
    }

    /**
     * 判断两个有环链表是否有交点, 有环返回交点, 无环返回null
     * @param head1
     * @param loopStart1 head1的环入口
     * @param head2
     * @param loopStart2 head2的环入口
     * @return
     */
    public static Node getIntersectionOfLoopLists(Node head1, Node loopStart1, Node head2, Node loopStart2){
        Node cur1;
        Node cur2;
        //环的入口相同
        if (loopStart1 == loopStart2){
            int len = 0;
            cur1 = head1;
            while (cur1 != loopStart1){
                cur1 = cur1.getNext();
                len ++;
            }
            cur2 = head2;
            while (cur2 != loopStart2){
                cur2 = cur2.getNext();
                len --;
            }
            cur1 = len > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            len = Math.abs(len);
            while (cur1 != cur2){
                cur1 = cur1.getNext();
                cur2 = cur2.getNext();
            }
            return cur1;
        }else {
            //环的入口不同
            cur1 = loopStart1.getNext();
            while (cur1 != loopStart1){
                if (cur1 == loopStart2){
                    return cur1; // 返回loopStart1 和loopStart2都可以
                }
                cur1 = cur1.getNext();
            }
            return null;
        }
    }

    /**
     * 将单链表每k个节点之间逆序, S:O(k), 一个栈
     * @param head
     * @param k
     * @return
     */
    public static Node reverseKNodes1(Node head, int k){
        if (head == null || head.getNext() == null || k < 2){
            return head;
        }
        Stack<Node> stack = new Stack<>();
        Node newHead = head; //记录新的头节点
        Node cur = head;
        Node pre = null;
        Node next = null;
        while (cur != null){
            stack.push(cur);
            next = cur.getNext();
            if (stack.size() == k){
                pre = resign1(stack, pre, next);
                newHead = newHead == head? cur : newHead;
            }
            cur = next;
        }
        return newHead;
    }

    public static Node resign1(Stack<Node> stack, Node left, Node right){
        Node cur = stack.pop();
        if (left != null){
            left.setNext(cur);
        }
        while (!stack.isEmpty()){
            cur.setNext(stack.pop());
            cur = cur.getNext();
        }
        cur.setNext(right);
        return cur;
    }

    /**
     * 不用栈
     * @param head
     * @param k
     * @return
     */
    public static Node reverseKNode2(Node head, int k){
        if (k < 2){
            return head;
        }
        Node newHead = head; // 记录新的头节点
        Node pre = null;
        Node next = null;
        Node cur = head;
        Node tempHead = head;
        int temp = 0;
        while (cur != null){
            temp ++;
            next = cur.getNext();
            while (temp == k){
//                cur.setNext(null);
                pre = resign2(tempHead, pre, next);
                newHead = newHead == head ? cur : newHead;
                tempHead = next;
                temp = 0;
            }
            cur = next;
        }
        return newHead;
    }

    public static Node resign2(Node head, Node left, Node right){
        Node pre = right;
        Node cur = head;
        Node next = null;
        while (cur != right){
            next = cur.getNext();
            cur.setNext(pre);
            pre = cur;
            cur = next;
        }
        if (left != null){
            left.setNext(pre);
        }
        return head;
    }

    /**
     * 删除重复的节点
     * @param head
     */
    public static void removeRep1(Node head){
        if (head == null || head.getNext() == null){
            return;
        }
        Set<Integer> set = new HashSet<>();
        set.add(head.getValue());
        Node cur = head.getNext();
        Node pre = head;
        Node next = null;
        while (cur != null){
            next = cur.getNext();
            if (set.contains(cur.getValue())){
                pre.setNext(next);
            }else {
                pre = cur;
                set.add(cur.getValue());
            }
            cur = next;
        }
    }

    public static void removeRep2(Node head){
        Node pre = head;
        Node cur = head.getNext();
        Node next = null;
        while (cur != null){
            next = cur;
            int value = pre.getValue();
            while (next != null){
                if (value == next.getValue()){
                    pre.setNext(next.getNext());
                }else {
                    pre = next;
                }
                next = next.getNext();
            }
            pre = cur;
            cur = cur.getNext();
        }
    }

    /**
     * 删除链表中所有指定值的节点
     * @param head
     * @param value
     * @return
     */
    public static Node removeValue1(Node head, int value){
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null){
            if (cur.getValue() != value){
                stack.push(cur);
            }
            cur = cur.getNext();
        }
        Node pre = stack.isEmpty() ? null : stack.pop();
        Node next = null;
        while (!stack.isEmpty()){
            next = stack.pop();
            next.setNext(pre);
            pre = next;
        }
        return pre;
    }

    public static Node removeValue2(Node head, int value){
        if (head == null) return null;
        Node cur = head;
        while (cur.getValue() == value){
            cur = cur.getNext();
            if (cur == null) return null;
        }
        head = cur;
        cur = head.getNext();
        Node pre = head;
        while (cur != null){
            if (cur.getValue() == value){
                pre.setNext(cur.getNext());
            }else {
                pre = cur;
            }
            cur = cur.getNext();
        }
        return head;
    }

    /**
     * 连接两个有序链表, 返回结果仍有序
     * @param head1
     * @param head2
     * @return
     */
    public static Node merge(Node head1, Node head2){
        if (head1 == null){
            return head2;
        }else if (head2 == null){
            return head1;
        }else {
            Node newhead = null;
            if (head1.getValue() > head2.getValue()){
                newhead = head2;
                head2 = head2.getNext();
            }else {
                newhead = head1;
                head1 = head1.getNext();
            }
            Node toEnd = newhead;
            while (head1 != null && head2 != null){
                if (head1.getValue() > head2.getValue()){
                    toEnd.setNext(head2);
                    head2 = head2.getNext();
                }else {
                    toEnd.setNext(head1);
                    head1 = head1.getNext();
                }
                toEnd = toEnd.getNext();
            }
            if (head1 == null){
                toEnd.setNext(head2);
            }else {
                toEnd.setNext(head1);
            }
            return newhead;
        }
    }

    public static Node merge2(Node head1, Node head2){
        if (head1 == null || head2 == null){
            return head1 == null ? head2 : head1;
        }
        Node newhead = head1.getValue() > head2.getValue() ? head2 : head1;
        Node cur1 = newhead == head1 ? head1.getNext() : head2.getNext();
        Node cur2 = newhead == head1 ? head2 : head1;
        Node pre = newhead;
        while (cur1 != null && cur2 != null){
            if (cur1.getValue() <= cur2.getValue()){
                pre = cur1;
                cur1 = cur1.getNext();
            }else {
                pre.setNext(cur2);
                cur2 = cur2.getNext();
                pre.getNext().setNext(cur1);
                pre = cur1;
                cur1 = cur1.getNext();
            }
        }
        if (cur1 == null){
            pre.setNext(cur2);
        }else {
            pre.setNext(cur1);
        }
        return newhead;
    }

    /**
     * 按照左右半区的方式从新组合单链表
     * @param head
     * @return
     */
    public static Node mergeLR(Node head){
        if (head == null || head.getNext() == null){
            return head;
        }
        Node mid = head;
        Node cur = head;
        while (cur != null && cur.getNext() != null){
            cur = cur.getNext().getNext();
            mid = mid.getNext();
        }
        Node pre = head;
        cur = head.getNext();
        Node toEnd = mid;
        while (cur != mid){
            pre.setNext(toEnd);
            toEnd = toEnd.getNext();
            pre.getNext().setNext(cur);
            pre = cur;
            cur = cur.getNext();
        }
        pre.setNext(toEnd);
        return head;
    }
}
