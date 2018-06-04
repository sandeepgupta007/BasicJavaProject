package com.coviam.project.collection;

import com.coviam.project.model.Employee;
import com.coviam.project.utilities.EmployeeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*
* @author: Megha Moondra
* My Collection class to read Employee data for 300 entries
* readCounter and writeCounter for count of files read and write
*
*/


public class MyCollection implements MyCollectionInterface{

    // READCOUNTER to store the count of Employee data read from the file.
    private static int readCounter;

    // WRITECOUNTER to store the count of Employee data written in the file.
    private static int writeCounter;

    private static List<Employee> employeeList = new ArrayList();

    private static volatile MyCollection myCollection = null;

    public static void sortMyCollection() {
        Collections.sort(employeeList, new EmployeeComparator());
    }

    public static void addEmployee(Employee emp) {
        synchronized (MyCollection.class) {
            employeeList.add(writeCounter, emp);
            writeCounter++;

        }
    }

    private MyCollection() {
    }

    public static int getReadCounter() {
        return readCounter;
    }

    public static void setReadCounter(int readCounter) {
        MyCollection.readCounter = readCounter;
    }

    public static int getWriteCounter() {
        return writeCounter;
    }

    public static void setWriteCounter(int writeCounter) {
        MyCollection.writeCounter = writeCounter;
    }

    public static List<Employee> getEmployeeList() {
        return employeeList;
    }

    public static void setEmployeeList(List<Employee> employeeList) {
        MyCollection.employeeList = employeeList;
    }

    public static Employee getEmployee() {
        Employee emp;
        synchronized (MyCollection.class) {
            readCounter++;
            return employeeList.get(readCounter - 1);
        }
    }

    public static MyCollection getInstance() {
        if(null == myCollection) {
            synchronized (MyCollection.class) {
                if(null == myCollection)
                    myCollection = new MyCollection();
            }
        }
        return  myCollection;
    }
}
