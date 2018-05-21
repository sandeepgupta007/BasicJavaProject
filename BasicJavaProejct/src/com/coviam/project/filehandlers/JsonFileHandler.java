/** @author: sandeepGupta
 *
 * Used JSON simple jar library to parse the Json file, If error persist after importing the
 * library try checking the JSON parse has been imported from correct library.
 *
* */

package com.coviam.project.filehandlers;

import com.coviam.project.model.Employee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class JsonFileHandler implements MyFileHandler{

    /**
     * readCount Variable to store the Counter from the file
     */
    private static Integer readCount = 0;
    private JSONArray jsonArray;
    private JSONArray writeArray;

    /**
    *  Constructer
    * */
    public JsonFileHandler(){
        JSONParser parser = new JSONParser();
        writeArray = new JSONArray();
        try {
            jsonArray = (JSONArray) parser.parse(new FileReader("/Users/sandeepgupta/Downloads/untitled/src/com/coviam/project/employee.json"));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public Employee read() {
        if (readCount < jsonArray.size()) {
            JSONObject employedetails = (JSONObject) jsonArray.get(readCount);
            String firstName = (String) employedetails.get("firstName");
            String lastName = (String) employedetails.get("lastName");
            Date dateOfBirth = null;
            try {
                dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(employedetails.get("dateOfBirth").toString());
            } catch (ParseException e) {
                dateOfBirth = new Date();
                System.out.println("Sorry, I couldn't parse the date");
            }
            double experience = Double.parseDouble(String.valueOf(employedetails.get("experience")));
            readCount += 1;
            return new Employee(firstName, lastName, dateOfBirth, experience);
        }
        return null;
    }

    @Override
    public void write(Employee employee) {
        JSONObject writeObject = new JSONObject();
        writeObject.put("firstName", employee.getFirstName());
        writeObject.put("lastName", employee.getLastName());
        writeObject.put("dateOfBirth", employee.getDateOfBirth());
        writeObject.put("experience", employee.getExperience());
        writeArray.add(writeObject);
    }

    @Override
    public void closeAll() throws IOException {
        FileWriter file = new FileWriter("/Users/sandeepgupta/Downloads/untitled/src/com/coviam/project/output.json");
        file.write(writeArray.toJSONString());
        file.flush();
    }
}