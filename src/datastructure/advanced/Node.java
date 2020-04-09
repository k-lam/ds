/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.advanced;

/**
 *
 * @author linqiye
 */
public class Node {
    public static enum TYPE{
        HEADER,
        ATOM,
        SUBLIST
    }
    
    TYPE type;
    Object value;
    Node next;

    public Node(TYPE type) {
        this.type = type;
    }
    
    public Node(TYPE type, Object value){
        this(type);
        this.value = value;
    }
    
    public void visit(){
        System.out.print(value);
    }
    
    static class ReenterableNode extends Node{
        boolean visted = false;

        public ReenterableNode(TYPE type) {
            super(type);
        }
    }
}
