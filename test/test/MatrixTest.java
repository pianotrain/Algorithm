package test;

import matrix.Matrix;
import org.junit.Test;

/**
 * Created by rsmno on 2018/3/19.
 */
public class MatrixTest {
    @Test
    public void test(){
        int[] arr = new int[]{
                3,-2,-4,0,6
        };
        int i = Matrix.maxLength(arr, -2);
        System.out.println(i);
    }

    @Test
    public void test1(){
        int[] arr = new int[]{
                10,20,30,40,50,60,70,89,100,110
        };
        int i = Matrix.maxGap(arr);
        System.out.println(i);
    }

    @Test
    public void testSort(){
        int[] arr = new int[]{
                10,1,30,5,50,9,70,89,2,110,20,10,30,5
        };
        heapSort(arr);
//        QuickSort(arr, 0, arr.length - 1);
        for (int i : arr){
            System.out.println(i);
        }
    }

    public static int[] QuickSort(int[] arr, int start, int end){
        if (arr.length < 1 || start < 0 || end >= arr.length || start > end){
            return null;
        }
        int smallIndex = parttiton(arr, start, end);
        if (start < smallIndex){
            QuickSort(arr, start, smallIndex - 1);
        }
        if (end > smallIndex){
            QuickSort(arr, smallIndex + 1, end);
        }
        return arr;
    }

    public static int parttiton(int[] arr, int start, int end){
        int pivot =(int) (start + Math.random() * ( end - start + 1));
        int smallIndex = start - 1;
        swap(arr, pivot, end);
        for (int i = start; i <= end; i ++){
            if (arr[i] <= arr[end]){
                smallIndex ++;
                if (i > smallIndex){
                    swap(arr, i , smallIndex);
                }
            }
        }
        return smallIndex;
    }
    public static void swap(int[] arr, int index1, int index2){
        if (index1 == index2){
            return;
        }
        arr[index1] = arr[index1] ^ arr[index2];
        arr[index2] = arr[index1] ^ arr[index2];
        arr[index1] = arr[index1] ^ arr[index2];
    }

    public static void heapInsert(int[] arr, int i){
        int parent = 0;
        while ( i != 0){
            parent = (i - 1) / 2;
            if (arr[parent] < arr[i]){
                swap(arr, parent, i);
                i = parent;
            }else {
                break;
            }
        }
    }

    public static void heapify(int[] arr, int i, int size){
        int left = i * 2 +1;
        int right = i * 2 +2;
        int largest = i;
        while (left < size){
            if (arr[left] > arr[i]){
                largest = left;
            }
            if (right < size && arr[right] > arr[largest]){
                largest = right;
            }
            if (largest != i){
                swap(arr, largest, i);
            }else {
                break;
            }
            i = largest;
            left = i * 2 + 1;
            right = i * 2 + 2;
        }
    }

    public static void heapSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        for(int i = 0; i < arr.length; i ++){
            heapInsert(arr, i);
        }
        for (int i = arr.length - 1; i != -1; i --){
            swap(arr,0, i);
            heapify(arr, 0 , i);
        }
    }
}
