package test;

import org.junit.Test;
import stackAndQueue.Utils;

/**
 * Created by rsmno on 2018/3/5.
 */
public class stackAndQueueTest {
    @Test
    public void hanoiprocessTest(){
        Utils.hanoiprocess(2,"left", "mid", "right");
    }

    @Test
    public void getMaxWindowTest(){
        int[] arr = new int[]{1,3,5,5,3,2,6,65,4,2,4,5,8,9,3};
        int[] maxs = Utils.getMaxWindow(arr, 3);
        for (int i : maxs){
            System.out.print(i+" ");
        }
        System.out.println();
    }
}
