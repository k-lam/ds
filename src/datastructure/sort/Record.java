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
public abstract class Record<T>{
    
    public T key;
    public Object data;
    
    public abstract boolean lessThan(Record<T> other);
    public abstract boolean equal(Record<T> other);
    
    public boolean lessOrEqual(Record<T> other){
        return lessThan(other) || equal(other);
    }
    
    public boolean bigerThan(Record<T> other){
        return !lessOrEqual(other);
    }
    
    public boolean bigerOrEqual(Record<T> other){
        return !lessThan(other);
    }
    
    public static boolean check(Record[] array){
        System.out.print(array[0].data +", ");
        for(int i = 0; i < array.length - 1; i++){
            System.out.print(array[i+1].data +", ");
            if(i != 0 && i % 30 == 0){
                System.out.println();
            }
            if(array[i].bigerThan(array[i+1])){
                System.out.println("false");
                return false;
            }
        }
        System.out.println(" ture ");
        return true;
    }
    
    
    public static boolean check(int[] array){
        System.out.print(array[0] + ", ");
        for(int i = 0; i < array.length - 1; i++){
            System.out.print(array[i+1] +", ");
            if(array[i] > array[i+1]){
                System.out.println("false");
                return false;
            }
        }
        System.out.println(" ture ");
        return true;
    }
    
}
