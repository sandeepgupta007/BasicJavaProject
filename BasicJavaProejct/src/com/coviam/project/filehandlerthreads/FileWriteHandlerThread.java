/**
 * @author sreerajr
 * @package com.coviam.project.filehandlerthreads
 * @project untitled
 */

package com.coviam.project.filehandlerthreads;

import com.coviam.project.model.Employee;
import com.coviam.project.collection.MyCollection;
import com.coviam.project.filehandlers.MyFileHandler;

import javax.xml.transform.TransformerException;

public class FileWriteHandlerThread extends Thread {
    private MyFileHandler myFileHandler;
    private MyCollection myCollection;


    public FileWriteHandlerThread(MyFileHandler myFileHandler, MyCollection myCollection) {
        this.myFileHandler = myFileHandler;
        this.myCollection = myCollection;
    }

    @Override
    public void run() {
        /**
         * Get one record from myCollection
         */
        for(int counter = 0; counter < 100; counter++) {
            Employee employee = myCollection.getEmployee();
            myFileHandler.write(employee);
        }

        try {
            myFileHandler.closeAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
