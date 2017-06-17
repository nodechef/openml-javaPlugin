/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openml.openml.mvn;
import java.util.Scanner;

/**
 *
 * @author Sami Ullah Chattha
 */
public class main {
    public static void main( String[] args ) throws Exception{
        Scanner input = new Scanner( System.in ); 
        int task;
        actions down = new actions();
        
        while(true){
            System.out.println("\n Select an option below : ");
            System.out.println("\n 1. Press 1 to download a dataset : ");
            System.out.println("\n 2. Press 2 to Upload a dataset : ");
            System.out.println("\n Press 3 to Exit ");
            task = input.nextInt();
            switch (task) {
                case 1:
                   down.download();
                   break;
                case 2:
                    System.out.println("\n Checking the second option: ");
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }  
    }
}
