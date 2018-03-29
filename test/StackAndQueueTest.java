import org.junit.Test;
import stackAndQueue.Node;
import stackAndQueue.StackAndQueueUtils;

/**
 * create by renshengmiao on 2018/3/6 .
 */
public class StackAndQueueTest {

    @Test
    public void maxTreeTest(){
        int[] arr = new int[]{1,2,3,4,5,8,9,111,44,55,66,99,88};
//        int[] arr = new int[]{1,3,2};
        Node maxTree = StackAndQueueUtils.getMaxTree(arr);
        Node temp = maxTree.getLeft();
        System.out.println("left tree");
        while (temp != null){
            System.out.println(temp.getValue());
            temp = temp.getLeft();
        }
        temp = maxTree.getRight();
        System.out.println("right tree");
        while (temp != null){
            System.out.println(temp.getValue());
            temp = temp.getRight();
        }
    }

    @Test
    public void maxRecFromBottomTest(){
        int[] ints = {3, 4, 5, 6, 3, 4};
        int rec = StackAndQueueUtils.maxRecFromBottom(ints);
        System.out.println(rec);
    }

    @Test
    public void maxRecAreaTest(){
        int[][] intMap = {{1, 0, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 0}};
        int recMax = StackAndQueueUtils.maxRecSize(intMap);
        System.out.println(recMax);
    }

    @Test
    public void getNumTest(){
        int[] ints = {1,4,5};
        int num = 3;
        System.out.println(StackAndQueueUtils.getNum(ints, num));
        System.out.println(StackAndQueueUtils.getNum(new int[]{1}, 3));
        System.out.println(StackAndQueueUtils.getNum(new int[]{1,2}, 3));
        System.out.println(StackAndQueueUtils.getNum(new int[]{3,2}, 3));
    }
}
