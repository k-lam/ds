/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.graph;
import static datastructure.graph.Graph.*;
import datastructure.binarytree.Heap;
import datastructure.list.*;
import datastructure.Tools;
import static datastructure.Tools.*;
/**
 *
 * @author linqiye
 */
public class ShortestPath {
    
    public static class Dist{
        int index;
        int length;
        int pre;
        
        @Override
        public String toString(){
//            return new StringBuilder("i: ").append(index)
//                    .append(", len: ").append(length)
//                    .append(", pre: ").append(pre)
//                    .;
            return String.format("i:%2d, len:%4d, pre:%3d", index, length>9999?9999:length, pre);
        }
        
        public String toString(Graph graph){
//            String preStr = (pre >=  0&& pre < graph.verticesNum()) ? graph.vertices[pre].data.toString() : null;
//            String iStr = (index >=  0&& index < graph.verticesNum()) ? graph.vertices[index].data.toString() : null;
            return String.format("i:%4s, len:%4d, pre:%4s", map2Info(graph, index), length>9999?9999:length, map2Info(graph, pre));
        }
        
        public String map2Info(Graph graph, int index){
            return (index >=  0&& index < graph.verticesNum()) ? graph.vertices[index].data.toString() : null;
        }
    }
    
    
    
    static class MinHeap extends Heap<Dist>{
        
        public MinHeap(int size){
            super(size);
        }

        @Override
        public boolean lessThen(Dist a, Dist b) {
            return a.length < b.length;
        }
    
    }
    
    static Dist[] dijkstra(Graph g, int v){
        Dist[] dists = new Dist[g.verticesNum()];
        for(int i = 0; i != g.verticesNum(); i++){
            dists[i] = new Dist();
            dists[i].index = i;
            dists[i].length = INFINTY;
            dists[i].pre = -1;
        }
        dists[v].pre = v;
        // KL: 下面这段是不能要的,不需要初始化,因为最后
        // if(dists[edge.to].length > dists[d.index].length + edge.weight)
        // 会更新dists,且入堆
//        for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = g.nextEdge(edge)){
//            dists[edge.to].length = edge.weight;
//            dists[edge.to].pre = v;
//        }
        
        dists[v].length = 0;
        MinHeap heap = new MinHeap(g.EdgesNum());
        
        heap.insert(dists[v]);
        MARK[] marks = g.createMarks();
        for(int t = 0; t != g.verticesNum(); t++){
            boolean FOUND = false;
            Dist d = null;
            while(!heap.isEmpty()){
                d = heap.removeTop();
                if(marks[d.index] == MARK.UNVISITED){
                    marks[d.index] = MARK.VISITED;
                    FOUND = true;
                    break;
                }
            }
            if(!FOUND) break;
            for(Edge edge = g.firstEdge(d.index); g.isEdge(edge); edge = g.nextEdge(edge)){
                if(dists[edge.to].length > dists[d.index].length + edge.weight){
                    dists[edge.to].length = dists[d.index].length + edge.weight;
                    dists[edge.to].pre = d.index;
                    heap.insert(dists[edge.to]);
                }
            }
            printDists(dists, g);
        }
        return dists;
    }
    
    public LinkedList<Integer> getPath(Dist[] dists, int to){
        LinkedList<Integer> ls = new LinkedList<>();
//        ls.insert(0, to);
        int pre = to;
        while(pre != -1){
            ls.insert(0, pre);
            pre = dists[pre].pre;
        }
        return ls;
    }
    
    static Dist[][] floyd(Graph graph){
        Dist[][] dists = new Dist[graph.verticesNum()][graph.verticesNum()];
        for(int i = 0; i != graph.verticesNum(); i++){
            for(int j = 0; j != graph.verticesNum(); j++){
                dists[i][j] = new Dist();
                if(i == j){
                    dists[i][j].pre = i;
                    dists[i][j].length = 0;
                }else{
                    dists[i][j].pre = -1;
                    dists[i][j].length = INFINTY;
                }
                dists[i][j].index = j; // 不需要的,为了输出好看而已                
            }
        }
        
        for(int v = 0; v != graph.verticesNum(); v++){
            for(Edge edge = graph.firstEdge(v); graph.isEdge(edge); edge = graph.nextEdge(edge)){
                dists[v][edge.to].pre = v;
                dists[v][edge.to].length = edge.weight;
            }
        }
                
        for(int v = 0; v != graph.verticesNum(); v++){
            for(int i = 0; i != graph.verticesNum(); i++){
                for(int j = 0; j != graph.verticesNum(); j++){
                    if(dists[i][j].length > dists[i][v].length + dists[v][j].length){
                        dists[i][j].length = dists[i][v].length + dists[v][j].length;
//                        dists[i][j].pre = dists[v][j].pre;
//                        dists[i][j].pre = v;// 用printPath 打印出来
                    }
                }
            }
        }
        return dists;
    }
    
    static void printDists(Dist[] ds, Graph graph){
//        for(Dist d : ds){
//            System.out.println(d.toString());
//        }
        printDists(new Dist[][]{ds}, graph);
    }
    
    static void printDists(Dist[][] d2s, Graph graph){
        for(Dist[] ds : d2s){
            StringBuilder sb = new StringBuilder();
            for(Dist d : ds){
                sb.append(d.toString(graph)). append(";  ");
            }
            System.out.println(sb);
        }
    }
    
    public static LinkedList<String> pre2Path(Dist[] dists, Graph g,  int from, int to){
        LinkedList<String> ls = new LinkedList<>();
        int pre = to;
        while (pre != from && pre != -1) {
//            ls.insert(0, pre);
            ls.insert(0, dists[from].map2Info(g, pre));
            pre = dists[pre].pre;
        }
//        ls.insert(0, pre);
        ls.insert(0, dists[from].map2Info(g, pre));
        return ls;
    }
    
    public static void printPath(Dist[][] dists, Graph g, int from, int to){
        // 第一个from 打印不出, 不要追求打印出来, 麻烦
        int mid = dists[from][to].pre;
        if(mid == from){
//            System.out.print(from + ", ");
            System.out.print(dists[from][to].map2Info(g, to) + ", ");
        }else{
            printPath(dists, g, from, mid);
            printPath(dists, g, mid, to);
        }
    }
    
    public static LinkedList<String> pre2Path(Dist[][] dists,Graph g, int from, int to){
        return pre2Path(dists[from],g, from, to);
    }
    
    
    public static void main(String[] args) {
        // PPT 7.4 P5的图
        int[][] edgeInfos = new int[][]{
            new int[]{0, 1, 50, 2, 10},
            new int[]{1, 2, 15, 4, 50},
            new int[]{2, 0, 20, 3, 15},
            new int[]{3, 1, 20, 4, 35},
            new int[]{4, 3, 30},
            new int[]{5, 3, 3}
        };
        Graph graph = Graph.createGraph(6, edgeInfos, true, true);
        Dist[] ds = dijkstra(graph, 0);
        for(int i = 0; i != graph.verticesNum(); i++){
            System.out.print(i + ": ");
            pre2Path(ds, graph, 0, i).printAll();
        }
//        printDists(ds);
                
        
//        int[][] edgeInfos1 = new int[][]{
//            new int[]{0, 1,4, 2,11},
//            new int[]{1, 0,6, 2,2},
//            new int[]{2, 0,3 }
//        };
//        
//        // PPT7.4 P22 图
//        Graph graph1 = Graph.createGraph(3, edgeInfos1, true, true);
//        Dist[][] dists1 = floyd(graph1);
////        printDists(dists1);
//        LinkedList<Integer> ls = pre2Path(dists1, 2, 0);
//        ls.printAll();
//      
        // <指导> P134 图6.8
//        int[][] edgeInfos2 = new int[][]{
//            new int[]{1, 3,15, },
//            new int[]{2, 1,10, 4,2, 5,30},
////            new int[]{3},
//            new int[]{4, 2,20, 6,15},
//            new int[]{5, 1,10, 3,4, 6,10},
//            new int[]{6, 2,4}
//        };
//        
//        Graph graph2 = Graph.createGraph(6, edgeInfos2, true, true, 1);
////        Dist[] dists2 = dijkstra(graph2, 3);
////        for(int i = 0; i != graph2.verticesNum(); i++){
////            LinkedList<String> path = pre2Path(dists2, graph2, 3, i);
////            System.out.print((i + 1 )+ " : ");
////            path.printAll();
////        }
//        // 3 : 4, 6, 2, 1, 3,
//        Dist[][] distses2f = floyd(graph2);
////        LinkedList<String> path2f = pre2Path(distses2f, graph2, 3, 2);
////        path2f.printAll();
//        printPath(distses2f,graph2 , 3, 2);
        
    }
    
}
