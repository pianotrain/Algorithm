package stackAndQueue;

import java.util.Stack;

/**
 * create by renshengmiao on 2018/3/2 .
 */
public class MyStack1 {
    //有getmin功能的栈, stackMin栈顶保存当前栈最小值
    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    MyStack1(){
        this.stackData = new Stack<>();
        this.stackMin = new Stack<>();
    }

    public Integer getMin(){
        if (stackMin.isEmpty()){
            throw new RuntimeException("your stack is empty");
        }else {
            return this.stackMin.peek();
        }
    }

    /**
     * 压入规则, stackMin为空,直接压入;不为空, 比较栈顶值, 如果新值较小则压入
     * @param newNum
     */
    public void push(int newNum){
        stackData.push(newNum);
        if (stackMin.isEmpty()){
            stackMin.push(newNum);
        }else if (newNum < this.getMin()){
            stackMin.push(newNum);
        }
    }

    /**
     * 弹出规则, 如果弹出值为最小值, 则一起弹出
     * @return
     */
    public int pop(){
        if (this.stackData.isEmpty()){
            throw new RuntimeException("empty");
        }
        int value = stackData.pop();
        if (value == this.getMin()){
            this.stackMin.pop();
        }
        return value;
    }
}
