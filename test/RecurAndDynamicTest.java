import RecurAndDynamic.RecurAndDynamic;
import org.junit.Test;

/**
 * create by renshengmiao on 2018/3/15 .
 */
public class RecurAndDynamicTest {

    @Test
    public void test1(){
        int i = RecurAndDynamic.minCoins2(new int[]{11,20}, 100);
        System.out.println(i);
    }

    @Test
    public void stest(){
        int[] a = new int[]{1,4,56,76,3,34,56,76,45,213};
        int[] dp2 = RecurAndDynamic.getDp2(a);
    }

    @Test
    public void tset(){
        String lcas = RecurAndDynamic.lcas("AHDTFDGAERQWDASFAUFFFF人", "FGHRTYRAEWFDRETWRFFFF");
        System.out.println(lcas+"人".length());
        String s = RecurAndDynamic.lcst1("asdfewrttasdfadf", "asdfasdfsadfas");
        System.out.println(s);
    }

    @Test
    public void testasdf(){
        //算法错误
        int i = RecurAndDynamic.longestConsecutive(new int[]{1, 4, 5, 76, 34, 2, 3, 4, 6, 7, 8, 9, 11, 10, 0});
        System.out.println(i);
    }
}
