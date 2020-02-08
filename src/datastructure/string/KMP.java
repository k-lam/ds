/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.string;
import datastructure.Tools;
/**
 *
 * @author linqiye
 */
public class KMP {
    
    public static void main(String[] args) {
        String target = "abcabcdeabc";
        String pattern = "abcd";
        
//        System.out.println(indexOf(target, pattern, 0));
        
        pattern = "aaaabaaaac";
        
        int[] next = findNext(pattern, false);
        Tools.printArray(next);
        next = findNext(pattern, true);
        Tools.printArray(next);
    }
    
    public static int indexOf(String target, String pattern, int start){
        int[] next = findNext(pattern, true);
        return kmpMatch(target, pattern, start, next);
    }
    
    public static int[] findNext(String p, boolean optimize){
        int[] next = new int[p.length()];
        next[0] = -1;
        int j = 0;
        int k = next[0];
        while(j < next.length - 1){
            while(k >= 0 && p.charAt(k) != p.charAt(j)){
                k = next[k];
            }
            j++; k++;
            if(optimize){
                if(p.charAt(k) == p.charAt(j)){
                    next[j] = next[k];
                }else{
                    next[j] = k;
                }
            }else{
                next[j] = k;
            }
        }
        return next;
    }
    
    
    public static int[] findNext1(String p, boolean opt){
        int[] next = new int[p.length()];
        next[0] = -1;
        int j = 0;
        int k = next[0];
        while(j < next.length - 1){
            while(k >= 0 || p.charAt(k) != p.charAt(j)){
                k = next[k];
            }
            j++; k++;
            if(opt){
                if(p.charAt(k) == p.charAt(j)){
                    next[j] = next[k];
                }else{
                    next[j] = k;
                }
            }else{
                next[j] = k;
            }
        }
        return next;
    }
    
    public static int kmpMatch1(String tStr, String pStr, int start, int[] next){
        if(start + pStr.length() > tStr.length()) return -1;
        int i = start, j = 0;
        while(j < pStr.length() && i < tStr.length()){
            if(next[j] < 0 || pStr.charAt(j) == tStr.charAt(i)){
                i++; j++;
            }else{
                j = next[j];
            }
        }
        if(j >= pStr.length()){
            return i - pStr.length();
        }
        return -1;
    }
    
    public static int kmpMatch(String tStr, String pStr, int start, int[] next){
        if(start + pStr.length() > tStr.length()) return -1;
        int tIndex = start;
        int pIndex = 0;
        while(tIndex < tStr.length() && pIndex < pStr.length()){
            if(next[pIndex] == -1 || tStr.charAt(tIndex) == pStr.charAt(pIndex)){
                pIndex++; tIndex++;
            }else{
                pIndex = next[pIndex];
            }
        }
        
        if(pIndex >= pStr.length()){
            return tIndex - pStr.length();
        }
        return -1;
    }
    
}
