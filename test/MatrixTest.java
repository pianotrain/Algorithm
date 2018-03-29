import matrix2.Matrix2;
import org.junit.Test;

import java.util.stream.Stream;

/**
 * create by renshengmiao on 2018/3/19 .
 */
public class MatrixTest {

    @Test
    public void test(){
        int[] arr = new int[]{
                1,5,6,7,8,9,2,3,4,5,4,3,1,8,9,0,6,5,7
        };
        int[] kMinNumsByHeap = Matrix2.getKMinNumsByHeap(arr, 9);
        for (int i : kMinNumsByHeap){
            System.out.println(i);
        }
        System.out.println();
        Matrix2.select(arr, 0, arr.length - 1, 6);
    }


    @Test
    public void etste(){
        int[] arr = new int[]{
                1,5,6,7,8,9,2,3,4,5,4,3,1,8,9,0,3,5,8
        };
        int minSortLength = Matrix2.getMinSortLength(arr);
        System.out.println(minSortLength);
    }

    @Test
    public void testprintKMajor(){
        int[] arr = new int[]{
                1,1,1,1,1,2,2,2,2,2,2,3,3,3,33,3,4,4,44
        };
        //打印出现次数大于arr.length/k 次的数
        Matrix2.printKMajor(arr, 4);
    }
    public static void insertionSort(int[] arr, int begin, int end){
        for (int i = begin + 1; i != end + 1; i ++){
            for (int j = i; j != begin; j -- ){
                if (arr[j - 1] < arr[j]){
                    Matrix2.swap(arr, j - 1, j);
                }
            }
        }
    }

    @Test
    public void testGetmaxLength(){
        int[] arr = new int[]{
                1,-2,3,-4,5,-9,1,5,7,8,3,4,-9,4,-5,1,4
        };
        int[] arr2 = new int[]{
                -1,0,1,2,3,4,5,6,7
        };
        int maxLength2 = Matrix2.getMaxLength2(arr2, 0);
        System.out.println(maxLength2);
    }

    @Test
    public void testProduct2(){
        int[] arr = new int[]{
                2,3,1,4
        };
        int[] ints = Matrix2.product2(arr);
        for (int i : ints){
            System.out.println(i);
        }
    }

    @Test
    public void testParttion(){
        int[] arr = new int[]{
                2,1,2,1,0,2,1,2,0,1,2,0,1
        };
        Matrix2.parttitionSort(arr);
        for (int i : arr){
            System.out.println(i);
        }
    }

    @Test
    public void testminPathValue(){
        int[][] arr = new int[][]{
                {1,0,1,1,1},
                {1,0,1,0,1},
                {1,1,1,0,1},
                {0,0,0,0,1}
        };
        int i = Matrix2.minPathValue(arr);
        System.out.println(i);
    }

    @Test
    public void testMissNum(){
        int[] arr = new int[]{1,2,11,66,5,6,4,99};
        int i = Matrix2.missNum(arr);
        System.out.println(i);
    }
}
