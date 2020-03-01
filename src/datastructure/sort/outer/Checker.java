/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.sort.outer;

import datastructure.ParamBox;
/**
 *
 * @author linqiye
 */
public class Checker {
    
    public static boolean check(String fname, int[] count){
        BufferInput input = new BufferInput(fname);
        int pre = Integer.MIN_VALUE;
        int now;
        int c = 0;
        while (input.available()) {            
            now = input.read();
            if(now < pre){
                System.out.println("pre:" + pre + ", now: " + now);
                input.close();
                return false; 
            }
            pre = now;
//            count[0]++;
            c++;
        }
        count[0] += c;
        System.out.println(fname + ':' + c);
        input.close();
        return true;
    }
    
    public static void check(String preName, int fileCount, int[] count){
        for(int i = 0; i != fileCount; i++){
            check(preName+i, count);
//            if(!check(preName+i, fileCount, count)){
//                System.err.println(preName + i + " check failed");
//            }
        }
        System.out.println(" check all count:  " + count[0]);
    } 
    
}
