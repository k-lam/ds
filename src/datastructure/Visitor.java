/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure;

/**
 *
 * @author linqiye
 */
public class Visitor {
    StringBuilder sb = new StringBuilder();
//    public Vistor(){}
    public void visit(Object obj){
        sb.append(obj.toString()).append("; ");
    }
    
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Visitor)) return false;
        return ((Visitor)obj).sb.toString().equals(sb.toString());
    }
    
    public boolean check(Visitor other){
        if(other.equals(this)){
            System.out.println("equal!!! " + other.sb.toString());
            return true;
        }else{
            System.err.println("not equal!!! ");
            return false;
        }
    }
}
