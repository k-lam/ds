/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.list;

/**
 * rear 虚指
 * @author linqiye
 */
public class LnkQueue2<T> implements Queue<T>{
    
    private int size = 0;
    private LinkedList.Node<T> front = null;
    private LinkedList.Node<T> rear = null;

    @Override
    public void clear() {
        size = 0;
        front = null;
        rear = null;
    }

    @Override
    public boolean enQueue(T item) {
        rear = new LinkedList.Node(item);
        if(size == 0){
            front = rear;
        }
        rear = rear.next;// java这样操作是没有意义的,但C++指针就不一样了,所以 这里作为伪代码看待
        size += 1;
        return true;
    }

    @Override
    public T deQueue() {
        if(isEmpty()){
            System.err.println("queue is empty");
            return null;
        }
        
        front = front.next;
        return front.data;
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
