/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.tree;

import com.google.gson.annotations.SerializedName;
import datastructure.VisitPeerChecker;
import datastructure.Visitor;

/**
 *
 * @author linqiye
 */
public class Tree<T> {
    
    public static class TreeNode<T>{
        @SerializedName("info")
        T value;
        @SerializedName("left")
        TreeNode<T> leftMostChild;
        @SerializedName("right")
        TreeNode<T> rightSibling;
        
        public TreeNode(T value){
            this.value = value;
        }
        
        public boolean isLeaf(){
            return leftMostChild == null;
        }
        
        public void setValue(T value){ this.value = value; }
        
        public void setChild(TreeNode<T> pointer){
            if(leftMostChild == null){ leftMostChild = pointer;}
            else{
                TreeNode<T> last = leftMostChild;
                while (last.rightSibling != null) last = last.rightSibling;
                last.rightSibling = pointer;
            }
        }
        
        public void setSibling(TreeNode<T> pointer){ this.rightSibling = rightSibling; }
        /**
         * 以第一个左孩子身份插入节点
         * @param node 
         */
        public void insertFirstChild(TreeNode<T> node){
            if(leftMostChild == null){ leftMostChild = node; }
            else{
                node.rightSibling = leftMostChild;
                leftMostChild = node;
            }
        }
        
        /**
         * 以有兄弟身份插入节点,插入到末尾
         * @param node 
         */
        public void insertNext(TreeNode<T> node){
            if(leftMostChild == null) leftMostChild = node;
            else{
                TreeNode<T> p = leftMostChild;
                while(p.rightSibling != null) p = p.rightSibling;
                p.rightSibling = node;
            }
        }
    }
    
    protected TreeNode<T> root;
    
    public Tree(TreeNode<T> root){ this.root = root; }
    
    public boolean isEmpty(){ return root == null; }
    
    public TreeNode<T> parent(TreeNode<T> current){ return null;}
    
    public void deleteSubTree(TreeNode subroot){}
    
    public void rootFirstTraverse(TreeNode node){
    
    }
    
    public void rootLastTraverse(TreeNode node){}
    
    public void layerTraverse(TreeNode root){}
    
    public void destroyNodes(TreeNode<T> root){}
    
    public void mirrorTree(){}
    
    private Visitor checker;
    public void setChecker(VisitPeerChecker checker){ this.checker = checker; }
    public void checkerChange(){
        if(checker != null) checker = ((VisitPeerChecker)checker).peer();
    }
    public void visit(TreeNode<T> node){
        if(checker != null){ checker.visit(node.value); }
        else{
            System.out.println(node.value);
        }
    }
    
    public static void main(String[] args) {
        
    }
}
