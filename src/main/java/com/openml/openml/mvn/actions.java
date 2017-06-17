/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openml.openml.mvn;

import java.net.URL;
import java.util.Scanner;
import org.openml.apiconnector.io.OpenmlConnector;
import org.openml.apiconnector.xml.DataFeature;
import org.openml.apiconnector.xml.DataQuality;
import org.openml.apiconnector.xml.DataQualityList;
import org.openml.apiconnector.xml.DataSetDescription;
import org.openml.apiconnector.xml.EvaluationScore;
import org.openml.apiconnector.xml.Run;
import org.openml.apiconnector.xml.Run.Parameter_setting;
import org.openml.apiconnector.xml.Task;
import org.openml.apiconnector.xml.Task.Input;
import org.openml.apiconnector.xml.Task.Output;
import org.openml.apiconnector.xml.UploadDataSet;


/**
 *
 * @author Sami Ullah Chattha
 */
public class actions {
    
    
     OpenmlConnector openml = new OpenmlConnector("https://www.openml.org/", "5ff389fda327b06847db93efd0cbc1ed");
     
//    public actions(){
//       
//    }
    public int download() throws Exception{
        
        int id;
        int select;
        String downloadCheck = "Y" ;
        Scanner input = new Scanner(System.in);
        
        while("Y".equals(downloadCheck)){
        System.out.println("Please enter the of DataSet : ");
        id = input.nextInt();
        DataSetDescription data = openml.dataGet(id);

        //Retrieves the description of a specified data set.
        String name = data.getName();
        String version = data.getVersion();
        String description = data.getDescription();
        String url = data.getUrl();
        System.out.println("Name :" + name);
        System.out.println("Version :" + version);
        System.out.println("Description :" + description);
        
//        DataQualitiesList response = openml.dataQualities(id);
//        String[] qualities = response.getQualities();



        System.out.println("\n Select an option below : ");
        System.out.println("\n 1. Press 1 to retrieve the description of the features of dataset : " + id);
//        System.out.println("\n 2. Press 2 to retrieve the description of the qualities (meta-features) of dataset :" +id);
        System.out.println("\n 2. Press 2 to download the dataset :"+id);
        System.out.println("\n 3. Press anyother key to continue :");
        select = input.nextInt();
        switch (select) {
            case 1:
                    DataFeature reponse = openml.dataFeatures(1);
                    DataFeature.Feature[] features = reponse.getFeatures();
                    String feature_name = features[0].getName();
                    String type = features[0].getDataType();
                    boolean	isTarget = features[0].getIs_target();
               break;
//            case 2:
//                    DataQuality response2= openml.dataQuality(1,0,10000,null);
//                    DataQuality.Quality[] qualities = reponse.getQualities();
//                    String name2 = qualities[0].getName();
//                    String value = qualities[0].getValue();
//                break;
            case 2:
                    URL myURL = new URL("https://www.openml.org/data/download/"+id+"/?api_key=5ff389fda327b06847db93efd0cbc1ed");
                    HttpConnector.getFileFromUrl(myURL, "openml/dataSet_"+id+"/",true );
                    System.out.println("\n Data has been successfully downloaded to /openml/ :");
                break;
            default:
                break;
        }
        
        System.out.println("Do you want to checkout anyother dataSet ? : Y or N : ");
        downloadCheck = input.next();   
        }
            return 10;
           
    }
    
    
    
    public int upload(int id) throws Exception{
//            DataSetDescription description = new DataSetDescription( "iris", "The famous iris dataset", "arff", "class");
//            UploadDataSet result = client.dataUpload( description, new File("data/path") );
//            int data_id = result.getId();
            
            
            
//            Registers an existing dataset (hosted elsewhere). The description needs to include the url of the data set. Throws an exception if the upload failed, see openml.data.upload for error codes.

//            DataSetDescription description = new DataSetDescription( "iris", "The iris dataset", "arff", "class");
//            description.setUrl("http://datarepository.org/mydataset");
//            UploadDataSet data = client.dataUpload( description );
//            int data_id = result.getId();

            return 10;
           
    }
    
    
    
    public int flow(int id) throws Exception{
//            Retrieves the description of the flow/implementation with the given id.

//            Implementation flow = client.flowGet(100);
//            String name = flow.getName();
//            String version = flow.getVersion();
//            String description = flow.getDescription();
//            String binary_url = flow.getBinary_url();
//            String source_url = flow.getSource_url();
//            Parameter[] parameters = flow.getParameter();
        
        
            return 10;
           
    }
    
    public int task(int id) throws Exception{
//            Retrieves the description of the task with the given id.

//                Task task = client.taskGet(1);
//                String task_type = task.getTask_type();
//                Input[] inputs = task.getInputs();
//                Output[] outputs = task.getOutputs();

            //Retrieves all evaluations for the task with the given id.

            //TaskEvaluations response = client.taskEvaluations(1);
            //Evaluation[] evaluations = response.getEvaluation();
            
            
//            For data streams. Retrieves all evaluations for the task over the specified window of the stream.

            //TaskEvaluations response = client.taskEvaluations(1);
            //Evaluation[] evaluations = response.getEvaluation();
        
        
            return 10;
           
    }
    
    
    public int run (){
//        Retrieves the description of the run with the given id.

//        Run run = client.runGet(1);
//        int task_id = run.getTask_id();
//        int flow_id = run.getImplementation_id();
//        Parameter_setting[] settings = run.getParameter_settings()
//        EvaluationScore[] scores = run.getOutputEvaluation();



        //Deletes the run with the given id (if you are its owner).

        //RunDelete response = client.runDelete(1);
        
        
        
//        Uploads a run to OpenML, including a description and a set of output files depending on the task type.

            //Run.Parameter_setting[] parameter_settings = new Run.Parameter_setting[1];
            //parameter_settings[0] = Run.Parameter_setting(null, "M", "2");
            //Run run = new Run("1", null, "100", "setup_string", parameter_settings);
            //Map outputs = new HashMap<String,File>();
            //outputs.add("predictions",new File("predictions.arff"));
            //UploadRun response = client.runUpload( run, outputs);
            //int run_id = response.getRun_id();

        
        return 1;
    }
    
    
    
}
