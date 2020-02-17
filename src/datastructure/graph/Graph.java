/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.graph;

import datastructure.list.*;
import static datastructure.Tools.INFINTY;
/**
 * 存储结构 严格按照PPT7.2 p9 的邻接表
 * @author linqiye
 */
public class Graph {
    
    public static class Edge{
        int from;
        int to;
        int weight = INFINTY;
        Object info;
        public Edge(){}
        public Edge(int from, int to, int weight, Object info){
            this.from = from;
            this.to = to;
            this.weight = weight;
            this.info = info;
        }
        public Edge(int from, int to){ this(from, to, 0, null);}
        Edge next;
        
        public boolean equals(Edge other){// 不能有重边,其实挺多余的,这方法
            if(other.from == from && other.to == to && other.weight == weight) return true;
            return false;
        }
        
        @Override
        public String toString(){
            return String.format("from: %3d to: %3d w:%4d %s" , from, to, weight > 9999 ? 9999 : weight, info);
        }
    }
    
    public static class Vertex{
        // 图的数据结构, 其实只需要关心 点与点之间的关系, 所以极不建议在图中存储顶点的相关内容
        // 所以 只需用一个data索引外面对象就可以了
        Object data;
        Edge firstEdge;
        
        public Vertex(String info){
            this.data = info;
        }
        
        public Vertex(){}
    }
    
    public static enum MARK{
        VISITED,
        UNVISITED
    }
    
    public Vertex[] vertices;
    public int edgeNum;// 图中边的条数, 呃呃呃, 用来干嘛的?
//    int[] inDegree;// 每个顶点的入度, 这个应该是在拓扑排序用的
    
    public boolean directed = false;
    
    
    /**
     * 
     * @param vertices
     * @param esInfos 不带权: [vertexIndex , to, to, to, to], 带权[vertexIndex ,to,weight, to,weight, to,weight]
     * @param directed 
     * @param w 是否带权
     */
    public Graph(Vertex[] vertices, int[][] esInfos, boolean directed, boolean w){
        this.vertices = vertices;
        this.directed = directed;
//        this.edges = new Edge[vertices.length];
        edgeNum = 0;
        for(int[] eInfo : esInfos){
            int vIndex = eInfo[0];
            Vertex v = vertices[vIndex];
            if(eInfo.length == 1) continue;
            if(w){
                v.firstEdge = new Edge(vIndex, eInfo[1], eInfo[2], null);
                Edge tail = v.firstEdge;
                edgeNum++;
                for(int i = 3; i < eInfo.length; i += 2){
                    tail.next = new Edge(vIndex, eInfo[i], eInfo[i + 1], null);
                    tail = tail.next;
                    edgeNum++;
                }
            }else{
                v.firstEdge = new Edge(vIndex, eInfo[1], 0, null);
                Edge tail = v.firstEdge;
                edgeNum++;
                for(int i = 2; i< eInfo.length; i++){
                    tail.next = new Edge(vIndex, eInfo[i], 0, null);
                    tail = tail.next;
                    edgeNum++;
                }
            }
        }
        if(!directed){ edgeNum /= 2;}
    }

    public static Graph createGraph(int vCount, int[][] esInfos, boolean directed, boolean weight, int offset){
        Vertex[] vertices = new Vertex[vCount];
        for(int i = 0; i != vCount; i++){
            vertices[i] = new Vertex();
            vertices[i].data = i + offset;
        }
        if(offset != 0){
            if(weight){
                for(int i = 0; i!= esInfos.length; i++){
                    esInfos[i][0] -= offset;
                    for(int j = 1; j < esInfos[i].length; j+=2){
                        esInfos[i][j] -= offset;
                    }
                }
            }else{
                for(int i = 0; i != esInfos.length; i++){
                    for(int j = 0; j < esInfos[i].length; j++){
                        esInfos[i][j] -= offset;
                    }
                }                
            }

        }
        return new Graph(vertices, esInfos, directed, weight);
    }
    
    public static Graph createGraph(int vCount, int[][] esInfos, boolean directed, boolean weight){
        return createGraph(vCount, esInfos, directed, weight, 0);
    }
    
    public int verticesNum(){ return vertices.length; }
    
    public int EdgesNum(){ return edgeNum; }
    
    public Edge firstEdge(int vertex){ return vertices[vertex].firstEdge; }
    
    public Edge nextEdge(Edge e){ return e.next; }
    
    public boolean delEdge(int fromVertex, int toVertex){ 
        if(directed) return doDelEdge(fromVertex, toVertex);
        else {
            edgeNum++;
            return doDelEdge(fromVertex, toVertex) && doDelEdge(toVertex, fromVertex);
        }
    }
    
    public boolean doDelEdge(int from, int to){
        Edge edge = vertices[from].firstEdge;
        if(edge == null) return false;
        while(edge.next != null){
            if(edge.next.to == to){
                edge.next = edge.next.next;
                edgeNum--;
                return true;
            }
            edge = edge.next;
        }
        return false;
    }
    
    boolean isEdge(Edge edge){
        if(edge == null) return false;
        Edge pointer = vertices[edge.from].firstEdge;
        while(pointer != null){
            if(pointer.equals(edge))
                return true;
            pointer = pointer.next;
        }
        return false;
//        return edge != null;
    }
    
    int fromVertex(Edge edge){ return edge.from; }
    
    int toVertex(Edge edge){ return edge.to; }
    
    int weight(Edge edge){ return edge.weight; }
    
    public boolean setEdge(int from, int to, int weight){
        delEdge(from, to);
        addEdge(from, to, weight);
        if(!directed){
            addEdge(to, from, weight);
        }
        edgeNum++;
        return true;
    }
    
    private void addEdge(int from, int to, int weight){
        Edge next = vertices[from].firstEdge;
        vertices[from].firstEdge = new Edge(from, to, weight, null);
        vertices[from].firstEdge.next = next;
    }
    
    
    
    public MARK[] createMarks(){
        MARK[] marks = new MARK[vertices.length];
        for(int i = 0; i != marks.length; i++){
            marks[i] = MARK.UNVISITED;
        }
        return marks;
    }
    
    
    
}
