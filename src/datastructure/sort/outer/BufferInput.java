/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.sort.outer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author linqiye
 */
public class BufferInput {
    //        BufferedInputStream inputBuffer;
        BufferedReader br;
        int pointer = 0;
        int[] data;
//        int rCount;
        public BufferInput(String name) {
            try {
                br = Files.newBufferedReader(Paths.get(name));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
//        public int[] re
        
        public boolean available(){
            if(data != null && pointer < data.length){
                return true;
            }
            String s;
            try {
                s = br.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
            if(s == null) return false;// 读完
            if(s.indexOf("2096000289") != -1){
                System.out.println("get one");
            }
            String[] ss = s.trim().split(" ");
            if(ss.length == 0){ return false; }
            data = new int[ss.length];
//            System.out.println("length:" + data.length);
            for(int i = 0; i != data.length; i++){
                data[i] = Integer.valueOf(ss[i]);
//                System.out.println("get " + ss[i]);
            }
            
            pointer = 0;
            return true;
//            if(next == -1) return false;
//            try {
//                 if(inputBuffer == null){
//                     return false;
//                 }
//                 int result = -1;
//                 int space = 0;
//                 while(inputBuffer.available() > 0){
//                     char c = (char) inputBuffer.read();
//                     if(c == ' '){
//                         if(space == 1) break;
//                         space++;
//                     }else if( c >= '0' && c <= '9'){
//                         if(result == -1){
//                             result = (int)c;
//                         }else{
//                             result = result * 10 + c;
//                         }
//                     }
//                 }
//                 next = result;
//                 return next == -1;
//                                  
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                return false;
//            }
        }

        public int read(){
//            System.out.println(" pointer is " + pointer);
//            rCount++;
            int d = data[pointer++];
//            if(d == 2096000289){
//                System.out.println("read 2096000289," + pointer);
//            }
//            System.out.println("read " + d);
            return d;
        }
        
        public void close(){ 
            try {
                br.close();
//                System.out.println("read count: " + rCount);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
}
