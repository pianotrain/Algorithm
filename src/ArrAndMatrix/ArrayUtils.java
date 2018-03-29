package ArrAndMatrix;

import java.util.HashMap;
import java.util.Map;

/**
 * 数组相关
 * Created by rsmno on 2018/3/10.
 */
public class ArrayUtils {

    /**
     * 未排序正数数组中累加和为给定值的最长子数组长度
     * 数组元素与sum必须为正数， 否则left可能越界
     * @param arr
     * @param sum 指定的和的值
     * @return
     */
    public static int getMaxLengthOfSum(int[] arr, int sum){
        if (arr == null || arr.length < 1 || sum < 0){
            return 0;
        }
        int left = 0;
        int right = 0;
        int sumRecord = arr[0]; //记录当前子数组的和
        int len = 0;  //记录子数组和为sum的最大长度
        while (right < arr.length){
            //如果当前子数组和小于sum，right右移， 并累加
            if (sumRecord < sum){
                right ++;
                if (right == arr.length){
                    break;
                }
                sumRecord += arr[right];
//                sumRecord += arr[right ++];//错误示范
            }else if (sumRecord > sum){
                //如果当前子数组的和大于sum，left右移并缩减sumRecord
                sumRecord -= arr[left ++];
            }else {
                //满足和， 记录长度并left右移，缩减
                len = Math.max(len, right - left + 1);
                sumRecord -= arr[left ++];
            }
        }
        return len;
    }

    /**
     * 给定一个无序数组arr， 其中元素可正可负，给定一个正数sum，求arr中所有子数组的累加和为sum的最长子数组长度
     * @param arr
     * @param sum
     * @return
     */
    public static int getMaxLengthOfSumPlus1(int[] arr, int sum){
        if (arr == null || arr.length < 1 ){
            return 0;
        }
        //key为从0开始的所有子数组和 ，即s(i),代表arr[0,i]的和
        //value 为和为s(i)时，最小的子数组长度
        //那么，arr[j + 1, i]的和 == s(i) - s(j) = arr[0,i] - arr[0, j]
        Map<Integer, Integer> sumMap = new HashMap<>(arr.length);
        sumMap.put(0, -1);//重要
        int sumRecord = 0;//arr[0, i]的和
        int len = 0;//最大子数组长度
        for (int i = 0; i < arr.length; i ++){
            sumRecord += arr[i];
            int need = sumRecord - sum;
            if (sumMap.containsKey(need)){
                len = Math.max(i - sumMap.get(need), len);
            }
            if (!sumMap.containsKey(sumRecord)){
                sumMap.put(sumRecord, i);
            }
        }
        return len;
    }

    /**
     * 求子数组包含正数与负数数量相等的最大子数组长度
     * @param arr
     * @return
     */
    public static int getMaxLengthOfPostiveEqualsNegative(int[] arr){
        if (arr == null || arr.length < 1){
            return 0;
        }
        int flag = 0;
        int len = 0;
        Map<Integer, Integer> flagMap = new HashMap<>(arr.length);
        flagMap.put(0, -1);
        for (int i = 0; i < arr.length; i++){
            if (arr[i] > 0){
                flag ++;
            }else if (arr[i] < 0){
                flag --;
            }
            if (flagMap.containsKey(flag)){
                len = Math.max(len,i - flagMap.get(flag));
            }
            if (!flagMap.containsKey(flag)){
                flagMap.put(flag, i);
            }
        }
        return len;
    }

    /**
     * arr只包含0,1  ，求子数组包含0和1的数量相等的最大子数组长度
     * @param arr
     * @return
     */
    public static int getMaxLengthOf1NumberEquals0Number(int[] arr){
        if (arr == null || arr.length < 1){
            return 0;
        }
        int flag = 0;
        int len = 0;
        Map<Integer, Integer> flagMap = new HashMap<>(arr.length);
        flagMap.put(0, -1);
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == 0){
                flag ++;
            }else if (arr[i] == 1){
                flag --;
            }
            if (flagMap.containsKey(flag)){
                len = Math.max(len,i - flagMap.get(flag));
            }
            if (!flagMap.containsKey(flag)){
                flagMap.put(flag, i);
            }
        }
        return len;
    }

}
