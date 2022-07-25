/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author HOPELY
 */

class Node { 
        String value; 
        Node leftChild;
        Node rightChild; 
   
        public Node(){ 
            value = null; 
            leftChild = rightChild = null; 
        }
        
        public Node(String val){ 
            value = val; 
            leftChild = rightChild = null; 
        }
} 

class BST { 
    
    Node root; 
    int flag;
   
    BST(){ 
        flag = 0;
        root = null; 
    } 
    
    void remove(String value) { 
        root = delete_Recursive(root, value); 
    } 
   
    
    Node delete_Recursive(Node root, String value)  { 

        if (root == null)  return root; 
   
        if (value.compareTo(root.value) < 0)     
            root.leftChild = delete_Recursive(root.leftChild, value); 
        else if (value.compareTo(root.value) > 0)  
            root.rightChild = delete_Recursive(root.rightChild, value); 
        else if (value.compareTo(root.value) == 0) { 
            flag = 1;
            if (root.leftChild == null) 
                return root.rightChild; 
            else if (root.rightChild == null) 
                return root.leftChild; 

            Node min = root.rightChild;
            while(min.leftChild != null) {
                min = min.leftChild;
            }
            root.value = min.value;
            root.rightChild = delete_Recursive(root.rightChild, root.value); 
        }
        return root; 
    } 
   
  

    void insert(String value)  { 
        root = insert_Recursive(root, value); 
    } 
   

    Node insert_Recursive(Node root, String value) { 

        if (root == null) { 
            root = new Node(value); 
            return root; 
        } 

        if (value.compareTo(root.value) < 0)    
            root.leftChild = insert_Recursive(root.leftChild, value); 
        else if (value.compareTo(root.value) > 0)  
            root.rightChild = insert_Recursive(root.rightChild, value); 

        return root; 
    } 
    
    void visit(Node node) {
        if (node == null)
            return;
        visit(node.leftChild);
        System.out.print(node.value + " ");
        visit(node.rightChild);
    }
 }

public class Main {
    public static void main(String[] args) throws IOException  { 

        BST bst = new BST(); 
        System.out.println("Enter 5 names: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String data = reader.readLine();
        String[] listData = data.split(" ");
        for (int i = 0; i < listData.length; i++) {
            bst.insert(listData[i]);
        }

        System.out.println("The names in the BST: "); 
        bst.visit(bst.root);
        System.out.println();
        System.out.println("Enter the name to be removed: ");
        data = reader.readLine();
        bst.remove(data);
        if (bst.flag == 0) {
           System.out.println("The name is not found.");
           return;
        }
        System.out.println("The name is removed. The elements in the BST: ");
        bst.visit(bst.root);
     } 
}