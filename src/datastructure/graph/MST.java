/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.graph;
import static datastructure.graph.Graph.*;
import static datastructure.graph.ShortestPath.*;
import static datastructure.Tools.INFINTY;
import datastructure.ParamBox;
import datastructure.binarytree.Heap;
import datastructure.tree.ParTree;

/**
 * 最小生成树
 * @author linqiye
 */
public class MST {
    
    static boolean prim(Graph graph, int s, ParamBox box){
        Edge[] mst = new Edge[graph.verticesNum() - 1];
        box.pointer = mst;
        MARK[] marks = graph.createMarks();
//        int mstIndex = 0;
        Dist[] ds = new Dist[graph.verticesNum()];
        for(int v = 0; v != ds.length; v++){
            ds[v] = new Dist();
            ds[v].length = INFINTY;
            ds[v].pre = v;
        }
        ds[s].length = 0;
        marks[s] = MARK.VISITED;
        int v = s;
        for(int i = 0; i != mst.length; i++){
            for(Edge edge = graph.firstEdge(v); graph.isEdge(edge); edge = graph.nextEdge(edge)){
                if(marks[edge.to] == MARK.UNVISITED
                        && edge.weight < ds[edge.to].length){
                    ds[edge.to].length = edge.weight;
                    ds[edge.to].pre = v;
                }
            }
            
            v = minVertex(ds, marks);
            if(v == -1) {
                System.err.println("不存在最小生成树");
                return false;
            }
            marks[v] = MARK.VISITED;
            //if(ds[v].pre != v)// 只需要初始化的时候加上marks[s] = MARK.VISITED; 就不需要这个判断
            Edge edge = new Edge(ds[v].pre, v, ds[v].length, null);
            mst[i] = edge;
            
        }
        
        return true;
    }
    
    static int minVertex(Dist[] dists, MARK[] marks){
        int min = INFINTY;
        int index = -1;
        for(int i = 0; i != dists.length; i++){
            if(marks[i] == MARK.UNVISITED && dists[i].length < min){
                min = dists[i].length;
                index = i;
            }
        }
        return index;
    }
    
    static class MinEdgeHeap extends Heap<Edge>{
        
        public MinEdgeHeap(int count){
            super(count);
        }

        @Override
        public boolean lessThen(Edge a, Edge b) {
            return a.weight < b.weight;
        }
    
    }
    
    static boolean kruskal(Graph g, ParamBox box){
        Edge[] mst = new Edge[g.verticesNum() - 1];
        box.pointer = mst;
        ParTree<Integer> parTree = new ParTree(g.verticesNum());
        int equNum = g.verticesNum();
        MinEdgeHeap heap = new MinEdgeHeap(g.EdgesNum());
        for(int v = 0; v != g.verticesNum(); v++){
            for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = g.nextEdge(edge)){
                if(edge.from < edge.to){
                    heap.insert(edge);
                }
            }
        }
        // 初始化完成
        int i = 0;
        while(equNum > 1){
            if(heap.isEmpty()){//存在环路 或 不连通 都会进入这里
                System.err.println("不存在最小生成树");
                return false;
            }
            Edge edge = heap.removeTop();
            if(parTree.different(edge.from, edge.to)){
                mst[i++] = edge;
                parTree.union(edge.from, edge.to);
                equNum--;
            }
        }
        return true;
    }
    
    static void printMST(Edge[] mst){
        for(Edge edge : mst){
            System.out.println(edge.toString());
        }
    }
    
    public static void main(String[] args) {
        int[][] edinfos = new int[][]{
            new int[]{0, 1,20, 4,1},
            new int[]{1, 0,20, 2,6, 3,4},
            new int[]{2, 1,6, 6,2},
            new int[]{3, 1,4, 5,12, 6,8},
            new int[]{4, 0,1, 5,15},
            new int[]{5, 4,15, 6,10, 3, 12},
            new int[]{6, 2,2, 3,8, 5,10}
        };
        
        Graph g = createGraph(7, edinfos, false, true);
        
        ParamBox box0 = new ParamBox();
        ParamBox box1 = new ParamBox();
        System.out.println("prim:");
        if(prim(g, 0, box0)){
            printMST((Edge[]) box0.pointer);
        }
        
        System.out.println("kruskal");
        if(kruskal(g, box1)){
            printMST((Edge[]) box1.pointer);
        }
    }
}
