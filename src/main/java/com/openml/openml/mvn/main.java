/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openml.openml.mvn;

import java.util.Scanner;
import org.openml.apiconnector.io.OpenmlConnector;
import org.openml.apiconnector.xml.DataFeature;
import org.openml.apiconnector.xml.DataSetDescription;

/**
 *
 * @author Sami Ullah Chattha
 */
public class main {
    
    
    public int downloading(int id) throws Exception{
        OpenmlConnector client = new OpenmlConnector("api_key");
        DataSetDescription data = client.dataGet(id);
        
        //Retrieves the description of a specified data set.
        
        
        String name = data.getName();
        String version = data.getVersion();
        String description = data.getDescription();
        String url = data.getUrl();
        
        
        //Retrieves the description of the features of a specified data set.
        
        DataFeature reponse = client.dataFeatures(1);
        DataFeature.Feature[] features = reponse.getFeatures();
        String feature_name = features[0].getName();
        String type = features[0].getDataType();
        boolean	isTarget = features[0].getIs_target();
        
        
        return 0;
    }
    public static void main( String[] args ){
        
        Scanner input = new Scanner( System.in ); 
        System.out.print("Please enter the Dataset ID : ");
        int id = input.nextInt();
        int task;
        
        
        while(true){
            System.out.print("Select an option below : ");
            System.out.print("/n 1. Press 1 to download a dataset");
            System.out.print("/n 2. Press 2 to Upload a dataset");
            System.out.print("/n Press 3 to Exit");
            task = input.nextInt();
            switch (task) {
                case 1:
                   
                case 2:
                    System.out.print("Checking the second option: ");
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }
        
        
        
        
    }
}
