/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.list;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 带头结点的,尾指针的
 *
 * @author linqiye
 */
public class LinkedList<T> implements Cloneable {

    public static class Node<T> {

        public T data;
        public Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public Node head = new Node(-1);
    public Node tail = head;

    public LinkedList() {
    }

    public LinkedList(T[] items) {
        this();
        for (int i = 0; i != items.length; i++) {
            append(items[i]);
        }
    }

    public boolean isEmpty() {
        return head.next == null;
    }

    public int length() {
        int count = 0;
        Node node = head;
        while (node.next != null) {
            count++;
            node = node.next;
        }
        return count;
    }

    public boolean append(T value) {
        Node node = new Node(value);
        tail.next = node;
        tail = node;
        return true;
    }

    public Node setPos(int p) {
        if (p < 0) {
            return head;
        }
        Node node = head.next;
        for (int i = 0; i != p && node != null; i++) {
            node = node.next;
        }
        return node;
    }

    public boolean insert(int p, int value) {
        Node pre = setPos(p - 1);// 获取前一个节点
        Node node = new Node(value);
        if (pre == null) {
//            tail.next = node;
//            tail = node;
            System.err.println("非法插入点");
            return false;
        }
        node.next = pre.next;
        pre.next = node;
        if (tail == pre) {
            tail = node;
        }
        return true;
    }

    public boolean delete(int p) {
        Node pre = setPos(p - 1);
        if (null == pre || pre == tail) {
            System.err.println("非法删除位置");
            return false;
        }
        Node delNode = pre.next;
        if (tail == delNode) {
            tail = pre;
            pre.next = null;//TODO: 易漏
        }
        pre.next = delNode.next;
        return true;
    }
    
    public T[] toArray(){
        T[] array = (T[]) new Object[this.length()];
        for(int i = 0; i != length(); i++){
            array[i] = (T) setPos(i).data;
        }
        return array;
    }

    @Override
    public Object clone() {
        LinkedList<T> ls = new LinkedList<>();
        for (int i = 0; i != this.length(); i++) {
            ls.append((T) this.setPos(i).data);
        }
        return ls;
    }

}
