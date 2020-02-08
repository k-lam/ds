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
public abstract class Stack<T> {
    public abstract boolean isEmpty();
    public abstract boolean push(T elem);
    public abstract T top();
    public abstract boolean pop();
    public abstract void clear();
}
