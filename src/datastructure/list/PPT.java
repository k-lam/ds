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
public class PPT {
    
    /**
     * 队列实现
     */
    public static class FarmerCrossRiver implements Runnable{
//        int status;//人 狼 菜 羊,0 初始岸, 1 对岸    
        boolean farmer(int status){// true: 在对岸
            return (status & 0x8) != 0;
        }
        
        boolean wolf(int status){
            return (status & 0x4) != 0;
        }
        
        boolean cabbage(int status){
            return (status & 0x2) != 0;
        }
        
        boolean goat(int status){
            return (status & 0x1) != 0;
        }
        
        boolean safe(int status){
            if((goat(status) == cabbage(status)) && goat(status) != farmer(status)) return false;
            if(wolf(status) == goat(status) && goat(status) != farmer(status)) return false;
            return true;
        }

        @Override
        public void run() { 
            int[] route = new int[16];
            for(int i = 0; i != route.length; i++){
                route[i] = -1;
            }
            route[0] = 0;
            Queue<Integer> moveTo = new ArrQueue<>(15);
            int status = 0;
            moveTo.enQueue(status);
            while(!moveTo.isEmpty() && route[15] == -1){
                status = moveTo.deQueue();
                for(int movers = 1; movers <= 8; movers = movers << 1){
                    //1.与人同一岸 2. 未曾访问过 3. 安全状态 //人 狼 菜 羊
//                    if((status & 8) == (status & movers)){// 错的 如11 & 8 = 8, 11 & 1 = 1!
                    if(((status & 8) != 0) == (((status & movers) != 0))){          
                        int newStatus = status ^ (movers | 8); 
                        if(route[newStatus] == -1 && safe(newStatus)){
                            route[newStatus] = status;
                            moveTo.enQueue(newStatus);
                        }
                    }
                }
            }
            if(route[15] == -1){
                System.err.println("fail!!!!!");
                return;
            }
            printRoute(route);
        }
        
        void printRoute(int[] route){
            Stack<Integer> stack = new ArrStack<>(15);
            int pre = 15;
            while(pre != 0){
                stack.push(pre);
                pre = route[pre];
            }
            
            while(!stack.isEmpty()){
                System.out.println(whoInTargetDock(stack.top()));
                stack.pop();
            }
            System.out.println("done!");
        }
        
        String whoInTargetDock(int status){
            String s = "";
            String[] names = {"羊", "菜", "狼", "人"}; // 人 狼 菜 羊
            for(int i= 0,flag = 1; flag <= 8; i++, flag = flag << 1){
                if((status & flag) != 0){
                    s += names[i] + " ";
                }
            }
            return s;
        }
    }
    
    /**
     * 递归实现
     */
    static class FarmerCrossRiverR extends FarmerCrossRiver{

        @Override
        public void run(){
            int[] route = new int[16];
            for(int i = 0; i != route.length; i++){
                route[i] = -1;
            }
            route[0] = 0;
            go(route, 0);
            
            printRoute(route);
        }
        
        void go(int[] route, int status){
            for(int mover = 1; mover <= 8; mover = mover << 1){
                //1.与人同一岸 2. 未曾访问过 3. 安全状态 //人 狼 菜 羊
                if(((status & 8) != 0) == (((status & mover) != 0))){
                    int newStatus = status ^ (mover | 8); 
                        if(route[newStatus] == -1 && safe(newStatus)){
                            route[newStatus] = status;
                            go(route, newStatus);
                        }
                }
            }
        }
    }
    
    /**
     * 用栈实现
     */
    static class FarmerCrossRiverS extends FarmerCrossRiver{
         @Override
        public void run(){
            int[] route = new int[16];
            for(int i = 0; i != route.length; i++){
                route[i] = -1;
            }
            route[0] = 0;
            Stack<Integer> stack = new ArrStack<>(15);
            int status = 0;
            stack.push(0);
            while(!stack.isEmpty() && route[15] == -1){
                status = stack.top();
                stack.pop();
                for(int movers = 1; movers <= 8; movers = movers << 1){
                    //1.与人同一岸 2. 未曾访问过 3. 安全状态 //人 狼 菜 羊
//                    if((status & 8) == (status & movers)){// 错的 如11 & 8 = 8, 11 & 1 = 1!
                    if(((status & 8) != 0) == (((status & movers) != 0))){          
                        int newStatus = status ^ (movers | 8); 
                        if(route[newStatus] == -1 && safe(newStatus)){
                            route[newStatus] = status;
                            stack.push(newStatus);
                        }
                    }
                }
            }
            
             printRoute(route);
        }
    }
    
    static class Dist{
            int index = 0;
            int pre = -1;
            int cost = Integer.MAX_VALUE;// 0:此岸, 1 对岸
    }
    /**
     * 用队列实现
     */
    static class CrossBridge implements Runnable{
//        int[] costs = {1, 3, 6, 8, 12};
        int[] costs = {12, 8, 6, 3, 1};
        int deadline = 29;
        @Override
        public void run() {
            // status = index 每一位: 最高位是电筒,余下从高到低:与costs一致, 1表示目标岸,0表示起始岸
            Dist[] dists = new Dist[64];
            for(int i = 0; i != 64; i++){
                dists[i] = new Dist();
                dists[i].index = i;
            }
            dists[0].pre = 0;
            dists[0].cost = 0;
//            Queue<Dist> queue = new ArrQueue<>(128);
            Queue<Dist> queue = new LnkQueue<>();
            queue.enQueue(dists[0]);
            while(!queue.isEmpty()){
                if(dists[63].pre != -1) break;// 都过河了
                Dist dist = queue.deQueue();
                int status = dist.index;//1, 3, 6, 8, 12 最高一位1表示电筒在目标岸
                if((status & 32) != 0){//电筒在目标岸, 只需要一位回来
//                    status |= 1;//电筒
                    for(int mover = 1, i = 0; mover <= 16; mover = mover<<1, i++){
                        // 1. 原本在目标岸 2. 去起始岸新的时间比原来的少
                        if((status & mover) != 0){// 原本在目标岸
                            int newStatus = status & ~mover & 31;// 回起始岸
//                            System.out.println(status + "," + mover + "," + newStatus + "," + i);
                            int cost = dists[status].cost + costs[i];
                            if(cost <= deadline && cost < dists[newStatus].cost){
                                dists[newStatus].pre = status;
                                dists[newStatus].cost = cost;
                                queue.enQueue(dists[newStatus]);
                            }
                        }
                    }
                }else{//电筒在起始岸,两位过目标岸
                    for(int mover1 = 1,i = 0; mover1 <= 8; mover1 = mover1 << 1, i++){
                        if((mover1 & status) != 0) continue;// 在起始岸
                        for(int mover2 = mover1 << 1; mover2 <= 16; mover2 = mover2 << 1){
                            int newStatus = status | mover1 | mover2 | 32;
                            int cost = dists[status].cost + costs[i];
                            if(cost <= deadline && cost < dists[newStatus].cost){
                                dists[newStatus].pre = status;
                                dists[newStatus].cost = cost;
                                queue.enQueue(dists[newStatus]);
                            }
                        }
                    }
                }
            }
            
            printRoute(dists);
           
        }
        
        void printRoute(Dist[] dists){
            if(dists[63].pre == -1){
                System.err.println("fail");
                return;
            }
            Stack<Dist> stack = new ArrStack(64);
            Dist dist = dists[63];
//            for(int i = 0; i <= 63; i++){
//                print(dists[i]);
//            }
            while(dist.index != 0){
//                print(di)
//                dist = stack.top();
//                stack.pop();
                stack.push(dist);
                dist = dists[dist.pre];
            }
            
            stack.push(dists[0]);
            
            while(!stack.isEmpty()){
                print(stack.top());
                stack.pop();
            }
        }
        
        void print(Dist dist){
            String initDock = "";
            String nextDock = "";
            for(int i = 1; i != 6; i++){
                int status = dist.index;
                if(status == 56){
                    status = 56;
                }
                if((status & (1 << (i - 1))) != 0){
                    nextDock += (i + " ");
                }else{
                    initDock += (i + " ");
                }
            }
            System.out.println(dist.index + " :init dock: " + initDock + ", nextDock: " + nextDock + ", cost: " + dist.cost + ",pre: " + dist.pre );
        }
    }
    
    /**
     * 用递归实现
     */
    static class CrossBridgeR extends CrossBridge{
        @Override
        public void run(){
            Dist[] dists = new Dist[64];
            for(int i = 0; i != 64; i++){
                dists[i] = new Dist();
                dists[i].index = i;
            }
            dists[0].pre = 0;
            dists[0].cost = 0;
//            if()
            go(0, dists);
//            for(int i = 0; i != 64; i++){
//                print(dists[i]);
//            }
            printRoute(dists);
        }
        
        void go(int status, Dist[] dists){
            if(dists[63].pre == -1){
                for(int mover1 = 1,i = 0; mover1 <= 8; mover1 = mover1 << 1, i++){//to next dock
                    if((mover1 & status) != 0) continue;// 在起始岸
                    for(int mover2 = mover1 << 1; mover2 <= 16; mover2 = mover2 << 1){
                        int newStatus = status | mover1 | mover2 | 32;
                            int cost = dists[status].cost + costs[i];
                            if(cost <= deadline && cost < dists[newStatus].cost){
                                dists[newStatus].cost = cost;
                                dists[newStatus].pre = status;
                                if(newStatus == 63){
                                    return;
                                }
                                
                                for(int mover = 1, j = 0; mover <= 16; mover = mover<<1, j++){// back to init dock
                                    if((newStatus & mover) == 0) continue;
                                    int newStatus2 = newStatus & ~mover & 31;
                                    if(newStatus == 35 && newStatus2 == 3){
                                        newStatus = 35;
                                    }
                                    int cost2 = dists[newStatus].cost + costs[j];
                                    if(cost2 <= deadline && cost2 < dists[newStatus2].cost){
                                        dists[newStatus2].pre = newStatus;
                                        dists[newStatus2].cost = cost2;
                                        go(newStatus2, dists);
                                    }
                                }
                            }
                    }
                }
            }
        }
    }
    
    /**
     * 用栈实现
     */
    public static class CrossBridgeS extends CrossBridge{
        @Override
        public void run(){
            Dist[] dists = new Dist[64];
            for(int i = 0; i != 64; i++){
                dists[i] = new Dist();
                dists[i].index = i;
            }
            dists[0].pre = 0;
            dists[0].cost = 0;
            Stack<Dist> stack = new ArrStack<>(128);
            stack.push(dists[0]);
            while(!stack.isEmpty()){
                if(dists[63].pre != -1) break;// 都过河了
                Dist dist = stack.top();
                stack.pop();
                int status = dist.index;//1, 3, 6, 8, 12 最高一位1表示电筒在目标岸
                if((status & 32) != 0){//电筒在目标岸, 只需要一位回来
//                    status |= 1;//电筒
                    for(int mover = 1, i = 0; mover <= 16; mover = mover<<1, i++){
                        // 1. 原本在目标岸 2. 去起始岸新的时间比原来的少
                        if((status & mover) != 0){// 原本在目标岸
                            int newStatus = status & ~mover & 31;// 回起始岸
//                            System.out.println(status + "," + mover + "," + newStatus + "," + i);
                            int cost = dists[status].cost + costs[i];
                            if(cost <= deadline && cost < dists[newStatus].cost){
                                dists[newStatus].pre = status;
                                dists[newStatus].cost = cost;
//                                queue.enQueue(dists[newStatus]);
                                stack.push(dists[newStatus]);
                            }
                        }
                    }
                }else{//电筒在起始岸,两位过目标岸
                    for(int mover1 = 1,i = 0; mover1 <= 8; mover1 = mover1 << 1, i++){
                        if((mover1 & status) != 0) continue;// 在起始岸
                        for(int mover2 = mover1 << 1; mover2 <= 16; mover2 = mover2 << 1){
                            int newStatus = status | mover1 | mover2 | 32;
                            int cost = dists[status].cost + costs[i];
                            if(cost <= deadline && cost < dists[newStatus].cost){
                                dists[newStatus].pre = status;
                                dists[newStatus].cost = cost;
                                stack.push(dists[newStatus]);
                            }
                        }
                    }
                }
            }
            
//            for(int i = 0; i != dists.length; i++){
//                print(dists[i]);
//            }
            
            printRoute(dists);
        }
    }
    
    public static void main(String[] args) {
//        Runnable runnable = new FarmerCrossRiver();
//        runnable.run();
//        runnable = new FarmerCrossRiverR();
//        runnable.run();
//        runnable = new FarmerCrossRiverS();
//        runnable.run();
//        Runnable runnable = new CrossBridgeR();
//        runnable.run();
//        System.out.println("all done");
//        new CrossBridge().run();
//        System.out.println("all done");
        
        new CrossBridgeS().run();
        System.out.println("all done");
    }
}
