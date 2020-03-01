/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.sort.outer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author linqiye
 */
class BufferOutput {
    BufferedWriter bw;
//    int wCount = 0;
    public BufferOutput(String name) {
        try {
            bw = Files.newBufferedWriter(Paths.get(name), StandardOpenOption.CREATE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void write(int i) {
        try {
            bw.write(i + " ");
//            wCount++;
            //                System.out.println("write " + i);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            bw.flush();
            bw.close();
//            System.out.println("write count: " + wCount);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
