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
public class VisitPeerChecker extends Visitor{
    
    Visitor other = new Visitor();
    
    public Visitor peer(){ return other; }
    
    public boolean check(){
        return check(other);
    }
    
    
}
