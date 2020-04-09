/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.advanced;
import datastructure.list.ArrStack;
import datastructure.list.Stack;
import static datastructure.advanced.Node.TYPE;
import datastructure.Tools;
/**
 * @author linqiye
 */
public class GeneralizedList extends Node{// 这个比较有意思,List 是node的一个子类
    
    Node header = new Node(TYPE.HEADER);

    public GeneralizedList() {
        super(TYPE.SUBLIST);
    }
     
    // 不可重入的
    // 格式 "( ( a,b,c ) d,e,f,( g ))"
    static GeneralizedList create(String s){
        String[] ss = s.split("[ ,]{1,}");
        Stack<Object> stack = new ArrStack<>(ss.length);
        for(int i = 0; i != ss.length; i++){
            if(!ss[i].equals(")")){
                if(ss[i].equals("(")){
                    stack.push("(");
                }else{
                    stack.push(new Node(TYPE.ATOM, ss[i]));
                }
            }else{// 只有 "(" 与 node 两种可能
                Node last = null;
                while(!stack.top().equals("(")){
                    Object obj = stack.top();
                    stack.pop();
                    Node thisNode = (Node) obj;
//                    if(obj instanceof String){
//                        thisNode = new Node(TYPE.ATOM, obj);
//                        
//                    }else if(obj instanceof Node){
//                        thisNode = (Node) obj;
//                    }
                    thisNode.next = last;
                    last = thisNode;
                }
                stack.pop();// pop "(";
                GeneralizedList subList = new GeneralizedList();
                subList.header.next = last;
                stack.push(subList);
            }
        }
        GeneralizedList list = (GeneralizedList) stack.top();
        return list; 
    }
    
    public void traverse(){
        for(Node node = this.header; node != null; node = node.next){
            if(node.type == TYPE.SUBLIST){
                ((GeneralizedList)node).traverse();
            }else if(node.type == TYPE.ATOM){
                node.visit();
            }else{
                continue;
            }
        }
    }
    
    
    public static void main(String[] args) {
        String s = "( ( ( A , ( B , C ) , D , ( ( E , F ) , G ) , H ) , I ) )";
        String[] ss = s.split("[ ,]{1,}");
        Tools.printlnArray(ss);
        GeneralizedList list = GeneralizedList.create(s);
        list.traverse();
    }
}
