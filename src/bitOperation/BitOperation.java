package bitOperation;

/**
 * Created by rsmno on 2018/3/18.
 */
public class BitOperation {

    //n为0，返回1， n为1，返回0
    private static int flip(int n){
        return n ^ 1;
    }

    //n的符号， 正数和0返回1， 负数返回0
    private static int sign(int n){
        return flip((n >> 31) & 1);
    }

    //不用任何比较操作找出两个数中较大的数
    //此方法如果发生溢出会出错， 可以使用方法2
    public static int getMax1(int a, int b){
        int c = a - b;
        int scA = sign(c);
        int scB = flip(scA);
        return scA * a + scB * b;  //scA 和scB中有一个0，一个1， 最终返回a，b中较大的那个
    }
    //不用任何比较操作找出两个数中较大的数
    //不会发生溢出错误
    public static int getMax2(int a, int b){
        int c = a - b;
        //abc的符号
        int sa = sign(a);
        int sb = sign(b);
        int sc = sign(c);
        //a,b的符号是否不相同
        int difSab = sa ^ sb;
        int sameSab = flip(difSab);
        //如果ab符号不同，可可能发生溢出，则根据a的符号返回正数那个数，
        // 如果ab符号相同，则根据c的符号， 返回较大那个值
        int returnA = difSab * sa + sameSab * sc;
        int returnB = flip(returnA);
        return a * returnA + b * returnB;
    }

    //利用为运算实现加法
    public static int add(int a, int b){
        int sum = a;
        while (b != 0){
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return sum;
    }

    public static int minus(int a, int b){
        //减法操作， a - b == a+ (-b)
        return add(a, negNum(b));
    }

    //获得一个数的相反数
    //按位取反，加1（补码）
    public static int negNum(int n){
        return add(~n, 1);
    }

    //位运算实现乘法
    //不论ab正负，都有 a * b = a * 2^0 * b1 + a * 2^1 *b2 + .....a * 2^i * bi +...+ a * 2^31 *b31
    public static int multi(int a, int b){
        int res = 0;
        while (b != 0){
            if ((b & 1) != 0){
                res = add(res, a);
            }
            a <<= 1; //左移，即*2
            b >>>= 1; //无符号的位移处理，它不会将所处理的最高位视为正负号，始终在最左边填充0
        }
        return res;
    }

    //n是否为负数
    public static boolean isNeg(int n){
        return n < 0;
    }
    //位运算实现除法，是乘法的逆思想
    //但是32位的整数最小值为-2147483648， 最大值是2147483647， 最小值不能转为正的最大值
    //要对最小值做特殊处理, 见devide
    public static int div(int a, int b){
        int x = isNeg(a)? negNum(a) : a;
        int y = isNeg(b)? negNum(b) : b;
        int res = 0;
        for (int i = 31; i > -1; i = minus(i, 1)){
            if ( (x >> i) >= y ){
                //a能包含下一个b*2^i
                res |= (1 << i);
                //a减去一个b*2^i, 看剩下部分汉能不能包含b
                x = minus(x , y << i);
            }
        }
        return isNeg(a) ^ isNeg(b)? negNum(res) : res;
    }

    //位运算除法的修正处理
    public static int divide(int a, int b){
        if (b == 0){
            throw new RuntimeException("divisor is 0");
        }else if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE){
            return 1;
        }else if (b == Integer.MIN_VALUE){
            return 0;
        }else if (a == Integer.MIN_VALUE){
            //a是最小值，但b不是,进行修正
            //假设最小值为-10， 最大值为9， a = -10， b = 5
            //先计算（-10 + 1）/5 = -1
            //计算-1 * 5 = -5
            //计算-10 - -5 = -5
            //计算rest即修正值， -5/5 = -1
            //返回 -1 + -1
            int res = div(add(a, 1), b);
            return add(res, div(minus(a, multi(res, b)), b));
        }else {
            return div(a, b);
        }
    }

    //整数的二进制表达中有多少个1
    public static int count2(int n){
        int res = 0;
        while (n != 0){
            //n &= (n-1);此操作实质是抹掉最右边的1
            //如 n = 01000100, n - 1= 01000011, n&(n-1) = 01000000
            //因此循环的次数就是1的个数
            n &= (n-1);
            res ++;
        }
        return res;
    }

    public static int count3(int n){
        int res = 0;
        while (n != 0){
            //n & (~n + 1)，此操作的实质是得到最右边的1
            //如n = 01000100, n & (~n + 1) = 00000100
            n -= n & (~n + 1);
            res ++;
        }
        return res;
    }

    //平行算法
    public static int count4(int n){
        n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
        n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
        n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
        n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
        return n;
    }

    //一个数组中，只有一个数出现了奇数次，其他都出现了偶数次， 打印这个奇数次数
    public static void printOddTimeNum(int[] arr){
        //n与0异或是n， n与n异或是0，所有数异或后，就是那个唯一一个奇数次的数
        int eO = 0;
        for (int i = 0; i < arr.length; i ++){
            eO ^= arr[i];
        }
        System.out.print(eO);
    }

    //打印数组中唯二的两个出现奇数次的数， 其他都出现了偶数次
    public static void printOddTimesNum2(int[] arr){
        int eO = 0, eOhasOne = 0;
        for (int i = 0; i < arr.length; i ++){
            eO ^= arr[i];
        }
        //e0的最终结果是 两个奇数次数的异或结果
        int rightOne = eO & (~eO + 1); //找到一个不等于0的bit位，比如第k个
        //再遍历一次数组， 这次只和第k个bit位为1的整数异或，其他数字忽略
        //那么最后eOhasOne就是两个数中的其中一个
        for (int i = 0 ; i < arr.length; i ++){
            if ((arr[i] & rightOne) != 0){
                eOhasOne ^= arr[i];
            }
        }
        //此时 eO ^ eOhasOne就是另一个奇数次的数
        System.out.print(eOhasOne + " " + (eO ^ eOhasOne));
    }

    //在其他数都出现k次的数组中，找到只出现一次的数
    //利用k进制，在某一位上，k个数字进行无进位相加，最后的结果为0
    //所有的数字都相加后，最后的结果就是只出现一次的数的k进制表示，转换成十进制即可
    public static int onceNum(int[] arr, int k){
        int[] eO = new int[32];
        for (int i = 0; i <arr.length; i ++){
            setExclusiveOr(eO, arr[i], k);
        }
        return getNumFromKSysNum(eO, k);
    }

    //将value转换为k进制数，并与eO进行无进位相加
    private static void setExclusiveOr(int[] eO, int value, int k){
        int[] kSysNumFromNum = getKSysNumFromNum(value, k); //转换为k进制数
        for (int i = 0; i < kSysNumFromNum.length; i ++){
            eO[i] = (eO[i] + kSysNumFromNum[i]) % k;
        }
    }

    //将value转换为k进制数数组
    private static int[] getKSysNumFromNum(int value, int k){
        int[] res = new int[32];
        int index = 0;
        while (value != 0){
            res[index ++] = value % k;
            value /= k;
        }
        return res;
    }

    //将k进制的数组转换为十进制数
    private static int getNumFromKSysNum(int[] eO, int k){
        int res = 0;
        for (int i = eO.length; i > -1; i --){
            res = res * k + eO[i];
        }
        return res;
    }


}
