/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.list;
import datastructure.Tools;
/**
 *
 * @author linqiye
 */
public class ArrayList {
       
    private int maxSize;
    private int curSize;
    private int position;
    private int[] data;
    
    public ArrayList(int maxSize){
        this.maxSize = maxSize;
        data = new int[maxSize];
    }
    
    public int length(){
        return curSize;
    }
    
    public boolean insert(int p, int value){
        if(maxSize <= curSize) return false;
        if(p > curSize || p < 0) return false;
        for(int i = curSize; i != p; i--){
            data[i] = data[i - 1];
        }
        data[p] = value;
        curSize += 1;
        return true;
    }
    
    public boolean delete(int p){
        if(curSize <= 0) return false;
        if(p < 0 || p >= curSize) return false;
        for(int i = p; i != curSize - 1; i++){
            data[i] = data[i+1];
        }
        curSize -= 1;
        return true;
    }
    
    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList(10);
        for(int i = 0; i != 20; i++){
            arrayList.insert(i, i);
        }
        Tools.printArray(arrayList.data);
        arrayList.delete(0);
        Tools.printArray(arrayList.data);
        arrayList.delete(10);
        Tools.printArray(arrayList.data);
        arrayList.delete(3);
        Tools.printArray(arrayList.data);
    }
    
}
