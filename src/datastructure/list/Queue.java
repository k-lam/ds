/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.list;

/**
 *
 * @author linqiye
 */
public abstract interface Queue<T> {
    void clear();
    boolean enQueue(T item);
    T deQueue();// 返回并删除队头
    T getFront();// 返回队头
    boolean isEmpty();
    boolean isFull();
    int size();
}
