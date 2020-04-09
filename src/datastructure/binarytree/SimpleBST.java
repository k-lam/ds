/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.binarytree;
import static datastructure.binarytree.BinaryTree.BinaryTreeNode;
import datastructure.ParamBox;

/**
 * 删除与插入不做调整
 * @author linqiye
 */
public class SimpleBST extends BinaryTree<Integer>{
    
    public boolean insert(int value){
        ParamBox parentParam = new ParamBox();
        ParamBox nodeParam = new ParamBox();
        if(!find(value, parentParam, nodeParam)){
            BinaryTreeNode node = new BinaryTreeNode(value);
            BinaryTreeNode<Integer> parent = (BinaryTreeNode<Integer>) parentParam.pointer;
            if(value > parent.info){
                parent.setRightChild(node);
            }else{
                parent.setLeftChild(node);
            }
            return true;
        }else{
            return false;
        }
    }
    
    public void delete(int value){
        ParamBox parentParam = new ParamBox();
        ParamBox nodeParam = new ParamBox();
        if(find(value, parentParam, nodeParam)){
            BinaryTreeNode<Integer> parent = (BinaryTreeNode<Integer>) parentParam.pointer;
            if(parent.leftChild().info == value){
                parent.setLeftChild(null);
            }else{
                parent.setRightChild(null);
            }
        }
    }
    
    /**
     * 如果返回false, parentParm指向叶子结点
     * 如果返回true,nodeParm 指向找到的结点, parentParm指向父结点
     * @param value
     * @param parentParm
     * @param nodeParm
     * @return 
     */
    public boolean find(int value, ParamBox parentParm, ParamBox nodeParm){
        BinaryTreeNode<Integer> node = root;
        BinaryTreeNode<Integer> parent = null;
        parentParm.pointer = parent;
        nodeParm.pointer = root;
        if(root == null) return false;
        while(!node.info.equals(value)){
            if(node.isLeaf()){
                parentParm.pointer = node;
                return false;
            }else {
                parent = node;
                if(node.info < value){
                    node = node.rightChild();
                }else{
                    node = node.leftChild();
                }
                
            }
        }
        parentParm.pointer = parent;
        nodeParm.pointer = node;
        return true;
    }
    
    public void zig(){
    
    }
    
    public void zag(){
    
    }
    
}
