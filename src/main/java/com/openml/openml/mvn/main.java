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
            System.out.println("\n 1. Press 1 to view all datasets : ");
            System.out.println("\n 2. Press 2 to download a dataset : ");
            System.out.println("\n 3. Press 3 to Upload a dataset : ");
            System.out.println("\n 4. Press 4 to retrieve the description of the flow/implementation : ");
            System.out.println("\n 5. Press 5 to retrieve the description of the Task : ");
            System.out.println("\n 6. Press 6 to retrieve the description of the Run : ");
            System.out.println("\n Press any other key to Exit ");
            task = input.nextInt();
            switch (task) {
                case 1:
                   down.dataSetList();
                   break;
                case 2:
                   down.download();
                   break;
                case 3:
                    down.upload();
                    break;
                case 4:
                    down.flow();
                    break;
                case 5:
                    down.task();
                    break;
                case 6:
                    down.run();
                    break;
                default:
                    break;
            }
        }  
    }
}
