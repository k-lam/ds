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
public class ArrStack<T> extends Stack<T>{
    
    private int size;
    public Object[] elems;
    private int top = -1;// top 指向栈顶,不是下一个插入位置
    public ArrStack(int size){
        this.size = size;
        elems = new Object[size];
    }

    @Override
    public boolean isEmpty() {
        return top == -1;// 易错
    }

    @Override
    public boolean push(T elem) {
        if(top == size - 1) {
            System.err.println("stack is full!");
            return false;
        }
        this.elems[++top] = elem;
        return true;
    }

    @Override
    public T top() {
        return (T)this.elems[top];
    }

    @Override
    public boolean pop() {
        if(isEmpty()){
            System.err.println("stack is empty");
            return false;
        }
        elems[top] = null;// delete
        top--;
        return true;
    }

    @Override
    public void clear() {
        while (pop());
    }
    
    public int size(){
        return top - 0 + 1;
    }
    
}
