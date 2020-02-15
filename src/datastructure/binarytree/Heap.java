/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.binarytree;
import datastructure.ParamBox;

/**
 * 默认是最小堆实现
 * @author linqiye
 */
public abstract class Heap<T> {
    protected T[] heapArray;
    int currentSize;
    
    public Heap(){}
    
    public Heap(int size){
        heapArray = (T[]) new Object[size];
        currentSize = 0;
    }
    
    public Heap(T[] array){
        heapArray = array;
        currentSize = array.length;
        buildHeap();
    }
    
    void buildHeap(){
        for(int i = currentSize / 2 - 1; i >= 0 ; i--){
            siftDown(i);
        }
    }
    
    public boolean isEmpty(){
        return currentSize == 0;
    }
    
    public boolean isLeaf(int pos){
        return pos > (currentSize / 2 - 1); 
    }
    
    public int leftChild(int pos){
        return 2 * pos + 1;
    }
        
    public int rightChild(int pos){
        return 2 * pos + 2;
    }
    
    public int parent(int pos){
        if(pos == 0) return -1;// 这个重要 因为 (0 - 1) / 2 居然返回 0
        return (pos - 1) / 2;
    }
    
    public boolean remove(int pos, ParamBox paramBox){
        if(pos >= currentSize || pos < 0){ return false; }
        T tmp = heapArray[pos];
        heapArray[pos] = heapArray[currentSize - 1];
        heapArray[currentSize - 1] = tmp;
        paramBox.pointer = heapArray[--currentSize];
        int parent = parent(pos);
        if(parent >= 0 && lessThen(heapArray[pos], heapArray[parent])){
            siftUp(pos);
        }else{
            siftDown(pos);
        }
        return true;
    }
    
    public T removeTop(){
        ParamBox paramBox = new ParamBox();
        if(remove(0, paramBox)){
            return (T) paramBox.pointer;
        }
        System.err.println("Heap is empty!");
        return null;
    }
    
    public boolean insert(T newNode){
        if(currentSize >= heapArray.length) { return false; }
        heapArray[currentSize++] = newNode;
        siftUp(currentSize - 1);
        return true;
    }
    
    // a 比 b 小 返回true
    public abstract boolean lessThen(T a, T b);
    
//    public 
    
    public void siftUp(int position){// 实际和PPT代码一样的
        int parent = parent(position);
        int last = position;
        T tmp = heapArray[position];
        while(parent >= 0 && lessThen(tmp, heapArray[parent])){
            heapArray[parent] = heapArray[last];
            last = parent;
            parent = parent(parent);
        }
        heapArray[last] = tmp;
    }
    
    public void siftDown(int position){
        T tmp = heapArray[position];
        int child = leftChild(position);
        int last = position;
        while(child < currentSize){
            if(child < currentSize - 1 && lessThen(heapArray[child + 1], heapArray[child])){
                child++;
            }
            if(lessThen(heapArray[child], tmp)){
                heapArray[last] = heapArray[child];
                last = child;
                child = leftChild(child);
            }else{
                break;
            }
        }
        heapArray[last] = tmp;
    }
    
    public static class MinHeapInt extends Heap<Integer>{
        
        public MinHeapInt(){}
        
        public MinHeapInt(int [] array){
            Integer[] integers = new Integer[array.length];
            for(int i = 0; i != array.length; i++){
                integers[i] = array[i];
            }
            this.heapArray = integers;
        }

        @Override
        public boolean lessThen(Integer a, Integer b) {
            return a < b;
        }
    }
    
    public static class MaxHeapInt extends MinHeapInt{

        @Override
        public boolean lessThen(Integer a, Integer b) {
            return a > b;
        }
        
    }
    
    public static void main(String[] args) {
        int[] a = new int[10];
        int i = 0;
        a[i++] = 10;
        System.out.println(a[0]+ "," + a[1]);
    }
}
