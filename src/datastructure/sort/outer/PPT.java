/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.sort.outer;

import datastructure.Project;
import java.io.File;
import static datastructure.Tools.clearDir;

/**
 *
 * @author linqiye
 */
public class PPT {
    static int replaceRunsSize = 1000;
    static int loserTreeRunsMergeCount = 8;
    
    static String[] runsDir = new String[]{
        Project.tmpFileDir + "/outer_sort/runs",
        Project.tmpFileDir + "/outer_sort/runs1"
    };
        
    public static void main(String[] args) {
        
        int[] cc = new int[1];
        
//        Checker.check("/Users/linqiye/NetBeansProjects/DataStructure/data/tmp/outer_sort/runs1/run_", 50, cc);
//        if(true){ return; }
        
        for(String d : runsDir){
            clearDir(d);
        }
        
        ReplacementSelection replacementSelection = new ReplacementSelection(Project.OUTERSORT_INPUT_FILE, runsDir[0], replaceRunsSize);
        replacementSelection.run();
        int outputRunsCount = replacementSelection.getRunsCount();
        
//        Checker.check(runsDir[0] + "/run_",outputRunsCount, cc);
        boolean even = true;
        String inputDir = null;
        String outputDir = null;
        int runsCount = 0;
        System.out.println("init runs count:" + outputRunsCount);
        int time = 1;
        
        while(outputRunsCount > 1){
            cc[0] = 0;
            runsCount = outputRunsCount;
            outputRunsCount = 0;
            System.out.println("time:" + time + " need merge runs count:" + runsCount);
            inputDir = runsDir[even ? 0 : 1];
            outputDir = runsDir[even ? 1 : 0];
            even = !even;
            
            for(int i = 0; i < runsCount; i+= loserTreeRunsMergeCount){
                int count = loserTreeRunsMergeCount;
                if(i + loserTreeRunsMergeCount >= runsCount){ count = runsCount - i; }
                String[] inpuntFileNames = new String[count];
                for(int j = 0; j != count; j++){
                    inpuntFileNames[j] = inputDir+"/run_"+(i + j);
                }
                String outputFileName = outputDir+"/run_" + outputRunsCount;
                
                LoserTree loserTree = new LoserTree(inpuntFileNames, outputFileName);
                loserTree.run();
                loserTree.cleanInputFiles();
                
                outputRunsCount++;
            }
            System.out.println("time:" + time + " produce runs count:" + outputRunsCount);
            Checker.check(outputDir + "/run_", outputRunsCount, cc);
            time++;
        }
        int[] count = new int[1];
        if(Checker.check(outputDir + "/run_0", count)){
            System.out.println("success!!!!  sort count: " + count[0]);
        }else{
            System.out.println("failed!!!!  sort count: " + count[0]);
        }
        
    }
    

}
