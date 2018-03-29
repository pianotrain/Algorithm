package test;

import org.junit.Test;

/**
 * Created by rsmno on 2018/3/18.
 */
public class BitOpeTest {
    @Test
    public void test(){
        //不用其他额外变量交换两个整数的值
        int a = 234234;
        int b = 435345;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println(a + "  " + b);
    }
}
