/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.sort.outer;

import datastructure.Tools;
import java.io.File;

/**
 *
 * @author linqiye
 */
public class LoserTree {
    
    int maxSize;    // 最大选手数
    int n;          // 当前选手数
    int lowExt;     // 最底层外部结点数
    int offset;     // 最底层外部结点之上的结点总数
    int[] B;        // 败方树数组,实际存放的是下标
    int[] L;        // 元素数组, 为了和ppt保持一致, 下标从 1 开始
    BufferInput[] inputs;
    
    BufferOutput output;
    
    String[] fnames;
    String outputFileName;
//    int rCount = 0;
//    int wCount = 0;
//    int[] inputRcount;
    public LoserTree(String[] fnames, String outputFileName){
        this.outputFileName = outputFileName;
        this.fnames = fnames;
        maxSize = fnames.length;
        if(maxSize == 1){ return;}
        
        output = new BufferOutput(outputFileName);
        L = new int[fnames.length + 1];
        
        n = fnames.length;
        // n2 = n0 - 1
        B = new int[maxSize];
        inputs = new BufferInput[maxSize];
//        inputRcount = new int[maxSize];
        for(int i = 0; i != fnames.length; i++){
            inputs[i] = new BufferInput(fnames[i]);
            read(i + 1);
        }
        
    } 
    /**
     * 
     * @param i L[i] 的i, 对应inputs[i- 1]
     * @return 
     */
    private boolean read(int i){
//        rCount++;
        if(inputs[i - 1].available()){
            L[i] = inputs[i - 1].read();
//            inputRcount[i - 1]++;
            return true;
        }
//        else{
//            System.out.println( (i - 1)+", read "+ inputRcount[i - 1]);
//        }
        return false;
    }
    
    void init(){
        int i,s;
        for(s = 1; 2*s <= n - 1; s+=s);
        lowExt = 2 *(n-s); offset = 2 * s - 1;
        
        for(i = 2; i<= lowExt; i+= 2)
            play((offset + i) / 2, i - 1, i);
        if(n % 2 == 1){
            play( n / 2, B[(n - 1) / 2], lowExt + 1);
            i = lowExt + 3;
        }else{
            i = lowExt + 2;
        }
        for(; i <= n; i += 2){
            play((i - lowExt + n - 1) / 2, i - 1, i);
        }
    }
    
    int winner(){
        return B[0]; 
    }
    
    // 数组A中,对下标是 b,c的进行比赛,返回胜利者(b or c)
    int winner(int[] A, int b, int c){
        return A[b] >= A[c] ? c : b;// 注意 winner 与 loser 是一对相反函数,如果 winner(a,b) == loser(a,b) 会有问题的所以其中一个要有 等号!!!!!!
    }
    
    // 数组A中,对下标是 b,c的进行比赛,返回败者(b or c)
    int loser(int[] A, int b, int c){
        return A[b] < A[c] ? c : b;
    }
    /**
     * 在知道左右孩子的情况下,在p结点进行的比赛
     * @param p 在p结点进行的比赛
     * @param lc 左孩子
     * @param rc 右孩子
     */
    void play(int p, int lc, int rc){
        B[p] = loser(L, lc, rc);
        int lastWin = winner(L, lc, rc);
        int win;// 缓存win
        while(p > 1 && p % 2 == 1){// 右路
            p = p/2;
            win = winner(L, lastWin, B[p]);
            B[p] = loser(L, lastWin, B[p]);
            lastWin = win;
        }
        B[p/2] = lastWin;
        
//        B[p] = loser(L, lc, rc);
//        int temp1, temp2;
//        temp1 = winner(L, lc, rc);
//        while(p > 1 && p%2 == 1){
//            temp2 = winner(L, temp1, B[p/2]);
//            B[p/2] = loser(L, temp1, B[p/2]);
//            temp1 = temp2;
//            p /= 2;
//        }
//        B[p / 2] = temp1;
    }
    
    void replay(int i){
        if(i <= 0 || i > n){
            System.err.println("i is out of bounds:" + i);
        }
        int p;
        if(i <= lowExt){
            p = (i + offset) / 2;
        }    
        else{
            p = (i - lowExt + n - 1) / 2;
        }
        B[0] = winner(L, i, B[p]);
        B[p] = loser(L, i, B[p]);
        for(; (p / 2) >= 1; p/= 2){
            int temp = winner(L, B[p / 2], B[0]);
            B[p / 2] = loser(L, B[p / 2], B[0]);
            B[0] = temp;
        }
    }
    
//    boolean min(){
//        for(int i = 1; i != L.length; i++){
//            if(L[B[0]] > L[i]){
//                return false;
//            }
//        }
//        if((L[B[1]] > L[B[2]] && L[B[1]] > L[B[3]]) ||
//                (L[B[2]] > L[B[4]] && L[B[2]] > L[B[5]]) ||
//                (L[B[3]] > L[B[6]] && L[B[3]] > L[7]) ||
//                (L[B[4]] > L[1] && L[B[4]] > L[2]) ||
//                (L[B[5]] > L[3] && L[B[5]] > L[4]) ||
//                (L[B[6]] > L[5] && L[B[6]] > L[6])
//                )
//        {return false;}
//        return true;
//    }
    
//    void printData(){
////        System.out.print(wCount + ": win:" + B[0] +"  B:");
//        for(int i = 0;i != B.length;i++){
//            System.out.print(i+":"+L[B[i]] +", ");
//        }
//        System.err.print(" L:");
//        for(int i = 1; i != L.length; i++){
//            System.out.print(i+":"+L[i] +", ");
//        }
//        System.out.println("");
//    }
    
    void run(){
        if(maxSize == 1){
            new File(fnames[0]).renameTo(new File(outputFileName));
            return;
        }
        init();
        int win = winner();
        int lastWin = win;
        int lastData = 0;
        while(L[winner()] != Integer.MAX_VALUE){
//            printData();
//            if(!min()){
//                System.err.println("ffffff");
//            }
            lastData = L[win];
            output.write(L[win]);
//            wCount++;
//            if(wCount == 162){ break; }
//            System.out.println("win:" + L[win]);
//            Tools.printArray(L);
//            printB();
//            if(win == 0){
////                System.out.println(win);
//                Tools.printArray(L);
//                printB();
//            }
            if(!read(win)){
                L[win] = Integer.MAX_VALUE;
                inputs[win - 1].close();
            }
            replay(win);
            lastWin = win;
            win = winner();
        }
//            Tools.printArray(L);
//            printB();
//            Tools.printArray(inputRcount);
//        System.out.println(" loserTree read count:" + rCount);
        output.close();
    }
    
//    void printB(){
//        StringBuilder sb = new StringBuilder("   ");
//        for(int b : B){
//            sb.append(L[b]).append("  ");
//        }
//        System.out.println(sb.toString());
//    }
    
    void cleanInputFiles(){
        for(String f : fnames){
            if(!new File(f).delete()){
                System.err.println("delete file " + f + " failed!!! ");
            }
        }
    }
    
    public static void main(String[] args) {
        int runsCount = 7;
        String[] fnames = new String[runsCount];
        for(int i = 0; i != fnames.length; i++){
            fnames[i] = "/Users/linqiye/NetBeansProjects/DataStructure/data/tmp/outer_sort/loserTree/data/run"+i;
        }
        String outputName = "/Users/linqiye/NetBeansProjects/DataStructure/data/tmp/outer_sort/loserTree/result/run";
        new LoserTree(fnames, outputName).run();
        
    }
    
}
