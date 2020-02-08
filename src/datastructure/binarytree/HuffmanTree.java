/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.binarytree;

import com.google.gson.Gson;

/**
 *
 * @author linqiye
 */
public class HuffmanTree {
    static class HuffmanTreeNode{
        int weight;
        HuffmanTreeNode left;
        HuffmanTreeNode right;
        String info;
        public HuffmanTreeNode(int weight){
            this.weight = weight;
        }
    }
    
    private HuffmanTreeNode root;
    
    private HuffmanTreeNode mergeTree(HuffmanTreeNode node1, HuffmanTreeNode node2){
        HuffmanTreeNode parent = new HuffmanTreeNode(node1.weight + node2.weight);
        parent.left = node1;
        parent.right = node2;
        return parent;
    }
    
    static class MinHeapHuffman extends Heap<HuffmanTreeNode>{

        public MinHeapHuffman(HuffmanTreeNode[] array) {
            super(array);
        }

        @Override
        public boolean lessThen(HuffmanTreeNode a, HuffmanTreeNode b) {
            return a.weight < b.weight;
        }
    }
    
    public HuffmanTree(int[] weights){
        HuffmanTreeNode[] leaves = new HuffmanTreeNode[weights.length];
        for(int i = 0; i != weights.length; i++){
            leaves[i] = new HuffmanTreeNode(weights[i]);
        }
        Heap<HuffmanTreeNode> heap = new MinHeapHuffman(leaves);
        
        for(int i = 0; i != weights.length - 1; i++){
            HuffmanTreeNode node1 = heap.removeTop();
            HuffmanTreeNode node2 = heap.removeTop();
            root = mergeTree(node1, node2);
            heap.insert(root);
        }
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String result = gson.toJson(this, HuffmanTree.class);
        return result;
    }
    
    public static void main(String[] args) {
        int[] weights = new int[]{2, 3, 5, 7};
        HuffmanTree huffmanTree = new HuffmanTree(weights);
        System.out.println(huffmanTree.toString());
    }
    
    
}
