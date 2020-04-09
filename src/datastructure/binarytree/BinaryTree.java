/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.binarytree;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import datastructure.list.*;
import datastructure.Project;
import datastructure.Tools;
import datastructure.Visitor;
import datastructure.VisitPeerChecker;
import datastructure.ParamBox;
/**
 *
 * @author linqiye
 */
public class BinaryTree<T> {

    public static class BinaryTreeNode<T> {

        protected T info;
        private BinaryTreeNode<T> left;
        private BinaryTreeNode<T> right;
        public BinaryTreeNode<T> parent;

        public BinaryTreeNode() {
        }

        public BinaryTreeNode(T ele) {
            this();
            this.info = ele;
        }

        public BinaryTreeNode(T ele, BinaryTree<T> l, BinaryTree<T> r) {
            this(ele);
            this.left = left;
            this.right = right;
        }

        public T value() {
            return info;
        }

        public BinaryTreeNode<T> leftChild() {
            return left;
        }

        public BinaryTreeNode<T> rightChild() {
            return right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public void setLeftChild(BinaryTreeNode<T> node) {
            this.left = node;
            node.parent = this;
        }

        public void setRightChild(BinaryTreeNode<T> node) {
            this.right = node;
            node.parent = this;
        }
    }

    protected BinaryTreeNode<T> root;
    private Visitor visitor;

    public BinaryTree() {}

    public BinaryTree(T info) {
        root = new BinaryTreeNode<>(info);
    }

    public BinaryTree(BinaryTreeNode<T> root) {
        this();
        this.root = root;
    }

    public static <T> BinaryTree<T> createTree(T info, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
        BinaryTree<T> tree = new BinaryTree<T>(info);
        tree.root.setLeftChild(leftTree.root);
        tree.root.setRightChild(rightTree.root);
        return tree;
    }

    public BinaryTreeNode<T> root() {
        return root;
    }

    public BinaryTreeNode<T> parent(BinaryTreeNode<T> current) {
        return parent(root, current);
    }
    
    public BinaryTreeNode<T> parent(BinaryTreeNode<T> root, BinaryTreeNode<T> current){// 假设没有parent指针
        if(root == null) return null;
        if(current == root.left || current == root.right) return root;
        BinaryTreeNode<T> node = parent(root.left, current);
        if(node != null) return node;
        return parent(root.right, current);
    }
    
    public BinaryTreeNode<T> parentNoRecusion(BinaryTreeNode<T> root, BinaryTreeNode<T> current){// 假设没有parent指针
        // 中序框架,PPT用前序框架
        BinaryTreeNode<T> pointer = root;
        Stack<BinaryTreeNode<T>> stack = new LnkStack<>();
        while(pointer != null || !stack.isEmpty()){
            if(pointer != null){
                if(pointer.left == current || pointer.right == current){
                    return pointer;
                }
                stack.push(pointer);
                pointer = pointer.left;
            }else{
                pointer = stack.top();
                stack.pop();
                pointer = pointer.right;
            }
        }
        return null;
    }

    public BinaryTreeNode<T> leftSibling(BinaryTreeNode<T> current) {
        return null;
    }
    
    public BinaryTreeNode<T> leftSibling(BinaryTreeNode<T> root, BinaryTreeNode<T> current) {// 假设没有parent指针
        // 寻找兄弟用非递归方法比较好,因为递归框架通过 null 来判断是找到还是继续往下,
        // 但很可能没有left sibling啊!!!所以两种情况会耦合在一起,一种解决方式是用参数来返回, 函数返回true false来区分是否找到
        // 见 leftSiblingR方法
        Stack<BinaryTreeNode<T>> stack = new LnkStack<>();
        BinaryTreeNode<T> pointer = root;
        while(pointer != null || !stack.isEmpty()){
            if(pointer != null){
                if(pointer.right == current) return pointer.left;
                stack.push(pointer);
                pointer = pointer.left;
            }else{
                pointer = stack.top();
                stack.pop();
                pointer = pointer.right;
            }
        }    
        return null;
    }
    
    public boolean leftSiblingR(BinaryTreeNode<T> root, BinaryTreeNode<T> current, ParamBox param) {// 假设没有parent指针
        if(root == null) return false;
        if(root.right == current){
            param.pointer = root.left;
            return true;
        }
        if(leftSiblingR(root.left, current, param))
            return true;
        else
            return leftSiblingR(root.right, current, param);
    }
    
    public BinaryTreeNode<T> rightSibling(BinaryTreeNode<T> current) {
        return null;
    }

    public void preOrder() {
        preOrder(root);
    }

    public void preOrder(BinaryTreeNode<T> node) {
        if (node == null) {
            return;
        }
        visit(node);
        preOrder(node.leftChild());
        preOrder(node.rightChild());
    }

    public void inOrder() {
        inOrder(root);
    }

    public void inOrder(BinaryTreeNode<T> node) {
        if (node == null) {
            return;
        }
        inOrder(node.leftChild());
        visit(node);
        inOrder(node.rightChild());
    }

    public void postOrder() {
        postOrder(root);
    }

    public void postOrder(BinaryTreeNode<T> root) {
        if (root == null) {
            return;
        }
        postOrder(root.leftChild());
        postOrder(root.rightChild());
        visit(root);
    }

    public void preOrderNoRecusion() {
        Stack<BinaryTreeNode> stack = new LnkStack<>();
        stack.push(null);// 监视梢
        BinaryTreeNode<T> node = root;
        while (node != null) {
            visit(node);
            if (node.rightChild() != null) {
                stack.push(node.rightChild());
            }
            if (node.leftChild() != null) {
                node = node.leftChild();
            } else {
                node = stack.top();
                stack.pop();
            }
        }
    }

    public void inOrderNoRecusion() {
        Stack<BinaryTreeNode> stack = new LnkStack<>();
        BinaryTreeNode<T> node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
//                visit(node); 前序中序可以共用一个框架, 放开这里的注释,去除else的visit, 已验证
                stack.push(node);
                node = node.left;
            } else {
                node = stack.top();
                stack.pop();
                visit(node);
                node = node.right;
            }
        }
    }

    enum TAG {
        left,
        right
    }

    static class StackElement<T> {

        BinaryTreeNode<T> node;
        TAG tag;

        public StackElement(BinaryTreeNode<T> node, TAG tag) {
            this.node = node;
            this.tag = tag;
        }
    }

    public void postOrderNoRecusion0() {// 错的
        Stack<StackElement> stack = new LnkStack<>();
        StackElement elem = new StackElement(root, TAG.right);
        stack.push(elem);
        while (!stack.isEmpty()) {
            while (elem.node.leftChild() != null) {
                elem = new StackElement(elem.node.left, TAG.left);
            }
            elem = stack.top();
            stack.pop();
            if (elem.tag == TAG.left) {
                elem.tag = TAG.right;
                stack.push(elem);
                if (elem.node.rightChild() != null) {
                    elem = new StackElement(elem.node.right, TAG.right);
                    stack.push(elem);
                }
            } else {
                visit(elem.node);
                // 想想,visit之后, 又到了while(elem.node.leftChild() != null)还是会继续执行循环!!!
                // 所以还是要像ppt那样,用pointer 与 elem 分开
                elem = stack.top();
                stack.pop();
            }
        }
    }

    public void postOrderNoRecusion() {// 完全按照PPT的, pointer与 elem 分开是一个很好的trick
        Stack<StackElement> stack = new LnkStack<>();
        BinaryTreeNode pointer = root;
        while (pointer != null || !stack.isEmpty()) {
            while (pointer != null) {
                StackElement elem = new StackElement(pointer, TAG.left);
                stack.push(elem);
                pointer = pointer.leftChild();
            }
            StackElement elem = stack.top();
            stack.pop();
            pointer = elem.node;
            if (elem.tag == TAG.left) {
                elem.tag = TAG.right;
                stack.push(elem);
                pointer = pointer.rightChild();
            } else {
                visit(pointer);
                pointer = null;
            }
        }
    }


    public void levelOrder(BinaryTreeNode<T> root) {
        Queue<BinaryTreeNode<T>> queue = new LnkQueue<>();
        queue.enQueue(root);
        while(!queue.isEmpty()){
            BinaryTreeNode node = queue.getFront();
            queue.deQueue();
            visit(node);
            if(node.left != null){
                queue.enQueue(node.left);
            }
            if(node.right != null){
                queue.enQueue(node.right);
            }
        }
    }

    public void deleteBinaryTree(BinaryTreeNode<T> root) {
        root = null;
    }

    protected void visit(BinaryTreeNode<T> node) {
        if (visitor != null) {
            visitor.visit(node.info);
        } else {
            System.out.println(node.info);
        }
    }

    public void setVistor(Visitor vistor) {
        this.visitor = vistor;
    }

    public Visitor getVistor() {
        return visitor;
    }
   

    public static void main(String[] args) {
        String json = Tools.readProjectAssetFile(Project.binaryTreeFiles[0]);
        Gson gson = new Gson();
        Object obj = gson.fromJson(json, new TypeToken<BinaryTreeNode<String>>() {
        }.getType());
        BinaryTreeNode<String> root = (BinaryTreeNode<String>) obj;
        BinaryTree<String> binaryTree = new BinaryTree(root);
        
        VisitPeerChecker vistor = new VisitPeerChecker();
        binaryTree.setVistor(vistor);
        binaryTree.inOrder();
        binaryTree.setVistor(vistor.peer());
        binaryTree.inOrderNoRecusion();
        vistor.check();

        vistor = new VisitPeerChecker();
        binaryTree.setVistor(vistor);
        binaryTree.preOrder();
        binaryTree.setVistor(vistor.peer());
        binaryTree.preOrderNoRecusion();
        vistor.check();

        vistor = new VisitPeerChecker();
        binaryTree.setVistor(vistor);
        binaryTree.postOrder();
        binaryTree.setVistor(vistor.peer());
        binaryTree.postOrderNoRecusion();
        vistor.check();

    }

}
