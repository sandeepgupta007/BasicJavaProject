/** @author: shriya
* */

package com.coviam.project.filehandlers;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.coviam.project.model.Employee;

import javax.xml.transform.TransformerException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVFileHandler implements MyFileHandler {
    CSVReader reader;
    CSVWriter csvWriter;

    public CSVFileHandler() throws IOException {
        reader = new CSVReader(new FileReader("/Users/sandeepgupta/Downloads/untitled/src/com/coviam/project/employee.csv"), ',');
        csvWriter = new CSVWriter(new FileWriter("/Users/sandeepgupta/Downloads/untitled/src/com/coviam/project/output.csv"));
    }

    @Override
    public Employee read() throws ParseException, IOException {
        Employee emp = null;
        String[] record = null;
        record = reader.readNext();
        if (record != null) {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(record[2]);
            double experience = Double.parseDouble(record[3]);
            emp = new Employee(record[0], record[1], date1, experience);
        }
        return emp;

    }

    @Override
    public void write(Employee employee) {
        csvWriter.writeNext(new String[]{employee.getFirstName(), employee.getLastName(), employee.getDateOfBirth().toString(), employee.getExperience().toString()});
    }

    @Override
    public void closeAll() throws IOException {
        reader.close();
        csvWriter.close();
    }

}


