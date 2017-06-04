/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openml.openml.mvn;
  import org.openml.apiconnector.io.OpenmlConnector;
import org.openml.apiconnector.xml.DataSetDescription;
import java.util.Scanner;

/**
 *
 * @author Sami Ullah Chattha
 */
public class main {
    
    
    
    public static void main( String[] args ) throws Exception{
                OpenmlConnector openml = new OpenmlConnector("https://www.openml.org/", "5ff389fda327b06847db93efd0cbc1ed");
		DataSetDescription data = openml.dataGet(61);
		System.out.println("datasetTesting  " + data);
		System.out.println("dataset name: " + data.getName()); // output = "dataset name: iris"
                
                
                
                
                
//        Scanner input = new Scanner( System.in ); 
////        System.out.print("Please enter the Dataset ID : ");
////        int id = input.nextInt();
//        int task;
//        actions down = new actions();
//        
//        
//        while(true){
//            System.out.print("Select an option below : ");
//            System.out.print("/n 1. Press 1 to download a dataset ");
//            System.out.print("/n 2. Press 2 to Upload a dataset ");
//            System.out.print("/n Press 3 to Exit ");
//            task = input.nextInt();
//            switch (task) {
//                case 1:
//                   down.download(10);
//                case 2:
//                    System.out.print("Checking the second option: ");
//                    break;
//                case 3:
//                    break;
//                default:
//                    break;
//            }
//        }
        
        
        
        
    }
}
