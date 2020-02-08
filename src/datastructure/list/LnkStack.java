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
public class LnkStack<T> extends Stack<T>{
    
    static class Link<T>{
        T data;
        Link<T> next;
        public Link(T data){
            this.data = data;
        }
    }
    
    private Link top;
    private int size;
    
    public LnkStack(){
        size = 0;
        top = null;
    }
    

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean push(T elem) {
        Link<T> tmp = new Link(elem);
        tmp.next = top;
        top = tmp;
        size += 1;
        return true;
    }

    @Override
    public T top() {
//        if(top.data == null) return null;
        return (T)top.data;
    }

    @Override
    public boolean pop() {
        if(top == null){
            return false;
        }
        size -= 1;
        top = top.next;
        return true;
    }

    @Override
    public void clear() {
        while(pop());
    }
    
    public static void main(String[] args) {
        Stack<String> stack = new LnkStack<>();
        stack.push(null);
        String s = stack.top();
        System.out.println(s);
    }
    
}
