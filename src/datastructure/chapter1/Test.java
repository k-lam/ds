/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.chapter1;

/**
 *
 * @author linqiye
 */
public class Test {
    public static void main(String[] args) {
        int[] a = {0, 1,2,3,4,5,6,7,8,9};
//        offsetK2(a, 4);
        rotatek(a, 4);
        int N = 10;
//        check(N,4);
//        for(int i = 1; i != N; i++){
//            if(!check(N,i)){
//                System.out.println(i + " false!");
//            }
//        }
        System.out.print("done");
    }
    
    static boolean check(int N, int p){
        if(N % p ==0) return true;
        boolean[] result = new boolean[N];
        for(int i = 0; i != N; i++){
            result[i] = false;
        }
        result[0] = true;
        // x_(i+1) = (x_i + p) % N
        int next = p;
        while(next != 0){
            System.out.print(next + ", ");
            result[next] = true;
            next = (next + p) % N;
        }
        for(boolean r : result){
            if(!r) return false;
        }
        return true;
    }


    static void offsetK(int[] a,int K){
        // 由于KL不懂群论，但是经过观察可以得出以下结论
        // N = a.length， a = 0
        // , N 能整除 K，则很简单了，刚好有K个循环群, 如 a = {0, 1, 2, 3, 4, 5};K = 2;
        // 则{0 ,2 ,4}, {1, 3, 5}是各自独立的群体
        // N 不能整除 K， a = a = {0, 1, 2, 3, 4, 5, 6} K = 2,可以看到 i = (i+k) % N 这样的操作
        // i初始为0，则会得到{0, 2, 4, 6, 1, 3, 5} 这样的序列
        int count = a.length % K == 0 ? K : 1;
        K = a.length - (K % a.length);
        for(int i = 0; i < count; i++){
            int tmp = a[i];
            int l = i, r = (i + K) % a.length;
            while (r != i){
                a[l] = a[r];
                l = r;
                r = (r + K) % a.length;
            }
            a[l] = tmp;
        }

    }
 
    static void rotatek(int[] a, int k){
        int n = a.length;
        int tmp = a[0];
        int curr = 0, min = n;
        while(curr!=k){
            a[curr]=a[(curr+n-k)%n];
            curr=(curr+n-k)%n;
            if(min>curr) min=curr;
        }
        a[k] = tmp; 
        printArray(a);
        int round = 1;
        while(round < min){
            tmp = a[round];
            curr = round;
            while(curr != k+round){
                a[curr]=a[(curr+n-k)%n];
                curr=(curr+n-k)%n;
            }
            a[k + round] = tmp;
            ++round;
            printArray(a);
        }
        printArray(a);
    }
    
    static void reverse(int[] a, int start, int end){
        int count = (end - start) / 2;
        for(int i = 0; i != count; i++){
            int tmp = a[start + i];
            int j = end - i;
            a[start + i] = a[j];
            a[end - i] = tmp;
        }
    }   
    static void offsetK2(int[] a, int K){
        K = K % a.length;
        reverse(a, 0, a.length - K - 1);
        printArray(a);
        reverse(a, a.length - K, a.length - 1);
        printArray(a);
        reverse(a, 0, a.length - 1);
        printArray(a);
    }
    
    static void printArray(int[] a){
        for(int i : a){
            System.out.print(i + ", ");
        }
        System.out.println();
    }



    static int gcd(int m, int n) {

        int r;

        while((r = m % n) != 0) {

            m = n; n = r;

        }
       

        return n;
    }
}
