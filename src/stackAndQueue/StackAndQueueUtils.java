package stackAndQueue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

/**
 * 栈和队列的使用相关算法
 * create by renshengmiao on 2018/3/2 .
 */
public class StackAndQueueUtils {
    //用一个辅助栈对stack栈从大到小排序
    public static void downsortStackByStack(Stack<Integer> stack){
        Stack<Integer> help = new Stack<>();
        while (!stack.isEmpty()){
            int cur = stack.pop();
            while (!help.isEmpty() || cur > help.peek()){
                stack.push(help.pop());
            }
            help.push(cur);
        }
        while (!help.isEmpty()){
            stack.push(help.pop());
        }
    }

    /**
     * maxTree
     * @param arr 不重复数字的数组
     * @return
     */
    public static Node getMaxTree(int[] arr){
        Node[] nodes = new Node[arr.length];
        for (int i = 0; i< arr.length; i++){
            nodes[i] = new Node(arr[i]);
        }
        Stack<Node> stack = new Stack<>();
        //key node1, value : node1左边第一个比node1大的节点
        Map<Node, Node> lBigMap = new HashMap<>();
        //key node2, value : node2右边第一个比node2大的节点
        Map<Node, Node> rBigMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++){
            Node cur = nodes[i];
            while (!stack.isEmpty() && stack.peek().getValue() < cur.getValue()){
                popStackSetMap(stack, lBigMap);
            }
            stack.push(cur);
        }
        while (!stack.isEmpty()){
            popStackSetMap(stack, lBigMap);
        }
        for (int i = arr.length-1; i > -1; i--){
            Node cur = nodes[i];
            while (!stack.isEmpty() && stack.peek().getValue() < cur.getValue()){
                popStackSetMap(stack, rBigMap);
            }
            stack.push(cur);
        }
        while (!stack.isEmpty()){
            popStackSetMap(stack, rBigMap);
        }
        Node head = null;
        for (int i = 0; i < arr.length; i++){
            Node cur = nodes[i];
            Node left = lBigMap.get(cur);
            Node right = rBigMap.get(cur);
            if (left == null && right == null){
                //cur为最大值
                head = cur;
            }else if (left == null){
                //cur左侧无更大值,而右侧有更大值(最近的), 则cur作为right的子节点
                setChild(right, cur);
            }else if (right == null){
                setChild(left, cur);
            }else {
                //左右两边都有比他大的值,取较小的那个作为父节点
                Node parent = left.getValue() > right.getValue() ? right : left;
                setChild(parent, cur);
            }
        }
        return head;
    }

    private static void setChild(Node parent, Node child){
        if (parent.getLeft() == null){
            parent.setLeft(child);
        }else {
            parent.setRight(child);
        }
    }
    private static void popStackSetMap(Stack<Node> stack, Map<Node,Node> map){
        Node cur = stack.pop();
        if (stack.isEmpty()){
            //没有更大的值了
            map.put(cur, null);
        }else {
            map.put(cur, stack.peek());
        }
    }


    /**
     * 求最大子矩阵的大小
     * @param map 整型矩阵, 只有0,1
     *            如 1 0 1 1
     *               1 1 1 1
     *               1 1 1 0
     * @return e.g.: 6
     */
    public static int maxRecSize(int[][] map){
        if (map == null || map.length == 0 || map[0].length == 0){
            return 0;
        }
        int maxArea = 0;
        int[] height = new int[map[0].length];
        for (int i = 0; i < map.length; i ++){
            for (int j = 0; j < height.length; j ++){
                height[j] = map[i][j] == 0? 0 : height[j] + 1;
            }
            maxArea = Math.max(maxArea, maxRecFromBottom(height));
        }
        return maxArea;
    }

    /**
     *
     * @param height 直方图, 如map[1] -> height = {2,1,2,2}
     * @return
     */
    public static int maxRecFromBottom(int[] height){
        if (height == null || height.length == 0){
            return 0;
        }
        Stack<Integer> stack = new Stack<>(); //存位置
        int maxArea = 0;
        for (int i = 0; i < height.length; i ++){
            //计算位置i左边,切割出来的最大矩阵面积
            //不计算向右扩, 因为之后的循环会计算
            while (!stack.isEmpty() && height[i] <= height[stack.peek()]){
                int j = stack.pop();
                int k = stack.isEmpty()? -1 : stack.peek();
                //计算位置(k + 1) .. (i - 1) 之间的面积, 即位置j向左扩的最大面积
                //不计算位置i向左扩的最大面积, 是因为之后的循环会计算
                int curArea = height[j] * (i - k - 1);
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()){
            int j = stack.pop();
            int k = stack.isEmpty()? -1 : stack.peek();
            int curArea = height[j] * (height.length - k - 1);
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }
//    public static void main(String[] args) {
//        System.out.println("汉诺塔问题");
//        hanoiProblem1(2,"left", " mid", "right");
//    }
    public static long hanoiProblem1(int num, String left, String mid, String right){
        if (num < 1){
            return 0;
        }
        return process(num, left, mid, right, left, right);
    }

    /**
     * 求一个数组中, 子数组满足 最大值减去最小值 小于或等于num 的子数组数量 T:O(N)
     * max(arr[i..j]) - min(arr[i..j]) <= num
     * @param arr
     * @param num
     * @return
     */
    public static int getNum(int[] arr, int num){
        //存下标
        LinkedList<Integer> qmax = new LinkedList<>();
        LinkedList<Integer> qmin = new LinkedList<>();
        int res = 0;
        int i = 0;
        int j = 0;
        //i向右移动, 队列从左变缩小
        while (i < arr.length){
            //j向右移动, 队列向右扩大
            while (j < arr.length){
                while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[j]){
                    qmax.pollLast();
                }
                qmax.addLast(j);
                while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j]){
                    qmin.pollLast();
                }
                qmin.addLast(j);
                //队首是当前最大值或最小值的下标
                if ((arr[qmax.getFirst()] - arr[qmin.getFirst()]) > num){
                    //当最大值-最小值 > num, 即不满足题目条件时,结束
                    //因为即使j 继续增加, 最大值只会变大, 最小值只会变小, 始终不会满足题目要求的条件
                    break;
                }
                j ++;
            }
            //满足条件的arr[i..j], 其子数组arr[i..j-1],arr[i..j-2],...也满足条件
            res += j - i;
            //队首过期, 去掉(因为i增加, 子数组左端右移)
            if (qmax.peekFirst() == i){
                qmax.pollFirst();
            }
            if (qmin.peekFirst() == i){
                qmin.pollFirst();
            }
            i ++;
        }
        return res;
    }

    public static long process(int num, String left, String mid, String right, String from, String to){
        if (num == 1){
            if (from.equals(mid) || to.equals(mid)){
                System.out.println("Move 1 from " + from + " to " + to);
                return 1;
            }else {
                System.out.println("Move 1 from " + from + " to " + mid);
                System.out.println("Move 1 from " + mid + " to " + to);
                return 2;
            }
        }else {
            if (from.equals(mid) || to.equals(mid)){
                String another = (from.equals(left) || to.equals(left)) ? right : left;
                long part1 = process(num -1 , left, mid, right, from, another);
                long part2 = 1;
                System.out.println("Move "+ num +" from " + from + " to " + to);
                long part3 = process(num - 1, left, mid, right, another, to);
                return part1+part2+part3;
            }else {
                long part1 = process(num - 1, left, mid, right, from, to);
                long part2 = 1;
                System.out.println("Move "+ num +" from " + from + " to " + mid);
                long part3 = process(num - 1, left, mid, right, to, from);
                long part4 = 1;
                System.out.println("Move "+ num +" from " + mid + " to " + to);
                long part5 = process(num - 1, left, mid, right, from, to);
                return part1+part2+part3+part4+part5;
            }
        }
    }
}
