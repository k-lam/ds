/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.list;

/**
 * rear实指
 * @author linqiye
 */
public class LnkQueue<T> implements Queue<T>{
    
    private int size = 0;
    protected LinkedList.Node<T> front;
    protected LinkedList.Node<T> rear;

    @Override
    public void clear() {
        size = 0;
        LinkedList.Node tmp = null;
        while(front != null){
            tmp = front;
            front = front.next;
            tmp.next = null;
        }
        rear = null;
    }

    @Override
    public boolean enQueue(T item) {
        if(size == 0){
            front = rear = new LinkedList.Node<>(item);
        }else{
            rear.next = new LinkedList.Node<>(item);
            rear = rear.next;
        }
        size += 1;
        return true;
    }

    @Override
    public T deQueue() {
        if(isEmpty()){
            System.err.println("queue is empty");
            return null;
        }
        LinkedList.Node<T> node = front;
        front = front.next;
        size -= 1;
        if(size == 0){// 比PPT用front == null清晰
            rear = null;
        }
        return node.data;
    }

    @Override
    public T getFront() {
        if(isEmpty()){
            System.err.println("queue is empty");
            return null;
        }
        return front.data;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public int size() {
        return size;
    }
    
}
