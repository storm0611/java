/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.alloc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author HOPELY
 */
public class Alloc {
    
    public static List<String> ltFileName;
    public static int[] naExistId;
    public static int nFileId;
    public static int nBlockId;
    public static int nBlockNum;
    public static String[] saAlloc;
    
    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        List<String> ltInstructions = Files.readAllLines(Paths.get("disk.dat"));
        ltFileName = new ArrayList<String>();
        nBlockNum =  Integer.parseInt(ltInstructions.get(0));
        System.out.println("totBlock = " + Integer.toString(nBlockNum));
        naExistId = new int[nBlockNum];
        saAlloc = new String[nBlockNum];
        for (int nI = 0; nI < nBlockNum; nI++) {
            saAlloc[nI] = "*";
        }
        for (int nI = 1; nI < ltInstructions.size(); nI++) {
            fnDecode(ltInstructions.get(nI));
        }
    }
    
    /**
     *
     * @param sInstruction
     */
    public static void fnDecode(String sInstruction) {
        String[] split = sInstruction.split("\"");
        switch(split[0]) {
            /*
            case "add ":
                split[2] = split[2].substring(1);
                nBlockId += Integer.parseInt(split[2]) - 1;
                if (nBlockId >= nBlockNum) {
                    System.out.println("Cannot add");
                } else if (ltFileName.contains(split[1])){
                    System.out.println("The file is already exist");
                } else {
                    ltFileName.add(split[1]);
                    naExistId[nFileId++] = Integer.parseInt(split[2]);
                    int cnt = 0;
                    for (int nI = 0; nI < nBlockNum; nI++) {
                        if (saAlloc[nI] == "*") {
                            cnt ++;
                            if (cnt > Integer.parseInt(split[2])) {
                                break;
                            }
                            saAlloc[nI] = Integer.toString(nFileId);
                        }
                    }
                    System.out.println("File " + split[1] + " was added successfully");
                }
                break;
            */
            case "add ":
                split[2] = split[2].substring(1);
                nBlockId += Integer.parseInt(split[2]) - 1;
                if (nBlockId >= nBlockNum) {
                    System.out.println("Cannot add");
                } else if (ltFileName.contains(split[1])){
                    System.out.println("The file is already exist");
                } else {
                    ltFileName.add(split[1]);
                    naExistId[nFileId++] = Integer.parseInt(split[2]);
                    int tmp = 0;
                    for (int nI = 0; nI < saAlloc.length; nI ++) {
                        if (saAlloc[nI] == "*") {
                            tmp++;
                            if (tmp >= naExistId[nFileId - 1]) {
                                for (; tmp > 0; tmp--) {
                                    saAlloc[nI - tmp + 1] = Integer.toString(nFileId);
                                }
                                break;
                            }
                        } else {
                            tmp = 0;
                        }
                    }
                    System.out.println("File " + split[1] + " was added successfully");
                }
                break;
            case "del ":
                if (!ltFileName.contains(split[1])) {
                    System.out.println("File not exist");
                } else {
                    int nCurrent = ltFileName.indexOf(split[1]);
                    ltFileName.remove(split[1]);
                    for (int nI = 0; nI < saAlloc.length; nI ++) {
                        if (saAlloc[nI].equals(Integer.toString(nCurrent + 1))) {
                            saAlloc[nI] = "*";
                        }
                    }
                    System.out.println("File " + split[1] + " was delete successfully");
                }
                break;
            case "read ":
                if (!ltFileName.contains(split[1])) {
                    System.out.println("File not exist");
                } else {
                    System.out.println("File " + split[1] + " was read successfully with 1 head move(s).");
                }
                break;
            case "print":
                fnDisplay();
                break;
            default:
                System.out.println("Syntax error");                
        }
    }
    
    /**
     *
     */
    public static void fnDisplay() {
        System.out.println();
                System.out.println(" DIRECTORY:");
                int nCnt = 0;
                for (int nJ = 0; nJ < ltFileName.size(); nJ++) {
                    System.out.print(Integer.toString(nJ + 1) + ". " + ltFileName.get(nJ) + ", Blocks ");
                    for (int nI = 0; nI < naExistId[nJ]; nI++) {
                        System.out.print(Integer.toString(nCnt++) + " ");
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println("DETAILS:");
                for (int nI = 1; nI <= saAlloc.length; nI++) {
                    System.out.print(saAlloc[nI - 1] + " ");
                    if (nI % 10 == 0) {
                        System.out.println();
                    }
                }
                System.out.println();
    }
}
