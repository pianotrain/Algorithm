package stackAndQueue;

import java.util.Stack;

/**
 *
 * 使用两个栈设计的队列
 * 必须做到以下两点
 * 1,如果stackpush要向stackpop压入数据, 那么必须一次性把stackpush中的数据全部压入
 * 2, 如果stackpop不为空, 那么stackpush绝对不能向stackpop压入数据
 * create by renshengmiao on 2018/3/2 .
 */
public class TwoStackQuene<E> {
    private Stack<E> stackPush;
    private Stack<E> stackPop;

    public TwoStackQuene(){
        this.stackPush = new Stack<>();
        this.stackPop = new Stack<>();
    }

    public void add(E e){
        stackPush.push(e);
    }

    public void checkEmpty(){
        if (stackPop.isEmpty() && stackPush.isEmpty()){
            throw new RuntimeException("empty");
        }
    }

    public void reversed(){
        if (stackPop.isEmpty()){
            while (!stackPush.isEmpty()){
                stackPop.push(stackPush.pop());
            }
        }
    }
    public E poll(){
        checkEmpty();
        reversed();
        return stackPop.pop();
    }

    public E peek(){
        checkEmpty();
        reversed();
        return stackPop.peek();
    }
}
