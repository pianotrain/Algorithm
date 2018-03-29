package stackAndQueue;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;

/**
 * create by renshengmiao on 2018/3/1 .
 * 泛型化编程,test
 */
public class Stack1<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public static void main(String[] args) {
        Stack1<String> stringStack1 = new Stack1<>();
        for (String s : Arrays.asList("1","2","3")){
            stringStack1.push(s);
        }
        while (!stringStack1.isEmpty()){
            System.out.println(stringStack1.pop());
        }
    }

    @SuppressWarnings("uncheched")
    public Stack1() {
//        elements = new E[DEFAULT_INITIAL_CAPACITY];
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public static <E> Set<E> union(Set<? extends  E> set1, Set<? extends E> set2){
        return new HashSet<>();
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        E result = elements[--size];
        elements[size] = null;
        return result;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
