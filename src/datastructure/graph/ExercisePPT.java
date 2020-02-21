/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.graph;
import datastructure.Tools;
import datastructure.binarytree.Heap;
import static datastructure.graph.Graph.*;
import datastructure.tree.ParTree;
import datastructure.list.*;
import static datastructure.Tools.INFINTY;
/**
 *
 * @author linqiye
 */
public class ExercisePPT {
    
    /**
     * 北大习题课, 第七章
     */
    static class POJ1164{
        int[][] modules = new int[][]{
            new int[]{11, 6, 11, 6, 3, 10, 6},
            new int[]{7,9,6,13,5,15,5},
            new int[]{1,10, 12, 7, 13, 7,5},
            new int[]{13, 11, 10, 8, 10, 12, 13}
        };
        int cCount = modules[0].length;
        int rCount = modules.length;
        int count = cCount * rCount;
        Graph init(){
            Vertex[] vertexs = new Vertex[modules.length * modules[0].length];//先行后列
            
            for(int i = 0; i != modules.length * modules[0].length; i++){
                vertexs[i] = new Vertex();
            }
            
            for(int i = 0; i != rCount; i++){// 行
                for(int j = 0; j != cCount; j++){// 列
                    int v = i * cCount + j;
                    vertexs[v].data = new int[]{i, j};
                    // 下右上左
                    if((modules[i][j] & 1) == 0 && j >= 0 ){// 左
                        Edge edge = new Edge(v, j - 1 + i*cCount);
                        edge.next = vertexs[v].firstEdge;
                        vertexs[v].firstEdge = edge;
                    }
                    if((modules[i][j] & 2) == 0 && i >= 0){// 上
                        Edge edge = new Edge(v, j + (i - 1) * cCount);
                        edge.next = vertexs[v].firstEdge;
                        vertexs[v].firstEdge = edge;
                    }
                    if((modules[i][j] & 4) == 0 && j < rCount - 1){// 右
                        Edge edge = new Edge(v, j + 1 + i*cCount);
                        edge.next = vertexs[v].firstEdge;
                        vertexs[v].firstEdge = edge;
                    }
                    if((modules[i][j] & 8) == 0 && i < cCount - 1){// 下
                        Edge edge = new Edge(v, j + (i + 1) * cCount);
                        edge.next = vertexs[v].firstEdge;
                        vertexs[v].firstEdge = edge;
                    }
                }
            }
            return new Graph(vertexs, false);
        }
        
        void run(){
            Graph g = init();
            ParTree parTree = new ParTree(count);
            for(int v = 0; v != count; v++){
                for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = g.nextEdge(edge)){
                    if(edge.from > edge.to){// 省去一半
                        parTree.union(edge.from, edge.to);
                    }
                }
            }
            
            int max = 0;
            int maxIndex = -1;
            int area = 0;
            for(int i = 0; i != count; i++){
                if(parTree.array[i].parent == null){
                    area++;
                    if(parTree.array[i].getCount() > max){
                        max = parTree.array[i].getCount();
                        maxIndex = i;
                    }
                }
            }
            
            LinkedList<int[]> ls = new LinkedList<>();
            for(int v = 0; v != count; v++){
                if(!parTree.different(maxIndex, v)){
                    ls.append((int[]) g.vertices[v].data);
                }
            }
            
            System.out.println(String.format("area count: %d, largest: %d", area, max));
            for(int[] ij : ls){
                System.out.println(ij[0] + ", " + ij[1]);
            }
        }
        
        void floodFill(){// 借鉴PPT的
            int[][] mark = new int[rCount][cCount];// -1 未访问, 相同的值表示同一连通分区
            for(int i = 0; i != rCount; i++){// 行
                mark[i] = new int[cCount];
                for(int j = 0; j != cCount; j++){// 列
                    mark[i][j] = -1;
                }
            }
            
            int area = 0;
            int maxArea = 0;// 最大的area
            int max = 0;// 最大area的值
            for(int i = 0; i != rCount; i++){// 行
                for(int j = 0; j != cCount; j++){// 列
                    if(mark[i][j] == -1){// 这两个for循环值得借鉴, 也就是说, 图不一定要用一维数组,能遍历所有点就ok
                        int result = connect(i, j, mark, area);
                        if(max < result){
                            maxArea = area;
                            max = result;
                        }
                        area++;
                    }
                }
            }
            
            System.out.println(String.format("area count: %d, largest: %d", area, max));
            for(int i = 0; i != rCount; i++){// 行
                for(int j = 0; j != cCount; j++){// 列
                    if(mark[i][j] == maxArea){
                        System.out.println(i + ", " +j);
                    }
                }
            }
            
        }
        int[][] inc = new int[][]{//下右上左
            new int[]{0, -1},
            new int[]{-1, 0},
            new int[]{0, 1},
            new int[]{1, 0}
        };
        int connect(int i, int j, int[][] marks,int area){
            int res = 1;//连通量
            marks[i][j] = area;
            for(int q = 0; q != 4; q++){
                if((modules[i][j] >> q & 1) == 0){
                    int nextI = i + inc[q][0];
                    int nextJ = j + inc[q][1];
                    if(marks[nextI][nextJ] == -1 && nextI < rCount && nextJ < cCount){
                        // 这也是一个值得借鉴的地方,不需要在意层次还是深度优先了, 
                        // 反正在mark和双重循环的作用下,只要移动点,就可以遍历所有
                        res += connect(nextI, nextJ, marks, area);
                    }
                }
            }
            return res;
        }
        
        
    }
    
    static class POJ1094{
        int vCount = 4;
        int eCount = 6;
        Edge[] edges = new Edge[eCount];
        
        String inputs = "A<B\n" +
                        "A<C\n" +
                        "B<C\n" +
                        "C<D\n" +
                        "B<D\n" +
                        "B<A";
        Graph init(){
            String[] ss = inputs.split("\n");
            for(int i = 0; i != ss.length; i++){
                edges[i] = new Edge(ss[i].charAt(0) - 'A', ss[i].charAt(2) - 'A');
            }
            
            Graph g = new Graph();
            g.directed = true;
            g.vertices = new Vertex[vCount];
            for(int i = 0; i != vCount; i++){
                char c = (char)('A' + i);
                g.vertices[i] = new Vertex(String.valueOf(c));
            }
            return g;
        }
        
        void run(){
            Graph g = init();
            for(Edge edge : edges){
                g.setEdge(edge.from, edge.to, 0);
                topSort(g);
            }
        }
        
        boolean topSort(Graph g){
            int[] inDregee = new int[g.verticesNum()];
            for(int v = 0; v != g.verticesNum(); v++){
                for(Edge edge = g.firstEdge(v); g.isEdge(edge); edge = g.nextEdge(edge)){
                    inDregee[edge.to]++;
                }
            }
            Queue<Integer> queue = new LnkQueue<>();
            for(int i = 0; i != inDregee.length; i++){
                if(inDregee[i] == 0){
                    queue.enQueue(i);
                }
            }
            
            StringBuilder sb = new StringBuilder();
//            MARK[] marks = g.createMarks();
            
            while(!queue.isEmpty()){
                if(queue.size() > 1){
                    System.err.println("Sorted sequence cannot be determined");
                    return false;
                }
                int v = queue.deQueue();
                sb.append(g.vertices[v].data);
                for(Edge e = g.firstEdge(v); g.isEdge(e); e = g.nextEdge(e)){
                    inDregee[e.to]--;
                    if(inDregee[e.to] == 0){
                        queue.enQueue(e.to);
                    }
                }
            }
            
            for(int d : inDregee){
                if(d != 0){
                    System.err.println("Inconsistency found after 2 relations.");
                    return false;
                }
            }
            
            System.out.println(String.format("Sorted sequence determined after %d relations: %s.", sb.length(), sb.toString()));
            return true;
        }
    }
    
    static class POJ1376{
        int[][] inputs = new int[][]{
                new int[]{ 0,0,0,0,0,0,1,0,0,0}, 
                new int[]{ 0,0,0,0,0,0,0,0,1,0},
                new int[]{ 0,0,0,1,0,0,0,0,0,0},
                new int[]{ 0,0,1,0,0,0,0,0,0,0}, 
                new int[]{ 0,0,0,0,0,0,1,0,0,0}, 
                new int[]{ 0,0,0,0,0,1,0,0,0,0},
                new int[]{ 0,0,0,1,1,0,0,0,0,0},
                new int[]{ 0,0,0,0,0,0,0,0,0,0},
                new int[]{ 1,0,0,0,0,0,0,0,1,0}};
        
        int[] startpoint = new int[]{7, 2};
        int startDirect = 3;//左上右下
        int[] endpoint = new int[]{2, 7};
        
//        class MiniHeap extends Heap<Dist>{
//            
//            public MiniHeap(int size){
//                super(size);
//            }
//            public boolean lessThen(Dist a, Dist b){
//                return a.length < b.length;
//            }
//        }
        
//        class Dist{
//            int preR;
//            int preC;
//            int preD;//1 2 -1 -2,左上右下
//            int length = INFINTY;
//            int myR, myC, myD;
//            
////            Dist[] connecDists(){
////                
////            }
//           
//        }
        
//        int weigth(Dist d1, Dist d2){
//            if(d1.myC == d2.myC && d1.myR == d2.myR){
//                if(d1.myD == d2.myD){
//                    return 0;
//                }else if(d1.myD + d1.myD == 0){
//                    return 2;
//                }
//                return 1;
//            }
//            if(d1.myD == d2.myD && Math.abs(d1.myC - d2.myC) == 1 || Math.abs(d1.myR - d2.myR) == 1){
//                return 1;
//            }
//            return INFINTY;
//        }
        
        int[] set(int v){
            int[] result = new int[4];
            for(int i = 0; i != 4; i++){
                result[i] = v;
            }
            return result;
        }
        int[][] direct = new int[][]{new int[]{0, -1}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{1, 0}};
        public void run(){
            int[][] boards = new int[inputs.length - 1][inputs[0].length - 1];
            for(int r = 0; r != inputs.length - 1; r++){
                boards[r] = new int[inputs[0].length - 1];
                for(int c = 0; c != inputs[0].length - 1; c++){
                    if(inputs[r][c] == 1){
                        boards[r][c] = -1;
                        if(r - 1 >= 0){
                            boards[r - 1][c] = -1;
                        }
                        if(c - 1 >= 0){
                            boards[r][c - 1] = -1;
                            if(r - 1 >= 0){
                                boards[r - 1][c - 1] = -1;
                            }
                        }
                    }
                }
            }
            
            int[][][] distance = new int[boards.length][][];
            for(int i = 0; i != boards.length; i++){
                distance[i] = new int[boards[0].length][];
                for(int j = 0; j != distance[i].length; j++){
                    if(boards[i][j] == -1){
                        distance[i][j] = set(INFINTY);
                    }else{
                        distance[i][j] = set(-1);
                    }
                }
            }
           
            Queue<int[]> queue = new LnkQueue<>();
            endpoint[0]--; endpoint[1]--;
            queue.enQueue(new int[]{--startpoint[0], --startpoint[1], startDirect});
            distance[startpoint[0]][startpoint[1]][startDirect] = 0;
            int[] point = null;
            while(!queue.isEmpty()){
                point = queue.deQueue();
                if(point[0] == endpoint[0] && point[1] == endpoint[1]){
                    System.err.println(".........");
                    break;
                }
                int r = point[0];
                int c = point[1];
                int d = point[2];
                for(int delta = 1; delta < 4; delta += 2){// 转向
                    int nextD = (d + delta) % 4;
                    if(distance[r][c][nextD] == -1){
                        distance[r][c][nextD] = distance[r][c][d] + 1;
                        queue.enQueue(new int[]{r, c, nextD});
//                        if(delta == 2){
//                            distance[r][c][(d + delta) % 4]+= 2;
//                        }else{
//                            distance[r][c][(d + delta) % 4]+= 1;
//                        }
                    }
                }
                
                int nextR = r + direct[d][0];
                int nextC = c + direct[d][1];
//                if(boards[nextR][nextC] == -1) continue;
                if(nextR >= 0 && nextR < distance.length 
                        && nextC >= 0 && nextC < distance[0].length
                        && boards[nextR][nextC] != -1){
                    if(distance[nextR][nextC][d] == -1){
                        distance[nextR][nextC][d] = distance[r][c][d] + 1;
                        queue.enQueue(new int[]{nextR, nextC, d});
                    }
                }
            }
            
            System.out.println("shortest distance:"+distance[point[0]][point[1]][point[2]]);
            
        }
                
    }
    
    public static void main(String[] args) {
//        new POJ1164().run();
//        new POJ1164().floodFill();
        
//         new POJ1094().run();
        
        new POJ1376().run();
    }
    
    
}
