/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.tree;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import datastructure.LnkChecker;
import datastructure.Project;
import datastructure.Tools;
import datastructure.list.LnkStack;
import datastructure.list.Stack;
import datastructure.list.*;
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
        
        public void setSibling(TreeNode<T> pointer){ this.rightSibling = pointer; }
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
    
    public TreeNode<T> parent(TreeNode<T> current){ 
        TreeNode<T> tmp = root;
        Queue<TreeNode<T>> queue = new LnkQueue<>();
        while(tmp != null){
            if(current == tmp) return null;
            queue.enQueue(tmp);
            tmp = tmp.rightSibling;
        }
        TreeNode<T> parent = null;
        while (!queue.isEmpty()) {            
            tmp = queue.deQueue();
            parent = tmp;
            tmp = tmp.leftMostChild;
            while(tmp != null){
                if(tmp == current){
                    return parent;
                }
                queue.enQueue(tmp);
                tmp = tmp.rightSibling;
            }
        }
        return null;
    }
    
    public void deleteSubTree(TreeNode subroot){
        if(root == subroot){ 
                root = root.rightSibling;
        }else{
            TreeNode parent = parent(subroot);
            if(parent == null){// parent = null 有三种可能,1. root 2, root的兄弟 3, subroot不属于森林
                TreeNode pointer = root;
                while(pointer != null && pointer.rightSibling != subroot){
                    pointer = pointer.rightSibling;
                }
                pointer.rightSibling = subroot.rightSibling;
            }else if(parent.leftMostChild == subroot){
                parent.leftMostChild = subroot.rightSibling;
            }else{
                TreeNode pointer = parent.leftMostChild;
                while(pointer.rightSibling != subroot) pointer = pointer.rightSibling;
                pointer.rightSibling = subroot.rightSibling;
            }
        }
        subroot.rightSibling = null;
        destroyNodes(subroot);
    }
    
    public void rootFirstTraverse(){ rootFirstTraverse(root); }
    
    public void rootFirstTraverse(TreeNode node){
        if(node == null) return;
        visit(node);
        rootFirstTraverse(node.leftMostChild);
        rootFirstTraverse(node.rightSibling);
    }
    
    public void rootFirstTravsrsePPT(){
        rootFirstTravsrsePPT(root);
    }
    
    public void rootFirstTravsrsePPT(TreeNode<T> root){
        while(root != null){
            visit(root);
            rootFirstTravsrsePPT(root.leftMostChild);
            root = root.rightSibling;
        }
    }
    
    public void rootFirstTravsrseNoR(){
        depthTraverse(true);
    }
    
    private void depthTraverse(boolean first){
        TreeNode<T> pointer = root;
        Stack<TreeNode<T>> stack = new LnkStack<>();
        while(pointer != null || !stack.isEmpty()){
            if(pointer != null){
                if(first){ visit(pointer); }
                stack.push(pointer);
                pointer = pointer.leftMostChild;
            }else{
                pointer = stack.top();
                stack.pop();
                if(!first){ visit(pointer); }
                pointer = pointer.rightSibling;
            }
        }
    }
    
    public void rootLastTraverse(TreeNode node){
        if(node == null) return;
        rootLastTraverse(node.leftMostChild);
        visit(node);
        rootLastTraverse(node.rightSibling);
    }
    
    public void rootLastTraverse(){
        rootLastTraverse(root);
    }
    
    public void rootLastTraversePPT(TreeNode root){
        while(root != null){
            rootLastTraverse(root.leftMostChild);
            visit(root);
            root = root.rightSibling;
        }
    }
    
    public void rootLastTraversePPT(){
        rootLastTraverse(root);
    }
    
    public void rootLastTraverseNoR(){
        depthTraverse(false);
    }
    
    public void layerTraverse(TreeNode<T> root){
        // 注意与二叉树层次遍历不同
        Queue<TreeNode<T>> queue = new LnkQueue<>();
        while(root != null){
            queue.enQueue(root);
            root = root.rightSibling;
        }
        while(!queue.isEmpty()){
            root = queue.deQueue();
            visit(root);
            root = root.leftMostChild;
            while(root != null){
                queue.enQueue(root);
                root = root.rightSibling;
            }
        }
    }
    
    public void layerTraverse(){ layerTraverse(root);}
    
    public void destroyNodes(TreeNode<T> root){}
    
    public TreeNode<T> mirrorTree(){
        root = mirrorTree(root);
        return root;
    }
    
    public TreeNode<T> mirrorTree2(TreeNode<T> root){
        // 不用stack实现,考试不要写这个,很他妈容易错,用简单的方法mirrorTree
        if(root == null) return null;
        TreeNode<T> next = root.rightSibling;
        root.rightSibling = null;
        while(root != null){
            root.leftMostChild = mirrorTree2(root.leftMostChild);
            if(next != null){
                TreeNode<T> tmpNext = next;
                next = next.rightSibling;
                tmpNext.rightSibling = root;
                root = tmpNext;
            }else{
                break;
            }
        }
        return root;
    }
    
    public TreeNode<T> mirrorTree(TreeNode<T> root){ 
        if(root == null) return null;
        Stack<TreeNode<T>> stack = new LnkStack<>();
        stack.push(null); // 监视梢
        while(root != null){
            stack.push(root);
            root = root.rightSibling;
        }
        root = stack.top();
        stack.pop();
        TreeNode<T> pointer = root;
//        while(pointer != null){//两个一样的
        while(!stack.isEmpty()){
            pointer.leftMostChild = mirrorTree(pointer.leftMostChild);// 容易漏掉赋值!!!
            pointer.rightSibling = stack.top();
            pointer = stack.top();
            stack.pop();
        }        
        return root;
    }
    
    private LnkChecker checker;
    public void setChecker(LnkChecker checker){ this.checker = checker; }

    public void visit(TreeNode<T> node){
        if(checker != null){ checker.visit(node == null ? null : node.value); }
        else {
            System.out.println(node == null ? null : node.value);
        }
    }
    
    public static void main(String[] args) {
        String json = Tools.readProjectAssetFile(Project.treeFiles[2]);
        Gson gson = new Gson();
        Object obj = gson.fromJson(json, new TypeToken<Tree<String>>() {
        }.getType());
        Tree<String> tree = (Tree<String>) obj;
        
//        LnkChecker checker = new LnkChecker();
//        
//        tree.setChecker(checker);
//        tree.rootFirstTraverse();
//        
//        checker.otherChecker();
//        tree.rootFirstTravsrsePPT();
//        
//        checker.otherChecker();
//        tree.rootFirstTravsrseNoR();
//        
//        checker.check();
//        
//        LnkChecker lastChecker = new LnkChecker();
//        tree.setChecker(lastChecker);
//        tree.rootLastTraverse();
//        
//        lastChecker.otherChecker();
//        tree.rootLastTraversePPT();
//        
//        lastChecker.otherChecker();
//        tree.rootLastTraverseNoR();
//        
//        lastChecker.check();
//        
//        tree.layerTraverse();
        tree.mirrorTree(tree.root);
        tree.rootLastTraverse();
    }
}
