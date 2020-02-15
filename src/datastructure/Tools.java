/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure;

import datastructure.list.LinkedList;
import java.util.Objects;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files 
import org.omg.CORBA.Environment;

/**
 *
 * @author linqiye
 */
public class Tools {
    
    // 本项目中 int 允许的最大值, 作为无穷大,因为用Integer.MAX_VALUE, Integer.MAX_VALUE + 1就溢出了
    // 2 * INFINTY < Integer.MAX_VALUE, 所以  用来比较的时候,还是用 Integer.MAX_VALUE
    public final static int INFINTY = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        long t0 = System.currentTimeMillis();
        int[] data = {1, 2, 3, 4};
        int[][] results = permutation();
//        int[][] results = permutation2(data);
//        for(int i = 0; i != results.length; i++){
//            System.out.print(i + ": ");
//            printArray(results[i]);
//        }
        long t1 = System.currentTimeMillis();
        System.out.println("total: " + results.length);
        System.out.println(t1 - t0);
//        System.out.println(check(results));
    }

    public static boolean check(int[][] results) {
        String[] ss = new String[results.length];
        for (int i = 0; i != ss.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j != results[0].length; j++) {
                sb.append(results[i][j]).append(",");
            }
            ss[i] = sb.toString();
        }
        for (int i = 0; i != ss.length - 1; i++) {
            for (int j = i + 1; j != ss.length; j++) {
                if (ss[i].equals(ss[j])) {
                    System.err.println("false:" + i + "," + j);
                    return false;
                }
            }
        }
        return true;
    }

    public static void printArray(int[] a) {
        for (int i : a) {
            System.out.print(i + ", ");
        }
        System.out.println();
    }

    public static void printArray(Integer[] a) {
        for (int i : a) {
            System.out.print(i + ", ");
        }
        System.out.println();
    }

    public static void printArray(String[] a) {
        for (String i : a) {
            System.out.print(i + ", ");
        }
        System.out.println();
    }

    public static int[][] permutation2(int[] data) {
        int size = data.length;
        for (int f = data.length - 1; f != 1; f--) {
            size *= f;
        }
        int[][] results = new int[size][data.length];
        results[0] = new int[data.length];
        for (int i = 0; i != data.length; i++) {
            results[0][i] = data[i];
        }
        int tmp = 0;
        for (int i = 1, last = 0, a = 0, b = 1; i != results.length; last++, i++) {
            results[i] = new int[data.length];
            //复制
            for (int j = 0; j != data.length; j++) {
                results[i][j] = results[last][j];
            }
            tmp = results[i][a];
            results[i][a] = results[i][b];
            results[i][b] = tmp;
            a = (a + 1) % data.length;
            b = (b + 1) % data.length;
        }
        return results;
    }

    public static int[][] permutation() {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8};
        LinkedList<Integer> ls = new LinkedList<>();
        for (int item : data) {
            ls.append(item);
        }
        LinkedList<LinkedList<Integer>> lsResult = doPermutation(ls);
        int[][] results = new int[lsResult.length()][];
//        Integer[][] results = new Integer[lsResult.length()][];
        for (int i = 0; i != results.length; i++) {
            LinkedList<Integer> tmpLs = (LinkedList<Integer>) lsResult.setPos(i).data;
            int[] sub = new int[tmpLs.length()];
            for (int j = 0; j != sub.length; j++) {
                sub[j] = (int) tmpLs.setPos(j).data;
            }
            results[i] = sub;
        }
        return results;
    }

    public static LinkedList<LinkedList<Integer>> doPermutation(LinkedList<Integer> ls) {
        if (ls.length() == 1) {
            LinkedList<Integer> tmp = new LinkedList<>();
//            tmp.append((Integer) ls.setPos(0).data);
            LinkedList<LinkedList<Integer>> result = new LinkedList();
            result.append(ls);
            return result;
        }
        LinkedList<LinkedList<Integer>> allResult = new LinkedList<>();
        for (int i = 0; i != ls.length(); i++) {
            int first = (int) ls.setPos(i).data;
            LinkedList<Integer> rest = (LinkedList<Integer>) ls.clone();
            rest.delete(i);
            LinkedList<LinkedList<Integer>> results = doPermutation(rest);
            for (int j = 0; j != results.length(); j++) {
                LinkedList<Integer> tmp = (LinkedList<Integer>) results.setPos(j).data;
                tmp.insert(0, first);
                allResult.append(tmp);
            }
        }
        return allResult;
    }

    public static String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                sb.append(data).append("\r\n");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("An error occurred.");
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    public static String readProjectAssetFile(String subPath){
        String path = Project.projectAssetDir + "/" + subPath;
        return readFile(path);
    }
    
    

}
