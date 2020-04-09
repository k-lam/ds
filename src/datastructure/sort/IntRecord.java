/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.sort;

/**
 *
 * @author linqiye
 */
public class IntRecord extends Record<Integer>{
    
    public IntRecord(){}
    
    public IntRecord(int key){
        this();
        this.key = key;
    }

    @Override
    public boolean lessThan(Record<Integer> other) {
        return this.key < other.key;
    }
    
    public boolean lessThan(int key){
        return this.key < key;
    }
    
    @Override
    public boolean equal(Record<Integer> other) {
        return key.equals(other.key);
    }
    
    public boolean equal(int other) {
        return key.equals(other);
    }

    public static IntRecord[] createArray(int[] arrays){
        IntRecord[] rs = new IntRecord[arrays.length];
        for(int i = 0; i != arrays.length; i++){
            rs[i] = new IntRecord();
            rs[i].key = arrays[i];
            rs[i].data = arrays[i]+"";
        }
        return rs;
    }



    
}
