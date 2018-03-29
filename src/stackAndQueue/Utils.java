package stackAndQueue;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by rsmno on 2018/3/5.
 */
public class Utils {
    public enum Action{
        No, LToM, MToR, RToM, MToL
    }
    public static void hanoiprocess(int num, String left, String mid, String right){
        Stack<Integer> ls = new Stack<>();
        Stack<Integer> ms = new Stack<>();
        Stack<Integer> rs = new Stack<>();
        ls.push(Integer.MAX_VALUE);
        ms.push(Integer.MAX_VALUE);
        rs.push(Integer.MAX_VALUE);
        for (int i =num; i > 0 ; i --){
            ls.push(i);
        }
        //记录前一个动作
        //用数组，在传递过程中不会失去引用
        Action[] record = {Action.No};
        //枚举类似final常量， 直接赋值相当于将指针指向另一个常量， 传递时传的是拷贝
//        Action record = Action.No;
        //记录步数
        int step = 0;
        while (rs.size() != num +1){
            step += fromStacktoToStack(record, Action.MToL, Action.LToM, ls, ms, left, mid);
            step += fromStacktoToStack(record, Action.LToM, Action.MToL, ms, ls, mid,left);
            step += fromStacktoToStack(record, Action.RToM, Action.MToR, ms, rs, mid, right);
            step += fromStacktoToStack(record, Action.MToR, Action.RToM, rs,ms,right, mid);
        }
        System.out.println(step);
    }

    /**
     *
     * @param record 前一个动作
     * @param pro 当前动作的逆动作
     * @param now 当前动作
     * @param fromStack 弹出栈
     * @param toStack 压入栈
     * @param from
     * @param to
     * @return
     */
    public static int fromStacktoToStack(Action[] record, Action pro, Action now, Stack<Integer> fromStack
        , Stack<Integer> toStack, String from, String to){
        //满足当前动作不会和上一步的动作相反（相邻不可逆）， 并且始终小压大（不会重复上一次动作）
        //必定在另外两个动作中有一个满足小压大， 另一个不满足
        //因此，四个动作中， 只有一个动作会满足要求
        if (record[0] != pro && fromStack.peek() < toStack.peek()){
            record[0] = now;
            toStack.push(fromStack.pop());
            System.out.println("Move " + toStack.peek() + " from " + from + " to " + to);
            return 1;
        }
        return 0;
    }

    /**
     * [4 3 5] 4 3 3 6 7   max = 5
     * 4 [3 5 4] 3 3 6 7    max = 5
     * 4 3 [5 4 3] 3 6 7    max = 5
     * 4 3 5 [4 3 3] 6 7    max = 4
     * 4 3 5 4 [3 3 6] 7    max = 6
     * 4 3 5 4 3 [3 6 7]    max = 7
     * @param arr 整形数组 如 【4,3,5,4,3,3，6,7】
     * @param w 窗口大小 3
     * @return 最大值数组[5,5,5,4,6,7]
     */
    public static int[] getMaxWindow(int arr[], int w){
        if (arr == null || w < 1 || arr.length <w){
            return null;
        }
        //双端队列
        LinkedList<Integer> qMax = new LinkedList<>();
        int[] res = new int[arr.length - w + 1];//结果
        int resIndex = 0;
        for (int i = 0; i< arr.length; i++){
            while (!qMax.isEmpty() && arr[qMax.peekLast()] <= arr[i]){
                //非空， 且队尾arr[j]比当前arr[i] 小
                qMax.pollLast();
            }
            //是否为空，是则直接加入队尾
            qMax.addLast(i);
            //队首是否过期
            if(qMax.peekFirst() == (i - w)){
                qMax.pollFirst();//不用removeFirst，因为当队列为空时，poll返回空，而remove报异常
            }
            if (i >= (w - 1)){
                res[resIndex++] = arr[qMax.peekFirst()];
            }
        }
        return res;
    }

}
