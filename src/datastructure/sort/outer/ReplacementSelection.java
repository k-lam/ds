/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.sort.outer;

import datastructure.Project;
import datastructure.Tools;
import datastructure.binarytree.Heap;

/**
 * 置换选择算法,写成一个类方便看
 * 
 * 为了方便处理, 每一个顺串单独保存成一个文件!
 * 当然可以所有顺串保存在同一个文件,但要定义数据格式, 而这不是重点
 * @author linqiye
 */
public class ReplacementSelection {
    
    
    public static class MinHeap extends Heap<Integer>{
        /**
         * 
         * @param array 初始化数据, 这个array长度就是以后heap的大小
         * @param length 初始化heap的currentSize
         */
        public MinHeap(int[] array, int length){
            this.heapArray = new Integer[array.length];
            for(int i = 0; i != length; i++){
                heapArray[i] = array[i];
            }
            this.currentSize = length;
        }

        @Override
        public boolean lessThen(Integer a, Integer b) {
            return a < b;
        }
        
        public void setTop(int value){
            this.heapArray[0] = value;
        }
        
        public int getTop(){
            return heapArray[0];
        }

        public void setLast(int value) {
            heapArray[currentSize] = value;
        }
        
        public void setSize(int size){
            this.currentSize = size;
        }
        
        public Integer[] getArray(){
            return heapArray;
        }
        
        public int getSize(){ return currentSize;}
       
    }
    
    String inputFileName;
//    String outputFileName;
    int n;
    String runDir; 
    int runs_count = 0;
    
    /**
     * 
     * @param input
     * @param output
     * @param n 初始顺串长度, 也就是堆长
     */
    public ReplacementSelection(String input,String outputDir, int n){
        this.inputFileName = input;
        this.runDir = outputDir;
//        this.outputFileName = output;
        this.n = n;
    }
    
    public void run(){
        Tools.clearDir(runDir);
        BufferInput bufferInput = new BufferInput(inputFileName);
//        int cc = 0;
//        while(bufferInput.available()){
//            bufferInput.read();
//            cc++;
//        }
//        if(true){
//            System.out.println(cc);
//            return;
//        }
        int[] array = new int[n];
        int len;
        for(len = 0; len != n; len++){
            if(bufferInput.available()){
                array[len] = bufferInput.read();
            }else{
                break;
            }
        }
        if(len == 0){ return; }
        
        MinHeap heap = new MinHeap(array, len);

//        int no = 0;
        int[] count = new int[1];
        count[0] = 0;
        while(true){
            boolean flag = makeOneRun(runs_count, bufferInput, heap, len);

            runs_count++;
            if(!flag){
                break;
            }
        }
        bufferInput.close();
        
//        for(int i = 0; i != runs_count; i++){
//            if(!Checker.check(runDir + "/run_" + i, count)){
//                System.err.println("check fail in " + runs_count);
//            }
//        }
//        Checker.check(runDir + "/run_" + (runs_count + 1), count);
//        try {
//            Checker.check(runDir + "/run_" + (runs_count), count);
//        } catch (Throwable e) {
////            e.printStackTrace();
//        }
        
//        System.out.println("done, check : " + count[0] + ", " + runs_count);
    }
    
    /**
     * 可能情况:
     * 1. heap刚好满size
     * 2. bufferInput 已经空了
     * @param no
     * @param bufferInput
     * @param heap
     * @return heap size
     */
    private boolean makeOneRun(int no, BufferInput bufferInput, MinHeap heap, int len){
        heap.setSize(len);
        heap.buildHeap();
        BufferOutput bufferOutput = new BufferOutput(runDir + "/run_" + no);
//        int nextSize = 0;
        int preSize = heap.getSize();
        int last;// last is the last element's index of current loop
//        int wCount = 0;
        for(last = heap.getSize() - 1; last >= 0;){
            if(bufferInput.available()){
                int minValue = heap.getTop();
                bufferOutput.write(minValue);
//                System.out.println("minValue:" + minValue);
                int r = bufferInput.read();
//                System.out.println("read data:" + r);
                if(minValue == 2146173121){
                    System.out.println(no + ",");
                    Tools.printArray(heap.getArray());
                }
                if(r >= minValue){
                    heap.setTop(r);
                    heap.siftDown(0);
//                    System.out.println(no + ": r"+ r + ",min:" + minValue);
                }else{
                    heap.removeTop();
                    heap.setLast(r);
                    heap.setSize(last--);           
                }
            }else{//bufferInput已经没有更多数据
                // 把heap的数据都输出到bufferOutput
//                System.out.println("not available, " + preSize + ", last:" + last);
                
                while(!heap.isEmpty()){
                    bufferOutput.write(heap.removeTop());
                }
                
                // 移动heapArray后面的到前面
                Integer[] array = heap.getArray();
//                Tools.printArray(array);
                heap.setSize(preSize - last - 1);
                for(int i = 0; i != heap.getSize(); i++){
                    array[i] = array[last + 1 + i];
                }
                heap.buildHeap();
                bufferOutput.close();
                runs_count++;
                bufferOutput = new BufferOutput(runDir + "/run_" + runs_count);
                while(!heap.isEmpty()){
                    bufferOutput.write(heap.removeTop());
                }
                bufferOutput.close();
                return false;
            }
        }
        bufferOutput.close();
        return true;
    }
    
    public int getRunsCount(){
        return runs_count;
    }
    
    public static void main(String[] args) {
//        Tools.clearDir(Project.OUTERSORT_INPUT_FILE);
        new ReplacementSelection(Project.OUTERSORT_INPUT_FILE, Project.tmpFileDir + "/outer_sort/runs", 256).run();
    }
    
}
