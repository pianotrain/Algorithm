package others;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsmno on 2018/3/21.
 */
public class Others {
    public static int rand1To5(){
        //随机生成1.。。5
        return (int)(Math.random() * 5 + 1);
    }

    /**
     * 从5随机到 7随机
     * @return
     */
    public static int rand1To7(){
        int num = 0;
        do {
            //不能化简
            //rand1To5 - 1 生成 0...4
            // (rand1To5() - 1) * 5 生成0...20
            //(rand1To5() - 1) * 5 + rand1To5() - 1生成0..24
            num = (rand1To5() - 1) * 5 + rand1To5() - 1;
        }while (num > 20);
        return num % 7 + 1;
    }

    public class MyValue<V>{
        private V value;
        private long time;

        public MyValue(V value, long time) {
            this.value = value;
            this.time = time;
        }

        public V getValue(){
            return value;
        }

        public long getTime(){
            return time;
        }
    }

    public class MyHashMap<K, V>{
        private Map<K,MyValue<V>> map;
        private long time;
        private MyValue<V> setAll;

        public MyHashMap(){
            map = new HashMap<>();
            time = 0;
            setAll = new MyValue<>(null, -1);
        }

        public void put(K key, V value){
            this.map.put(key, new MyValue<>(value, this.time ++));
        }

        public boolean containsKey(K key){
            return this.map.containsKey(key);
        }
        public void setAll(K key, V value){
            setAll = new MyValue<>(value, time ++);
        }

        public V get(K key){
            if (map.containsKey(key)){
                if (map.get(key).getTime() < this.setAll.getTime()){
                    return setAll.getValue();
                }else {
                    return map.get(key).getValue();
                }
            }else {
                return null;
            }
        }
    }

    /**
     * 最大的   leftMax与rightMax只差的绝对值
     * 对数组预处理 T:O(N)， S:O(N)
     * @param arr
     * @return
     */
    public static int maxABS1(int[] arr){
        if (arr == null || arr.length  == 0){
            return 0;
        }
        int[] lArr = new int[arr.length];
        int[] rArr = new int[arr.length];
        lArr[0] = arr[0];
        for (int i = 1 ; i < arr.length ; i ++){
            lArr[i] = Math.max(arr[i], lArr[i - 1]);
        }
        rArr[arr.length - 1] = arr[arr.length - 1];
        for (int i = arr.length - 2; i > -1; i ++){
            rArr[i] = Math.max(arr[i], rArr[i + 1]);
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length - 1; i ++){
            int abs = Math.abs(lArr[i] - rArr[i + 1]);
            max = Math.max(abs, max);
        }
        return max;
    }

    private  class Node<V>{
        private V value;
        private Node<V> last;
        private Node<V> next;

        public Node(V value){
            this.value = value;
        }
    }

    private class NodeDoubleLinkedList<V> {
        private Node<V> head;
        private Node<V> tail;

        public NodeDoubleLinkedList(){
            head = null;
            tail = null;
        }

        public void addNode(Node<V> node){
            if (node == null){
                return;
            }
            if (head == null){
                head = node;
                tail = node;
            }else {
                tail.next = node;
                node.last = tail;
                tail = node;
            }
        }

        public void moveNodeToTail(Node<V> node){
            if (tail == node){
                return;
            }
            if (head == node){
                this.head = node.next;
                this.head.last = null;
            }else {
                node.last.next = node.next;
                node.next.last = node.last;
            }
            node.last = tail;
            tail.next = node;
            node.next = null;
            tail = node;
        }

        public Node<V> removeHead(){
            if (head == null){
                return null;
            }
            Node<V> node = this.head;
            if (head == tail){
                head = null;
                tail = null;
            }else {
                head = node.next;
                node.next = null;
                head.last = null;
            }
            return node;
        }
    }

    /**
     * 设计可以变更的缓存结构 ， map + 双向链表
     * @param <K>
     * @param <V>
     */
    public class MyCache<K,V>{
        private Map<K, Node<V>> keyNodeMap;
        private Map<Node<V>, K> nodeKeyMap;
        //双向链表
        //头节点始终是最不常用的节点
        //末节点始终是最新使用的节点
        private NodeDoubleLinkedList<V> nodeList;
        private int capacity;

        public MyCache(int capacity) {
            if (capacity < 1){
                throw new RuntimeException("capacity should be great than 0");
            }
            keyNodeMap = new HashMap<>();
            nodeKeyMap = new HashMap<>();
            nodeList = new NodeDoubleLinkedList<>();
            this.capacity = capacity;
        }

        public V get(K key){
            if (this.keyNodeMap.containsKey(key)){
                nodeList.moveNodeToTail(keyNodeMap.get(key));
                return keyNodeMap.get(key).value;
            }
            return null;
        }

        public void  set(K key, V value){
            if (this.keyNodeMap.containsKey(key)){
                keyNodeMap.get(key).value = value;
                nodeList.moveNodeToTail(keyNodeMap.get(key));
            }else {
                Node<V> node = new Node<>(value);
                keyNodeMap.put(key, node);
                nodeKeyMap.put(node, key);
                nodeList.addNode(node);
                if (capacity + 1 == this.keyNodeMap.size()){
                    removeMostUnusedCache();
                }
            }
        }

        public void removeMostUnusedCache(){
            Node<V> node = nodeList.removeHead();
            K k = nodeKeyMap.get(node);
            nodeKeyMap.remove(node);
            keyNodeMap.remove(k);
        }
    }

    //可以随机获得某一个元素的池子
    public class RandomPool<K>{
        private Map<K, Integer> keyIndexMap;
        private Map<Integer, K> indexKeyMap;
        private int size;

        public RandomPool() {
            keyIndexMap = new HashMap<>();
            indexKeyMap = new HashMap<>();
            size = 0;
        }

        public void insert(K key){
            if (keyIndexMap.containsKey(key)){
                keyIndexMap.put(key, size);
                indexKeyMap.put(size ++, key);
            }
        }

        //关键逻辑
        //删除某一个元素时，用最后一个加入的元素将要删除的元素的索引值覆盖掉， 然后再将最后一个元素删掉
        //这样可以保持size的连续性， 从而能够随机获取元素
        public void delete(K key){
            if (keyIndexMap.containsKey(key)){
                Integer deleteIndex = keyIndexMap.get(key);
                int lastIndex = -- size;
                K lastKey = indexKeyMap.get(lastIndex);
                keyIndexMap.put(lastKey, deleteIndex);
                indexKeyMap.put(deleteIndex, lastKey);
                keyIndexMap.remove(key);
                indexKeyMap.remove(lastIndex);
            }
        }

        public K random(){
            if (this.size == 0){
                return null;
            }
            int randomIndex =(int) (Math.random() * size);
            return this.indexKeyMap.get(randomIndex);
        }
    }

    /**
     * 一种字符串和数字的对应关系
     * @param chs {'A','B','C','D'}
     * @param n  5  6   8    16
     * @return AA  AB   AD   DD
     */
    public static String getStrings(char[] chs, int n){
        if(chs == null || chs.length == 0){
            return "";
        }
        int cur = 1;
        int len = 0;
        int base = chs.length;
        while(n >= cur){
            len ++;
            n -= cur;
            cur = cur * base;
        }
        char[] res = new char[len];
        int curN = 0;
        int index = 0;
        do {
            cur /= base;
            curN = n / cur;
            res[index ++] = getKthChar(chs, curN + 1);
            n %= cur;
        }while (index != res.length);
        return String.valueOf(res);
    }

    public static char getKthChar(char[] chs, int k){
        if (k < 1 || k > chs.length){
            return 0;
        }
        return chs[k - 1];
    }

    public static int getNum(char[] chs, String str){
        if (chs == null || chs.length == 0){
            return 0;
        }
        char[] strs = str.toCharArray();
        int res = 0;
        int base = chs.length;
        int cur = 1;
        for (int i = strs.length - 1; i != -1; i --){
            res += getNthFromChars(chs, strs[i]) * cur;
            cur *= base;
        }
        return res;
    }

    public static int getNthFromChars(char[] chs, char ch){
        int res = -1;
        for (int i = 0; i < chs.length ; i ++){
            if (chs[i] == ch){
                res = i + 1;
                break;
            }
        }
        return res;
    }

    public static int solution2(int num){
        if (num < 1){
            return 0;
        }
        int len = getLengthOfNum(num);
        if (len == 1){
            return 1;
        }
        int temp1 = powerBaseOf10(len);
        int first = num / temp1;
        int firstOneNum = first == 1? num % temp1 + 1: temp1;
        int otherOneNum = first * (len - 1) *( temp1 / 10 );
        return firstOneNum + otherOneNum + solution2(num % temp1);
    }

    public static int getLengthOfNum(int num){
        int len = 0;
        while (num != 0){
            len ++;
            num /= 10;
        }
        return len;
    }

    public static int powerBaseOf10(int base){
        return (int) (Math.pow(10, base));
    }

    public static void swap(int[] arr, int index1, int index2){
        if (index1 == index2){
            return;
        }
        arr[index1] = arr[index1] ^ arr[index2];
        arr[index2] = arr[index1] ^ arr[index2];
        arr[index1] = arr[index1] ^ arr[index2];
    }

    public static void randPrintM(int[] arr, int m){
        if (arr == null || arr.length == 0 || m < 0){
            return;
        }
        int count = 0;
        int i = 0;
        while (m -- != 0){
            i = (int) (Math.random() * (arr.length - count - 1));
            System.out.println(arr[i]);
            swap(arr,i, arr.length - count ++ - 1);
        }
    }

    /**
     * 判断一个数字是否是回文数
     * @param num
     * @return
     */
    public static boolean isPalindrome(int num){
        if (num == Integer.MIN_VALUE){
            return false;
        }
        num = Math.abs(num);
        int help = 1;
        while (num / help > 10){//防止help溢出
            help *= 10;
        }
        while (num != 0){
            if (num / help != num % 10){
                return false;
            }
            num = (num % help) / 10;
            help /= 100;
        }
        return true;
    }

    public static int[] getNextArr(char[] chs){
        if (chs.length == 1){
            return new int[]{-1};
        }
        int[] nextArr = new int[chs.length];
        nextArr[0] = -1;
        nextArr[1] = 0;
        int pos = 2;
        int cn = 0;
        while (pos < chs.length){
            if (chs[pos - 1] == chs[cn]){
                nextArr[pos ++] = ++ cn;
            }else if (nextArr[cn] != -1){
                cn = nextArr[cn];
            }else {
                nextArr[pos ++] = 0;
            }
        }
        return nextArr;
    }

    public static int getIndexOf(String str, String m){
        if (str == null || m == null || str.length() < m.length()){
            return -1;
        }
        char[] strs = str.toCharArray();
        char[] ms = m.toCharArray();
        int[] nextArr = getNextArr(ms);
        int si = 0;
        int mi = 0;
        while (si < str.length() && mi < m.length()){
            if (strs[si] == ms[mi]){
                si ++;
                mi ++;
            }else if (ms[mi] != -1){
                mi = ms[mi];
            }else {
                si ++;
            }
        }
        return mi == m.length()? si - mi : -1;
    }
}
