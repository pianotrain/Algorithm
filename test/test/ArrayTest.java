package test;

import ArrAndMatrix.ArrayUtils;
import org.junit.Test;

import java.util.Random;

/**
 * Created by rsmno on 2018/3/10.
 */
public class ArrayTest {

    public int[] initArr(int len){
        int[] arr = new int[len];
        for (int i = 0; i < len; i ++){
            arr[i] = i ;
        }
        return arr;
    }

    public int[] initArr2(int len){
        Random random = new Random();
        int[] arr = new int[len];
        for (int i = 0; i < len; i ++){
            arr[i] = random.nextInt();
        }
        return arr;
    }
    @Test
    public void testGetMaxLengthOfSum(){
        int[] ints = initArr(16);
        int maxLengthOfSum = ArrayUtils.getMaxLengthOfSum(ints, 21);
        System.out.println(maxLengthOfSum);
    }

    @Test
    public void testGetMaxLength(){
        int[] arr = new int[]{
                1,2,4,3,2,0,-1,-4,-65,-32,23,54,-3,234,53,2,0,5,6,2,-3,4,-54,23
        };
        int maxLengthOfSumPlus1 = ArrayUtils.getMaxLengthOfSumPlus1(arr, 16);
        System.out.println(maxLengthOfSumPlus1);
        int maxLengthOfPostiveEqualsNegative = ArrayUtils.getMaxLengthOfPostiveEqualsNegative(arr);
        System.out.println(maxLengthOfPostiveEqualsNegative);
        arr = new int[]{
                1,1,1,0,1,1,1,0,0,0,0,1,1,0
        };
        int maxLengthOf1NumberEquals0Number = ArrayUtils.getMaxLengthOf1NumberEquals0Number(arr);
        System.out.println(maxLengthOf1NumberEquals0Number);
    }


}
