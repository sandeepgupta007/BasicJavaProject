/**
 * @author sreerajr
 * @package com.coviam.project.filehandlerthreads
 * @project untitled
 */

package com.coviam.project.filehandlerthreads;

import com.coviam.project.model.Employee;
import com.coviam.project.collection.MyCollection;
import com.coviam.project.filehandlers.MyFileHandler;
import com.coviam.project.utilities.UtilityFunctions;

public class FileReadHandlerThread extends Thread {

    private MyFileHandler myFileHandler;
    private MyCollection myCollection;


    public FileReadHandlerThread(MyFileHandler myFileHandler, MyCollection myCollection) {
        this.myFileHandler = myFileHandler;
        this.myCollection = myCollection;
    }

    @Override
    public void run() {
        /**
         * Read the file records one by one until eof is reached
         * eof expected to return null from file handlers
         */
        Employee employee = null;
        while (null != (employee = UtilityFunctions.readFromFileHandler(myFileHandler))) {
            /**
             * Adding to myCollection should be mutually exclusive for thread safety
             */
            myCollection.addEmployee(employee);
        }
    }
}
