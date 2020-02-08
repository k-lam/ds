/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure;

import static datastructure.Tools.check;
import datastructure.list.ArrStack;

/**
 *
 * @author linqiye
 */
public class Permutation {
    public static void main(String[] args) {
        int l = 10;
        int size = l;
        for(int f = l - 1; f != 1; f--){
            size *= f;
        }
        System.out.println("result need: " +  (size * l * 4.0 / 1024 / 1024) + 'M');
        int[] data = new int[l];
        for(int i = 0; i != l; i++){
            data[i] = i;
        }
        permutation3(data);
    }
    
    public static class TreeNode{
        TreeNode leftChild;
//        TreeNode childHeader = new TreeNode(-1);// 不用带header的循环链表,写循环就是蛋疼
        TreeNode next;
        int value;
   
        public TreeNode(int value){
            this.value = value;
        }
        
        public boolean isLeaf(){
            return leftChild == null;
//            return childHeader.next == null;
        }
        
//        public boolean 
    }
    
    static class PermutationTree{
        int[] data;
        TreeNode root;

        public PermutationTree(int[] data) {
            this.data = data;
        }
        
        TreeNode buildTree(){
//            root = new TreeNode(data[0]);
            TreeNode tail = null;
            for(int i = 0; i != data.length; i++){
                if(i == 0){
                    root = tail = new TreeNode(data[i]);
                }else{
                    tail.next = new TreeNode(data[i]);
                    tail = tail.next;
                }
            }
            tail.next = root;// 循环链表
            buildLayer(root);
            return root;
        }
        
        void buildLayer(TreeNode layerNode){
            if(layerNode.next == layerNode){// 只有一个的情况
                layerNode.leftChild = null;
                layerNode.next = null;
                return;
            }
            boolean flag = true;
            TreeNode parentNode = layerNode;
            TreeNode lastNode = layerNode;
            while(layerNode != parentNode || flag){
                TreeNode childNode = parentNode.leftChild = buildLayerNodeExcludeParent(parentNode);
                //构建下一层
                buildLayer(childNode);
                flag = false;
                lastNode = parentNode;
                parentNode = parentNode.next;
            }
            lastNode.next = null;// 解开循环
        }
        
        TreeNode buildLayerNodeExcludeParent(TreeNode parentNode){// parentNode.next != parentNode
            TreeNode head = null, tail = null;
            for(TreeNode node = parentNode.next; node != parentNode; node = node.next){
                if(head == null){
                    head = tail = new TreeNode(node.value);
                }else{
                    tail.next = new TreeNode(node.value);
                    tail = tail.next;
                }
            }
            tail.next = head;
            return head;
        }
        
        int[][] results;
        int i = 0;
        ArrStack<TreeNode> stack;
        int[][] visit(){
            int size = data.length;
            for(int f = data.length - 1; f != 1; f--){
                size *= f;
            }
            results = new int[size][data.length];
            i = 0;
            stack = new ArrStack<>(data.length);
            doVisit(root);
            return results;
        }
        
        void doVisit(TreeNode node){
            if(node == null){
                if(stack.size() == data.length){
                    for(int j = 0; j != data.length; j++){
                        results[i][j] = ((TreeNode)stack.elems[j]).value;
                    }
                    i++;
                }
                stack.pop();
                return;
            }
            stack.push(node);
//            results[i][j] = node.value;
//            j++;
            doVisit(node.leftChild);
            doVisit(node.next);
        }
    }
    
    
    
    public static int[][] permutation3(int [] data){
        long t0 = System.currentTimeMillis();
        int size = data.length;
        for(int f = data.length - 1; f != 1; f--){
            size *= f;
        }
//        int[][] results = new int[size][data.length];
        
        PermutationTree tree = new PermutationTree(data);
        TreeNode root = tree.buildTree();
        long t1 = System.currentTimeMillis();
        int[][] results = tree.visit();
//        for(int i = 0; i != results.length; i++){
//            System.out.print(i + ": ");
//            printArray(results[i]);
//        }
        long t2 = System.currentTimeMillis();
        System.out.println("total: " + results.length);
        System.out.println(t1 - t0 + "," + (t2 - t0));
//        System.out.println(check(results));
        return results;
    }  
}
