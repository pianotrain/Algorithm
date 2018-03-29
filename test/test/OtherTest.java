package test;

import org.junit.Test;
import others.MedianHolder;
import others.MessageBox;
import others.MyHeap;

import java.util.Comparator;

/**
 * Created by rsmno on 2018/3/21.
 */
public class OtherTest {
    @Test
    public void testMessageBox(){
        MessageBox box = new MessageBox();
        box.receive(3)
                .receive(2)
                .receive(1)
                .receive(4)
                .receive(7)
                .receive(9)
                .receive(8)
                .receive(5)
                .receive(6);
    }

    @Test
    public void testMyHeap(){
        MyHeap<Integer> integerMyHeap = new MyHeap<>(new MaxHeapComparator());
        Integer head = integerMyHeap.add(4)
                .add(2)
                .add(4)
                .add(6)
                .add(7)
                .getHead();
        System.out.print(head);
    }
    @Test
    public void testMyHeapAndMedian(){
        MedianHolder medianHolder = new MedianHolder();
        Integer median = medianHolder.addNumber(1)
                .addNumber(3)
                .addNumber(6)
                .addNumber(9)
                .addNumber(4)
                .addNumber(1)
                .addNumber(5)
                .getMedian();
        System.out.print(median);
    }

    //生成大根堆的比较器
    public class MaxHeapComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o2 > o1){
                return 1;
            }else {
                return -1;
            }
        }
    }
}
