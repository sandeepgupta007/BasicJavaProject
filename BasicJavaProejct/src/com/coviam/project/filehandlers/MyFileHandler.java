/**
 * @author sreerajr
 * @package com.coviam.project.filehandlers
 * @project untitled
 */

package com.coviam.project.filehandlers;

import com.coviam.project.model.Employee;

public interface MyFileHandler {
    public Employee read() throws Exception;
    public void write(Employee employee);
    public void closeAll() throws Exception;
}
