package stringUtils;

import bTree.TreeUtils;

/**
 * 操作字符串的相关算法,以及kmp算法
 * create by renshengmiao on 2018/3/16 .
 */
public class StringUtils {

    //判断两个字符串是否互为变形词
    //如果字符编码超过255, 可以用哈希表代替map
    public static boolean isDeformation(String str1, String str2){
        //长度必须相等
        if (str1 == null || str2 == null|| str1.length() != str2.length()){
            return false;
        }
        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();
        int[] map = new int[256]; //假设字符编码值在0 - 255之间
        for (int i = 0; i < ch1.length; i ++){
            map[ch1[i]] ++; //统计str1中字符的出现次数
        }
        for (int i = 0; i< ch2.length; i ++){
            //遍历str2, 如果有出现相同的字符, 则递减
            //如果递减变成了负数,则false
            if (map[ch2[i]] --  == 0){
                return false;
            }
        }
        return true;
    }

    //字符串中数字子串的求和
    //str = "A1DSF2FDSA32F" return 35
    // str "-1--4" return -1+4 = 3
    public static int numSum(String str){
        if (str == null || str.length() == 0){
            return 0;
        }
        char[] chars = str.toCharArray();
        int num = 0;
        boolean posi = true;
        int res = 0;
        int cur = 0;
        for (int i= 0; i < chars.length; i ++){
            cur = chars[i] - '0';
            if (cur < 0 || cur > 9){
                res += num;
                num = 0;
                if (chars[i] == '-'){
                    if (i - 1 > -1 && chars[i - 1] == '-'){
                        posi = ! posi;
                    }
                }else {
                    posi = true;
                }
            }else {
                num = num * 10 + (posi? cur : - cur);
            }
        }
        res += num;
        return res;
    }

    public static String removeKZeros(String str, int k){
        if (str == null || str.length() == 0|| k < 1){
            return str;
        }
        char[] chars = str.toCharArray();
        int count = 0;
        int start = -1;
        for (int i = 0 ; i < chars.length; i ++){
            if (chars[i] == '0'){
                count ++;
                start = start == -1? i: start;
            }else {
                if (count == k){
                    while (count -- != 0){
                        chars[start ++] = 0;
                    }
                }
                count = 0;
                start = -1;
            }
        }
        if (count == k){
            while (count -- != 0){
                chars[start ++] = 0;
            }
        }
        return String.valueOf(chars);
    }

    //判断两个字符串是否互为旋转词, 如 "abcde" "bcdea" "cdeab" "bcdea"
    public static boolean isRotation(String a, String b){
        if (a == null || b == null || a.length() != b.length()){
            return false;
        }
        //用两个b拼起来组成b2, 如果互为旋转词, 那么a必为b2的子串
        String b2 = b + b;
        return TreeUtils.getIndexOfByKMP(b2, a) != -1; //kmp
    }

    //检查chars是否是合法数字形式
    public static boolean isValid(char[] chars){
        if (chars[0] != '-' && (chars[0] - '0' < 0 || chars[0] - '9' > 9)){
            return false;
        }
        if (chars[0] == '0' && chars.length > 1){
            return false;
        }
        if (chars[0] == '-' && ( chars.length == 1 || chars[1] == '0' )){
            return false;
        }
        for (int i = 1; i < chars.length; i ++){
            if (chars[i] - '0' < 0 || chars[0] - '9' > 9){
                return false;
            }
        }
        return true;
    }

    //将一个字符串转换成int型的数字, 注意溢出
    public static int convert(String str){
        if (str == null || str.length() == 0){
            return 0;
        }
        char[] chars = str.toCharArray();
        if (!isValid(chars)){
            return 0;
        }
        boolean posi = chars[0] != '-';
        int minq = Integer.MIN_VALUE / 10;
        int minr = Integer.MIN_VALUE % 10;
        int res = 0 ;
        int cur = 0;
        for (int i = posi? 0 : 1; i < chars.length; i ++){
            cur ='0' - chars[i];
            if ((res < minq) || (res == minq ) && cur < minr){
                return 0;
            }
            res = res * 10 + cur;
        }
        if (posi && res == Integer.MIN_VALUE){
            //posi为true代表正数,res为负数形式最大值的绝对值
            //int型的最大负数比最大整数的绝对值大于1
            return 0;
        }
        return posi? -res : res;
    }

    /**
     * 替换字符串中连续出现的指定字符串,本方法在检索from字符串中有错误, 需改成kmp算法
     * @param str
     * @param from
     * @param to
     * @return
     */
    public static String replace(String str, String from , String to){
        if (str == null || from == null || str.equals("") || from.equals("")){
            return str;
        }
        char[] cha1 = str.toCharArray();
        char[] cha2 = from.toCharArray();
        int match = 0;
        for (int i = 0; i < cha1.length; i ++){
            //匹配from子串
            if (cha1[i] == cha2[match ++]){
                if (match == cha2.length){
                    //完全匹配到后, 将str中的from改成0000形式
                    clear(cha1, i, cha2.length);
                    match = 0;
                }
            }else {
                match = 0;
            }
        }
        String res = "";
        String cur = "";
        //生成新字符串
        for (int i = 0; i < cha1.length; i ++){
            if (cha1[i] != 0){
                cur += String.valueOf(cha1[i]);
            }
            if (cha1[i] == 0 && ( i == 0 || cha1[i - 1] != 0)){
                res += cur + to;
                cur = "";
            }
        }
        if (!cur.equals( "")){
            res += cur;
        }
        return res;
    }

    public static void clear(char[] chas, int end, int len){
        while (len -- != 0){
            chas[end --] = 0;
        }
    }

    public static int twoPart(int[] arr, int value){
        int l = 0;
        int r = arr.length - 1;
        int m =0;
        while (l <= r){
            m = (l + r)/ 2;
            if (arr[m] > value){
                r = m -1;
            }else {
                l = m + 1;
            }
        }
        System.out.println(arr[r]);
        return arr[l];
    }


    /**
     * 有序但含有空的数组中查找字符串
     * @param strings [null,"a",null,"b",null, "c", "b"]
     * @param str  "b"
     * @return 3
     */
    public static int getcharIndex(String[] strings, String str){
        if (strings == null || strings.length == 0 || str == null){
            return -1;
        }
        int res = -1;
        int left = 0;
        int right = strings.length -1;
        int m = 0;
        int i = 0;
        //二分查找
        while (left <= right){
            m = (left + right) / 2;
            if (strings[m] != null && strings[m].equals(str)){
                //找到不为空并且相等, 记录, 继续往前找
                right = m - 1;
                res = right;
            }else if (strings[m] != null){
                //不为空,但不相等,比较
                if (strings[m].compareTo(str) < 0){
                    //找右边
                    left = m + 1;
                }else {
                    //找左边
                    right = m - 1;
                }
            }else {
                //为空
                i = m;
                while (strings[i] == null && -- i > left){
                    //一直往左找
                    ;
                }
                if (i < left || strings[i].compareTo(str) < 0){
                    //如果左边全是null,或者比较后,找右边
                    left = m + 1;
                }else {
                    //找到或者比较后, 找左边
                    res = strings[i].equals(str) ? i: res;
                    right = i - 1;
                }

            }
        }
        return res;
    }

    public static void replace(char[] chars){
        if (chars == null || chars.length == 0){
            return ;
        }
        int num = 0;
        int len ;
        for (len = 0; len < chars.length && chars[len] != 0; len ++){
            //没遇到空字符之前
            if (chars[len] == ' '){
                num ++;
            }
        }
        int j = len + num * 2 - 1;
        for (int i = len - 1; i > -1; i --){
            if (chars[i] != ' '){
                chars[j --] = chars[i];
            }else {
                chars[j --] = '0';
                chars[j --] = '2';
                chars[j --] = '%';
            }
        }
    }

    public static void modify(char[] chas){
        if (chas == null || chas.length == 0){
            return;
        }
        int len = chas.length - 1;
        for (int i = chas.length - 1; i > -1; i --){
            if (chas[i] != '*'){
                chas[len --] = chas[i];
            }
        }
        for (; len > -1;){
            chas[len --] = '*';
        }
    }

    /**
     * 反转字符串 如 I'm a student -> student a I'm, 只对单词逆序, 空格不做要求
     * @param chars
     */
    public static void rotateWord(char[] chars){
        if (chars == null || chars.length == 0){
            return;
        }
        reverse(chars, 0, chars.length - 1);
        int l = -1;
        int r = -1;
        for (int i = 0; i < chars.length; i ++){
            if (chars[i] != ' '){
                l = i == 0 || chars[i - 1] == ' '? i: l;
                r = i == chars.length - 1 || chars[i + 1] == ' '? i : r;
            }
            if (l != -1 && r != -1){
                reverse(chars, l, r);
                l = -1;
                r = -1;
            }
        }
    }
    public static void reverse(char[] chars, int start, int end){
        char temp;
        while (start < end){
            temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
            start ++;
            end --;
        }
    }

    /**
     * 如 "ABCDE" , 3 -> "DEABC"
     * @param chars
     * @param size
     */
    public static void rotate1(char[] chars, int size){
        if (chars == null || chars.length == 0 || size < 1){
            return;
        }
        //先反转前一部分, 在反转后一部分 -> CBAED
        reverse(chars, 0 , size - 1);
        reverse(chars, size, chars.length - 1);
        //整个反转 -> DEABC
        reverse(chars, 0, chars.length - 1);
    }
}
