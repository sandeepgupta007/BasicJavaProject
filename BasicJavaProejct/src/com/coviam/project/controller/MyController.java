/**
 * @author sreerajr
 * @co-author sandeepGupta
 * @package com.coviam.project.controller
 * @project untitled
 */

package com.coviam.project.controller;

import com.coviam.project.collection.MyCollection;
import com.coviam.project.filehandlers.CSVFileHandler;
import com.coviam.project.filehandlers.JsonFileHandler;
import com.coviam.project.filehandlers.XMLFileHandler;
import com.coviam.project.filehandlerthreads.FileReadHandlerThread;
import com.coviam.project.filehandlerthreads.FileWriteHandlerThread;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MyController implements MyControllerInterface {

    private static final Integer TOTAL_SIZE = 300;
    private static final Integer FILE_SIZE = 100;
    private MyCollection myCollection;
    private XMLFileHandler xmlFileHandler;
    private CSVFileHandler csvFileHandler;
    private JsonFileHandler jsonFileHandler;

    public MyCollection getMyCollection() {
        return myCollection;
    }

    public MyController() throws ParserConfigurationException, SAXException, IOException {
        xmlFileHandler = new XMLFileHandler();
        csvFileHandler = new CSVFileHandler();
        jsonFileHandler = new JsonFileHandler();
        myCollection = MyCollection.getInstance();
    }

    /**
     * Method to read from csv, json and xml files separately from 3 threads and write on to the myCollection
     */
    @Override
    public void readFromFiles() {
        FileReadHandlerThread myCsvReadHandlerThread = new FileReadHandlerThread(csvFileHandler, myCollection);
        FileReadHandlerThread myJsonReadHandlerThread = new FileReadHandlerThread(jsonFileHandler, myCollection);
        FileReadHandlerThread myXmlReadHandlerThread = new FileReadHandlerThread(xmlFileHandler, myCollection);

        /**
         * Start running three threads each for csv, json and xml file reading and writing
         * on to myCollection
         */
        myXmlReadHandlerThread.start();
        myCsvReadHandlerThread.start();
        myJsonReadHandlerThread.start();

        /**
         * waiting for threads to finish
         */

        try {
            //System.out.println("Waiting for threads to finish");
            myCsvReadHandlerThread.join();
            myJsonReadHandlerThread.join();
            myXmlReadHandlerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Main Thread Interrupted");
        }
    }

    @Override
    public void writeToFiles() {
        FileWriteHandlerThread myCsvFileWriteHandlerThread = new FileWriteHandlerThread(csvFileHandler, myCollection);
        FileWriteHandlerThread myXmlFileWriteHandlerThread = new FileWriteHandlerThread(xmlFileHandler, myCollection);
        FileWriteHandlerThread myJsonFileWriteHandlerThread = new FileWriteHandlerThread(jsonFileHandler, myCollection);

        /**
         * Start running three threads each for writing csv, xml and json by reading from myCollection
         */

        myCsvFileWriteHandlerThread.start();
        myJsonFileWriteHandlerThread.start();
        myXmlFileWriteHandlerThread.start();

        try {
            myCsvFileWriteHandlerThread.join();
            myJsonFileWriteHandlerThread.join();
            myXmlFileWriteHandlerThread.join();
            //System.out.println("Waiting for threads to finish");
        } catch (InterruptedException e){
            e.printStackTrace();
            System.out.println("Main Thread Interrupted");
        }
    }

    /**
     * Check the read counter and myCollection size to confirm it is indeed TOTAL_SIZE
     * @return
     */
    @Override
    public boolean checkCountersAfterRead() {
        return (myCollection.getEmployeeList().size() == TOTAL_SIZE) && (myCollection.getWriteCounter() == TOTAL_SIZE);
    }


    /**
     * Method to check no of records in each file is indeed FILE_SIZE and there are no duplicate records in any of the files
     * @return
     */
    @Override
    public boolean checkDuplicatesAndRecordCount() {
        return myCollection.getReadCounter() == 300;
    }


    /**
     * Main function to test
     * @param args
     */
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        MyController myController = new MyController();

        //Read from files
        myController.readFromFiles();

        //To check the contents of myCollection
        //System.out.println(myController.getMyCollection().getEmployeeList());

        //Size and Counter Check
        if(myController.checkCountersAfterRead()) {
            System.out.println("READING: from all 300 records from different input streams using thread COMPLETED !");
            System.out.println("Size and Counter checks Successful");
        }
        else
            System.out.println("Size and Counter checks Failed");

        //Sort MyCollection
        myController.myCollection.sortMyCollection();

        //Write to files
        myController.writeToFiles();

        //Some check after writing to files

        if(myController.checkDuplicatesAndRecordCount()) {
            System.out.println("WRITING: from all 300 records into different output formats COMPLETED !");
            System.out.println("No duplicates and read all records successfully");
        }
        else {
            System.out.println("Read Counter Check Failed");
        }

    }
}
