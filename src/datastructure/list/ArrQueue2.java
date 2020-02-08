/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.list;

/**
 * PPT里面所说的方案二,rear指向下一个位置  
 * @author linqiye
 */
public class ArrQueue2<T> implements Queue<T>{
    private Object[] items;
    private int front;
    private int rear;
    
    public ArrQueue2(int size){
        items = new Object[size + 1];
        this.front = 0;
        this.rear = 0;
    }

    @Override
    public void clear() {
        front = rear;
    }

    @Override
    public boolean enQueue(T item) {
        if(isFull()) {
            System.err.println("queue is full");
            return false;
        }
        items[rear] = item;
        rear = (rear + 1) % items.length;
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
        return this.front == this.rear;
    }

    @Override
    public boolean isFull() {
        return  (rear + 1) % items.length == front;
    }

    @Override
    public int size() {
//        return isEmpty() ? 0 : (rear - 1 - front + items.length) % items.length;
        return (rear - front + items.length) % items.length;
    }
}
