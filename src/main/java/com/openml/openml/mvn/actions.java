/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openml.openml.mvn;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import org.openml.apiconnector.algorithms.Conversion;
import org.openml.apiconnector.xml.Flow;
import org.openml.apiconnector.xml.Flow.Parameter;
import org.openml.apiconnector.xml.UploadDataSet;
import org.openml.apiconnector.xml.EvaluationList;
import org.openml.apiconnector.xml.EvaluationList.Evaluation;
import org.openml.apiconnector.xml.FlowDelete;
import org.openml.apiconnector.xml.FlowExists;
import org.openml.apiconnector.xml.FlowOwned;
import org.openml.apiconnector.xml.RunDelete;
import org.openml.apiconnector.xml.UploadFlow;
import org.openml.apiconnector.xml.UploadRun;
import org.openml.apiconnector.xstream.XstreamXmlMapping;


/**
 *
 * @author Sami Ullah Chattha
 */
public class actions {
    
    
     OpenmlConnector openml = new OpenmlConnector("https://www.openml.org/", "5ff389fda327b06847db93efd0cbc1ed");
     Scanner input = new Scanner(System.in);
//    public actions(){
//       
//    }
    public int download() throws Exception{
        
        int id;
        int select;
        String downloadCheck = "Y" ;
        
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
    
    
    
    public int upload() throws Exception{
        
        String dataSetName;
        String dataSetDescription;
        System.out.println("Name of the DataSet : ");
        dataSetName = input.next();
        System.out.println("Description of the DataSet : ");
        dataSetDescription = input.next();
        DataSetDescription dataset = new DataSetDescription( dataSetName, dataSetDescription, "arff", "class");
        
        int check;
        System.out.println("Press 1 to upload file or Press 2 to post the URL of the file hosted somewhere else(Recommended if"
                + "file size is > 100MB ) : ");
        
        check = input.nextInt();
        if(check==1){
            XStream xstream = XstreamXmlMapping.getInstance();
            String filename;
            System.out.println("Filename : ");
            filename = input.next();
            File description = Conversion.stringToTempFile(xstream.toXML(dataset), filename, "xml");
            String workingDirectory = System.getProperty("user.dir")+"/openml/uploads";
            File datasetFile = new File(workingDirectory, filename);
        //        File datasetFile = new File( DATASET_PATH );
            UploadDataSet data = openml.dataUpload( description, datasetFile);
            int data_id = data.getId();
            System.out.println("Here's the ID of uploaded dataset : "+ data_id);
        }else if(check==2){
//           Registers an existing dataset (hosted elsewhere). The description needs to include the url of the data set. Throws an exception if the upload failed, see openml.data.upload for error codes.
//            
//            dataset.setUrl("http://datarepository.org/mydataset");
//            UploadDataSet data = openml.dataUpload( description );
//            int data_id = data.getId();

        }
            return 10;
    }
    
    
    
    public int flow() throws Exception{
//            Retrieves the description of the flow/implementation with the given id.
            int id;
            int check =1;
            while(check==1){
                
                System.out.println("Enter the ID of flow : ");
                id = input.nextInt();
                Flow implementation = openml.flowGet(id);
                String name = implementation.getName();
                String version = implementation.getVersion();
                String description = implementation.getDescription();
                String binary_url = implementation.getBinary_url();
                String source_url = implementation.getSource_url();
                Parameter[] parameters = implementation.getParameter();
                System.out.println("Flow testing : "+ name+" version : " +version+ "    Description : " +description+ " Binary : "+binary_url+" Source : "+source_url+" parameters :"+parameters);
                System.out.println("Press 1 to check again or 0 to return to main menu : ");
                check = input.nextInt();
                
            }
            
            
//            Retrieves an array of id's of all flows/implementations owned by you.
                FlowOwned  response = openml.flowOwned();
                Integer[] ids = response.getIds();
//        

//                Checks whether an implementation with the given name and version is already registered on OpenML.
                FlowExists checkFlow = openml.flowExists("weka.j48", "3.7.12");
                boolean exists = checkFlow.exists();
                int flow_id = checkFlow.getId();
                
                
//                Removes the flow with the given id (if you are its owner).
                FlowDelete  response2 = openml.flowDelete(1);

                
//                Uploads implementation files (binary and/or source) to OpenML given a description.
                XStream xstream = XstreamXmlMapping.getInstance();
                Flow flow = new Flow("weka.J48", "3.7.12", "description", "Java", "WEKA 3.7.12");
                File description = Conversion.stringToTempFile(xstream.toXML(flow), "some_name", "xml");
                UploadFlow response3 = openml.flowUpload( description, new File("code.jar"), new File("source.zip"));
                int flow_id3 = response3.getId();
                
                
            return 10;
           
    }
    
    public int task(int id) throws Exception{
//          Retrieves the description of the task with the given id.
            Task task = openml.taskGet(id);
            String task_type = task.getTask_type();
            Input[] inputs = task.getInputs();
            Output[] outputs = task.getOutputs();
            System.out.println("task_type : "+ task_type+" version : " +inputs+ "Description : " +outputs);
            //Retrieves all evaluations for the task with the given id.

//                TaskEvaluations response = openml.taskEvaluations(1);
//                Evaluation[] evaluations = response.getEvaluation();
            
            
//            For data streams. Retrieves all evaluations for the task over the specified window of the stream.

            //TaskEvaluations response = client.taskEvaluations(1);
            //Evaluation[] evaluations = response.getEvaluation();
        
        
            return 10;
           
    }
    
    
    public int run (int id) throws Exception{
//      Retrieves the description of the run with the given id.
        Run run = openml.runGet(id);
        int task_id = run.getTask_id();
        int flow_id = run.getFlow_id();
        Parameter_setting[] settings = run.getParameter_settings();
        EvaluationScore[] scores = run.getOutputEvaluation();
        System.out.println("Task ID  : "+ task_id+" Flow ID : " +flow_id+ " Settings : " +settings + " Scores : " +scores );
        
        
//        Deletes the run with the given id (if you are its owner).
        RunDelete response = openml.runDelete(1);
        
        
        
//        Uploads a run to OpenML, including a description and a set of output files depending on the task type.

    
//        Run.Parameter_setting[] parameter_settings = new Run.Parameter_setting[1];
//        parameter_settings[0] = Run.Parameter_setting(null, "M", "2");
//        
//        Run run2 = new Run(1, "", 100, "setup_string", parameter_settings,new String[]{ "a", "b", "c" });
//        Map outputs = new HashMap<String,File>();
//        outputs.put("predictions",new File("predictions.arff"));
//        
//        XStream xstream = XstreamXmlMapping.getInstance();
//        File description = Conversion.stringToTempFile(xstream.toXML(run2), "some_name", "xml");
//        UploadRun response2 = openml.runUpload( description, outputs);
//        int run_id = response2.getRun_id();
  
        return 1;
    }
    
    
    
}
