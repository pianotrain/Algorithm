package bTree;

import java.util.*;

/**
 * 二叉树相关的算法
 * create by renshengmiao on 2018/3/9 .
 */
public class TreeUtils {

    //将数组转化为平衡二叉树
    public static BinaryTree initBTree(int[] arr){
        BinaryTree root = new BinaryTree(arr[0]);
        for (int i = 1; i < arr.length; i ++){
            BinaryTree.createBinaryTree(root, arr[i]);
        }
        return root;
    }

    //先序打印 , 根,左,右
    public static void preOrderRecur(BinaryTree head){
        if (head == null){
            return;
        }
        System.out.println(head.getData());
        preOrderRecur(head.getLeft());
        preOrderRecur(head.getRight());
    }

    //不用递归方法, 先序遍历
    public static void preOrderUnRecur(BinaryTree head){
        if (head == null){
            return;
        }
        Stack<BinaryTree> stack = new Stack<>();
        stack.push(head);
        BinaryTree left = head.getLeft();
        BinaryTree right = head.getRight();
        while (!stack.isEmpty()){
            BinaryTree cur = stack.pop();
            System.out.println(cur.getData());
            //右孩子先进栈,左孩子后进栈
            //这样出栈就是左孩子先, 右孩子后
            if (cur.getRight() != null) {
                stack.push(cur.getRight());
            }
            if (cur.getLeft() != null) {
                stack.push(cur.getLeft());
            }
        }
        System.out.println();
    }

    //中序打印, 左, 根, 右,  打印结果升序
    public static void inOrderRecur(BinaryTree head){
        if (head == null){
            return;
        }
        inOrderRecur(head.getLeft());
        System.out.println(head.getData());
        inOrderRecur(head.getRight());
    }

    //不用递归中序遍历
    public static void inOrderUnRecur(BinaryTree head){
        if (head != null){
            Stack<BinaryTree> stack = new Stack<>();
            BinaryTree cur = head.getLeft();
            while (! stack.isEmpty() || head != null){
                if (head != null){
                    stack.push(head);
                    head = head.getLeft();
                }else {
                    head = stack.pop();
                    System.out.println(head.getData());
                    head = head.getRight();
                }
            }
        }
        System.out.println();
    }
    //后序, 左,右,根
    public static void posOrderRecur(BinaryTree head){
        if (head == null){
            return;
        }
        posOrderRecur(head.getLeft());
        posOrderRecur(head.getRight());
        System.out.println(head.getData());
    }

    /**
     * 问题: 一颗二叉树原本是搜索二叉树,但其中有两个节点调换了位置,使得这个二叉树不在是搜索二叉树,找出这两个节点并返回
     * 解:用中序遍历,出现的正常节点会一直升序, 如果有两个节点错了,就一定会降序
     * 如果在中序遍历中出现了两次降序, 第一个错误的节点为第一次降序时较大的那个,第二个错误节点为第二次降序时较小的那个
     * 如果在中序遍历中只出现一次降序, 第一个错误的节点为较大的那个, 第二个错误节点为较小那个
     * 总结:第一个错误节点为第一次降序时较大的一个, 第二个错误节点为最后一次降序时较小那个
     * @param head
     * @return
     */
    public static BinaryTree[] getTwoErrorNodes(BinaryTree head){
        BinaryTree[] errs = new BinaryTree[2];
        if (head == null){
            return errs;
        }
        Stack<BinaryTree> stack = new Stack<>();
        BinaryTree pre = null;
        while (! stack.isEmpty() || head != null){
            if (head != null){
                stack.push(head);
                head = head.getLeft();
            }else {
                head = stack.pop();
                if (pre != null && pre.getData() > head.getData()){
                    errs[0] = errs[0] == null? pre : errs[0];
                    errs[1] = head;
                }
                pre = head;
                //左子树处理完,按同样方式处理右子树
                head = head.getRight();
            }
        }
        return errs;
    }

    /**
     * 进阶问题:找到两个错误节点, 在不修改节点值的要求下, 恢复搜索二叉树
     * 解:1. 需要找到两个错误节点的父节点, 然后互换错误节点所属的环境,但是需要考虑很多情况
     * 问题一:e1,e2是否有一个是头节点? 如果有, 谁是tou
     * 问题二:e1,e2是否相连?如果是, 谁是谁的父节点
     * 问题三:e1,e2分别是各自父节点的左孩子还是右孩子
     * *特别注意*:因为是在中序遍历时,先找到e1,再找到e2,所以e1一定不是e2的右孩子, e2一定不是e1的左孩子
     * 针对以上问题分为14种情况
     * 1.e1是头,e1是e2的父,此时e2只可能是e1的右孩子
     * ...
     * @param head
     * @return
     */
    public static BinaryTree recoverTree(BinaryTree head){
        if (head == null){
            return null;
        }
        BinaryTree[] errorNodes = getTwoErrorNodes(head);
        BinaryTree[] errParentNodes = getTwoErrParentNodes(head, errorNodes[0], errorNodes[1]);
        BinaryTree e1 = errorNodes[0];
        BinaryTree e1p = errParentNodes[0] ;
        BinaryTree e1l = e1.getLeft();
        BinaryTree e1r = e1.getRight();
        BinaryTree e2 = errorNodes[1];
        BinaryTree e2p = errParentNodes[1];
        BinaryTree e2l = e2.getLeft();
        BinaryTree e2r = e2.getRight();
        if (e1 == head){
            if (e1.getRight() == e2){  //情况1
                e1.setLeft(e2l);
                e1.setRight(e2r);
                e2.setLeft(e1l);
                e2.setRight(e1);
            }else if (e2 == e2p.getLeft()){
                e2.setRight(e1r);
                e2.setLeft(e1l);
                e2p.setLeft(e1);
                e1.setLeft(e2l);
                e1.setRight(e2r);
            }else {
                e2.setRight(e1r);
                e2.setLeft(e1l);
                e2p.setRight(e1);
                e1.setLeft(e2l);
                e1.setRight(e2r);
            }
            head = e1;
        }else if (e2 == head){
            if (e2.getLeft() == e1){
                e2.setRight(e1r);
                e2.setLeft(e1l);
                e1.setLeft(e2);
                e1.setRight(e2r);
            }else if (e1 == e1p.getLeft()){
                e2.setRight(e1r);
                e2.setLeft(e1l);
                e1p.setLeft(e2);
                e1.setLeft(e2l);
                e1.setRight(e2r);
            }else {
                e2.setRight(e1r);
                e2.setLeft(e1l);
                e1p.setRight(e2);
                e1.setLeft(e2l);
                e1.setRight(e2r);
            }
            head = e2;
        }else {
            if (e1 == e2p){
                if (e1p.getLeft() == e1){
                    e1p.setLeft(e2);
                    e1.setLeft(e2l);
                    e1.setRight(e2r);
                    e2.setLeft(e1l);
                    e2.setRight(e1r);
                }else {
                    e1p.setRight(e2);
                    e1.setLeft(e2l);
                    e1.setRight(e2r);
                    e2.setLeft(e1l);
                    e2.setRight(e1r);
                }
            }else if (e2 == e1p){
                if (e2 == e2p.getLeft()){
                    e2p.setLeft(e1);
                    e2.setRight(e1r);
                    e2.setLeft(e1l);
                    e1.setLeft(e2l);
                    e1.setRight(e2r);
                }else {
                    e2p.setRight(e1);
                    e2.setRight(e1r);
                    e2.setLeft(e1l);
                    e1.setLeft(e2l);
                    e1.setRight(e2r);
                }
            }else {
                if (e1 == e1p.getLeft()){
                    if (e2 == e2p.getLeft()){
                        e1p.setLeft(e2);
                        e1.setRight(e2r);
                        e1.setLeft(e2l);
                        e2p.setLeft(e1);
                        e2.setLeft(e1l);
                        e2.setRight(e1r);
                    }else {
                        e1p.setLeft(e2);
                        e1.setRight(e2r);
                        e1.setLeft(e2l);
                        e2p.setRight(e1);
                        e2.setLeft(e1l);
                        e2.setRight(e1r);
                    }
                }else {
                    if (e2 == e2p.getLeft()){
                        e1p.setRight(e2);
                        e1.setRight(e2r);
                        e1.setLeft(e2l);
                        e2p.setLeft(e1);
                        e2.setLeft(e1l);
                        e2.setRight(e1r);
                    }else {
                        e1p.setRight(e2);
                        e1.setRight(e2r);
                        e1.setLeft(e2l);
                        e2p.setRight(e1);
                        e2.setLeft(e1l);
                        e2.setRight(e1r);
                    }
                }
            }
        }
        return head;
    }

    //获得两个错误节点的父节点
    public static BinaryTree[] getTwoErrParentNodes(BinaryTree head, BinaryTree err1, BinaryTree err2){
        BinaryTree[] parents = new BinaryTree[2];
        if (head == null){
            return parents;
        }
        Stack<BinaryTree> stack = new Stack<>();
        while (!stack.isEmpty() || head != null){
            if (head != null){
                stack.push(head);
                head = head.getLeft();
            }else {
                head = stack.pop();
                if (head.getLeft() == err1 || head.getRight() == err1){
                    parents[0] = head;
                }
                if (head.getLeft() == err2 || head.getRight() == err2){
                    parents[1] = head;
                }
                head = head.getRight();
            }
        }
        return parents;
    }

    /**
     * 判断head1树是否包含head2树全部的拓补结构,如下返回true
     * 这个方法要求树结构不能有重复值
     * 先序遍历, 先找到h1中和h2头的值相等的位置, 然后同时遍历两个节点下的节点
     *             1
     *          |--^--|
     *          2     3                     2
     *      |---^--| |-^----|           |---^--|
     *      4     5  6     7            4     5
     *  |---^-| |-^--|              |---^-|
     *  8    9  10                  8
     * @param head1
     * @param head2
     * @return
     */
    public static boolean checkContains(BinaryTree head1, BinaryTree head2){
        if (head1 == null && head2 == null){
            return true;
        }else if (head1 != null && head2 != null){
            BinaryTree cur1 = head1;
            Stack<BinaryTree> stack = new Stack<>();
            stack.push(head1);
            while (!stack.isEmpty() && cur1.getData() != head2.getData()){
                cur1 = stack.pop();
                if (cur1.getLeft() != null){
                    stack.push(cur1.getLeft());
                }
                if (cur1.getRight() != null){
                    stack.push(cur1.getRight());
                }
            }
            if (cur1.getData() != head2.getData()){
                return false;
            }
            return checkRecur(cur1, head2);
        }else {
            return false;
        }
    }

    //判断head1树是否包含head2树全部的拓补结构,递归方法
    //这个方法较好, 如果树有重复值也会正确
    public static boolean checkContains2(BinaryTree head1, BinaryTree head2){
        return checkRecur(head1, head2) || checkContains2(head1.getLeft(), head2) || checkContains2(head1.getRight(), head2);
    }

    private static boolean checkRecur(BinaryTree node1, BinaryTree node2){
        if (node2 == null){
            return true;
        }
        if (node1 == null){
            return false;
        }
        if (node1.getData() == node2.getData()){
            return checkRecur(node1.getLeft(), node2.getLeft()) && checkRecur(node1.getRight(), node2.getRight());
        }else {
            return false;
        }
    }

    /**
     * 字符串匹配算法, kmp算法 T:O(N+M), 而暴力算法要O(N*M)
     * @param str
     * @param match
     * @return
     */
    public static int getIndexOfByKMP(String str, String match){
        if (str == null || match == null || match.length() < 1 || match.length() > str.length()){
            return -1;
        }
        char[] strs = str.toCharArray();
        char[] matches = match.toCharArray();
        int si = 0;
        int mi = 0;
        int[] nextArr = getNextArr(matches);
        while (si < strs.length && mi < matches.length){
            if (strs[si] == matches[mi]){
                si ++;
                mi ++;
            }else if (nextArr[mi] == -1){
                si ++;
            }else {
                mi = nextArr[mi];
            }
            //当 mi == matches.length的时候就是第一次匹配到,结束循环
            //如果要打印多次匹配到的下标值,则解除以下的注释
//            if (mi == matches.length){
//                System.out.println(si - mi);
//             // si,mi都向前回溯一位
//                si --;
//                mi = nextArr[mi - 1];
//            }
        }
        return mi == matches.length ? si - mi : -1;
    }


    /**
     * 获得kmp算法的预处理移位数组next
     * next移位数组的0下标位是-1, 代表""子串没有匹配
     * 1下标位值是0, 代表"a"子串, 最大相等前缀和后缀长度为0(定义不包括第一个字符)
     * 2下标位值是0, 代表"ab"子串, 最大相等前缀和后缀长度为0
     * 5下标位值时1, 代表"abcaa"子串, .....为1
     * 6下标位值时2, 代表"abcaab"子串,......为2
     * 9下标位值是5, 代表"abcaabcaa"子串, .....为5
     * @param ms "abcaabcaaaaabcababababc"
     * @return -1 0 0 0 1 1 2 3 4 5 1 1 1 2 3 4 2 1 2 1 2 1 2
     */
    private static int[] getNextArr(char[] ms){
        if (ms.length == 1){
            return new int[]{-1};
        }
        int[] next = new int[ms.length];
        next[0] = -1;
        next[1] = 0;
        int pos = 2;
        int cn = 0;
        while (pos < ms.length){
            if (ms[pos - 1] == ms[cn]){
                next[pos ++] = ++ cn;
            }else if (cn > 0){
                cn = next[cn];
            }else {
                next[pos ++] = 0;
            }
        }
        return next;
    }

    /**
     * 在二叉树中找到两个节点的最近公共祖先, 后序遍历
     * @param head
     * @param node1
     * @param node2
     * @return
     */
    public static BinaryTree getLowestAncestor(BinaryTree head, BinaryTree node1, BinaryTree node2){
        if (head == null || head == node1 || head == node2){
            //找到节点,或找不到节点, 直接返回
            return head;
        }
        //在左右子树中找节点
        BinaryTree left = getLowestAncestor(head.getLeft(), node1, node2);
        BinaryTree right = getLowestAncestor(head.getRight(), node1, node2);
        if (left != null && right != null){
            //两个都找到,当前就是最近公共祖先,返回
            return head;
        }
        //都没找到返回null
        //有一个找到, 返回找到的那个节点
        return left == null ? right : left;
    }

    //根据有序数组创建平衡二叉树
    public static BinaryTree createBalanceTreeBySortArr(int[] arr){
        if (arr == null){
            return null;
        }
        return createBalanceTreeBySortArr(arr, 0, arr.length - 1);
    }

    private static BinaryTree createBalanceTreeBySortArr(int[] arr, int start, int end){
        if (start > end){
            return  null;
        }
        int mid = (start + end) / 2;
        BinaryTree node = new BinaryTree(arr[mid]);
        node.setLeft(createBalanceTreeBySortArr(arr, start , mid - 1));
        node.setRight(createBalanceTreeBySortArr(arr, mid + 1, end));
        return node;
    }

    public static void printTree(BinaryTree head){
        if (head != null){
            printTree(head, "H", 17, 0);
        }
        System.out.println();
    }

    private static void printTree(BinaryTree head, String to, int spaceLen, int level){
        if (head == null){
            return;
        }
        printTree(head.getRight(), "v", spaceLen, level + 1);
        String value = to + head.getData() + to;
        int L = ( spaceLen - value.length()) / 2;
        int R = spaceLen - value.length() - L;
        value = getSpace(L) + value + getSpace(R);
        System.out.println(getSpace(level * spaceLen ) + value);
        printTree(head.getLeft(), "^", spaceLen, level + 1);
    }

    private static String getSpace(int len){
        if (len < 1){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while (len -- > 0){
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 进阶:在二叉树中找到两个节点的最近公共祖先, 查询操作频繁, 想办法让查询时间减少
     * 利用一个hash表, 存入每个节点的父节点,建立查询表T:O(N), S:O(N), 每次查询:T:O(h)
     */
    public class Record1{
        private Map<BinaryTree, BinaryTree> map;

        public Record1(BinaryTree head) {
            this.map = new HashMap<>();
            if (head != null){
                map.put(head, null);
            }

        }

        private void setMap(BinaryTree head){
            if (head == null){
                return;
            }
            if (head.getLeft() != null) {
                this.map.put(head.getLeft(), head);
            }
            if (head.getRight() != null) {
                this.map.put(head.getRight(), head);
            }
            setMap(head.getLeft());
            setMap(head.getRight());
        }

        public BinaryTree query(BinaryTree node1, BinaryTree node2){
            Set<BinaryTree> path = new HashSet<>();
            while (node1 != null){
                path.add(node1);
                node1 = map.get(node1);
            }
            while (!path.contains(node2)){
                node2 = map.get(node2);
            }
            return node2;
        }
    }

    //建表T:O(N^2) ,S:O(N^2),  查询:O(1)
    //建立每两个节点的最近公共祖先表
    public class Record2{
        private Map<BinaryTree, Map<BinaryTree, BinaryTree>> map;

        public Record2(BinaryTree head){
            map = new HashMap<>();
            initMap(head);
            setMap(head);
        }

        private void initMap(BinaryTree head){
            if (head == null){
                return;
            }
            map.put(head, new HashMap<>());
            initMap(head.getLeft());
            initMap(head.getRight());
        }

        private void setMap(BinaryTree head){
            if (head == null){
                return;
            }
            //map.get(node).set(head,head), 所有节点与head的公共祖先都是head
            headRecord(head.getRight(), head);
            headRecord(head.getLeft(), head);
            //左子树的一个节点,右子树的另一个节点, 这两个节点的公共祖先都是head
            subRecord(head);
            //递归
            setMap(head.getLeft());
            setMap(head.getRight());
        }

        private void headRecord(BinaryTree node, BinaryTree head){
            if (node == null){
                return;
            }
            map.get(node).put(head, head);
            headRecord(node.getLeft(), head);
            headRecord(node.getRight(), head);
        }

        private void subRecord(BinaryTree head){
            if (head == null){
                return;
            }
            preLeft(head.getLeft(), head.getRight(), head);
            subRecord(head.getRight());
            subRecord(head.getLeft());
        }

        private void preLeft(BinaryTree left, BinaryTree right, BinaryTree head){
            if (left == null){
                return;
            }
            preRight(left, right, head);
            preLeft(left.getLeft(), right, head);
            preLeft(left.getRight(), right, head);
        }

        private void preRight(BinaryTree left, BinaryTree right, BinaryTree head){
            if (right == null){
                return;
            }
            map.get(left).put(right, head);
            preRight(left, right.getLeft(), head);
            preRight(left, right.getRight(), head);
        }

        public BinaryTree query(BinaryTree node1, BinaryTree node2){
            if (node1 == node2){
                return node1;
            }
            if (map.containsKey(node1)){
                return map.get(node1).get(node2);
            }
            if (map.containsKey(node2)){
                return map.get(node2).get(node1);
            }
            return null;
        }
    }

    //并查集
    public class DisJointSets{
        private Map<BinaryTree, BinaryTree> fatherMap;//father表, key是节点, value是key所属的集合的最上层节点father
        private Map<BinaryTree, Integer> rankMap;  //秩表, key是节点,value是粗略代表key节点以下的深度

        public DisJointSets() {
            fatherMap = new HashMap<>();
            rankMap = new HashMap<>();
        }

        //根据head初始化并查集, 先序遍历,
        public void makeSets(BinaryTree head){
            fatherMap.clear();
            rankMap.clear();
            preOrderMake(head);
        }

        private void preOrderMake(BinaryTree head){
            if (head == null){
                return;
            }
            fatherMap.put(head, head);//每个node首先的father都是自己
            rankMap.put(head, 0);     //每个节点首先的rank秩都是0
            preOrderMake(head.getLeft());
            preOrderMake(head.getRight());
        }

        //找到node的最上层father,
        //路径压缩:  如果有多层, 把查找路径中的所有节点的father都指向这个最终的father
        public BinaryTree findFather(BinaryTree node){
            BinaryTree father = fatherMap.get(node);
            if (father != node){
                father = findFather(father);
            }
            fatherMap.put(node, father);
            return father;
        }

        //合并两个并查集,a所属的集合, 与b所属的集合根据最上层的father的rank值合并
        public void union(BinaryTree a, BinaryTree b){
            if (a == null || b == null){
                return;
            }
            BinaryTree aFather = fatherMap.get(a);
            BinaryTree bFather = fatherMap.get(b);
            //如果相等,说明a,b已经在一个并查集中
            if (aFather != bFather){
                int aFRank = rankMap.get(aFather);
                int bFRank = rankMap.get(bFather);
                //bfather的rank较大, 把a所有的元素的father改为bfather
                if (aFRank < bFRank){
                    fatherMap.put(aFather, bFather);
                }else if (aFRank > bFRank){
                    fatherMap.put(bFather, aFather);
                }else {
                    //rank相等,a或b都可以做father, 并使做father的rank加1
                    fatherMap.put(bFather, aFather);
                    rankMap.put(aFather, aFRank + 1);
                }
            }
        }
    }

    //tarJan查询主方法
    //塔尔杨
    public BinaryTree[] tarJanQuery(BinaryTree head, Query[] queries){
        BinaryTree[] ans = new TarJan().query(head, queries);
        return ans;
    }
    //tarjan类实现并查集的处理流程
    public class TarJan{
        //key表示查询涉及的某个节点,value表示key与哪些节点之间有查询任务
        private Map<BinaryTree, LinkedList<BinaryTree>> queryMap;
        //key表示查询涉及的某个节点,value表示如果依次解决有关key节点的每个问题, 该把答案放在ans的哪个位置
        private Map<BinaryTree, LinkedList<Integer>> indexMap;
        private Map<BinaryTree, BinaryTree> ancestorMap;
        private DisJointSets jointSets; //并查集

        public TarJan(){
            queryMap = new HashMap<>();
            indexMap = new HashMap<>();
            ancestorMap = new HashMap<>();
            jointSets = new DisJointSets();
        }

        public BinaryTree[] query(BinaryTree head, Query[] queries){
            BinaryTree[] ans = new BinaryTree[queries.length];
            setQueryMap(queries, ans);
            jointSets.makeSets(head);
            setAnswers(head, ans);
            return ans;
        }

        private void setQueryMap(Query[] queries, BinaryTree[] ans){
            BinaryTree node1 = null;
            BinaryTree node2 = null;
            for (int i = 0; i < queries.length ; i++){
                node1 = queries[i].node1;
                node2 = queries[i].node2;
                if (node1 == node2 || node1 == null || node2 == null){
                    ans[i] = node1 == null? node2 : node1;
                }else {
                    if (!queryMap.containsKey(node1)){
                        queryMap.put(node1, new LinkedList<>());
                        indexMap.put(node1, new LinkedList<>());
                    }
                    if (!queryMap.containsKey(node2)){
                        queryMap.put(node2, new LinkedList<>());
                        indexMap.put(node2, new LinkedList<>());
                    }
                    queryMap.get(node1).addLast(node2);
                    indexMap.get(node1).addLast(i);
                    queryMap.get(node2).addLast(node1);
                    indexMap.get(node2).addLast(i);
                }
            }
        }

        private void setAnswers(BinaryTree head, BinaryTree[] ans){
            if (head == null){
                return;
            }
            setAnswers(head.getLeft(), ans);
            jointSets.union(head.getLeft(), head);
            ancestorMap.put(jointSets.findFather(head), head);
            setAnswers(head.getRight(), ans);
            jointSets.union(head.getRight(), head);
            ancestorMap.put(jointSets.findFather(head),head);
            LinkedList<BinaryTree> btList = queryMap.get(head);
            LinkedList<Integer> iList = indexMap.get(head);
            BinaryTree node = null;
            BinaryTree father = null;
            int index = 0;
            while (btList != null && !btList.isEmpty()){
                node = btList.poll();
                index = iList.poll();
                father = jointSets.findFather(node);
                if (ancestorMap.containsKey(father)){
                    ans[index] = ancestorMap.get(father);
                }
            }
        }
    }

    public class Query{
        public BinaryTree node1;
        public BinaryTree node2;

        public Query(BinaryTree node1, BinaryTree node2) {
            this.node1 = node1;
            this.node2 = node2;
        }
    }

    /**
     * 二叉树节点间的最大距离问题
     * 以head为头的树上, 最大距离只可能来自以下三种情况
     * 1.head的左子树上的最大距离
     * 2.head的右子树上的最大距离
     * 3.head的左子树上离head.left最远的距离+ 1 (head) + head的右子树上离head.right最远的距离
     * 选择三者中最大的返回即可
     * @param head
     * @return
     */
    public static int maxDistance(BinaryTree head){
        int[] record = new int[1];
        return posOrder(head, record);
    }

    /**
     * 后序遍历
     * @param head
     * @param record 相当于全局变量, 用来返回子树上,离子树头最远的距离
     * @return
     */
    private static int posOrder(BinaryTree head, int[] record){
        if (head == null){
            record[0] = 0;
            return 0;
        }
        int lMax = posOrder(head.getLeft(), record);
        int maxFromLeft = record[0]; //head的左子树上离head.left最远的距离
        int rMax = posOrder(head.getRight(), record);
        int maxFromRight = record[0]; //head的右子树上离head.right最远的距离
        int curNodeMax = maxFromLeft + maxFromRight + 1;
        record[0] = Math.max(maxFromLeft, maxFromRight) + 1;
        return Math.max( Math.max(lMax, rMax), curNodeMax);
    }

    /**
     * 根据先序与中序数组结合重构二叉树, 数组不重复
     * @param pre 先序数组
     * @param in 中序数组
     * @return
     */
    public static BinaryTree preInToTree(int[] pre, int[] in){
        if (pre == null || in == null){
            return null;
        }
        Map<Integer, Integer> map = new HashMap<>(in.length);
        for (int i = 0; i < in.length; i ++){
            map.put(in[i], i);
        }
        return preIn(pre, 0, pre.length - 1, in, 0, in.length - 1, map);
    }

    private static BinaryTree preIn(int[] pre, int preI, int preJ, int[] in, int inI, int inJ, Map<Integer,Integer> map){
        if (preI > preJ){
            return null;
        }
        //先序数组的第一个元素为头结点
        BinaryTree head = new BinaryTree(pre[preI]);
        //获得这个元素在中序数组中的位置index
        //index之前的为左子树的中序数组, 长度为index - inI, index 之后的为右子树的中序数组, 长度为 inj - index
        //那么先序数组中, preI+1开始的index - inI长度的元素为左子树的先序数组, preI+index-inI+1开始的元素为右子树的先序数组
        //递归创建head的左子树和右子树
        int index = map.get(pre[preI]);
        head.setLeft(preIn(pre, preI + 1, preI + index - inI, in, inI, index - 1 , map));
        head.setRight(preIn(pre, preI + index - inI + 1, preJ, in, index + 1, inJ, map));
        return head;
    }

    /**
     * 根据中序与后序数组重构二叉树, 数组中没有重复, 和先序与中序逻辑类似, 后序数组的最后一个元素是头结点
     * @param in
     * @param pos
     * @return
     */
    public static BinaryTree inPosToTree(int[] in, int[] pos){
        if (in == null || pos == null){
            return null;
        }
        Map<Integer, Integer> map = new HashMap<>(in.length);
        for (int i = 0; i < in.length; i ++){
            map.put(in[i],i);
        }
        return inPos(pos, 0, pos.length - 1, in, 0, in.length - 1, map);
    }

    private static BinaryTree inPos(int[] pos, int posI, int posJ, int[] in, int inI, int inJ, Map<Integer, Integer> map){
        if (posI > posJ){
            return null;
        }
        int index = map.get(pos[posJ]);
        BinaryTree head = new BinaryTree(pos[posJ]);
        head.setLeft(inPos(pos, posI, posI + index - inI, in, inI, index - 1,map));
        head.setRight(inPos(pos, posI + index - inI + 1, posJ - 1, in, index + 1, inJ, map));
        return head;
    }

    //ps. 先序和后序重构二叉树, 必须确定二叉树除了叶节点以外, 其他节点的左右孩子都存在, 否则不能二叉树不唯一

    public static int[] preInToPos(int[] pre, int[] in){
        if (pre == null || in == null){
            return null;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < in.length - 1; i ++){
            map.put(in[i], i);
        }
        int[] pos = new int[in.length];
        preInToPos(pre, 0, in.length - 1, in, 0, in.length - 1, pos, in.length - 1, map);
        return pos;
    }

    private static int preInToPos(int[] pre, int preI, int preJ, int[] in, int inI, int inJ, int[] pos, int posIndex,
                                   Map<Integer, Integer> map){
        if (preI > preJ){
            return posIndex;
        }
        Integer index = map.get(pre[preI]);
        pos[posIndex --] = pre[preI];
        posIndex = preInToPos(pre, preJ - inJ + index + 1, preJ, in, index + 1, inJ, pos, posIndex, map);
        return preInToPos(pre, preI + 1, preI + index - inI, in, inI, index - 1, pos, posIndex, map);
    }

    /**
     * 统计所有不同的二叉树
     * @param n n<1代表空树结构, 否则代表中序遍历的结果为{1,2,3,....n-1, n}
     * @return  返回可能的二叉树结构数量
     */
    public static int numsTrees(int n){
        if (n < 2){
            return 1;
        }
        int[] num = new int[n + 1];
        num[0] = 1;
        //动态规划,T:O(N^2)
        for (int i = 1; i < n + 1; i ++){
            for (int j = 1; j < i + 1; j ++){
                num[i] += num[i - j] * num[j - 1];
            }
        }
        return num[n];
    }


    /**
     * 生成所有不同的二叉树
     * @param n
     * @return
     */
    public static List<BinaryTree> generateTrees(int n){
        return generate(1, n);
    }

    public static List<BinaryTree> generate(int start, int end ){
        List<BinaryTree> res = new LinkedList<>();
        if (start > end){
            res.add(null);
        }
        BinaryTree head = null;
        for (int i = start; i < end + 1; i ++){
            head = new BinaryTree(i);
            List<BinaryTree> lSubs = generate(start, i - 1);
            List<BinaryTree> rSubs = generate(i + 1, end);
            for (BinaryTree l : lSubs){
                for (BinaryTree r: rSubs){
                    head.setLeft(l);
                    head.setRight(r);
                    res.add(cloneTree(head));
                }
            }
        }
        return res;
    }
    public static BinaryTree cloneTree(BinaryTree head){
        if (head == null){
            return null;
        }
        BinaryTree res = new BinaryTree(head.getData());
        res.setLeft(cloneTree(head.getLeft()));
        res.setRight(cloneTree(head.getRight()));
        return res;
    }

    //统计完全二叉树的节点, T:O(H^2)
    public static int nodeNum(BinaryTree head){
        if (head == null){
            return 0;
        }
        return bs(head, 1, mostLeftLevel(head, 1));
    }
    private static int bs(BinaryTree head, int level, int height){
        if (level == height){
            return level;
        }
        if (mostLeftLevel(head.getRight(), level + 1) == height){
            return (level << (height - 1)) + bs(head.getRight(), level + 1, height);
        }else {
            return (level << (height - 1 - 1)) + bs(head.getLeft(), level + 1, height);
        }
    }

    //head的高度
    private static int mostLeftLevel(BinaryTree head, int level){
        if (head != null){
            level ++;
            head = head.getLeft();
        }
        return level - 1;
    }









    public static void main(String[] args) {
        String s = "abcaabcaaaaabcababababc";
        char[] chars = s.toCharArray();
        int[] nextArr = getNextArr(chars);
        for (int i : nextArr){
            System.out.println(i);
        }
    }
}
