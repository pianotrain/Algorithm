package matrix2;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 数组和矩阵的相关算法1
 * Created by rsmno on 2018/3/18.
 */
public class Matrix {

    /**
     * 转圈打印一个矩阵
     * 例如：  1   2   3   4
     *         5   6   7   8
     *         9  10  11  12
     *         13 14  15  16
     *     打印 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10
     *     提示：每次打印矩阵的最外层（tR，tC)=(0,0) , (dR, dC) = (3,3)
     * @param matrix
     */
    public static void spiralOrderPrint(int[][] matrix){
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC){
            printEdge(matrix, tR ++, tC ++, dR --, dC --);
        }
    }

    public static void printEdge(int[][] matrix, int tR, int tC, int dR, int dC){
        if (tR == dR){
            //只剩一行
            for (int i = tC; i <= dC; i ++){
                System.out.println(matrix[tR][i]);
            }
        }else if (tC == dC){
            //只剩一列
            for (int i = tR; i <= dR; i ++){
                System.out.println(matrix[i][tC]);
            }
        }else {
            //一般情况，转圈打印
            int curR = tR;
            int curC = tC;
            while (curC != dC){
                System.out.println(matrix[tR][curC ++]);
            }
            while (curR != dR){
                System.out.println(matrix[curR ++][dC]);
            }
            while (curC != tC){
                System.out.println(matrix[dR][curC --]);
            }
            while (curR != tR){
                System.out.println(matrix[curR --][tC]);
            }
        }
    }

    //将正方形矩阵顺时针转动90度
    public static void rotate(int[][] matrix){
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC){
            rotateEdge(matrix, tR ++, tC ++, dR -- , dC --);
        }
    }

    private static void rotateEdge(int[][] matrix, int tR, int tC, int dR, int dC){
        int times = dC - tC;
        int temp;
        for (int i = 0; i != times; i ++ ){
            temp = matrix[tR][tC + i];
            matrix[tR][tC + i] = matrix[tR + i][dC];
            matrix[tR + i][dC] = matrix[dR][dC - i];
            matrix[dR][dC - i] = temp;
        }
    }

    /**
     * 之字形打印矩阵
     * @param matrix 1  2   3   4
     *               5  6   7   8
     *               9  10  11  12
     *          输出 1 2 5 9 6 3 4 7 10 11 8 12
     */
    public static void printMatrixZigzag(int[][] matrix){
        int tR = 0;//上坐标（tR，tC）初始（0,0），先tC++，达到第一行末尾，再tR++
        int tC = 0;
        int dR = 0;//下坐标（dR,dC)初始（0,0），先dR++, 到达第一列末尾，再dC++
        int dC = 0;
        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;
        boolean f = false;
        while (tR != endR + 1){
            printLevel(matrix, tR, tC, dR, dC, f);
            //上下坐标同时移动每次移动后的上坐标与下坐标的连线就是矩阵中的一条斜线
            tR = tC == endC ? tR + 1: tR;
            tC = tC == endC? tC: tC + 1;
            dC = dR == endR? dC + 1: dC;
            dR = dR == endR? dR : dR + 1;
            f = !f;
        }
        System.out.println();
    }

    private static void printLevel(int[][] matrix, int tR, int tC, int dR, int dC, boolean f){
        if (f){
            while (tR != dR + 1){
                System.out.println(matrix[tR ++][tC --]);
            }
        }else {
            while (dR != tR - 1){
                System.out.println(matrix[dR --][dC ++]);
            }
        }
    }

    //未排序 |正数|  数组中累加和为给定值的最长子数组长度
    public static int getMaxLength(int[] arr, int k){
        if (arr == null || arr.length == 0 || k < 0){
            return 0;
        }
        int left = 0;
        int right = 0;
        int sum = arr[0];
        int len = 0;
        while (right < arr.length){
            if (sum == k){
                len = Math.max(len, right - left  + 1);
                sum -= arr[left ++];
            }else if (sum < k){
                right ++;
                if (right < arr.length){
                    break;
                }
                sum += arr[right];
            }else {
                sum -= arr[left ++];
            }
        }
        return len;
    }

    public static int getMaxLength2(int[] arr, int k){
        if (arr == null || arr.length == 0 ){
            return 0;
        }
        Map<Integer, Integer> sumMap = new HashMap<>();
        int len = 0;
        int sum = 0;
        sumMap.put(0,-1);//重要， 考虑到了从0开始的子数组
        for (int i = 0 ; i < arr.length; i ++){
            sum += arr[i];
            if (sumMap.containsKey(sum - k)){
                len = Math.max(len, i - sumMap.get(sum - k));
            }
            if (!sumMap.containsKey(sum - k)){
                sumMap.put(sum , i);
            }
        }
        return len;
    }

    //错误示范，arr有负数
    public static int maxLength(int[] arr, int k){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int len = 0;
        Deque<Integer> deque = new LinkedList<>();
        int sum = 0;
        for (int i =0 ; i < arr.length ; i ++){
            sum += arr[i];
            deque.addLast(arr[i]);
            if (sum <= k){
                len = Math.max(len, deque.size());
            }else {
                while (sum > k && !deque.isEmpty()){
                    int poll = deque.pollFirst();
                    sum -= poll;
                }
                len = Math.max(len, deque.size());
            }
        }
        return len;
    }

    /**
     * 子矩阵的最大累加和问题
     * 每行相加， 求一行的最大累加和， T:O(N^3)
     * @param arr
     * @return
     */
    public static int maxSum(int[][] arr){
        if (arr == null || arr.length == 0 || arr[0].length == 0){
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int[] tempArr ;
        for (int i = 0; i < arr.length; i ++){
            tempArr = new int[arr[0].length];
            for (int j = i ; j < arr.length; j ++){
                int cur = 0;
                for (int k = 0 ; k < tempArr.length; k ++){
                    tempArr[k] += arr[j][k];
                    cur += tempArr[k];
                    max = Math.max(max, cur);
                    if (cur < 0){
                        cur = 0;
                    }
                }
            }
        }
        return max;
    }

    /**
     * 在数组中找到一个局部最小的位置
     * @param arr 不重复数组
     * @return
     */
    public static int getLessIndex(int[] arr){
        if (arr == null || arr.length == 0){
            return -1;//不存在
        }else if (arr.length == 1 || arr[0] < arr[1]){
            return 0;//最左边是局部最小
        }else if (arr[arr.length - 1] < arr[arr.length - 2]){
            return arr.length - 1;//最右边是局部最小
        }else {
            //如果都不是的话， 那么这个数组不可能有序，一定存在中间的值， arr[i - 1] > arr[i] < arr[i +1]
            //利用二分查找
            int left = 1;
            int right = arr.length - 2;
            int mid = 0;
            while (left != right){
                mid = (left + right) / 2;
                if (arr[mid] > arr[mid + 1]){
                    left = mid + 1;
                }else if (arr[mid] > arr[mid - 1]){
                    right = mid - 1;
                }else {
                    return mid;
                }
            }
            return left;
        }
    }

    /**
     * 数组中子数组的最大累乘积
     * @param arr
     * @return
     */
    public static double maxProduct(double[] arr){
        if (arr == null ||arr.length == 0){
            return 0;
        }
        double max = arr[0];
        double min = arr[0];
        double res = arr[0];
        double maxEnd = 0;
        double minEnd = 0;
        for(int i = 1 ; i < arr.length; i ++){
            //maxEnd 为 以arr[i - 1]结尾的最大值，minEnd为 以arr[i - 1]结尾的最小值
            //那么， 以arr[i]结尾的最大值只可能来自以下三种情况
            //1.maxEnd * arr[i], minEnd * arr[i], 或者arr[i]
            maxEnd = max * arr[i];
            minEnd = min * arr[i];
            max = Math.max(arr[i], Math.max(maxEnd, minEnd));
            min = Math.min(arr[i], Math.min(maxEnd, minEnd));
            res = Math.max(max, res);
        }
        return res;
    }
    public static class heapNode{
        public int value;
        public int arrNum;//来自哪个数组
        public int index;//来自数组的哪个位置

        public heapNode(int value, int arrNum, int index) {
            this.value = value;
            this.arrNum = arrNum;
            this.index = index;
        }
    }
    private static void swap(heapNode[] heapNodes, int index1, int index2){
        heapNode temp = heapNodes[index1];
        heapNodes[index1] = heapNodes[index2];
        heapNodes[index2] = temp;
    }

    /**
     * 打印N个数组整体最大的topK， T:O(KlogN)
     * @param matrix N个一维数组， 每个数组排好序， 但是长度不一样
     * @param topK 打印所有数组中的最大的k个数
     */
    public static void printTopK(int[][] matrix, int topK){
        int heapSize = matrix.length;
        //构建大小为N的大根堆
        heapNode[] heapNodes = new heapNode[heapSize];
        for(int i = 0; i < heapSize; i ++){
            //建堆过程
            //把每个数组中的最后一个值，也就是最大值依次加入到堆里
            int index = matrix[i].length - 1;
            heapNodes[i] = new heapNode(matrix[i][index] , i , index);
            //建堆时的调整过程
            heapInsert(heapNodes, i);
        }
        System.out.println("TOP " + topK + " :");
        for (int i = 0 ; i < topK; i ++){
            if (heapSize == 0){
                break;
            }
            System.out.println(heapNodes[0].value);
            if (heapNodes[0].index != 0){
                //当前最大值在其所在数组不是最后一个数
                //将这个值设置为其数组中的下一个值
                heapNodes[0].value = matrix[heapNodes[0].arrNum][ -- heapNodes[0].index];
            }else {
                //当前最大值在其所在数组是最后一个数
                //将最大值去掉，将堆的大小减1，意思是这个数组已经没有数字了
                swap(heapNodes, 0, -- heapSize);
            }
            //对堆进行调整
            heapipy(heapNodes, 0, heapSize);
        }
    }

    private static void heapInsert(heapNode[] heapNodes, int index){
        while (index != 0){
            //建堆调整， 使堆根一定是堆内最大值
            //并且尽量让大的数在堆的前面
            int parent = (index - 1) / 2;
            if (heapNodes[parent].value < heapNodes[index].value){
                swap(heapNodes, parent, index);
                index = parent;
            }else {
                break;
            }
        }
    }

    private static void heapipy(heapNode[] heapNodes, int index, int size){
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int largest = index;
        while (left < size){
            if (heapNodes[left].value > heapNodes[index].value){
                largest = left;
            }
            if (right < size && heapNodes[right].value > heapNodes[index].value){
                largest = right;
            }
            if (largest != index){
                swap(heapNodes, index, largest);
            }else {
                break;
            }
            index = largest;
            left = index * 2 + 1;
            right = index * 2 + 2;
        }
    }

    /**
     * 数组排序之后相邻数的最大差值
     * 利用桶排序思想T:O(N)， S:O(N)
     * @param arr
     * @return
     */
    public static int maxGap(int[] arr){
        if (arr == null || arr.length < 2){
            return 0;
        }
        int len = arr.length;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        //找到arr中的max，min
        for (int i = 0; i < arr.length; i ++){
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }
        if (max == min ){
            return 0;
        }
        //准备N+1个桶
        int[] maxs = new int[len + 1];
        int[] mins = new int[len + 1];
        int bid = 0;
        boolean[] hasNum = new boolean[len +1];
        //将每个数放入对应的桶中，并计算最大最小值
        for (int i = 0;i < len; i ++){
            bid = bucket(arr[i], len, min, max); //算出桶号
            mins[bid] = hasNum[bid] ? Math.min(mins[bid], arr[i]) : arr[i];
            maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], arr[i]) : arr[i];
            hasNum[bid] = true;//表示这个桶里已经有数字了
        }
        int res = 0;
        int lastMax = 0;
        int i = 0;
        //找到第一个不为空的桶
        while (i < len){
            if (hasNum[i ++]){
                lastMax = maxs[i - 1];
                break;
            }
        }
        //最大差值只可能在某个非空桶的最小值， 减去前一个非空桶的最大值
        for (; i <= len; i ++){
            if (hasNum[i]){
                res = Math.max(res, mins[i] - lastMax);
                lastMax = maxs[i];
            }
        }
        return res;
    }

    public static int bucket(long num,long len, long min, long max){
        return (int) ((num - min) * len / (max - min));
    }


    //快速排序
    public static int[] QuickSort(int[] arr, int start, int end){
        if (arr.length < 1 || start < 0 || end >= arr.length || start > end){
            return null;
        }
        int smallIndex = parttion(arr, start, end);
        if (smallIndex > start){
            QuickSort(arr, start, smallIndex - 1);
        }
        if (smallIndex < end){
            QuickSort(arr, smallIndex + 1, end);
        }
        return arr;
    }
    public static int parttion(int[] arr, int start, int end){
        int pivot = (int) (start + Math.random() * (end - start + 1 ));
        int smallIndex = start - 1;
        Swap(arr, pivot, end);
        for(int i = start; i <= end; i ++){
            if (arr[i] <= arr[end]){
                smallIndex ++;
                if (i > smallIndex){
                    Swap(arr, i, smallIndex);
                }
            }
        }
        return smallIndex;
    }

    public static void Swap(int[] arr, int index1, int index2){
        int temp  = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
    public static void heapSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        for (int i = 0; i < arr.length; i ++){
            heapInsert(arr, i);
        }
        for (int i = arr.length - 1; i != -1; i --){
            Swap(arr,0, i);
            heapify(arr, 0, i);
        }
    }
    public static void heapInsert(int[] arr, int i){
        int parent = 0;
        while ( i != 0){
            parent = (i - 1) / 2;
            if (arr[parent] < arr[i]){
                Swap(arr, parent, i);
                i = parent;
            }else {
                break;
            }
        }
    }

    public static void  heapify(int[] arr, int i, int size){
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        int largest = i;
        while (left < size){
            if (arr[left] > arr[i]){
                largest = left;
            }
            if (right < size && arr[right] > arr[largest]){
                largest = right;
            }
            if (largest != i){
                Swap(arr, i, largest);
            }else {
                break;
            }
            i = largest;
            left = i * 2 + 1;
            right = i * 2 + 2;
        }
    }


}
