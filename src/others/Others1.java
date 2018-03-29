package others;

/**
 * create by renshengmiao on 2018/3/22 .
 */
public class Others1 {

    public static boolean isInside(double x1, double y1, double x4, double y4, double x, double y){
        if (x < x1 || x > x4 || y > y1 || y < y4 ){
            return false;
        }
        return true;
    }

//    public static boolean isInside(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, double x, double y){
//        if (y1 == y2){
//            return isInside(x1, y1, x4, y4, x, y);
//        }
//        double l = Math.abs(y4 - y3);
//        double k = Math.abs(x4 - x3);
//        double s = Math.sqrt(k * k + l* l);
//        double sin = l / s;
//        double cos = k / s;
//
//    }

    public static double getSideLength(double x1, double y1, double x2, double y2){
        double a = Math.abs(x1 - x2);
        double b = Math.abs(y1 - y2);
        return Math.sqrt(a * a + b * b);
    }

    public static double getArea(double x1, double y1, double x2, double y2, double x3, double y3){
        double a = getSideLength(x1, y1, x2, y2);
        double b = getSideLength(x1, y1, x3, y3);
        double c = getSideLength(x2, y2, x3, y3);
        double p = (a + b + c)/ 2;
        //海伦公式计算面积
        return Math.sqrt(p * (p - a) *(p- b) * (p - c));
    }

    /**
     * 判断一个点是否在三角形中, 面积法, 因为double类型的偏差可能出错
     * @return
     */
    public static boolean isInside2(double x1, double y1, double x2, double y2, double x3, double y3, double x , double y){
        double a = getArea(x1, y1, x2, y2, x, y);
        double b = getArea(x2,y2,x3,y3,x,y);
        double c = getArea(x1,y1,x3,y3,x,y);
        double all = getArea(x1,y1,x2,y2,x3,y3);
        return all >= a + b + c;
    }

    //向量叉乘
    public static double crossProduct(double x1, double y1, double x2, double y2){
        return x1 * y2 - x2 * y1;
    }

    public static boolean isInside3(double x1, double y1, double x2, double y2, double x3, double y3, double x , double y){
        if (crossProduct(x3 - x1, y3 - y1, x2 - x1, y2 - y1) >= 0){
            double tempx = x2;
            double tempy = y2;
            x2 = x3;
            y2 = y3;
            x3 = tempx;
            y3 = tempy;
        }
        if (crossProduct(x2 - x1, y2 - y1, x - x1, y - y1) < 0){
            return false;
        }
        if (crossProduct(x3 - x2, y3 - y2, x - x2, y - y2) <0){
            return false;
        }
        if (crossProduct(x1 - x3, y1 - y3, x - x3, y - y3) < 0){
            return false;
        }
        return true;
    }

    //实现在区间[0, x)上的数返回的概率是x^2
    //如果要求概率 x^k次, 则取k次最大值就可以
    public static double randXPower2(){
        return Math.max(Math.random() , Math.random());
    }
}
