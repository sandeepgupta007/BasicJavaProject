/**
 * @author sreerajr
 * @package com.coviam.project.utilities
 * @project untitled
 */

package com.coviam.project.utilities;

import com.coviam.project.model.Employee;
import com.coviam.project.filehandlers.MyFileHandler;

import java.text.ParseException;

public class UtilityFunctions {

    public static Employee readFromFileHandler(MyFileHandler myFileHandler) {
        Employee employee = null;
        try {
            employee = myFileHandler.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }
}
