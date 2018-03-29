import org.junit.Test;
import stringUtils.StringUtils;

/**
 * create by renshengmiao on 2018/3/16 .
 */
public class StringUtilsTest {

    @Test
    public void test(){
        String k000k0000k000 = StringUtils.removeKZeros("k000k0000k000", 3);
        System.out.println(k000k0000k000);
        System.out.println(Integer.MIN_VALUE / 10 + " "+ Integer.MIN_VALUE % 10);
        System.out.println(Integer.MIN_VALUE + " " + Integer.MAX_VALUE);
        System.out.println(-2147483648 - 1);
        System.out.println(StringUtils.convert("-2147483648"));
    }

    @Test
    public void test1(){
        String replace = StringUtils.replace("abc000acbaabc", "abc", "X");
        System.out.println(replace);
    }

    @Test
    public void testweopart(){
        int[] arr = {1,2,3,4,6,7,8,9,10,11};
        int i = StringUtils.twoPart(arr, 5);
        System.out.println(i);
    }
}
