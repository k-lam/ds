/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.tree;

/**
 *
 * @author linqiye
 */
public class ParTree<T> {
    public static class ParTreeNode<T>{
        T value;
        public ParTreeNode<T> parent;//parent=null 表示自己就是这个类的代表!
        int count = 0;
        
        ParTreeNode<T> getParent(){ return parent; }
        
        void setParent(ParTreeNode<T> parent){ this.parent = parent; }
        
        void setCount(int count){ this.count = count; }
        
        public int getCount(){ return count;}
    }
    // 实际上完全可以用链表来存储, 但考虑到并查集用于静态的情况,
    // 所以用数组存储(存储密度更高)
    public ParTreeNode<T>[] array;//不应该public的, 为了有些算法方便
    
//    public ParTree(ParTreeNode<T>[] array){ this.array = array; }
    
    
    public ParTree(int count){
//        array = (ParTreeNode<T>[]) new Object[count];
        array = new ParTreeNode[count];
        for(int i = 0; i != count; i++){
            array[i] = new ParTreeNode<>();
//            array[i].parent = array[i];这是错的!parent=null 表示自己就是这个类的代表!
            array[i].count = 1;
        }
    }
    
//    public ParTreeNode<T> find(int i){
//        return find(array[i]);
//    }
    
    public ParTreeNode<T> find(ParTreeNode<T> node){ 
        ParTreeNode<T> pointer = node;
        while(pointer.parent != null) pointer = pointer.parent;
        return pointer;
    }
    
    public void union(int i, int j){
        ParTreeNode node1 = array[i];
        ParTreeNode node2 = array[j];
        union(node1, node2);
    }
    
    public void union(ParTreeNode node1, ParTreeNode node2){
        ParTreeNode root1 = find(node1);
        ParTreeNode root2 = find(node2);
        if(root1 == root2) return;
        if(root1.getCount() > root2.getCount()){
            root2.setParent(root1);
            root1.setCount(root2.getCount() + root1.getCount());
        }else{
            root1.setParent(root2);
            root2.setCount(root2.getCount() + root1.getCount());
        }
    }
    
    public boolean different(int i, int j){ 
        return findPC(array[i]) != findPC(array[j]);
    }
    
    public ParTreeNode<T> findPC(ParTreeNode<T> node){ 
        if(node.parent == null){
            return node;
        }else{
            ParTreeNode p = findPC(node.parent);
            node.setParent(p);
            return p;
        }
    }
}
