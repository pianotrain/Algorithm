package matrix2;

import java.util.*;

/**
 * 数组和矩阵的相关算法2
 * create by renshengmiao on 2018/3/19 .
 */
public class Matrix2 {

    /**
     * 找到无序数组中最小的k个数 T:O(Nlogk)
     * 维护一个k大小的堆, 堆顶为这个堆里最大的数
     * @param arr
     * @param k
     * @return
     */
    public static int[] getKMinNumsByHeap(int[] arr, int k){
        if (k < 1 || k > arr.length){
            return arr;
        }
        int[] kHeap = new int[k];
        for (int i = 0 ; i != k; i ++){
            heapInsert(kHeap, arr[i], i);
        }
        for (int i = k; i < arr.length; i ++){
            if (arr[i] < kHeap[0]){
                //因为是k小的数, 所以有更小的数的时候,要进行替换
                //然后对堆调整, 使堆内最大的数始终在第一个
                kHeap[0] = arr[i];
                heapify(kHeap, 0 , k);
            }
        }
        return kHeap;
    }

    /**
     * 堆排序中的堆调整,第一个是堆内最大值, 并且堆内较大的值都会尽量靠前
     * @param arr 堆
     * @param index
     * @param heapSize
     */
    public static void heapify(int[] arr, int index, int heapSize){
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int largest = index;
        while (left < heapSize){
            if (arr[left] > arr[index]){
                largest = left;
            }
            if (right < heapSize && arr[right] > arr[largest]){
                largest = right;
            }
            if (largest != index){
                swap(arr, index, largest);
            }else {
                break;
            }
            index = largest;
            left = index * 2 + 1;
            right = index * 2 + 2;
        }
    }

    //堆排序的建堆
    //index为0直接插入
    //不为0 时,使用二分法将value插入到合适的位置,但整个堆不一定有序,大概?
    public static void heapInsert(int[] arr, int value, int index){
        arr[index] = value;
        //每次把大的数放在第一个
        while (index != 0){
            int parent = (index - 1) / 2;
            //大的数尽量在前面
            if (arr[parent] < arr[index]){
                swap(arr, parent, index);
                index = parent;
            }else {
                break;
            }
        }
    }
    //位运算交换两个数的值
    public static void swap(int[] arr, int index1, int index2){
        if (index1 == index2){
            //抑或交换法,必须是两个数字交换, 自己和自己交换
            return;
        }
        arr[index1] = arr[index1] ^ arr[index2];
        arr[index2] = arr[index1] ^ arr[index2];
        arr[index1] = arr[index1] ^ arr[index2];
    }

    /**
     * 找到无序数组中最小的k个数 T:O(N) BFPRT算法, (线性查找算法)
     * @param arr
     * @param k
     * @return
     */
    public static int[] getKinNumsByBFPRT(int[] arr, int k){
        if (k < 1 || k > arr.length){
            return arr;
        }
        //利用BFPRT算法找到第k小的数
        int minKth = getKthMinByBFPRT(arr, k);
        int[] res = new int[k];
        int index = 0;
        for (int i = 0 ; i < arr.length; i ++){
            if (arr[i] < minKth){
                res[index ++] = arr[i];
            }
        }
        for (; index != k; index ++){
            res[index] = minKth;
        }
        return res;
    }

    public static int getKthMinByBFPRT(int[] arr, int k){
        int[] copyArry = copyArray(arr);
        //递归找到arr中第k小的数
        return select(arr, 0 , arr.length - 1, k - 1);
    }

    public static int[] copyArray(int[] arr){
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i ++){
            res[i] = arr[i];
        }
        return res;
    }

    public static int select(int[] arr, int begin, int end, int i){
        if (begin == end){
            return arr[begin];
        }
        int pivot = medianOfMedians(arr, begin, end);
        //将数组用这个pivot划分成三部分
        //比pivot小的在左边,等于的在中间, 大于的在右边
        //返回分界点
        int[] pivotRange = partition(arr, begin, end, pivot);
        if (i >= pivotRange[0] && i <= pivotRange[1] ){
            //i在中间,直接返回
            return arr[i];
        }else if (i < pivotRange[0]){
            return select(arr, begin, pivotRange[0] - 1, i);
        }else {
            //在第k小的数的左边,应该在右边找第k小的数,所以
            return select(arr, pivotRange[1] + 1, end, i);
        }
    }

    public static int medianOfMedians(int[] arr, int begin, int end){
        int num = end - begin + 1;
        //5个数一组
        int offSet = num % 5== 0? 0 : 1;
        int[] mArr = new int[num / 5 + offSet];
        //找到每组数的中位数
        for (int i = 0; i < mArr.length; i ++){
            int beginI = i * 5 + begin;
            int endI = beginI + 4;
            mArr[i] = getMedian(arr, beginI, Math.min(end, endI));
        }
        //再找到这些中位数的中位数
        return select(mArr, 0 , mArr.length - 1, mArr.length/2 );
    }

    public static int[] partition(int[] arr, int begin, int end, int pivotValue){
        int small = begin - 1;
        int cur = begin;
        int big = end +1;
        while (cur != big){
            if (arr[cur] < pivotValue){
                swap(arr, ++small, cur ++);
            }else if (arr[cur] > pivotValue){
                swap(arr, cur, -- big);
            }else {
                cur ++;
            }
        }
        int[] range = new int[2];
        range[0] = small + 1;
        range[1] = big - 1;
        return range;
    }

    //一组数中的中位数
    public static int getMedian(int[] arr, int begin, int end){
        insertionSort(arr, begin, end);
        int sum = begin + end;
        //中位数下标, 如果数组个数为偶数, 规定找到下中位数
        //sum为奇数时,数组长度为偶数, sum为偶数时,数组长度为奇数
        int mid = (sum / 2) + (sum % 2);
        return arr[mid];
    }

    //插入排序
    public static void insertionSort(int[] arr, int begin, int end){
        for (int i = begin + 1; i != end + 1; i ++){
            for (int j = i; j != begin; j --){
                if (arr[j - 1] > arr[j]){
                    swap(arr, j - 1, j );
                }else {
                    break;
                }
            }
        }
    }


    public static int getMinSortLength(int[] arr){
        if (arr == null || arr.length < 2){
            return 0;
        }
        int min = arr[arr.length - 1];
        int nowMinIndex = -1;
        for (int i = arr.length - 2; i != -1; i --){
            if (arr[i] > min){
                nowMinIndex = i;
            }else {
                min = arr[i];
            }
        }
        if (nowMinIndex == -1){
            return 0;
        }
        int max = arr[0];
        int nowMaxIndex = 0;
        for (int i = 1; i < arr.length - 1; i ++){
            if (arr[i] < max){
                nowMaxIndex = i;
            }else {
                max = arr[i];
            }
        }
        return nowMaxIndex - nowMinIndex + 1;
    }

    //打印一个出现次数在一半以上的数
    public static void printHalfMajor(int[] arr){
        int cand = 0;
        int times = 0;
        for (int i = 0; i < arr.length; i ++){
            if (times == 0){
                cand = arr[i];
                times ++;
            }else if (cand == arr[i]){
                times ++;
            }else {
                times --;
            }
        }
        times = 0;
        for (int i = 0;i < arr.length; i ++){
            if (arr[i] == cand){
                times ++;
            }
        }
        if (times > arr.length/2){
            System.out.println(cand);
        }else {
            System.out.println("no such number");
        }
    }

    /**
     * 给定长度N的数组和k, 打印所有数组中出现次数大于N/k的数字
     * @param arr
     * @param k
     */
    public static void printKMajor(int[] arr, int k){
        if (k < 2){
            //不符合题目要求
            return;
        }
        Map<Integer, Integer> cands = new HashMap<>();
        for (int i = 0; i < arr.length; i ++){
            if (cands.containsKey(arr[i])){
                cands.put(arr[i], cands.get(arr[i]) + 1);
            }else if (cands.size() == k - 1){
                //出现了k个不重复的数字,此时所有的候选都减一, 不足1的去除
                allCandsMinusOne(cands);
            }else {
                cands.put(arr[i], 1);
            }
        }
        Map<Integer, Integer> reals = getReals(arr, cands);
        boolean hasPrint = false;
        for (Map.Entry<Integer,Integer> entry : reals.entrySet()){
            //判断出现次数是否真的大于 arr.length / k
            if (entry.getValue() > arr.length / k){
                hasPrint = true;
                System.out.println(entry.getKey());
            }
        }
        if (!hasPrint){
            System.out.println("no such number");
        }
    }

    private static void allCandsMinusOne(Map<Integer, Integer> cands){
        List<Integer> removeList = new ArrayList<>();
        for (Map.Entry<Integer,Integer> entry : cands.entrySet()){
            if (entry.getValue() == 1){
                removeList.add(entry.getKey());
            }else {
                entry.setValue(entry.getValue() - 1);
            }
        }
        for (int i : removeList){
            cands.remove(i);
        }
    }

    private static Map<Integer,Integer> getReals(int[] arr, Map<Integer,Integer> cands){
        Map<Integer, Integer> reals = new HashMap<>();
        for (int i = 0; i < arr.length; i ++){
            int curNum = arr[i];
            if (cands.containsKey(curNum)) {
                if (reals.containsKey(curNum)){
                    reals.put(curNum, reals.get(curNum) + 1);
                }else {
                    reals.put(curNum, 1);
                }
            }
        }
        return reals;
    }

    /**
     * 每行每列都排好序, 判断k是否在矩阵中
     * @param arr
     * @param k
     * @return
     */
    public static boolean isContains(int[][] arr, int k){
        int row = 0;
        int col = arr[0].length - 1;
        while (row < arr.length && col > -1){
            if (arr[row][col] == k){
                return true;
            }else if (arr[row][col] > k){
                col --;
            }else {
                row ++;
            }
        }
        return false;
    }

    /**
     * 最长可整合数组的长度, 可整合数组的定义为, 将数组排序后, 每相邻的数字相差的绝对值都为1
     * @param arr
     * @return
     */
    public static int getLIL2(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int len = 0;
        int max = 0;
        int min = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length ; i ++){
            max = Integer.MAX_VALUE;
            min = Integer.MIN_VALUE;
            for (int j = i ; j < arr.length; j ++){
                if (set.contains(arr[j])){
                    break;
                }
                set.add(arr[j]);
                max = Math.max(max, arr[j]);
                min = Math.min(min, arr[j]);
                if (max - min == j - i){
                    //可整合数组的判断方式, 数组中 最大值2-最小值1 == 数组的长度
                    len = Math.max(len , j - i + 1);
                }
            }
            set.clear();
        }
        return len;
    }

    /**
     * 不重复打印排序数组中相加和为给定值的所有二元组
     * @param arr 排过序的数组
     * @param k
     */
    public static void printUniquePair(int[] arr, int k){
        if (arr == null || arr.length < 2){
            return;
        }
        int left = 0;
        int right = arr.length - 1;
        int preLeft = -1;
        while (left != right){
            int sum = arr[left] + arr[right];
            if ( sum == k ){
                //如果sum == k ,打印
                if (preLeft == -1 || arr[left] != arr[preLeft]){
                    System.out.println(arr[left] + "," + arr[right]);
                    preLeft = left - 1;
                }
                left ++;
                right --;
            }else if (sum > k){
                right --;
            }else {
                left ++;
            }
        }
    }

    /**
     * 不重复打印排序数组中相加和为给定值的所有三元组
     * @param arr
     * @param k
     */
    public static void printUniqueTraid(int[] arr, int k){
        if (arr == null || arr.length < 3){
            return;
        }
        for (int i = 0; i < arr.length - 2; i ++){
            if ( i == 0 || arr[i - 1] != arr[i]){
                printRest(arr, i , i +1, arr.length - 1, k - arr[i]);
            }
        }
    }

    public static void printRest(int[] arr, int f, int l, int r, int k){
        while (l < r){
            int sum = arr[l] + arr[r];
            if (sum > k){
                r --;
            }else if (sum < k){
                l ++;
            }else {
                if ( (l == f + 1) || arr[l - 1] != arr[l]){
                    System.out.println(arr[f] + "," + arr[l] + "," + arr[r]);
                }
                l ++;
                r --;
            }
        }
    }

    /**
     * 未排序数组中累加和小于或等于给定值的最长子数组长度
     * @param arr
     * @param k
     * @return
     */
    public static int getMaxLength2(int[] arr , int k){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int[] sumArr = new int[arr.length];
        //sum[i] == s(0...i - 1),且sum递增,
        int sum = arr[0];
        int len = 0;
        for (int i = 1 ; i < arr.length; i ++){
            sum += arr[i];
            int indexFromSumArr = getIndexFromSumArr(sumArr, i, sum - k);
            len = Math.max(len, indexFromSumArr == -1? 0 : i - indexFromSumArr + 1);
            sumArr[i] = Math.max(sum - arr[i],sumArr[i - 1]);
        }
        return len;
    }

    public static int getIndexFromSumArr(int[] sumArr, int index, int max){
        int left = 0;
        int right = index;
        int mid=0;
        int res = -1;
        while (left <= right){
            mid = (left + right) / 2;
            if (sumArr[mid] < max){
                left = mid + 1;
            }else {
                right = mid - 1;
                res = mid;
            }
        }
        return res;
    }

    //自然数数组排序
    public static void sort1(int[] arr){
        if (arr == null ||arr.length == 0){
            return;
        }
        int temp = 0 ;
        int next = 0;
        for (int i = 0; i < arr.length; i ++){
            temp = arr[i];
            while (arr[i] != i + 1){
                next = arr[temp - 1];
                arr[temp - 1] = arr[i];
                arr[i] = next;
            }
        }
    }

    //数组中的最大累加和
    //如果数组全是负数, 那么最大累加和只可能是最大的那个负数
    //如果有正数, 累加时,当累加和小于0时, 说明这一段必定不是最大累加和,那么从这个下标开始重新累加,累加过程中,任何大于0的和都可能是最大累加和
    public static int maxSubSum(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int cur = 0;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i <arr.length; i++){
            cur += arr[i];
            maxSum = Math.max(maxSum, cur);
            cur = cur < 0? 0: cur;
        }
        return maxSum;
    }

    public static void setBoardMap(int[][] map , int[][] right, int[][] down){
        int r= map.length;
        int c = map[0].length;
        if (map[r - 1][c - 1] == 1){
            right[r - 1][c - 1] = 1;
            down[r - 1][c - 1] = 1;
        }
        for (int i = r - 2;i != -1; i --){
            if (map[i][c - 1] == 1){
                right[i][c - 1] = 1;
                down[i][c - 1] = down[i + 1][c - 1] + 1;
            }
        }
        for (int i = c  -2; i != -1;i --){
            if (map[r -1][i] == 1){
                right[r - 1][i] = right[r - 1][i + 1] + 1;
                down[r - 1][i] = 1;
            }
        }
        for (int i = r - 2; i != -1; i --){
            for (int j = c - 2; j != -1; j --){
                if (map[i][j] == 1){
                    right[i][j] = right[i][j + 1] + 1;
                    down[i][j] = down[i + 1][j] +1;
                }
            }
        }
    }

    /**
     * 边界都是1的最大 正方形 大小, T:O(N^3), 需要做预处理
     * @param map
     * @return
     */
    public static int getMAxSize(int[][] map){
        int[][] right = new int[map.length][map[0].length];
        int[][] down = new int[map.length][map[0].length];
        //预处理矩阵
        //right[i][j]代表map[i][j]向右右多少个连续的1
        //down[i][j]代表map[i][j]向下有多少个连续的1
        setBoardMap(map, right, down);
        for (int size = Math.min(map.length, map[0].length); size > - 1; size --){
            //
            if (hasSizeOBorder(size, right, down)){
                return size;
            }
        }
        return 0;
    }

    public static boolean hasSizeOBorder(int size, int[][] right, int[][] down){
        for (int i = 0; i < right.length - size + 1; i ++){
            for (int j = 0; j < right[0].length - size + 1; j ++){
                if (right[i][j] >= size && down[i][j] >= size
                        && down[i][j + size - 1] >= size
                        && right[i + size - 1][j] >= size){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 不包含本位置值的累乘数组 T:O(N), S:O(1)
     * 首先是可以使用除法的方法, 所有非0数累乘为all, all/arr[i] 即是当前位置的结果, 但要注意整个数组是否有0
     * @param arr
     * @return
     */
    public static int[] product1(int[] arr){
        if (arr == null || arr.length < 2){
            return arr;
        }
        int count = 0;
        int[] res = new int[arr.length];
        int all = 1;
        for (int i = 0; i < arr.length; i ++){
            if (arr[i] != 0){
                all *= arr[i];
            }else {
                count ++;
            }
        }
        if (count > 1){
            return res;
        }else if (count == 1) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == 0) {
                    res[i] = all;
                }
            }
        }else {
            for (int i = 0; i < arr.length; i ++){
                res[i] = all / arr[i];
            }
        }
        return res;
    }

    /**
     * 不使用乘法的方法, 先从左往右累乘, 在从右往左累乘, 复用res数组, 注意头尾
     * @param arr
     * @return
     */
    public static int[] product2(int[] arr){
        if (arr == null || arr.length < 2){
            return arr;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i ++){
            if (i == 0){
                res[i] = arr[i];
            }else {
                res[i] = res[i - 1] * arr[i];
            }
        }
        res[arr.length - 1] = res[arr.length - 2];
        int temp = 1;
        for (int i = arr.length - 1; i != 0; i --){
            res[i] = res[i - 1] * temp;
            temp = temp * arr[i];
        }
        res[0] = temp;
        return res;
    }

    /**
     * 给定一个有序数组, 调整使这个数组的左边部分元素不重复且升序, 右边无要求
     * @param arr
     */
    public static void leftUnique(int[] arr){
        int i = 1;
        int u = 0;
        while (i != arr.length){
            if (arr[i ++] != arr[u]){
                swap(arr, ++ u, i - 1);
            }
        }
    }

    /**
     * 数组的partition调整, 数组只有0,1,2 ;划分成3部分
     * 换一种问法:红黑栏三种颜色的球, 划分
     * 换一种问法:有一个数组. 给定一个k值, 比k小的在左边, 等于k的在中间, 大于k的在右边
     * @param arr
     */
    public static void parttitionSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        int index = 0;  //在left + 1 .... index上都是1, index是1区域最左边的位置
        int left = -1;  //在0...left上都是0
        int right = arr.length ;  //在 right...length-1上都是2
        while (index != right){
            if (arr[index] == 0){
                swap(arr, index ++, ++ left );
            }else if (arr[index] == 1){
                index ++;
            }else {
                swap(arr, index, -- right);
            }
        }
    }

    /**
     * 求最短通路径, 1代表有路,0代表无路,每一个位置只要不越界,都有上下左右4个方向
     * 宽度优先遍历   T:O(N*M) S:O(N*M)
     * @param map 1 0 1 1 1
     *            1 0 1 0 1
     *            1 1 1 0 1
     *            0 0 0 0 1
     * @return 12   从左上角到右下角的最短路径
     */
    public static int minPathValue(int[][] map){
        if (map == null ||map.length == 0 || map[0].length == 0
                || map[0][0] == 0 || map[map.length - 1][map[0].length - 1] == 0){
            return 0;
        }
        Queue<Integer> rowQ = new LinkedList<>();
        Queue<Integer> colQ = new LinkedList<>();
        int res = 0;
        int[][] m = new int[map.length][map[0].length];
        m[0][0] = 1;
        rowQ.add(0);
        colQ.add(0);
        int r = 0;
        int c = 0;
        while (! rowQ.isEmpty()){
            r = rowQ.poll();
            c = colQ.poll();
            if (r == map.length - 1&& c == map[0].length - 1){
                return m[r][c];
            }
            walkTo(m[r][c], r - 1, c, map, m, rowQ, colQ);
            walkTo(m[r][c], r + 1, c, map, m, rowQ, colQ);
            walkTo(m[r][c], r, c - 1, map, m, rowQ, colQ);
            walkTo(m[r][c], r, c + 1,map, m, rowQ, colQ);
        }
        return res;
    }

    private static void walkTo(int pre, int toRow, int toCol, int[][] map, int[][] m, Queue<Integer> rowQ, Queue<Integer> colQ){
        if (toRow < 0 || toRow == map.length || toCol < 0 || toCol == map[0].length || map[toRow][toCol] != 1
                //不能越界 && 下一步上可以走 && 下一步还没有走过,走过的路不能再走
                || m[toRow][toCol] != 0){
            return;
        }
        m[toRow][toCol] = pre + 1;
        rowQ.add( toRow);
        colQ.add(toCol);
    }

    public static int missNum(int[] arr){
        int left = 0;
        int right = arr.length;
        while (left < right){
            if (arr[left] == left + 1){
                left ++;
            }else if (arr[left] <= left || arr[left] > right || arr[arr[left] - 1] == arr[left]){
                arr[left] = arr[ --right];
            }else {
                swap(arr, left, arr[left ] - 1);
            }
        }
        return left + 1;
    }
}
