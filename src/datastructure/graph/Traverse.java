/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.graph;
//import static datastructure.graph.Graph.MARK;
import static datastructure.graph.Graph.*;
import datastructure.list.*;
/**
 *
 * @author linqiye
 */
public class Traverse {
    
    static void visit(Graph g, int v){
//        System.out.println(v + ", ");
        System.out.println(g.vertices[v].data);
    }
    
    static void traverse(Graph g){
        MARK[] marks = g.createMarks();
        for(int v = 0; v != g.verticesNum(); v++){
            // 一次调用, 一定能把一个连通子图(分量) 都访问完,一次不能访问完,就是不连通,
            // 见PPT 第七章 习题课 POJ 1164问题
            if(marks[v] == MARK.UNVISITED){
//                dfs(g, v, marks);
//                bfs(g,v, marks);
                bfsPPT(g, v, marks);
            }
        }
    }
    
    
    /**
     * 对连通分量进行遍历
     * @param g
     * @param v 
     */
    static void dfs(Graph g, int v, MARK[] marks){
        marks[v] = MARK.VISITED;
//        visit(g, v);//先访问, 先于所有后继
        for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = g.nextEdge(edge)){
            if(marks[edge.to] == MARK.UNVISITED){
                dfs(g, edge.to, marks);
            }
        }
        visit(g, v); //如果写在这里就是后访问,就是所有后继都访问完,才访问,可以判断是否有环的关键地方
    }
    
    static void bfs(Graph g, int v, MARK[] marks){
        Queue<Integer> queue = new LnkQueue<>();
        queue.enQueue(v);
        while(!queue.isEmpty()){
            // 写法一二都正确,建议用写法二
            // 写法一:
//            v = queue.deQueue();
//            if(marks[v] == MARK.UNVISITED){//都对
//                marks[v] = MARK.VISITED;
//                visit(g, v);
//                for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = edge.next){
//                    queue.enQueue(edge.to);
//                }
//            }
            // 写法二:
//            v = queue.deQueue();
//            marks[v] = MARK.VISITED;
//            visit(g, v);
//            for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = edge.next){
//                if(marks[edge.to] == MARK.UNVISITED){
//                    queue.enQueue(edge.to);
//                }
//            }
        }
    }
    
    static void bfsPPT(Graph g, int v, MARK[] marks){
        // 明显没有自己写的代码清晰
        Queue<Integer> queue = new LnkQueue<>();
        marks[v] = MARK.VISITED;
        visit(g, v);
        queue.enQueue(v);
        while(!queue.isEmpty()){
            v = queue.deQueue();
            for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = edge.next){
                if(marks[edge.to] == MARK.UNVISITED){
                    marks[edge.to] = MARK.VISITED;
                    visit(g, edge.to);
                    queue.enQueue(edge.to);
                }
            }
        }
    }
    
    static void topSortByDFS(Graph g){
        MARK[] marks = g.createMarks();
        Stack<Integer> stack = new ArrStack<>(g.verticesNum());
        for(int v = 0; v != g.verticesNum(); v++){
            if(marks[v] == MARK.UNVISITED){
                doTopSortByDFS(g, v, marks, stack);
            }
        }
        while (!stack.isEmpty()) {            
            visit(g, stack.top());
            stack.pop();
        }
    }
    
    static void doTopSortByDFS(Graph g,int v, MARK[] marks, Stack<Integer> stack){
        marks[v] = MARK.VISITED;
        for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = g.nextEdge(edge)){
            if(marks[edge.to] == MARK.UNVISITED){
                doTopSortByDFS(g, edge.to, marks, stack);
            }
        }
        stack.push(v);
    }
    // return true:有环, false 无环
    static boolean doTopSortDFS_CIRCLE(Graph g, int s, MARK_CIRCLE[] marks, Stack<Integer> stack){
        if(marks[s] == MARK_CIRCLE.PUSH) return true;
        marks[s] = MARK_CIRCLE.PUSH;
        
        for(Edge edge = g.firstEdge(s); g.isEdge(edge); edge = g.nextEdge(edge)){
            if(marks[g.toVertex(edge)] != MARK_CIRCLE.UNVISITED && 
                    doTopSortDFS_CIRCLE(g, s, marks, stack)){
                return true;
            }
        }
        
        marks[s] = MARK_CIRCLE.VISITED;
        stack.push(s);
        return false;
    }
    
    // 可判断有无环的
    static void topSortDFS_CIRCLE(Graph g){
        MARK_CIRCLE[] marks = g.createMarkCirles();
        Stack<Integer> stack = new ArrStack<>(g.verticesNum());
        for(int v = 0; v != g.verticesNum(); v++){
            if(marks[v] == MARK_CIRCLE.UNVISITED){
                if(doTopSortDFS_CIRCLE(g, v, marks, stack)){
                    System.err.println("有环");
                    stack = null;
                    return;
                }
            }
        }
        while (!stack.isEmpty()) {            
            visit(g, stack.top());
            stack.pop();
        }
    }
    
    
    static void topSortByQueue(Graph g){
        int[] inDegrees = new int[g.verticesNum()];
        for(int i = 0; i != g.verticesNum(); i++){
            for(Edge edge = g.firstEdge(i); g.isEdge(edge); edge = g.nextEdge(edge)){
                inDegrees[edge.to]++;
            }
        }
        Queue<Integer> queue = new LnkQueue<>();
        for(int v = 0; v != g.verticesNum(); v++){
            if(inDegrees[v] == 0){
                queue.enQueue(v);
            }
        }
        MARK[] marks = g.createMarks();// 其实多余, 只要inDegrees != 0 就可以判断未访问
        while(!queue.isEmpty()){
            int v = queue.deQueue();
            visit(g, v);
            marks[v] = MARK.VISITED;
            for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = g.nextEdge(edge)){
                inDegrees[edge.to]--;
                if(inDegrees[edge.to] == 0){
                    queue.enQueue(edge.to);
                }
            }
//            int v = queue.deQueue();// 这个写法是错的, 完全没理解拓扑排序的做法,ppt7.3 P16
//            if(marks[v] == MARK.UNVISITED){
//                marks[v] = MARK.VISITED;
//                visit(g, v);
//                for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = g.nextEdge(edge)){
//                    inDegrees[edge.to]--;
//                    if(inDegrees[edge.to] == 0){
//                        queue.enQueue(edge.to);
//                    }
//                }
//            }
            
        }
        for(int v = 0; v != marks.length; v++){
            if(marks[v] == MARK.UNVISITED){
                System.err.println("有环");
                break;
            }
        }
        
    }
    
    static boolean checkCircle(Graph g){
//        int[] marks = new int[g.verticesNum()];
        MARK_CIRCLE[] marks = g.createMarkCirles();
        for(int v = 0; v != g.verticesNum(); v++){
            if(doCheckCircle(g, v, marks)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 对有向图的, 无向图由于一条边出现两次,所以这方法一定会判断有环
     * @param g
     * @param v
     * @param marks 
     * @return true: 有环 false: 无环
     */
    static boolean doCheckCircle(Graph g, int v, MARK_CIRCLE[] marks){
        if(marks[v] == MARK_CIRCLE.PUSH){ return true; }
        marks[v] = MARK_CIRCLE.PUSH;

        for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = g.nextEdge(edge)){
            if(marks[edge.to] != MARK_CIRCLE.VISITED && doCheckCircle(g, edge.to, marks)){
                return true;
            }
        }
        marks[v] = MARK_CIRCLE.VISITED;
        return false;
    }
    
    public static void main(String[] args) {
        Vertex[] vertices = new Vertex[9];
        for(int i = 1; i != 10; i++){
            vertices[i - 1] = new Vertex("C"+i);
        }
        int[][] esInfos = new int[][]{
            new int[]{1, 3, 8},
            new int[]{2, 3, 5},
            new int[]{3, 4},
            new int[]{4, 6, 7},
            new int[]{5, 6},
            new int[]{6},
            new int[]{7},
            new int[]{8 , 9},
            new int[]{9}
        };
        
        for(int i = 0; i != esInfos.length; i++){
            for(int j = 0; j != esInfos[i].length; j++){
                esInfos[i][j]--;
            }
        }
        
        //PPT 7.3 p18图
        Graph graph = new Graph(vertices, esInfos, true, false);
        traverse(graph);
//        topSortByQueue(graph);
//        topSortByDFS(graph);
//        graphDFS(graph);
//        dfs(graph, 1, graph.createMarks());
//        int[][] esInfos2 = new int[][]{
//            new int[]{0, 1},
//            new int[]{1, 2},
//            new int[]{2, 0}
//        };
//        Graph graphCircle = Graph.createGraph(3, esInfos2, true, false);
//        System.out.println(checkCircle(graph));
//        System.out.println(checkCircle(graphCircle));
        
    }
    
}
