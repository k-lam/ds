/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.list;

import datastructure.Tools;

/**
 *
 * @author linqiye
 */
public class Expression {
     static boolean isOp(char c){
        return c == '+' || c == '-' || c =='*' || c == '/' || c == '^';
    }
    
    static boolean isOp(String c){
        return c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/") || c.equals("^");
    }
    
    static boolean isOp2(String c){
        return isOp(c) || c.equals("(") || c.equals(")");
    }
    
    static double compute(String op, double n1, double n2){
        switch(op){
                case "+":
                    return n1 + n2;
                case "-":
                    return n1 - n2;
                case "*":
                    return n1 * n2;
                case "/":
                    if(n2 < 0.0000000001){
                        return Double.MAX_VALUE;
                    }else{
                        return n1 / n2;
                    }
                case "^":
                    return Math.pow(n1, n2);
        }
        return 0;
    }
    
    /**
    * 后序表达式计算
    */
    public static double calculate(String s){// 操作数间用空格隔开,符号也是
       if(s == null || s.isEmpty()) return 0;
       Stack<Double> stack = new ArrStack<>(30);
       String[] ss = s.split(" ");
       Tools.printArray(ss);
       for(String e : ss){
           if(isOp(e)){
               double n2 = stack.top();
               stack.pop();
               double n1 = stack.top();
               stack.pop();
               stack.push(compute(e, n1, n2));
           }else{
               stack.push(Double.parseDouble(e));
           }
       }
       double result = stack.top();
       stack.clear();
//       delete stack
       return result;
    }
    
    static int opLevel(String op){
        char c = op.charAt(0);
        switch(c){
        case '^':
            return 4;
        case '*':  case '/': case '%':
            return 3;
        case '+': case '-':
            return 2;
        case '(': case '=':
            return 1;
    }
    return 0;        
    }
    
    /*
    def infixToPostfix(infixexpr):
    prec = {}
    prec["*"] = 3
    prec["/"] = 3
    prec["+"] = 2
    prec["-"] = 2
    prec["("] = 1
    opStack = Stack()
    postfixList = []
    tokenList = infixexpr.split()

    for token in tokenList:
        if token in "ABCDEFGHIJKLMNOPQRSTUVWXYZ" or token in "0123456789":
            postfixList.append(token)
        elif token == '(':
            opStack.push(token)
        elif token == ')':
            topToken = opStack.pop()
            while topToken != '(':
                postfixList.append(topToken)
                topToken = opStack.pop()
        else:
            while (not opStack.isEmpty()) and \
               (prec[opStack.peek()] >= prec[token]):
                  postfixList.append(opStack.pop())
            opStack.push(token)

    while not opStack.isEmpty():
        postfixList.append(opStack.pop())
    return " ".join(postfixList)

print(infixToPostfix("A * B + C * D"))
print(infixToPostfix("( A + B ) * C - ( D - E ) * ( F + G )"))

    */
    
    /**
     * 中序表达式转后序表达式
     * 测试: a + b * c - ( d + e )  输入 a b c * + d e + -
     * 23 + ( 34 * 45 ) / ( 5 + 6 + 7 )  output: 23 34 45 * 5 6 + 7 + / +
     * @param infixExp 中序表达式,为方便处理,要求数字与符号之间用空格隔开如 ( 1 + 2 ) * 3 / 4
     * @return 
     */
    static String infixExp2postfixExp(String infixExp){
        Queue<String> postQueue = new ArrQueue<>(50);
        Stack<String> opStack = new ArrStack<>(50);
        String[] elems = infixExp.trim().split(" ");
//        Tools.printArray(elems);
        for(String elem : elems){
            if(!isOp2(elem)){
                //假设只有数字和算符,不做异常输入检测
                postQueue.enQueue(elem);
            }else if(elem.equals("(")){
                opStack.push(elem);
            }else if(elem.equals(")")){// 把(之前的都输出
                while(!opStack.isEmpty()){
                    String op = opStack.top();
                    if(op.equals("(")) {
                        opStack.pop();
                        break;
                    }
                    postQueue.enQueue(op);
                    opStack.pop();
                }
            }else{
                // 栈顶不是 ( 且栈顶 > thisElem, pop出, 最后记得把thisElem压栈.
                while(!opStack.isEmpty() && !opStack.top().equals("(") && opLevel(opStack.top()) >= opLevel(elem)){
                    String op = opStack.top();
                    postQueue.enQueue(op);
                    opStack.pop();
                }
                opStack.push(elem);
            }
        }
        
        while(!opStack.isEmpty()){
            postQueue.enQueue(opStack.top());
            opStack.pop();
        }
        String result = "";
        while(!postQueue.isEmpty()){
            result += postQueue.deQueue() + " ";
        }
        return result.trim();
    }
    
    static void calStackTop(Stack<Double> stack, String op){
        double n2 = stack.top();
        stack.pop();
        double n1 = stack.top();
        stack.pop();
        stack.push(compute(op, n1, n2));
    }
    
    static double calculateInfixExpDirectly(String infixExp){
        Stack<Double> numStack = new ArrStack<>(50);
        Stack<String> opStack = new ArrStack<>(50);
        String[] elems = infixExp.trim().split(" ");
        for(String elem : elems){
//            if(elem.equals("6")){
//                System.out.println("hit ");
//            }
            if(!isOp2(elem)){
                //假设只有数字和算符,不做异常输入检测
                numStack.push(Double.parseDouble(elem));
            }else if(elem.equals("(")){
                opStack.push(elem);
            }else if(elem.equals(")")){
                while(!opStack.isEmpty()){//从opStack弹op 计算,直到"("
                    String op = opStack.top();
                    if(op.equals("(")) {
                        opStack.pop();
                        break;
                    }
                    calStackTop(numStack, op);
                    opStack.pop();
                }
            }else{
                while(!opStack.isEmpty() && !opStack.top().equals("(") && opLevel(opStack.top()) >= opLevel(elem)){
                    String op = opStack.top();
                    calStackTop(numStack, op);
                    opStack.pop();
                }
                opStack.push(elem);
            }
        }
        
        while(!opStack.isEmpty()){
            calStackTop(numStack, opStack.top());
            opStack.pop();
        }
        return numStack.top();
    }
    
    static double calculateInfixExp(String infixExp){
        
        String postfixExp = infixExp2postfixExp(infixExp); 
        System.out.println("postfixExp: "+ postfixExp);
        return calculate(postfixExp);
    }
    
    public static void main(String[] args) {
        // 多练习吧,北大要这样考,别无他法... 考选择题
        String infixExp = "23 + ( 34 * 45 ) / ( 5 + 6 + 7 )";
//        infixExp = "3 * 2 ^ ( 4 + 2 * 2 - 6 * 3 )";
//        infixExp = "150 - ( 7 + 5 ) * 2 + 30 * 2";
        infixExp = " ( ( 100 - 4 ) / 3 + 3 * ( 36 - 7 ) ) * 2 ";
        System.out.println(calculateInfixExp(infixExp));
        
        // 下面就是2020北大选择题第4题选择题,计算中序 用到两个栈
        System.out.println(calculateInfixExpDirectly(infixExp));
    }
}
