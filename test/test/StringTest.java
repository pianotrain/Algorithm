package test;

import org.junit.Test;
import strings.StringAlogic;

/**
 * Created by rsmno on 2018/3/17.
 */
public class StringTest {
    @Test
    public void test(){
        int abcab = StringAlogic.maxUnique("abcab");
        System.out.print(abcab+"   ");
        int dsfakhoiewjdhsfa = StringAlogic.maxUnique("dsfakhoiewjdhsfa");
        System.out.print(dsfakhoiewjdhsfa);
    }

    @Test
    public void test1(){
        boolean isMatched = StringAlogic.isMatchedDP("aaaaXXXXbcRRR", "a*..X*y*.c.*");
        System.out.print(isMatched);
    }

    @Test
    public void testTrie(){
//        StringAlogic.
//        new StringAlogic().
    }

}
