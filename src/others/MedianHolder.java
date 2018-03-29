package others;

import java.util.Comparator;

/**
 * Created by rsmno on 2018/3/24.
 */
public class MedianHolder {
    private MyHeap<Integer> minHeap;
    private MyHeap<Integer> maxHeap;

    public MedianHolder() {
        this.minHeap = new MyHeap<>(new MinHeapComparator());
        this.maxHeap = new MyHeap<>(new MaxHeapComparator());
    }

    private void modifyTwoHeapSize(){
        if (this.maxHeap.getSize() == this.minHeap.getSize() + 2){
            this.maxHeap.add(this.minHeap.popHead());
        }else if (this.minHeap.getSize() == this.maxHeap.getSize() + 2){
            this.minHeap.add(this.maxHeap.popHead());
        }
    }

    public MedianHolder addNumber(Integer num){
        if (this.maxHeap.isEmpty()){
            this.maxHeap.add(num);
            return this;
        }
        if(this.maxHeap.getHead() >= num){
            this.maxHeap.add(num);
        }else {
            if (this.minHeap.isEmpty()){
                this.minHeap.add(num);
                return this;
            }
            if(this.minHeap.getHead() > num){
                this.maxHeap.add(num);
            }else {
                this.minHeap.add(num);
            }
        }
        this.modifyTwoHeapSize();
        return this;
    }

    public Integer getMedian(){
        long maxHeapSize = this.maxHeap.getSize();
        long minHeapSize = this.minHeap.getSize();
        if (maxHeapSize + minHeapSize == 0){
            return null;
        }
        Integer maxHeapHead = this.maxHeap.getHead();
        Integer minHeapHead = this.minHeap.getHead();
        if (((maxHeapSize + minHeapSize) & 1) == 0) {
            //数字数量为偶数，中卫数返回两个堆顶值/2
            return (maxHeapHead + minHeapHead) / 2;
        }else if (maxHeapSize > minHeapSize){
            return maxHeapHead;
        }else {
            return minHeapHead;
        }
    }

    //生成大根堆的比较器
    public class MaxHeapComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o2 > o1){
                return 1;
            }else {
                return -1;
            }
        }
    }

    //小根堆比较器
    public class MinHeapComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o2 < o1){
                return 1;
            }else {
                return -1;
            }
        }
    }
}
