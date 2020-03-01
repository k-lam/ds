/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.sort;

import static datastructure.sort.Record.check;
import static datastructure.Tools.*;
import datastructure.binarytree.Heap;

/**
 *
 * @author linqiye
 */
public class PPT {

    static void swap(Record[] array, int i, int j) {
        Record t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    static void swap(int[] array, int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

//============================8.2 插入排序===============================    
    // 直接插入排序
    public static void insertSort(IntRecord[] array) {
        // 看下面 insertSort(IntRecord[] array, int l, int r)解析, KL常错
        for (int i = 1; i < array.length; i++) {
            IntRecord tmp = array[i];
            int j;
            for (j = i; j > 0 && array[j - 1].bigerThan(tmp); j--) {// 与PPT略有不同,但正确
                array[j] = array[j - 1];
            }
            array[j] = tmp;
        }
    }

    // 从 l 到 r,排序 l r都是包含的
    public static void insertSort(IntRecord[] array, int l, int r) {
        for (int i = l + 1; i <= r; i++) {// i = l + 1 是因为第一个元素已经有序,i <= r 是要比较到r
            IntRecord tmp = array[i];// tmp保存的是这次要插入的元素
            int j;// j是最终插入位置
            // j是最终插入位置,则要求 array[j - 1] < tmp
            for (j = i; j > l && array[j - 1].bigerThan(tmp); j--) {
                array[j] = array[j - 1];
            }
            array[j] = tmp;
        }
    }

    public static void modInsSort(IntRecord[] array, int offset, int delta) {
        // offset与delta是关键!和插入排序基本一致+-1 变成 +-delta, 0变成offset
        for (int i = delta + offset; i < array.length; i += delta) {
            IntRecord tmp = array[i];
            int j = i;
            for (j = i; j > offset && array[j - delta].bigerThan(tmp); j -= delta) {
                array[j] = array[j - delta];
            }
            array[j] = tmp;
        }

    }

    // 希尔排序
    public static IntRecord[] shellSort(IntRecord[] array) {
        for (int delta = array.length / 2; delta > 0; delta /= 2) {
            for (int i = 0; i < delta; i++) {
                modInsSort(array, i, delta);
            }
        }
        return array;
    }

//==============================8.3 选择排序==============================
    // 直接选择排序
    public static void selectSort(IntRecord[] rs) {
        for (int i = 0; i != rs.length - 1; i++) {// 进行i - 1次
            int smallIndex = i;
            for (int j = i + 1; j != rs.length; j++) {
                if (rs[j].lessOrEqual(rs[smallIndex])) {
                    smallIndex = j;
                }
            }
            swap(rs, i, smallIndex);
        }
    }

    static class MaxHeap extends Heap<IntRecord> {

        public MaxHeap(IntRecord[] array) {
            super(array);
        }

        @Override
        public boolean lessThen(IntRecord a, IntRecord b) {
            return a.key > b.key;
        }

    }

    // 堆排序
    public static void heapSort(IntRecord[] rs) {
        MaxHeap heap = new MaxHeap(rs);
        heap.buildHeap();
        for (int i = 0; i != rs.length - 1; i++) {
            heap.removeTop();
        }
    }

//==============================8.4交互排序=============================
    // 冒泡排序
    public static void bubbleSort(IntRecord[] array) {
        // 向前冒泡
        for (int i = 0; i != array.length - 1; i++) {
            boolean swaped = false;
            for (int j = array.length - 1; j != i; j--) {

                if (array[j - 1].bigerThan(array[j])) {
                    swap(array, j - 1, j);
                    swaped = true;
                }
            }
            if (!swaped) {
                return;
            }
        }
    }

    static int partitiion(IntRecord[] array, int l, int r) {
        //假设pivot在位置r
        IntRecord tmp = array[r];
        while (l < r) {
            while (l < r && array[l].lessOrEqual(tmp)) {
                l++;//TODO: 没有等号会错,试想待排序数组有两个相同的关键字！
            }
            array[r] = array[l];

            while (l < r && array[r].bigerOrEqual(tmp)) {// 注意,两个while的条件是不一样的
                r--;
            }
            array[l] = array[r];
        }
        array[l] = tmp; // 易漏这句
        return l;
    }

    // 快速排序
    /**
     * 选一个pivot 比pivot大的都放到右边 比pivot小的都放左边 于是pivot就在最终位置 再对左右两边用相同的过程
     *
     * @param array
     * @param l
     * @param r
     */
    public static void quickSort(IntRecord[] array, int l, int r) {
        if (l < r) {
            int pivot = (l + r) / 2;
            swap(array, r, pivot);
            pivot = partitiion(array, l, r);
            quickSort(array, l, pivot - 1);
            quickSort(array, pivot + 1, r);
        }
    }

    static int THRESHOLD = 10;

    public static void quickSortWithThreshold(IntRecord[] array, int l, int r) {
        if (r - l >= THRESHOLD) {
            int pivot = (l + r) / 2;
            swap(array, r, pivot);
            pivot = partitiion(array, l, r);
            quickSortWithThreshold(array, l, pivot - 1);
            quickSortWithThreshold(array, pivot + 1, r);
        }
    }

    // 为了减少递归,当小于某个阈值,就不再Partition,最后一个插入排序就可以了
    // 见PPT8.4 P21

    public static void quickSortWithThreshold(IntRecord[] array) {
        quickSortWithThreshold(array, 0, array.length - 1);
        insertSort(array);
    }

//=============================归并排序==============================
    // 未优化的归并排序
    public static void mergeSort(IntRecord[] array, IntRecord[] tmpArray, int l, int r) {
        if (l < r) {
            int mid = (l + r) / 2;
            mergeSort(array, tmpArray, l, mid);
            mergeSort(array, tmpArray, mid + 1, r);
            merge(array, tmpArray, l, r, mid);
        }
    }

    public static void merge(IntRecord[] array, IntRecord[] tmpArray, int l, int r, int m) {
        System.arraycopy(array, l, tmpArray, l, r - l + 1);
        // 这个写法比王道的强
        int i1 = l, i2 = m + 1, k = l;
        while (i1 <= m && i2 <= r) {
            if (tmpArray[i1].lessOrEqual(tmpArray[i2])) {// <就不稳地, 要保证稳定,要 tmpArray[i1] <= tmpArray[i2]
                array[k++] = tmpArray[i1++];
            } else {
                array[k++] = tmpArray[i2++];
            }
        }
        while (i1 <= m) {
            array[k++] = tmpArray[i1++];
        }
        while (i2 <= r) {
            array[k++] = tmpArray[i2++];
        }
    }

    public static void mergeSortImprove(IntRecord[] array, IntRecord[] tmpArray, int l, int r) {
        if (r - l > THRESHOLD) {
            int mid = (r + l) / 2;
            mergeSortImprove(array, tmpArray, l, mid);
            mergeSortImprove(array, tmpArray, mid + 1, r);
            mergeRS(array, tmpArray, l, r, mid);
        } else {
            insertSort(array, l, r);
        }

    }

    public static void mergeRS(IntRecord[] array, IntRecord[] tmpArray, int l, int r, int m) {
        System.arraycopy(array, l, tmpArray, l, m - l + 1);
        int n2 = r - m;// 第二部分是m + 1 到 r
        for (int i = 0; i != n2; i++) {
            tmpArray[m + 1 + i] = array[r - i];
        }

        int i1 = l, i2 = r, k = l;// i1指第一部分, i2 指第二部分
        while (k <= r) {
            if (tmpArray[i1].lessOrEqual(tmpArray[i2])) {
                array[k++] = tmpArray[i1++];
            } else {
                array[k++] = tmpArray[i2--];
            }
        }
    }

    //================================分配排序===============================
    // 桶排序
    public static void bucketSort(IntRecord[] array, int max) {
        int[] count = new int[max + 1];

        IntRecord[] tmpArray = new IntRecord[array.length];
        for (int i = 0; i != tmpArray.length; i++) {
            tmpArray[i] = array[i];
        }

        for (int i = 0; i <= max; i++) {// init
            count[i] = 0;
        }

        for (int i = 0; i != array.length; i++) {
            count[array[i].key]++;
        }

        for (int i = 1; i != count.length; i++) {
            count[i] += count[i - 1];
        }
        
        // 收
        for (int i = array.length - 1; i >= 0; i--) {
//            System.out.println(i + "," + count[i]);
            array[--count[tmpArray[i].key]] = tmpArray[i];// 注意了,是 先减1 且是array[count[tmpArray[i]]] 三重!
        }
    }
    
    // 低位优先的基数排序    
    static void radixSort(IntRecord[] array, int d, int r){
        int[] count = new int[r];
        IntRecord[] tmpArray = new IntRecord[array.length];
        int aggRadius = 1;
        while(d-- > 0){
            for(int i = 0; i != count.length; i++){
                count[i] = 0;
            }
            
            int key = 0;
            // 分配
            for(int i = 0; i != tmpArray.length; i++){
                tmpArray[i] = array[i];
                key = tmpArray[i].key / aggRadius %r;
                count[key]++;
            }
            // 累加
            for(int i = 1; i != count.length;i++){
                count[i] += count[i - 1];
            }
            // 收集
            for(int i = tmpArray.length - 1; i >= 0; i--){
                key = tmpArray[i].key / aggRadius % r;// 注意了, 这里用tmpArray 不是用array,因为是把tmp的放到array, array的已经覆盖
                array[--count[key]] = tmpArray[i];// 注意, 先 --
            }
            aggRadius *= r;
        }
    }
    
    static class StaticQueue{
        int head = -1;
        int tail = -1;

        public StaticQueue(int head, int tail) {
            this.head = head;
            this.tail = tail;
        }
        
    }
    
    // 基于静态链的基数排序
    static void radixSortByLink(IntRecord[] array, int d, int r){
        // 与ppt不同的地方是  next数组分开存, 不是作为record的一个属性, 一样的
        int[] nexts = new int[array.length];
        for(int i = 0; i != nexts.length;i++){
            nexts[i] = i+1;
        }
        nexts[nexts.length - 1] = -1;
        StaticQueue[] queues = new StaticQueue[r];
        for(int i = 0; i != r; i++){
            queues[i] = new StaticQueue(-1, -1);
        }
        int first = 0;
        for(int round = 0; round != d; round++){
            distribute(array, nexts, first, queues, round, r);
            first = collect(array, nexts, queues);
        }
        nextAddrSort(array, nexts, first);
    }
    
    static void distribute(IntRecord[] array, int[] nexts, int first,StaticQueue[] queues, int round, int r){ 
        for(int i = 0; i != queues.length; i++){
            queues[i].head = -1;
            queues[i].tail = -1;
        }
        int div = 1;
        while(round-- > 0){
            div *= r;
        }
        for(int i = first; i != -1; i = nexts[i]){
            int key = array[i].key / div % r;
            if(queues[key].head == -1){
                queues[key].head = i;
            }else{
                nexts[queues[key].tail] = i;
            }
            queues[key].tail = i;
        }
    }
    
    static int collect(IntRecord[] array, int[] nexts, StaticQueue[] queues){
        int qi = 0;
        while(queues[qi].head == -1) qi++;//
        int first = queues[qi].head;
        int lastQi = qi;// 很容易错, lastQi 是保存上一个,queues 的 index,不是nexts的index,这里index多易错
        for(qi = qi + 1; qi < queues.length; qi++){
            while(qi < queues.length && queues[qi].head == -1) qi++;
            if(qi < queues.length){
                nexts[queues[lastQi].tail] = queues[qi].head;
                lastQi = qi;
            }
        }
        nexts[queues[lastQi].tail] = -1;
        return first;
    }
    
    static int maxIndex(Record[] array){
        int maxI = 0;
        for(int i = 0; i != array.length;i++){
            if(array[i].bigerThan(array[maxI])){
                maxI = i;
            }
        }
        return maxI;
    }
    
    static int getDegree(int data){
        int i = 0;
        while(data != 0){
            data /= 10;
            i++;
        }
        return i;
    }
    
    
//==================================索引排序====================================    
    
    // 地址排序, 地址是next 注意与后面索引排序不一样, 索引定义不一样.不是PPT8.7 p7 与 p12
    static void nextAddrSort(IntRecord[] array, int[] nexts, int first){
        // 超难写的!!!!index怎样保存  怎样交换! 难
        // index太多 变量注意命名, array的index为ai,nexts的index为ni
        int ni = first;
        for(int ai = 0; ai != array.length - 1; ai++){// 还是老样子, 排序的只需要操作n-1次,
            int tmpNi = nexts[ni];
            swap(array, ai, ni);
            nexts[ni] = nexts[ai];
            nexts[ai] = ni;// 保存轨迹,轨迹是什么, swap后,指向ai的错了,所以ai要保存 这个index被交换去哪里(就是ni啊)
            ni = tmpNi;
            while(ni <= ai) ni = nexts[ni];
        }
    }
    
    // 索引版直接插入排序  Array[i] = 
    static void insertSortByAddr(IntRecord[] array){
        int[] indics = new int[array.length];
        // 注意初始化
        for(int i = 0; i != indics.length; i++){
            indics[i] = i;
        }
        for(int i = 1; i != array.length; i++){
            IntRecord tmp = array[indics[i]];
            for(int j = i; j > 0; j--){
                if(array[indics[j - 1]].bigerThan(tmp)){
                    swap(indics, j, j - 1);
                }else{
                    break;
                }
            }
        }
        addrSort(array, indics);
    }
    
    // 索引版快速排序  正确的
    static void quickSortIndex(IntRecord[] array){
        int[] indics = new int[array.length];
        for(int i = 0; i != indics.length; i++){
            indics[i] = i;
        }
        quickSortIndex(array, indics, 0, array.length - 1);
        addrSort(array, indics);
    }
    
    static void quickSortIndex(IntRecord[] array, int[] indics, int l, int r){
        if(l < r){
            int pivot = (r + l) / 2;
            swap(indics, r, pivot);
            pivot = patitionByIndics(array, indics, l, r);
            quickSortIndex(array, indics, l, pivot - 1);
            quickSortIndex(array, indics, pivot + 1, r);
        }
    }
    
    static int patitionByIndics(IntRecord[] array, int[] indics, int l, int r){
        int tmp = indics[r];
        while(l < r){
            while(l < r && array[indics[l]].lessOrEqual(array[tmp])) l++;
            indics[r] = indics[l];
            while(l < r && array[indics[r]].bigerOrEqual(array[tmp])) r--;
            indics[l] = indics[r];
        }
        indics[r] = tmp;
        return r;
    }
    
    // array[i] = array[indics[i]]
    static void addrSort(IntRecord[] array, int[] indics){
        for(int i = 0; i != array.length; i++){
            IntRecord tmp = array[i];
            int j = i;
            while(indics[j] != i){
                array[j] = array[indics[j]];
                int k = indics[j];
                indics[j] = j;
                j = k;
            }
            indics[j] = j;
            array[j] = tmp;
        }
    }
    
    // 错误的! 第一类索引无法得到正确答案, next索引很难实现,需要用链表
    static void radixSortByIndex(IntRecord[] array, int d, int r){
        int[] indics = new int[array.length];
        int[] count = new int[r];
        for(int i = 0; i != indics.length; i++){
            indics[i] = i;
        }
        int aggRadius = 1;
        while(d-- > 0){
            // 分配
            for(int i = 0; i != count.length; i++){
                count[i] = 0;
            }
            for(int i = 0; i != array.length; i++){
                int key = array[i].key / aggRadius % r;
                count[key]++;
            }
            
            //累加
            for(int i = 1; i != count.length; i++){
                count[i] += count[i - 1];
            }
            
            // 收集
            for(int i = 0; i != array.length; i++){
                //第一类索引在这里无法得到正确答案
            }
        }
    }

    // 索引版 堆排序 正确的
    static class HeapSortWithIndex extends Heap<Integer>{
        
        IntRecord[] records;

        public HeapSortWithIndex(IntRecord[] records) {
            this.records = records;
            Integer[] indics = new Integer[records.length];
            for(int i = 0; i != indics.length; i++){
                indics[i] = i;
            }
            this.heapArray = indics;// 相当于indics
            this.currentSize = indics.length;
        }
        
        void sort(){
            buildHeap();
            for(int i = 0; i != records.length - 1; i++){
                removeTop();
            }
            int[] indics = new int[heapArray.length];
            for(int i = 0; i != indics.length; i++){
                indics[i] = heapArray[i];
            }
            addrSort(records, indics);
        }

        @Override
        public boolean lessThen(Integer a, Integer b) {
            return records[a].key > records[b].key;// 最大堆
        }
    
    }
    
    //===============================测试数据======================================
    static int[] addrsortTest = new int[]{0, 1, 2, 3 ,4}; //ppt 8.7 p4 数据
    static int[] addrSortP1 = new int[]{29, 25, 34, 64, 34, 12, 32, 45};// ppt 8.7 p10 数据
    static int[] reverse = new int[]{7, 6, 5, 4, 3, 2, 1};
    static int[] normal = new int[]{3, 5, 1, 6, 2, 2, 7, 4};
    static int[] sorted = new int[]{1, 2, 3, 4, 5, 6, 7};
    static int[] normal2 = new int[]{30, 50, 10, 60, 40, 20, 70, 40, 100};
    static int[] ppt0 = new int[]{45, 34, 78, 2, 34, 32, 29, 64};
    static int[] ppt1 = new int[]{2, 2, 1, 2, 2};
    static int[] big = new int[]{548, 723, 500, 428, 313, 378, 419, 102, 599, 8, 909, 425, 425, 825, 686, 871, 574, 340, 6, 770, 15, 34, 184, 396, 254, 244, 505, 239, 532, 805, 241, 241, 180, 932, 953, 148, 199, 645, 36, 480, 579, 860, 709, 83, 871, 583, 805, 690, 391, 850, 350, 346, 125, 976, 663, 490, 538, 997, 313, 672, 396, 62, 914, 43, 938, 945, 685, 556, 850, 89, 611, 662, 107, 237, 38, 660, 354, 498, 690, 70, 653, 709, 324, 465, 492, 825, 699, 145, 641, 6, 97, 967, 694, 944, 387, 300, 123, 487, 537, 292, 731, 240, 718, 906, 932, 166, 887, 48, 21, 394, 768, 842, 152, 648, 334, 683, 156, 879, 331, 51, 21, 366, 605, 618, 82, 492, 796, 627, 669, 516, 994, 689, 211, 354, 572, 485, 607, 956, 389, 84, 88, 135, 938, 648, 865, 396, 239, 184, 180, 613, 874, 903, 111, 166, 353, 229, 700, 916, 17, 722, 696, 289, 436, 331, 511, 21, 963, 241, 449, 34, 228, 818, 553, 638, 392, 52, 603, 454, 875, 423, 113, 594, 420, 587, 750, 783, 309, 444, 926, 860, 127, 916, 20, 319, 877, 443, 579, 213, 372};

    static int[][] as = new int[][]{addrSortP1, reverse, normal, sorted, normal2, ppt0, big, addrsortTest};

    static IntRecord[][] stableSortArray() {
        int[][] intArrays = new int[][]{ppt0, ppt1};
        IntRecord[][] records = new IntRecord[intArrays.length][];
        for (int i = 0; i != records.length; i++) {
            records[i] = IntRecord.createArray(intArrays[i]);
        }
        records[0][4].data += "'";
        records[1][1].data += "'";
        records[1][3].data += "''";
        records[1][4].data += "'''";
        return records;
    }

    public static void main(String[] args) {

        boolean allCheckResult = true;

        for (int[] a : as) {
            IntRecord[] array = IntRecord.createArray(a);
//            insertSort(array, 0 , array.length - 1);
//            heapSort(array);
//            bubbleSort(array);
//            quickSort(array, 0, array.length - 1);
//            quickSortWithThreshold(array);
//            IntRecord[] tmpArray = new IntRecord[array.length];
//            mergeSort(array, tmpArray, 0, array.length - 1);
//            mergeSortImprove(array, tmpArray, 0, array.length - 1);
//            int maxI = maxIndex(array);
//            int d = getDegree(array[maxI].key);
//            bucketSort(array, array[maxI].key);
//            radixSort2(array, d, 10);
//            quickSortIndex(array);
            new HeapSortWithIndex(array).sort();
            boolean result = check(array);
            if (!result) {
                allCheckResult = false;
            }
        }

        System.out.println("测试稳定性: ");
        // 测试是否稳定性
        IntRecord[][] rss = stableSortArray();
        for (IntRecord[] array : rss) {
            printArray(array);
//            insertSort(array, 0 , array.length - 1);
//            heapSort(array);
//            bubbleSort(array);
//            quickSort(array, 0, array.length - 1);
//            quickSortWithThreshold(array);
//            IntRecord[] tmpArray = new IntRecord[array.length];
//            mergeSortImprove(array, tmpArray, 0, array.length - 1);
//            int maxI = maxIndex(array);
//            int d = getDegree(array[maxI].key);
//            bucketSort(array, array[maxI].key);
//            radixSort2(array, d, 10);
//            quickSortIndex(array);
            new HeapSortWithIndex(array).sort();
            boolean result = check(array);
            if (!result) {
                allCheckResult = false;
            }
        }

        System.out.println("\r\nall =======:  " + allCheckResult);
    }
}
