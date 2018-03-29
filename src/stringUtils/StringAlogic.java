package stringUtils;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by rsmno on 2018/3/16.
 */
public class StringAlogic {

    public static void rotate2(char[] chars, int size){
        if (chars == null || chars.length == 0 || size <1){
            return;
        }
        int start = 0;
        int end = chars.length - 1;
        int lPart = size;
        int rPart = chars.length - size;
        int s = Math.max(lPart, rPart);
        int d = lPart - rPart;
        while (true){
            exchange(chars, start, end, s);
            if (d == 0){
                break;
            }else if(d > 0){
                start += s;
                lPart = d;
            }else {
                end -= s;
                rPart = -d;
            }
            s = Math.min(lPart , rPart);
            d = lPart - rPart;
        }
    }
    public static void exchange(char[] chars, int start, int end, int size){
        int i = end - size + 1;
        char temp = 0;
        while (size -- > 0){
            temp = chars[start];
            chars[start] = chars[i];
            chars[i] = temp;
            start ++;
            i ++;
        }
    }

    //数组中两个字符串的最小距离
    public static int minDistance(String[] strs, String s1, String s2){
        if(s1 == null || s2 == null){
            return -1;
        }
        if (s1.equals(s2)){
            return 0;
        }
        int min = Integer.MAX_VALUE;
        int last1 = -1; //记录最近一次出现s1的位置，
        int last2 = -1; //记录最近一次出现s2的位置
        for (int i = 0 ; i < strs.length; i ++){
            if (strs[i] .equals(s1)){
                //遍历到s1时， i-last2就是s1和左边离他最近的s2之间的距离
                min = Math.min(min, last2 != -1? i - last2: min);
                last1 = i;
            }
            if (strs[i].equals(s2)){
                //遍历到s2时，i-last1就是s2和左边离他最近的s1之间的距离
                min = Math.min(min, last1 != -1? i - last1:min);
                last2 = i;
            }
        }
        return min == Integer.MAX_VALUE? -1 : min;
    }

    /**
     * 括号字符串的有效性和最大有效长度
     * @param chars
     * @return
     */
    public static int maxlength(char[] chars){
        if (chars == null || chars.length == 0){
            return 0;
        }
        int[] dp = new int[chars.length];
        int pre = 0;
        int res = 0;
        for(int i = 0; i < chars.length ; i ++){
            //当前字符为）才有可能配对，如果是（是不可能配对的
            if (chars[i] == ')'){
                //如果是（（）），dp[3] = 1, 需要找到最左边的（才能完成这次配对
                pre = i - dp[i - 1] - 1;
                if (pre >= 0 && dp[pre] == '('){
                    //有可能是（）（（）），需要加上前面的（）的长度才算是最终结果
                    dp[i] = dp[i - 1] + 2 + pre > 0? dp[pre - 1] : 0;
                }
            }
            res =Math.max(res , dp[i]);
        }
        return res;
    }

    /**
     * 公式字符串求值
     * @param exp 表示一个公式， 公式里有整数，加减乘除和左右括号等
     * @return 公式的计算结果
     */
    public static int getValue(String exp){
        return value(exp.toCharArray(), 0)[0];
    }

    /**
     * 从左到右遍历str， 开始遇到‘（’时就进行递归，当发现str遍历完或遇到字符“）”就结束
     * 遇到‘（’就交给下一层递归处理，自己只要接收结果就可以，对于当前递归可以看做计算公式都是不含有括号的
     * 比如，对于value(str, 0)来说， 实际上计算的公式是3*9 + 7，“4+5”的部分交给递归过程value（str，3）处理
     * 拿到9之后， 再从字符“+”继续
     * @param chars
     * @param i 遍历开始的下标
     * @return 两个结果， 下标0 为本次递归返回的结果， 下标1为本次遍历到‘）’的下标值
     */
    public static int[] value(char[] chars, int i){
        Deque<String> deque = new LinkedList<>();
        int pre = 0;
        int[] bra = null;
        //从左到右遍历， 遇到“）”或遍历完结束
        while (i < chars.length && chars[i] != ')'){
            if (chars[i] >= '0' || chars[i] <= '9'){
                //一直是数字字符，计算数字
                pre += pre * 10 + chars[i ++] - '0';
            }else if (chars[i] == '('){
                //遇到“（”，交给下一层递归，接收递归结果
                bra = value(chars, i + 1);
                i = bra[1] + 1;//从下一层递归结束后的位置开始
                pre = bra[0];//递归结果
            }else {
                //遇到 +-*/字符，前一个数字加入队列
                addNum(deque, pre);
                //将 +-*/字符加入队列
                deque.addLast(String.valueOf(chars[i ++]));
                pre = 0;
            }
        }
        addNum(deque, pre);
        return new int[]{getNum(deque),i};
    }

    //把数字加入队列
    public static void addNum(Deque<String> deque, int num){
        //队列不为空， 前一个字符可能为*/
        if (!deque.isEmpty()){
            int cur = 0;
            String top = deque.pollLast();
            if(top.equals("+") || top.equals("-")){
                //重新放入队列
                deque.addLast(top);
            }else {
                //如果是*/先计算，将结果放入队列
                cur = Integer.valueOf( deque.pollLast() );
                num = top.equals("*")? (cur * num) : (cur / num);
            }
        }
        deque.addLast(String.valueOf(num));
    }

    //计算队列中的公式，该队列都已处理成数字和+-号
    public static int getNum(Deque<String> deque){
        int res = 0;
        boolean add = true;//+-
        String cur = null;
        int num =0;
        while (!deque.isEmpty()){
            cur = deque.pollFirst();
            if (cur.equals("+")){
                add = true;
            }else if (cur.equals("-")){
                add = false;
            }else {
                num = Integer.valueOf(cur);
                res += add? num : - num;
            }
        }
        return res;
    }

    //找到字符串的最长无重复字符子串
    public static int maxUnique(String str){
        if (str == null || str.equals("")){
            return 0;
        }
        int[] map = new int[256];
        char[] chars = str.toCharArray();
        for (int i = 0 ; i < map.length; i ++){
            map[i] = -1;
        }
        int cur = 0;
        int len = 0;
        int pre = -1;
        for(int i = 0; i < chars.length; i++){
            pre = Math.max(pre, map[chars[i]]);
            cur = i - pre;
            len = Math.max(len, cur);
            map[chars[i]] = i;
        }
        return len;
    }

    public String pointNewChar(String str, int k){
        if (str == null || str.equals("") || k < 0 || k > str.length()){
            return "";
        }
        char[] chars = str.toCharArray();
        int uNum = 0;
        for (int i = k - 1; i >= 0; i --){
            if (!isUpper(chars[i])){
                break;
            }
            uNum ++;
        }
        if (uNum%2 == 0){
            //偶数
            if (isUpper(chars[k])){
                return str.substring(k, k + 1);
            }else {
                return String.valueOf(chars[k]);
            }
        }else {
            return str.substring(k - 1, k);
        }
    }

    private static boolean isUpper(char c){
        return c >= 'A' && c <= 'Z';
    }

    /**
     * 最小包含子串的长度
     * @param str1 abcde  12345
     * @param str2 ac      344
     * @return     3       0
     */
    public static int minLength(String str1, String str2){
        if(str1 == null || str2 == null || str1.length() < str2.length()){
            return 0;
        }
        char[] cha1 = str1.toCharArray();
        char[] cha2 = str2.toCharArray();
        int[] map = new int[256];
        for (int i= 0; i< cha2.length; i ++){
            map[cha2[i]] ++;
        }
        int left = 0;
        int right = 0;
        int minlen = Integer.MAX_VALUE;
        int match = cha1.length ;
        //right右移
        while (right != cha1.length){
            if (-- map[cha1[right]]  >= 0){
                //匹配到
                match --;
            }
            if (match == 0){
                //完全匹配到， 让left左移
                while (map[cha1[left]] < 0){
                    map[cha1[left ++]] ++;
                }
                //最短长度
                minlen = Math.min(minlen, right - left + 1);
                match ++;//left再右移一位
                map[cha1[left ++]] ++;
            }
            right ++;
        }
        return minlen;
    }

    /**
     * 回文最少分割数， 动态规划
     * @param str  “ABA”不用分割，本身就是回文，返回0，“ACDCDCDAD”最少需要切两次变成“A”，“CDCDC”，“DAD”，所以返回2
     * @return
     */
    public static int minCut(String str){
        if (str == null || str.equals("")){
            return 0;
        }
        char[] chars = str.toCharArray();
        int len = chars.length;
        int[] dp = new int[len + 1];
        dp[len] = -1;
        //p[i][j]为true，说明str[i..j]是回文串
        boolean[][] p = new boolean[len][len];
        //从右往左依次计算dp[i]的值
        //dp[i]的含义是子串str[i..len - 1]至少需要切割几次，才能把str[i..len - 1]完全变成回文子串
        for (int i = len - 1; i >= 0; i --){
            dp[i] = Integer.MAX_VALUE;
            // i<=j<len,如果str[i..j]是回文串,那么dp[i]的值可能是dp[j +1]+1
            for (int j = i; i < len ; j ++){
                //如果p[i][j]为true，那么一定是以下三种情况
                //sr[i..j]由一个字符组成
                //str[i..j]由两个字符组成，且两个字符相等
                //str[i+1..j-1]是回文串，即p[i+1][j-1]为true，且str[i]==str[j]，即首位相等
                if (chars[i] == chars[j] && (j - i < 2 || p[i + 1][j - 1])){
                    p[i][j] = true;
                    dp[i] = Math.min(dp[i], dp[j + 1] +1);
                }
            }
        }
        return dp[0];
    }

    /**
     * 字符串匹配，类似正则表达式，动态规划
     * @param str abcd
     * @param exp .* //  .代表任何一个字符， *表示*前一个字符可以有0个或者多个
     * @return true //是否能匹配
     */
    public static boolean isMatchedDP(String str, String exp){
        if (str == null || exp == null){
            return false;
        }
        char[] strs = str.toCharArray();
        char[] exps = exp.toCharArray();
        if (!isValid(strs, exps)){
            //验证输入是否合法
            return false;
        }
        //初始化dp
        boolean[][] dp = initDp(strs, exps);
        //从右往左遍历
        for(int i = strs.length - 1; i > -1 ; i --){
            for (int j = exps.length - 2; j > - 1; j --){
                if (exps[j + 1] != '*'){
                    //如果下一个匹配字符不为*，则当前字符必须匹配， 并且之后的也匹配
                    dp[i][j] = (strs[i] == exps[j] || exps[j] == '.')
                            && dp[i + 1][j + 1];
                }else {
                    //下一个匹配字符为*
                    //如果strs[si] ，exps[j]能匹配
                    int si = i;
                    while (si != strs.length && (strs[si] == exps[j] || exps[j] == '.')){
                        if (dp[i][j + 2]){
                            dp[i][j] = true;
                            break;
                        }
                        si ++;
                    }
                    //如果strs[si] ，exps[j]不能匹配， 只能让exps[j，j+1]对应"", 然后考察exps[j+2]能否匹配
                    //比如bXXX与a*YYY,b不能和a*匹配，所以考察bXXX能否和YYY匹配
                    if (dp[i][j] != true){
                        dp[i][j] = dp[si][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }

    public static boolean isValid(char[] strs, char[] exps){
        for (int i = 0; i < strs.length ; i ++){
            //strs不能包含匹配字符
            if (strs[i] == '.' || strs[i] == '*'){
                return false;
            }
        }
        if ( exps[0] == '*'){
            //匹配串第一个字符不能为*
            return false;
        }
        for (int i = 1; i < exps.length; i ++){
            //不能有连续的*
            if (exps[i - 1] == '*' && exps[i] == '*'){
                return false;
            }
        }
        return true;
    }

    public static boolean[][] initDp(char[] s, char[] e){
        int slen = s.length;
        int elen = e.length;
        boolean[][] dp = new boolean[slen + 1][elen + 1];
        dp[slen][elen] = true; //"" 与""肯定能匹配
        for (int j = elen - 2; j >= 0; j = j - 2){
            //str为"",exps有剩余，除非exps剩余部分为X*X*X*的形式为true，否则false
            if (e[j] != '*' && e[j+1] == '*'){
                dp[slen][j] = true;
            }else {
                break;
            }
        }
        if (slen > 0 && elen > 0){
            if (e[elen - 1] == '.' || e[elen - 1] == s[ slen - 1]){
                //最后一个字符能匹配
                dp[slen - 1][elen - 1] = true;
            }
        }
        return dp;
    }

    //字典树（前缀树）
    static class TrieNode{
        public int path;
        public int end;
        //如果字符种类较多，可以考虑hashmap
        //这里字符为'a'-'z'
        public TrieNode[] map;

        public TrieNode(){
            path = 0;
            end = 0;
            map = new TrieNode[26];
        }
    }


    /**
     * 字典树基本性质：
     * 1，根节点没有字符路径。除根节点外，每一个节点都被一个字符路径找到
     * 2，从根节点到某一个节点，将路径上经过的字符连接起来，为扫过的对应字符串
     * 3，每一个节点向下所有的字符路径上的字符都不同
     *                     o
     *             a/     b|      c\
     *            o       o        o
     *           b/       c|       f\
     *           o        o         o
     *        c/ d\      d|        g\
     *        o   o       o         o
     *      d|
     *      o
     */
    public static class Trie{
        private TrieNode root;

        public Trie(){
            root = new TrieNode();
        }

        //插入操作
        public void insert(String word){
            if (word == null){
                return;
            }
            char[] chars = word.toCharArray();
            TrieNode node = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++){
                index = chars[i] - 'a';
                if (node.map[index] == null){
                    //为word的每一个字符创造节点
                    node.map[index] = new TrieNode();
                }
                node = node.map[index];
                node.path ++;//路径加一
            }
            node.end ++;//结果加一
        }

        //查找操作
        public boolean search(String word){
            if (word == null){
                return false;
            }
            char[] chars = word.toCharArray();
            TrieNode node = root;
            int index = 0;
            for (int i = 0; i < chars.length; i ++){
                index = chars[i] - 'a';
                //某一个字符节点没找到，那么就不存在这个字符串
                if (node.map[index] == null){
                    return false;
                }
                node = node.map[index];
            }
            //是否是末节点，才表示存在
            return node.end != 0;
        }

        //删除操作
        public void delete(String word){
            if (search(word)){//首先要查询一次，确保word存在
                char[] chars = word.toCharArray();
                TrieNode node = root;
                int index = 0;
                for (int i = 0; i < chars.length; i ++){
                    index = chars[i] - 'a';
                    //所有经过的字符路径减1，如果等于1， 那么删除这个节点及其后的节点
                    if (node.map[index].path -- == 1){
                        node.map[index] = null;
                        return;
                    }
                    node = node.map[index];
                }
                //如果路径不等于1，将end减一表示删除了这个字符串
                node.end --;
            }
        }

        //返回以字符串pre为前缀的单词数量
        public int prefixNumber(String pre){
            if (pre == null){
                return 0;
            }
            char[] chars = pre.toCharArray();
            int index = 0;
            TrieNode node = root;
            for (int i = 0; i < chars.length; i++){
                index = chars[i] - 'a';
                if (node.map[index] == null){
                    return 0;
                }
                node = node.map[index];
            }
            //一直查找到字符串pre的最后一个字符，返回该字符上的路径节点数量
            return node.path;
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("abc");
        trie.insert("abcdsf");
        trie.insert("fdggd");
        trie.insert("dfsgsdfg");
        System.out.println(trie.search("abc"));
        trie.delete("abc");
        System.out.println(trie.search("abc"));
        trie.insert("abc");
        System.out.print(trie.prefixNumber("ab"));
    }


    public static int[][] matrixPower(int[][] m, int p){
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i ++){
            //单位矩阵
            res[i][i] = 1;
        }
        int[][] temp = m;
        for (; p != 0; p >>= 1){
            if ((p & 1) != 0){
                res = muliMatrix(res , temp);
            }
            temp = muliMatrix(temp, temp);
        }
        return res;
    }
    public static int[][] muliMatrix(int[][] m1, int[][] m2){
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m2[0].length; i ++){
            for (int j = 0; j < m1.length; j ++){
                for (int k = 0; k < m2.length; k ++){
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }
}
