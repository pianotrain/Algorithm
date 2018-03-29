package node_list_tree;

import java.util.*;

/**
 * Created by rsmno on 2018/3/9.
 */
public class TreeUtils {

    public static BinaryNode initBTree(int[] arr){
        BinaryNode root = new BinaryNode(arr[0]);
        for (int i = 1; i < arr.length; i ++){
            BinaryNode.create(root, arr[i]);
        }
        return root;
    }
    //用一个栈，二叉树中序遍历
    public static void inOrderUnRecur(BinaryNode root){
        if (root != null){
            Stack<BinaryNode> stack = new Stack<>();
            while (!stack.isEmpty() || root != null){
                if (root != null){
                    stack.push(root);
                    root = root.getLeft();
                }else {
                    root = stack.pop();
                    System.out.println(root.getValue());
                    root = root.getRight();
                }
            }
        }
    }

    //两个栈，二叉树后序遍历
    public static void posOrderUnRecur(BinaryNode head){
        if (head != null){
            Stack<BinaryNode> stack1 = new Stack<>();
            Stack<BinaryNode> stack2 = new Stack<>();
            stack1.push(head);
            while (!stack1.isEmpty()){
                BinaryNode node = stack1.pop();
                if (node.getLeft() != null){
                    stack1.push(node.getLeft());
                }
                if (node.getRight() != null){
                    stack1.push(node.getRight());
                }
                stack2.push(node);
            }
            while (!stack2.isEmpty()){
                System.out.println(stack2.pop().getValue());
            }
        }
        System.out.println();
    }

    //一个栈，后序遍历
    public static void posOrderUnRecur2(BinaryNode head){
        if (head != null){
            BinaryNode cur = null;
            BinaryNode h = head;
            Stack<BinaryNode> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()){
                cur = stack.peek();
                if (cur.getLeft() != null && h != cur.getLeft() && h != cur.getRight()){
                    stack.push(cur.getLeft());
                }else if (cur.getRight() != null && h != cur.getRight()){
                    stack.push(cur.getRight());
                }else {
                    System.out.println(h.getValue());
                    h = cur;
                }
            }
            System.out.println();
        }
    }

    /**
     * 打印一个树的边界
     * 标准一： 1.头结点为边界；2.叶节点为边界；3。如果节点在其所在层是最优或最右，那么也是边界
     * @param head
     */
    public static void printEdge1(BinaryNode head){
        if (head == null){
            return;
        }
        //获得树高
        int h = getHight(head, 0);
        //获得最左边和最右边的节点
        BinaryNode[][] edgeMap = new BinaryNode[h][2];
        setEdgeMap(head , h ,edgeMap);
        //打印左边界
        for (int i = 0 ; i < edgeMap.length; i ++){
            System.out.println(edgeMap[i][0].getValue());
        }
        //打印既不是左边界也不是右边界的节点
        printLeafNotInMap(head,h, edgeMap);
        //打印右边界， 但不是左边界的节点
        for (int i = edgeMap.length - 1; i > -1; i --){
            if (edgeMap[i][1] != edgeMap[i][0]) {
                System.out.println(edgeMap[i][1].getRight());
            }
        }
    }

    //获得树高
    public static int getHight(BinaryNode head, int h ){
        if (head == null){
            return h;
        }
        return Math.max(getHight(head.getLeft(), h + 1), getHight(head.getRight(), h + 1));
    }

    public static void setEdgeMap(BinaryNode head, int h, BinaryNode[][] edgeMap){
        if (head == null){
            return;
        }
        edgeMap[h][0] = edgeMap[h][0] == null? head : edgeMap[h][0];
        edgeMap[h][1] = head;
        setEdgeMap(head.getLeft(), h + 1, edgeMap);
        setEdgeMap(head.getRight(), h + 1, edgeMap);
    }

    public static void printLeafNotInMap(BinaryNode head, int h, BinaryNode[][] map){
        if (head == null){
            return;
        }
        if (head.getLeft() == null && head.getRight() == null && head != map[h][0] & head != map[h][1]){
            System.out.println(head.getValue());
        }
        printLeafNotInMap(head.getLeft(), h + 1, map);
        printLeafNotInMap(head.getRight(), h + 1, map);
    }

    /**
     * 逆时针打印一个树的边界2
     * 标准二：1.头结点为边界；2.叶节点是边界；3.树左边延伸下去的节点为边界；4.树右边延伸下去的节点是边界
     * @param head
     */
    public static void printEdge2(BinaryNode head){
        if (head == null){
            return;
        }
        //首先找到第一个分叉点，如果头结点开始是棒状的则直接打印
        if (head.getRight() != null && head.getLeft() != null){
            //找到分叉点后打印左边树
            printLeft(head.getLeft(), true);
            //逆向打印右边
            printRight(head.getRight(), true);
        }else {
            System.out.println(head.getValue());
            printEdge2(head.getLeft() == null ? head.getRight() : head.getLeft());
        }
    }

    public static void printLeft(BinaryNode head, boolean print){
        if (head == null){
            return;
        }
        if (print || (head.getLeft() == null && head.getRight() == null)){
            System.out.println(head.getValue());
        }
        printLeft(head.getLeft() , print);
        printLeft(head.getRight(), (print && head.getLeft() == null )? true :false);
    }

    public static void printRight(BinaryNode head, boolean print){
        if (head == null){
            return;
        }
        printRight(head.getLeft(), print && head.getRight() == null);
        printRight(head.getRight(), print);
        if (print || (head.getLeft() == null && head.getRight() == null)){
            System.out.println(head.getValue());
        }
    }

    //如何较为直观的打印一个树
    public static void printTree(BinaryNode head){
        //H代表头，17是每个节点打印的长度， integer.min_value的长度为11
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    //把树逆时针旋转90度打印
    public static void printInOrder(BinaryNode head, int hight, String to, int len){
        if (head == null){
            return;
        }
        //打印右边
        printInOrder(head.getRight(), hight + 1, "v", len);
        String var = to + head.getValue() + to;
        int lenM = var.length();
        int lenL = (len - lenM) / 2; //左边空格数量
        int lenR = len - lenM - lenL; //右边空格数量
        var = getSpace(lenL) + var + getSpace(lenR);
        System.out.println(getSpace(hight * len) + var);
        //打印左边
        printInOrder(head.getLeft(), hight + 1, "^", len);
    }

    public static String getSpace(int len){
        String space = " ";
        StringBuffer sb = new StringBuffer();
        while (len -- > 0 ){
            sb.append(space);
        }
        return sb.toString();
    }

    //将二叉树先序序列化
    public static String serialByPre(BinaryNode head){
        if (head == null){
            return "#!";
        }
        String res = head.getValue() + "!";
        res += serialByPre(head.getLeft());
        res += serialByPre(head.getRight());
        return res;
    }

    //将先序序列化后的结果， 反序列化创建二叉树
    public static BinaryNode reconByPreString(String preStr){
        String[] split = preStr.split("!");
        Queue<String> queue = new LinkedList<>();
        for (int i = 0; i < split.length; i ++){
            queue.offer(split[i]);
        }
        return reconPreOrder(queue);
    }

    //先序创建二叉树， “#”代表null的节点
    public static BinaryNode reconPreOrder(Queue<String> queue){
        String value = queue.poll();
        if ("#".equals(value)){
            return null;
        }
        BinaryNode head = new BinaryNode(Integer.valueOf(value));
        head.setLeft( reconPreOrder(queue) );
        head.setRight( reconPreOrder(queue) );
        return head;
    }

    public static String serialByLevel(BinaryNode head){
        if (head == null){
            return "#!";
        }
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.offer(head);
        String res = head.getValue() + "!";
        while (!queue.isEmpty()){
            BinaryNode node = queue.poll();
            if (node.getLeft() != null){
                queue.offer(node.getLeft());
                res += (node.getLeft().getValue() + "!");
            }else {
                res += "#!";
            }
            if (node.getRight() != null){
                queue.offer(node.getRight());
                res += (node.getRight().getValue() + "!");
            }else {
                res += "#!";
            }
        }
        return res;
    }

    public static BinaryNode reconByLevel(String s){
        String[] split = s.split("!");
        int index = 0;
        BinaryNode head = generateBNode(split[index++]);
        if (head == null) return head;
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.offer(head);
        while (!queue.isEmpty()){
            BinaryNode poll = queue.poll();
            poll.setLeft(generateBNode(split[index++]));
            if (poll.getLeft()!= null){
                queue.offer(poll.getLeft());
            }
            poll.setRight(generateBNode(split[index ++]));
            if (poll.getRight() != null){
                queue.offer(poll.getRight());
            }
        }
        return head;
    }

    public static BinaryNode generateBNode(String value){
        if ("#".equals(value)){
            return null;
        }
        return new BinaryNode(Integer.valueOf(value));
    }

    /**
     * 二叉树神级遍历， 中序遍历
     * @param head
     */
    public static void morrisln(BinaryNode head){
        if (head == null) {
            return;
        }
        BinaryNode cur1 = head;
        BinaryNode cur2 = null;
        while (cur1 != null){
            cur2 = cur1.getLeft();//cur2 为 cur1左子树的头结点
            if (cur2 != null){
                //找到左子树的最右边的节点
                while (cur2.getRight() != null && cur2.getRight() != cur1){
                    cur2 = cur2.getRight();
                }
                //第一次找到的时候，将最右边的节点的右边的null指针指向cur1
                //然后将cur1指向做左子树的头节点，继续这个操作
                if (cur2.getRight() == null){
                    cur2.setRight(cur1);
                    cur1 = cur1.getLeft();
                    continue;//直到所有左子树完成操作，此时cur1为最左边的叶节点，左右孩子为null
                }else {
                    //第二次
                    //如果cur1跳到了头节点，将cur2的右指针恢复为null
                    cur2.setRight(null);
                }
            }
            //打印cur1的值，然后cur1跳到右子树或者跳到头节点
            //如果cur1跳到了头结点，然后cur2的右指针恢复为null，打印cur1的值，接下来cur1跳到右子树
            System.out.println(cur1.getValue());
            cur1 = cur1.getRight();
        }
        System.out.println();
    }

    /**
     * 神级遍历，先根遍历
     * 对中根遍历改写，打印时机放在对左子树的最右节点进行调整的时候
     * @param head
     */
    public static void mirrorsPre(BinaryNode head){
        if (head == null){
            return;
        }
        BinaryNode cur1 = head;
        BinaryNode cur2 = null;
        while (cur1 != null){
            cur2 = cur1.getLeft();
            if (cur2 != null){
                while (cur2.getRight() != null && cur2.getRight() != cur1){
                    cur2 = cur2.getRight();
                }
                if (cur2.getRight() == null){
                    cur2.setRight(cur1);
                    System.out.println(cur1.getValue());
                    cur1 = cur1.getLeft();
                    continue;
                }else {
                    cur2.setRight(null);
                }
            }else {
                //如果cur2为null，此时cur1是最左边的叶子
                System.out.println(cur1.getValue());
            }
            cur1 = cur1.getRight();
        }
        System.out.println();
    }

    /**
     * 二叉树神级遍历，后序
     * 调整更复杂，需要逆序操作后再反逆序
     * @param head
     */
    public static void morrisPos(BinaryNode head){
        if (head == null){
            return;
        }
        BinaryNode cur1 = head;
        BinaryNode cur2 = null;
        while (cur1 != null){
            cur2 = cur1.getLeft();
            if (cur2 != null){
                while (cur2.getRight() != null && cur2.getRight() != cur1){
                    cur2 = cur2.getRight();
                }
                if (cur2.getRight() == null){
                    cur2.setRight(cur1);
                    cur1 = cur1.getLeft();
                    continue;
                }else {
                    cur2.setRight(null);
                    printEdgeMorris(cur1.getLeft());
                }
            }
            cur1 = cur1.getRight();
        }
        printEdgeMorris(head);
    }

    public static void printEdgeMorris(BinaryNode head){
        BinaryNode tail = reverse(head);
        BinaryNode cur = tail;
        while (cur != null){
            System.out.println(cur.getValue());
            cur = cur.getRight();
        }
        reverse(tail);
    }

    public static BinaryNode reverse(BinaryNode from){
        BinaryNode pre = null;
        BinaryNode next = null;
        while (from != null){
            next = from.getRight();
            from.setRight(pre);
            pre = from;
            from = next;
        }
        return pre;
    }

    /**
     * 在二叉树中找到累加和为指定值的最长路径长度
     * @param head
     * @param sum
     * @return
     */
    public static int getMaxLengthOfSum(BinaryNode head, int sum){
        if (head == null){
            return 0;
        }
        Map<Integer, Integer> sumMap = new HashMap<>();
        sumMap.put(0, 0);//代表累加和为0的最短路径为0
        //类似数组中，求子数组的和为指定值的最大子数组长度
        return preOrder(head, 1, 0, sum, sumMap, 0);
    }

    /**
     *
     * @param head 当前子树头
     * @param level 当前节点层
     * @param preSum 头结点到上一节点的累加和
     * @param sum 指定值
     * @param sumMap
     * @param maxLen 最长路径值
     * @return
     */
    public static int preOrder(BinaryNode head, int level, int preSum, int sum, Map<Integer, Integer> sumMap,
                               int maxLen){
        if (head == null){
            return maxLen;
        }
        int curSum = head.getValue() + preSum;
        if (sumMap.containsKey(sum - curSum)){
            //累加和等于sum， 计算路径
            maxLen = Math.max(maxLen, level - sumMap.get(sum - curSum));
        }
        if (!sumMap.containsKey(curSum)){
            sumMap.put(curSum, level);
        }
        //遍历左子树和右子树
        preOrder(head.getLeft(), level + 1, curSum, sum, sumMap, maxLen);
        preOrder(head.getRight(), level + 1, curSum, sum, sumMap, maxLen);
        if (sumMap.get(curSum) == level){
            //遍历完后，要返回上级
            //如果累加和存在于map中， 并且value的值等于level，说明这个累加和是遍历到cur时加上去的，就要把这条记录删掉
            sumMap.remove(curSum);
        }
        return maxLen;
    }

    /**
     * 找到二叉树中的最大搜索二叉子树
     * @param head
     * @return
     */
    public static BinaryNode getBiggestSubBST(BinaryNode head){
        int[] record = new int[3];//记录子树的信息
        return posOrder(head, record);
    }

    public static BinaryNode posOrder(BinaryNode head, int[] record){
        if (head == null){
            record[0] = 0;//最大二叉子树的节点个数
            record[1] = Integer.MAX_VALUE;//子树的最小值
            record[2] = Integer.MIN_VALUE;//子树的最大值
            return null;
        }
        int value = head.getValue();
        BinaryNode left = head.getLeft();
        BinaryNode right = head.getRight();
        BinaryNode lBST = posOrder(left, record);//获得左子树的最大二叉子树的头节点与信息
        //左子树的信息
        int lSize = record[0];
        int lMin = record[1];
        int lMax = record[2];
        //获得右子树的最大二叉子树的头节点与信息
        BinaryNode rBST = posOrder(right, record);
        int rSize = record[0];
        int rMin = record[1];
        int rMax = record[2];
        record[1] = Math.min(value, lMin);//整个树的最小值
        record[2] = Math.max(value, rMax);//整个树的最大值
        //如果左子树的最大二叉子树的头结点为当前节点的左孩子，右子树的为右孩子
        //并且左边最大值小于当前节点的值，右边最小值大于当前节点值，则整个树就是搜索二叉树
        if (lBST == left && rBST == right && lMax < value && rMin > value){
            record[0] = lSize + rSize + 1;//最大二叉子树的节点个数
            return head;
        }
        //如果整个树不是搜索二叉树，返回左子树或右子树中最大的搜索二叉树
        record[0] = Math.max(lSize ,rSize);
        return lSize > rSize ? lBST: rBST;
    }

    //找到二叉树中符合搜索二叉树条件的最大拓补结构
    public static int bstTopoSize1(BinaryNode head){
        if (head == null){
            return 0;
        }
        int max = maxTopo(head, head);//以当前节点为头结点的最大拓补结构的节点数
        max = Math.max(max, bstTopoSize1(head.getLeft()));//左子树头结点的最大拓补结构的节点数
        max = Math.max(max, bstTopoSize1(head.getRight()));//右子树头结点的最大拓补结构的节点数
        return max;
    }

    private static int maxTopo(BinaryNode head, BinaryNode node){
        //考察node可不可以加入以head为头结点的搜索二叉树拓补结构
        if (head != null && node != null && isBSTNode(head, node, node.getValue())){
            //可以，则继续考察node的左右节点
            return maxTopo(head, node.getLeft()) + maxTopo(head, node.getRight()) + 1;
        }
        return 0;
    }

    //从head按搜索二叉树的方式向下找node， 如果找到了，则node可以加入以head为头结点的拓补结构
    private static boolean isBSTNode(BinaryNode head, BinaryNode node, int value){
        if (head == null){
            //没找到
            return false;
        }
        if (head == node){
            //找到了
            return true;
        }
        //继续向下找
        return isBSTNode(head.getValue() > value ? head.getLeft() : head.getRight(), node, value);
    }

    //获得最大搜索二叉树的拓补结构2
    //还没看懂， 用了后序
    public static int bstTopoSize2(BinaryNode head){
        Map<BinaryNode, Record> map = new HashMap<>();
        return posOrder(head, map);
    }

    public static class Record {
        public int l;
        public int r;

        public Record(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }
    private static int posOrder(BinaryNode head, Map<BinaryNode, Record> map){
        if (head == null){
            return 0;
        }
        int ls = posOrder(head.getLeft(), map);
        int rs = posOrder(head.getRight(), map);
        modifyMap(head.getLeft(), head.getValue(), map, true);
        modifyMap(head.getRight(), head.getValue(), map, false);
        Record lRecord = map.get(head.getLeft());
        Record rRecord = map.get(head.getRight());
        int lbst = lRecord == null? 0 :lRecord.l + lRecord.r + 1;
        int rbst = rRecord == null? 0 : rRecord.l + rRecord.r + 1;
        map.put(head, new Record(lbst, rbst));
        return Math.max(lbst + rbst + 1, Math.max(ls , rs));
    }

    private static int modifyMap(BinaryNode node, int value, Map<BinaryNode, Record> map, boolean s){
        if (node == null || !map.containsKey(node)){
            return 0;
        }
        Record r = map.get(node);
        if ((s && node.getValue() >value ) || ((!s) && node.getValue() <value ) ){
            map.remove(node);
            return r.r + r.l + 1;
        }else {
            int minus = modifyMap(s ? node.getRight() : node.getLeft(), value, map, s);
            if (s){
                r.r = r.r - minus;
            }else {
                r.l = r.l - minus;
            }
            map.put(node, r);
            return minus;
        }
    }

    //层级打印， 并打印第几层，需要记录是否到了本层的结尾在打印\n
    //当当前层的最后节点打印出来的时候， 正好是下一层的最后节点进入队列的时候
    public static void printByLevel(BinaryNode head){
        if (head == null){
            return;
        }
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.offer(head);
        BinaryNode last = head;//当前层的最后节点
        BinaryNode nLast = null;//下一层的最后节点
        int level = 1;
        System.out.print("level " + level++ + " :");
        while (!queue.isEmpty()){
            BinaryNode cur = queue.poll();
            System.out.print(cur.getValue() + " ");
            if (cur.getLeft() != null){
                queue.offer(cur.getLeft());
                nLast = cur.getLeft();
            }
            if (cur.getRight() != null){
                queue.offer(cur.getRight());
                nLast = cur.getRight();
            }
            if (cur == last && !queue.isEmpty()){
                //当前打印的是本层末尾
                System.out.println();
                System.out.print("level " + level ++ + " :");
                //层末尾改为下一层末尾
                last = nLast;
            }
        }
        System.out.println();
    }

    //Z字形层级打印， 并打印第几层
    //用双端队列，如果是从左到右的过程，一律从头部弹出， 并且进入队列的方式按先左后右从尾部进入
    //如果是从右到左的过程， 一律从尾部弹出，并且进入队列的方式按先右后左从头部进入
    //打印\n的时机，是cur == last的时候，打印完后last = nlast
    // nlast是下一层打印\n的时机， 在打印完当前层的\n后， 之后第一个进入队列的就是nlast该指向的时机节点
    public static void printByZigzag(BinaryNode head){
        if (head == null){
            return;
        }
        //双端队列
        Deque<BinaryNode> dq = new LinkedList<>();
        dq.addLast(head);
        BinaryNode last = head;
        BinaryNode nLast = null;
        boolean flag = true;//决定打印的方向
        int level = 1;
        BinaryNode cur = null;
        printLevelAndOrientation(level ++ , flag);
        while (!dq.isEmpty()){
            //从左到右打印
            if (flag){
                //从头部弹出， 并按先左后右的方式，从尾部进入队列
                cur = dq.pollFirst();
                if (cur.getLeft() != null){
                    dq.offerLast(cur.getLeft());
                    //如果nlast == null表示下一层的第一个进入队列的节点，要记录下来
                    //否则， 不允许覆盖nlast
                    nLast = nLast == null ? cur.getLeft() : nLast;
                }
                if (cur.getRight() != null){
                    dq.offerLast(cur.getRight());
                    nLast = nLast == null? cur.getRight() : nLast;
                }
            }else {
                //从尾部弹出，并按先右后左的方式从头部进入队列
                cur = dq.pollLast();
                if (cur.getRight() != null){
                    dq.offerFirst(cur.getRight());
                    nLast = nLast == null ? cur.getRight() : nLast;
                }
                if (cur.getLeft() != null){
                    dq.offerFirst(cur.getLeft());
                    nLast = nLast == null ? cur.getLeft() : nLast;
                }
            }
            System.out.print(" " + cur.getValue());
            if (last == cur && !dq.isEmpty()){
                last = nLast;
                nLast = null;//重要，清空后表示nlast可以再次覆盖
                flag = !flag;
                System.out.println();
                printLevelAndOrientation(level ++, flag);
            }
        }
        System.out.println();
    }

    public static void printLevelAndOrientation(int level, boolean lr){
        System.out.print("Level " + level + " from");
        System.out.print(lr ? " left to right : " : " right to left : ");
    }

    /**
     * 判断二叉树是否为平衡二叉树， 后序遍历，左子树是否平衡，右子树是否平衡，否会立即返回false
     * 平衡二叉树：要么是空树，要么任何一个节点的左右子树的高度差的绝对值不超过1
     * @param head
     * @return
     */
    public static boolean isBalance(BinaryNode head){
        //res相当于一个全局变量
        boolean[] res = new boolean[1];
        res[0] = true;
        getHight(head, 1, res);
        return res[0];
    }

    //后序遍历获取高度，并判断左右子树高度差是否大于1
    public static int getHight(BinaryNode head, int level, boolean[] res){
        if (head == null){
            return level;
        }
        int leftH = getHight(head.getLeft(), level + 1, res);
        if ( !res[0]){
            return level;
        }
        int rightH = getHight(head.getRight(), level + 1, res);
        if (! res[0]){
            return level;
        }
        if (Math.abs(leftH - rightH) > 1){
            res[0] = false;
        }
        return Math.max(leftH, rightH);
    }

    /**
     * array没有重复值， 判断array是否可能是节点值为整形的搜索二叉树后序遍历的结果
     * 进阶：并通过array重建搜索二叉树
     * 如果是二叉树后序遍历的结果，那么头节点的值一定是最后一个元素，且数组的前一部分都小于头节点，为左子树
     * 后一部分到最后一个值之间都大于头节点，为右子树， 递归判断是否满足这个条件即可
     * @param array
     * @return
     */
    public static boolean isPostArray(int[] array){
        if (array == null || array.length < 1){
            return false;
        }
        return isPost(array, 0, array.length - 1);
    }

    public static boolean isPost(int[] arr, int start, int end){
        if (start == end){
            return true;
        }
        int lessEnd = -1;
        int moreStart = end;
        for (int i = start; i < end; i ++){
            if (arr[end] > arr[i]){
                lessEnd = i;
            }else {
                moreStart = moreStart == end? i : moreStart;
            }
        }
        if (lessEnd == -1 || moreStart == end){
            return isPost(arr, start, end - 1);
        }
        if (lessEnd != moreStart - 1){
            return false;
        }
        return (isPost(arr, start, lessEnd) && isPost(arr, moreStart, end - 1));
    }

    /**
     * 根据后序数组重建搜索二叉树
     * @param posArr
     * @return
     */
    public static BinaryNode posArrayToBST(int[] posArr){
        if (posArr == null){
            return null;
        }
        return posToBST(posArr, 0, posArr.length - 1);
    }

    public static BinaryNode posToBST(int[] arr, int start, int end){
        if (start > end){
            return  null;
        }
        BinaryNode node = new BinaryNode(arr[end]);
        int lessEnd = -1;
        int moreStart = end;
        for (int i = start; i < end; i ++){
            if (arr[i] < arr[end]){
                lessEnd = i;
            }else {
                moreStart = moreStart == end? i : moreStart;
            }
        }
        node.setLeft(posToBST(arr, start, lessEnd));
        node.setRight(posToBST(arr, moreStart, end - 1));
        return node;
    }

    /**
     * 判断一棵树是否是搜索二叉树，morris中序遍历，判断节点是否一直升序
     * *注意* 当发现节点降序时，不能立即返回false，这么做会跳过恢复阶段，从而破坏二叉树结构
     * @param head
     * @return
     */
    public static boolean isBST(BinaryNode head){
        if (head == null){
            return true;
        }
        BinaryNode pre = null;
        BinaryNode cur1 = head;
        BinaryNode cur2 = null;
        boolean isBST = true;
        while (cur1 != null){
            cur2 = cur1.getLeft();
            if (cur2 != null) {
                while (cur2.getRight() != null && cur2.getRight() != cur1) {
                    cur2 = cur2.getRight();
                }
                if (cur2.getRight() == null) {
                    cur2.setRight(cur1);
                    cur1 = cur1.getLeft();
                    continue;
                } else {
                    cur2.setRight(null);
                }
            }
            if (pre != null && pre.getValue() > cur1.getValue()){
                isBST = false;
            }
            pre = cur1;
            cur1 = cur1.getRight();
        }
        return isBST;
    }

    /**
     * 判断一颗二叉树是否是完全二叉树
     * 解：按以下标准判断会比较容易
     *  1.按层遍历，从每层的左边到右边依次遍历
     *  2.对每个节点，如果左孩子为空，而右孩子不为空，则直接返回false
     *  3.如果当前节点并不是左右孩子都有， 则接下来的节点都必须是叶节点，否则返回false
     *  4.遍历过程中如果没返回false， 最后返回true
     */
    public static boolean isCBT(BinaryNode head){
        if (head == null){
            return true;
        }
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.offer(head);
        boolean leaf = false;
        BinaryNode left = null;
        BinaryNode right = null;
        while (!queue.isEmpty()){
            head = queue.poll();
            left = head.getLeft();
            right = head.getRight();
            if ( (leaf && (left != null || right != null) ) || (left == null && right != null)){
                return false;
            }
            //层遍历
            if (left != null){
                queue.offer(left);
            }
            if (right != null){
                queue.offer(right);
            }else {
                leaf = true;
            }
        }
        return true;
    }

    public static BinaryNode generateTreeBySortArr(int[] sortArr){
        if (sortArr == null){
            return null;
        }
        return genetate(sortArr, 0, sortArr.length - 1);
    }

    private static BinaryNode genetate(int[] arr, int start, int end){
        if (start > end){
            return null;
        }
        int mid = (end + start) / 2;
        int lessEnd = mid - 1;
        int moreStart = mid + 1;
        BinaryNode node = new BinaryNode(arr[mid]);
        node.setLeft(genetate(arr, start, lessEnd));
        node.setRight(genetate(arr, moreStart, end));
        return node;
    }

    /**
     * 一种新的二叉树节点，多了一个指向父节点的指针，如果是头结点，则父节点为null
     * 问题：在二叉树中找到一个节点的后继节点（在中序遍历中，node的下一个节点就是node的后继节点）
     * @param node
     * @return
     */
    public static NewBNode getNextNode(NewBNode node){
        if (node == null){
            return null;
        }
        //如果有右孩子，那么后继节点一定是右子树中最左的节点
        if (node.getRight() != null){
            node = node.getRight();
            while (node.getLeft() != null){
                node = node.getLeft();
            }
            return node;
        }
        //没有右孩子，一直向上找，当当前节点为父节点的左孩子时，父节点为后继节点
        NewBNode parent = node.getParent();
        while (parent != null && parent.getLeft() != node){
            node = parent;
            parent = node.getParent();
        }
        return parent;
    }
}
