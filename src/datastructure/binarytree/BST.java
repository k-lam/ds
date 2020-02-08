/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.binarytree;

/**
 * 用java编写难道超大! 在sublime用c++写
 * 保存在 study/data_structure/sublime_code/BST.cpp里
 * @author linqiye
 */
public class BST extends BinaryTree<Integer>{
    public void removehelp(BinaryTreeNode<Integer> root, int value){
        if(root.info > value){
            removehelp(root.leftChild(), value);
        }else if(root.info < value){
            removehelp(root.rightChild(), value);
        }else{
            
        }
    }
    
//    public void removehelp(BinaryTreeNode<Integer> parent, BinaryTreeNode<Integer> node, int value){
//        if(node == null){
//            System.err.println("not found");
//        }else if(node.info > value){
//            removehelp(node, node.leftChild(), value);
//        }else if(node.info < value){
//            removehelp(node, node.rightChild(), value);
//        }else{// 找到了
////            if(parent == null){ parent = root;}
////            if(node.leftChild() == null) node
//        }
//    }
    
    /**
     * 删除BST中最小的,并返回
     * @param node
     * @return 
     */
//    protected BinaryTreeNode<Integer> deleteMin(BinaryTreeNode<Integer> node){
//        BinaryTreeNode<Integer> pointer = node;
//        BinaryTreeNode<Integer> last = node;
//        while(pointer.leftChild() != null){
//            if(pointer.leftChild() != null){
//                
//            }
//        }
//    }
    
}
