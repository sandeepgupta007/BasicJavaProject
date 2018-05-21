/**
 * @author sreerajr
 * @package com.coviam.project.utilities
 * @project untitled
 */

package com.coviam.project.utilities;

import com.coviam.project.model.Employee;

import java.util.Comparator;

public class EmployeeComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getFirstName().compareTo(o2.getFirstName());
    }
}
