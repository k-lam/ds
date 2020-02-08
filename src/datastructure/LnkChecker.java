/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure;

import datastructure.list.*;
/**
 *
 * @author linqiye
 */
public class LnkChecker extends Visitor{
        
    private class CheckerQueue extends LnkQueue<Visitor>{
        public Visitor getRear(){ return rear.data;}
        public LinkedList.Node<Visitor> firstNode(){
            return front;
        }
    }
    
    CheckerQueue queue = new CheckerQueue();
    
    public LnkChecker(){
        queue.enQueue(new Visitor());
    }
    
    @Override
    public void visit(Object obj){
        queue.getRear().visit(obj);
    }
    
    @Override
    public void print(){
        queue.getRear().print();
    }
    
    public void otherChecker(){
        queue.enQueue(new Visitor());
    }
    
    public boolean check(){
        Visitor standard = queue.getFront();
        LinkedList.Node<Visitor> node = queue.firstNode().next;
        int i = 1;
        while(node != null){
            Visitor other = node.data;
            if(!standard.check(other, false)){
                System.err.println("not equal!!! position:" + i);
                return false;
            }
            node = node.next;
            i++;
        }
        System.out.println(queue.size() + " equal!!! " + standard.sb.toString());
        return true;
    }
}
