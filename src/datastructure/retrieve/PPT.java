/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.retrieve;

import datastructure.sort.IntRecord;
import datastructure.list.ArrayList;

/**
 *
 * @author linqiye
 */
public class PPT {
    // 数据从index 1 开始存储
    public static int seqSearch(ArrayList<IntRecord> array, int key){
        IntRecord guard = new IntRecord(key);
        array.set(0, guard);
//        for(int i = array.length() - 1; !array.get(i).equal(key); i--);
        int i = array.length() - 1;
        while(!array.get(i).equal(key)) i--;
        return i;
    }
    
    public static int binarySearch(ArrayList<IntRecord> array, int key){
        int low = 1, high = array.length() - 1;// 为了兼容seqSearch, 实际上这样是好的,对于包设计来说
        while(low <= high){// 易漏等号, 基本上都漏.... low = high 也是合理的啊!
            int mid = (low + high) / 2;
            if(array.get(mid).equal(key)){
                return mid;
            }else if(array.get(mid).lessThan(key)){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }
        return 0;
    }
    
    public static int binarySearch(int[] array, int key){
        int l = 1, r = array.length - 1;
        while(l <= r){
            int m = (l + r) / 2;
            if(array[m] == key){
                return m;
            }else if(array[m] < key){
                l = m + 1;
            }else{
                r = m - 1;
            }
        }
        return 0;
    }
    
    public static int seqSearch(int[] array, int key){
        //array 从1开始, 0位存哨岗
        array[0] = key;
        int index = array.length - 1;
        while(array[index] != key) index--;//while(array[index--] != key); 错的, 因为就算 == key后  还是会--...
        return index;
    }
    
    
    
    public static void main(String[] args) {
        int[] array0 = new int[]{ 1, 2 ,3 ,4, 5, 6};
//        IntRecord[] rs = IntRecord.createArray(array0);
//        ArrayList<IntRecord> list = new ArrayList<IntRecord>(rs, 1, array0.length + 1);
//        int key = 1;
//        int p = seqSearch(list, key);
//        int p1 = binarySearch(list, key);
//        
//        System.out.println(p);
//        System.out.println(p1);
        int[] array = new int[array0.length + 1];
        
        System.arraycopy(array0, 0, array, 1, array0.length);
        int key = 2;
        
        int p = seqSearch(array, key);
        int p1 = binarySearch(array, key);
        
        System.out.println(p);
        System.out.println(p1);
        System.out.println("done");
    }
}
