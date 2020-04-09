/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.retrieve;

import datastructure.ParamBox;

/**
 * 现在是只支持int作为key的
 * @author linqiye
 */
public class HashDictClose<V> {
    static class Elem<V>{
        int key;
        V value;

        public Elem() {
        }

        public Elem(int key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    protected Elem[] arrays = new Elem[256];// 如果比256大 要修改, 取这个值是为了方便平方取整法
    protected int currentSize = 0;
    public Elem EMPTY = new Elem();
    protected Elem TOMBSTONE = new Elem();
    
    public HashDictClose(){
        for(int i = 0; i != arrays.length; i++){
            arrays[i] = EMPTY;
        }
    }
    
    private int hash(int key){
        // 平方取中法
        int result = 0;
        for(int d = 1; d != 5; d++){
            int mask = 0xff << (d  - 1) * 8;
            int tmp = key & mask;
            tmp = tmp << 1 & mask;
            tmp = tmp >> (d - 1) * 8;
            
            result |= (tmp & (0x3 << (d - 1) * 2)); 
        }
        // 折叠法
//        int result = 0;
//        for(int d = 0; d != 4; d++){
//            result += (key >> 8 * d & 0xff);
//        }
//        
//        return result % arrays.length;
        return result % arrays.length;
    }
    
    private int hash2(int key){
        return key % 251 + 1;// 最接近255的质数
    }
    
    // 探查序列
    // ppt 10.5 p20 的方法4
    private int p(int d0, int i){
        return (d0 + i * hash2(i)) % arrays.length;
    }
    
    // 实际代码是不完整的,因为没有考虑是否满了
    private boolean doInsert(int key, V value){
        int d0 = hash(key);
        int d = d0;
        int i = 1;
        int pos = 0;
        boolean foundTomb = false;
        while(arrays[d] != EMPTY){
            if(arrays[d].key == key){ // 相同, 不允许插入,插入失败
                return false;
            }
            if(arrays[d] == TOMBSTONE && !foundTomb){// 不能找到第一个就直接插入, 要保证没有重复key
                foundTomb = true;
                pos = d;
            }
            d = p(d0, i);
            i++;
        }
        if(foundTomb) d = pos;
        arrays[d] = new Elem(key, value);
        return true;
    } 
    
    public boolean insert(int key, V value){
        if(currentSize == arrays.length) {
            System.err.println("hashDict is full");
            return false;
        }
        if(doInsert(key, value)){
            currentSize++;
            return true;
        }
        return false;
    }
    
    public V delete(int key){
        int d0 = hash(key);
        int d = d0;
        int i = 1;
        while(arrays[d] != EMPTY){
            if(arrays[d].key == key){
                V tmp = (V) arrays[d].value;
                arrays[d] = TOMBSTONE;
                currentSize--;
                return tmp;
            }
            d = p(d0, i);
            i++;
        }
        return null;
    }
    
    public boolean search(int key, ParamBox box){
        int d0 = hash(key);
        int d = d0;
        int i = 1;
        while(arrays[d] != EMPTY){
            if(arrays[d].key == key){
                box.pointer = arrays[d].value;
                return true;
            }
            d = p(d0, i);
            i++;
        }
        return false;
    }
    
}
