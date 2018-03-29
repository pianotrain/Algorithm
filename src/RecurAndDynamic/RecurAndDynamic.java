package RecurAndDynamic;

import java.util.HashMap;
import java.util.Map;

/**
 * 递归与动态规划相关算法
 * create by renshengmiao on 2018/3/15 .
 */
public class RecurAndDynamic {

    /**
     * 换钱的最少货币数 S:O(N*aim)
     * @param arr 正数不重复,每种值代表一种面值的的货币,每种面值的货币可以使用任意张
     * @param aim
     * @return 组成aim的最少货币数, 找不开返回-1
     */
    public static int minCoins1(int[] arr, int aim){
        if (arr == null || arr.length  < 1 || aim < 0){
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n][aim + 1];
        int max = Integer.MAX_VALUE;
        for (int j = 1; j <= aim; j ++) {
            dp[0][j] = max;
            if (j - arr[0] >= 0 && dp[0][j - arr[0]] != max){
                dp[0][j] = dp[0][j - arr[0]] + 1;
            }
        }
        int left = 0;
        for (int i = 1; i < n; i ++ ){
            for (int j = 1; j <= aim; j ++){
                left = max;
                //下标没越界
                if (j - arr[i] >= 0 && dp[i][j - arr[i]] != max){
                    left = dp[i][j - arr[i]] + 1;
                }
                dp[i][j] = Math.min(left, dp[i - 1][j]);
            }
        }
        return dp[n - 1][aim] != max? dp[n - 1][aim] : -1;
    }

    //换钱的最少货币数 S:O(aim), 空间压缩
    public static int minCoins2(int[] arr, int aim){
        if (arr == null || arr.length  < 1 || aim < 0){
            return 0;
        }
        int n = arr.length;
        int[] dp = new int[aim + 1];
        int max = Integer.MAX_VALUE;
        for (int i = 1; i <= aim; i ++){
            dp[i] = max;
            if (i - arr[0] >= 0 && dp[i - arr[0]] != max){
                dp[i] = dp[i - arr[0]] + 1;
            }
        }
        for (int i = 1; i < n ; i ++){
            for (int j = 1; j <= aim; j ++){
                int left = max;
                if (j - arr[i] >= 0 && dp[j - arr[i]] != max){
                    left = dp[j - arr[i]] + 1;
                }
                dp[j] = Math.min(left, dp[j]);
            }
        }
        return dp[aim] != max? dp[aim] : -1;
    }

    /**
     *
     * @param arr 可重复正数, 表示一共有点钱和面值, 每个数字只能使用一次
     * @param aim
     * @return
     */
    public static int minCoins3(int[] arr, int aim){
        if (arr == null || arr.length  < 1 || aim < 0){
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n][aim + 1];
        int max = Integer.MAX_VALUE;
        for (int i = 1; i <= aim; i ++){
            dp[0][i] = max;
        }
        if (aim >= arr[0]){
            dp[0][arr[0]] = 1;
        }
        int leftUp = 0;
        for (int i = 1; i < n; i++){
            for (int j = 1; j <= aim; j ++){
                leftUp = max;
                //这里有变动
                if (j - arr[i] >= 0 && dp[i - 1][j - arr[i]] != max){
                    leftUp = dp[i - 1][j - arr[i]] + 1;
                }
                dp[i][j] = Math.min(leftUp, dp[i -1][j]);
            }
        }
        return dp[n - 1][aim] != max? dp[n - 1][aim] : -1;
    }

    //换钱的方法数
    public static int coins4(int[] arr, int aim){
        if (arr == null || arr.length < 1 || aim < 0){
            return 0;
        }
        int[][] dp = new int[arr.length][aim + 1];
        for (int i = 0; i < arr.length; i ++){
            dp[i][0] = 1;
        }
        for (int i = 1; arr[0] * i <= aim; i ++){
            dp[0][arr[0] * i] = 1;
        }
        for (int i = 1; i < arr.length; i ++){
            for(int j = 1; j<= aim; j ++){
                dp[i][j] = dp[i - 1][j];
                dp[i][j] += j - arr[i] >= 0? dp[i][j - arr[i]] : 0;
            }
        }
        return dp[arr.length - 1][aim];
    }

    public static int[] getDp2(int[] arr){
        int[] dp = new int[arr.length];
        int[] ends = new int[arr.length];
        ends[0] = arr[0];
        dp[0] = 1;
        int right = 0;
        int l = 0;
        int r = 0;
        int m = 0;
        for (int i = 0; i < arr.length; i ++){
            l = 0;
            r = right;
            //二分查找
            while (l <= r){
                m = (l + r) / 2;
                if (arr[i] > ends[m]){
                    l = m + 1;
                }else {
                    r = m - 1;
                }
            }
            right =Math.max(right, l);
            ends[l] = arr[i];
            dp[i] = l + 1;
        }
        return dp;
    }

    public static int[][] getDp(char[] str1, char[] str2){
        //dp[i][j]的含义是str1[0..i]与str2[0..j]的最长公共子序列的长度
        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0] ? 1: 0;
        for (int i = 1; i < str1.length; i ++){
            dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0]? 1 : 0);
        }
        for (int j = 1; j < str2.length; j ++){
            dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1: 0);
        }
        for (int i = 1; i < str1.length; i ++){
            for (int j = 1; j < str2.length; j ++){
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str1[i] == str2[j]){
                    dp[i][j] = Math.max(dp[i][j] , dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp;
    }

    /**
     * 最长公共子序列
     * @param str1  "ASDF"
     * @param str2  "AFRTE"
     * @return   AF
     */
    public static String lcas(String str1, String str2){
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int[][] dp = getDp(chs1, chs2);
        int m = chs1.length - 1;
        int n = chs2.length - 1;
        char[] res = new char[dp[m][n]];
        int index = res.length - 1;
        while (index >= 0){
            if (n > 0 && dp[m][n] == dp[m][n - 1]){
                n --;
            }else if (m > 0 && dp[m][n] == dp[m - 1][n]){
                m --;
            }else {
                res[index --] = chs1[m];
                m --;
                n --;
            }
        }
        return String.valueOf(res);
    }


    public static String lcst1(String string1, String s2){
        char[] chs1 = string1.toCharArray();
        char[] chs2 = s2.toCharArray();
        int[][] dp2 = getDp2(chs1, chs2);
        int end = 0 ;
        int max = 0;
        for (int i = 0; i < chs1.length; i ++){
            for (int j = 0; j < chs2.length; j ++){
                if (dp2[i][j] > max){
                    end = i;
                    max = dp2[i][j];
                }
            }
        }
        return string1.substring(end - max + 1, end + 1);
    }
    public static int[][] getDp2(char[] str1, char[] str2){
        int[][] dp = new int[str1.length][str2.length];
        for (int i = 0; i < str1.length; i ++){
            if (str1[i] == str2[0]){
                dp[i][0] = 1;
            }
        }
        for (int j = 1; j < str2.length; j ++){
            if (str2[j] == str1[0]){
                dp[0][j] =1;
            }
        }
        for (int i = 1; i< str1.length; i ++){
            for (int j = 1; j < str2.length; j ++){
                if (str1[i] == str2[j]){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }
        return dp;
    }

    /**
     * 跳跃游戏
     * @param arr
     * @return
     */
    public static int jump(int[] arr){
        if (arr == null|| arr.length == 0){
            return 0;
        }
        int jump = 0;
        int next = 0;
        int cur = 0;
        for (int i = 0; i < arr.length; i ++){
            //cur >= i 说明跳jump步可以达到i的位置, 此时什么也不做
            //cur < i 说明跳jump步不能到达i位置,要多跳一步才行
            if (cur < i){
                jump ++;
                cur = next;
            }
            next = Math.max(next, i + arr[i]);
        }
        return jump;
    }

    //序列中的最长连续序列.....这个算法是错误的
    public static int longestConsecutive(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int max = 1;
        Map<Integer, Integer> map = new HashMap<>(arr.length);
        for (int i = 1; i < arr.length; i ++){
            if (!map.containsKey(arr[i])){
                map.put(arr[i], 1);
                if (map.containsKey(arr[i] - 1)){
                    max = Math.max(max, merge( map, arr[i] - 1, arr[i]));
                }
                if (map.containsKey(arr[i] + 1)){
                    max = Math.max(max, merge( map, arr[i], arr[i] + 1));
                }
            }
        }
        return max;
    }

    public static int merge(Map<Integer, Integer> map, int less, int more){
        int left = less - map.get(less ) + 1;
        int right = more + map.get(more) - 1;
        int len = right - left + 1;
        map.put(left, len);
        map.put(right, len);
        return len;
    }
}
