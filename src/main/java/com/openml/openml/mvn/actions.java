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
import org.openml.apiconnector.xml.DataQualityList;
import org.openml.apiconnector.xml.DataSetDescription;
import org.openml.apiconnector.xml.EvaluationScore;
import org.openml.apiconnector.xml.Run;
import org.openml.apiconnector.xml.Run.Parameter_setting;
import org.openml.apiconnector.xml.Task;
import org.openml.apiconnector.xml.Task.Input;
import org.openml.apiconnector.xml.Task.Output;
import org.openml.apiconnector.algorithms.Conversion;
import org.openml.apiconnector.xml.Data;
import org.openml.apiconnector.xml.Data.DataSet;
import org.openml.apiconnector.xml.Flow;
import org.openml.apiconnector.xml.Flow.Parameter;
import org.openml.apiconnector.xml.UploadDataSet;
import org.openml.apiconnector.xml.FlowDelete;
import org.openml.apiconnector.xml.FlowExists;
import org.openml.apiconnector.xml.FlowOwned;
import org.openml.apiconnector.xml.RunDelete;
import org.openml.apiconnector.xml.Tasks;
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
     private static final XStream xstream = XstreamXmlMapping.getInstance();
//    public actions(){
//       
//    }
     
      public int dataSetList() throws Exception {
           Data data = openml.dataList("");
            for (DataSet d : data.getData()) {
                System.out.println("ID : "+d.getDid()+" | Name : "+ d.getName() +" | Status  : "+ d.getStatus()+"  | Version  : "+ d.getVersion());
            }
          
          return 1;
      }
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
                case 2:
                        URL myURL = new URL("https://www.openml.org/data/download/"+id+"/?api_key=5ff389fda327b06847db93efd0cbc1ed");
                        HttpConnector.getFileFromUrl(myURL, "openml/dataSet_"+id+"/",true );
                        System.out.println("\n Data has been successfully downloaded to /openml/ :");
                    break;
                default:
                    System.out.println("Please Press the valid key : ");
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
//                String dataUrl = "http://storm.cis.fordham.edu/~gweiss/data-mining/weka-data/cpu.arff";
                String dataUrl ;
                System.out.println("Paste the URL of the dataSet : ");
                dataUrl = input.next();
		DataSetDescription dsd = new DataSetDescription(dataSetName, dataSetDescription, "arff", dataUrl, "class");
		String dsdXML = xstream.toXML(dsd);
		File description = Conversion.stringToTempFile(dsdXML, "test-data", "arff");
		UploadDataSet ud = openml.dataUpload(description, null);
                int data_id = ud.getId();
                System.out.println("Here's the ID of uploaded dataset : "+ data_id);
        }
            return 10;
    }
    
    
    
    public int flow() throws Exception{
//            Retrieves the description of the flow/implementation with the given id.
            int id;
            int check1;
            int check =1;
            while(check==1){
                
                System.out.println("Press 1 to Retrieve the description of flow : ");
                System.out.println("Press 2 to Retrieve array of id's of all flows/implementations owned by you. : ");
                System.out.println("Press 3 to Check whether an implementation with the given name and version is already registered on OpenML : ");
                System.out.println("Press 4 to Remove the flow with the given id (if you are its owner) : ");
                System.out.println("Press 5 to Upload implementation files (binary and/or source) to OpenML. : ");
                check1 = input.nextInt();
                
                switch(check1){
                    case 1:
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
                        
                        break;
                    case 2:
                        //            Retrieves an array of id's of all flows/implementations owned by you.
                        FlowOwned  response = openml.flowOwned();
                        Integer[] ids = response.getIds();
                        System.out.println("Array of all the ID's : "+ ids);
                        
                        break;
                    case 3:
        //              Checks whether an implementation with the given name and version is already registered on OpenML.
                        String checkName;
                        String checkversion;
                        System.out.println("Enter the name of Flow : ");                       
                        checkName = input.next();
                        System.out.println("Enter the version of Flow : ");
                        checkversion = input.next();
                        FlowExists checkFlow = openml.flowExists(checkName, checkversion);
                        boolean exists = checkFlow.exists();
                        int flow_id = checkFlow.getId();
                        System.out.println("Does the flow exits : " + exists );
                        System.out.println("Flow ID : " + flow_id );
                        
                        break;
                    case 4:
       //                Removes the flow with the given id (if you are its owner).
                        int flowID;
                        System.out.println("Enter the flow ID to delete : " );                        
                        flowID = input.nextInt();
                        FlowDelete  response2 = openml.flowDelete(flowID);
                        
                         break;
                    case 5:
//                          Uploads implementation files (binary and/or source) to OpenML given a description.
                        String descriptionFlow;
                        String codeJar;
                        String sourceZip;
                        System.out.println("Enter the description of Flow : ");                       
                        descriptionFlow = input.next();
                        System.out.println("Enter the CodeJar File of Flow : ");
                        codeJar = input.next();
                        System.out.println("Enter the sourceZip File of Flow : ");
                        sourceZip = input.next();
                        Flow flow = new Flow("weka.J48", "3.7.12", descriptionFlow, "Java", "WEKA 3.7.12");
                        File description3 = Conversion.stringToTempFile(xstream.toXML(flow), "some_name", "xml");
                        UploadFlow response3 = openml.flowUpload( description3, new File(codeJar), new File(sourceZip));
                        int flow_id3 = response3.getId();
                        System.out.println("Here's the ID of uploaded flow : "+ flow_id3);
                        break;
                    default:
                        System.out.println("Please Press the valid key : ");
                        break;
                }
                
                System.out.println("Press 1 to check again or 0 to return to main menu : ");
                check = input.nextInt();
                
            }   
            return 10;
           
    }
    
    public int task() throws Exception{
            
        int id;
        int check1;
        int check =1;
        while(check==1){

            System.out.println("Press 1 to Retrieve the description of Task : ");
            System.out.println("Press 2 to Retrieve all evaluations for the task over the specified window of the stream : ");    
            check1 = input.nextInt();
             switch(check1){
                case 1:
       //          Retrieves the description of the task with the given id.
                    System.out.println("Please enter the ID of task : ");
                    id = input.nextInt();
                    Task task = openml.taskGet(id);
                    String task_type = task.getTask_type();
                    Input[] inputs = task.getInputs();
                    Output[] outputs = task.getOutputs();
                    System.out.println("task_type : "+ task_type+" version : " +inputs+ "Description : " +outputs);
                    break;
                case 2:
                    //Retrieves all evaluations for the task with the given id.
                    Tasks tasks = openml.taskList("");
                    for(org.openml.apiconnector.xml.Tasks.Task t : tasks.getTask() ){
                        System.out.println("ID : "+t.getDid() +" | Name : "+t.getName()+" | Status : " +t.getStatus() + " | Task Type "+ t.getTask_type());
                    }
                default:
                    System.out.println("Please Press the valid key : ");
                    break;
             }
             System.out.println("Press 1 to check again or 0 to return to main menu : ");
             check = input.nextInt();
        }
             
        return 10;
           
    }
    
    
    public int run () throws Exception{
        int id;
        int check1;
        int check =1;
        while(check==1){
            
            System.out.println("Press 1 to Retrieve the description of Run : ");
            System.out.println("Press 2 to Deletes the Run : ");    
            System.out.println("Press 3 to  Upload a Run to OpenML : ");    
            check1 = input.nextInt();
             switch(check1){
                case 1:
                    //      Retrieves the description of the run with the given id.
                    System.out.println("Please enter the ID of Run : ");
                    id = input.nextInt();
                    Run run = openml.runGet(id);
                    int task_id = run.getTask_id();
                    int flow_id = run.getFlow_id();
                    Parameter_setting[] settings = run.getParameter_settings();
                    EvaluationScore[] scores = run.getOutputEvaluation();
                    System.out.println("Run ID  : "+ task_id+" Flow ID : " +flow_id+ " Settings : " +settings + " Scores : " +scores );
                    break;
                    
                case 2:
                    //        Deletes the run with the given id (if you are its owner).
                    System.out.println("Please enter the ID of Run to Delete : ");
                    id = input.nextInt();
                    RunDelete response = openml.runDelete(id);
                    
                    break;
                case 3:
                    //        Uploads a run to OpenML, including a description and a set of output files depending on the task type.
//                    Run.Parameter_setting[] parameter_settings = new Run.Parameter_setting[1];
//                    parameter_settings[0] = newParameter_setting(null, "M", "2");
//
//                    Run run2 = new Run(1, "", 100, "setup_string", parameter_settings,new String[]{ "a", "b", "c" });
//                    Map outputs = new HashMap<String,File>();
//                    outputs.put("predictions",new File("predictions.arff"));
//
//                    XStream xstream = XstreamXmlMapping.getInstance();
//                    File description = Conversion.stringToTempFile(xstream.toXML(run2), "some_name", "xml");
//                    UploadRun response2 = openml.runUpload( description, outputs);
//                    int run_id = response2.getRun_id();
                    String[] tags = {"first_tag", "another_tag"};
                    Run r = new Run(1, null, 10, null, null, tags);
                    String runXML = xstream.toXML(r);
                    File runFile = Conversion.stringToTempFile(runXML, "runtest",  "xml");
                    File predictions = new File("data/test.arff"); 
                    Map<String,File> output_files = new HashMap<String, File>();
                    output_files.put("predictions", predictions);
                    UploadRun ur = openml.runUpload(runFile, output_files);
                    
                    break;
                    
                default:
                    System.out.println("Please Press the valid key : ");                    
                    break;
             }
              System.out.println("Press 1 to check again or 0 to return to main menu : ");
             check = input.nextInt();
        
        }
        return 1;
    }
    
    
    
}
