/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.list;

import com.sun.xml.internal.ws.addressing.policy.AddressingFeatureConfigurator;

/**
 *  rear实指,方案一实现
 * @author linqiye
 */
public class ArrQueue<T> implements Queue<T>{
    
    private Object[] items;
    private int front;
    private int rear;
    
    public ArrQueue(int size){
        items = new Object[size + 1];
        this.front = 0;
        this.rear = -1;
    }

    @Override
    public void clear() {
        rear = front - 1;
    }

    @Override
    public boolean enQueue(T item) {
        if(isFull()) {
            System.err.println("queue is full");
            return false;
        }
        rear = (rear + 1) % items.length;
        items[rear] = item;
        return true;
    }

    @Override
    public T deQueue() {
        if(isEmpty()) {
            System.err.println("queue is empty");
            return null;
        }
        T item = getFront();
        front = (front + 1) % items.length;
        return item;
    }

    @Override
    public T getFront() {
        if(isEmpty()){
            System.err.println("queue is empty");
            return null;
        }
        return (T)this.items[this.front];
    }

    @Override
    public boolean isEmpty() {
        return (rear + 1) % items.length == front;
    }

    @Override
    public boolean isFull() {
        return  (rear + 2) % items.length == front;
    }

    @Override
    public int size() {
        return (rear - front + 1 + items.length) % items.length;
    }
    
}
