/**
 * @author: payal
 * */

package com.coviam.project.filehandlers;


import com.coviam.project.utilities.CommonVar;
import com.coviam.project.model.Employee;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class XMLFileHandler implements MyFileHandler {
    public static final String XML_FILE_PATH = "/Users/sandeepgupta/Downloads/untitled/src/com/coviam/project/output.xml";
    private Document writeXmlDoc;
    private Element mainRootElement;
    private NodeList xmlNodeList;
    private static int RECORD;

    public XMLFileHandler() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder = null;
        icBuilder = icFactory.newDocumentBuilder();
        File fXmlFile = new File("/Users/sandeepgupta/Downloads/untitled/src/com/coviam/project/employee.xml");
        Document readXmlDoc = icBuilder.parse(fXmlFile);
        readXmlDoc.getDocumentElement().normalize();
        xmlNodeList = readXmlDoc.getElementsByTagName("employee");
        writeXmlDoc = icBuilder.newDocument();
        mainRootElement = writeXmlDoc.createElement("employees");
        writeXmlDoc.appendChild(mainRootElement);
    }

    @Override
    public Employee read() throws ParseException {
        Employee employee = null;
        if (RECORD < xmlNodeList.getLength()) {
            Node nNode = xmlNodeList.item(RECORD);
            Element eElement = (Element) nNode;
            employee = new Employee(eElement.getElementsByTagName(CommonVar.FIRSTNAME).item(0).getTextContent(), eElement.getElementsByTagName(CommonVar.LASTNAME).item(0).getTextContent(), new SimpleDateFormat("dd/mm/yyyy").parse(eElement.getElementsByTagName(CommonVar.DATE_OF_BIRTH).item(0).getTextContent()), Double.parseDouble(eElement.getElementsByTagName(CommonVar.EXPERIENCE).item(0).getTextContent()));
            RECORD++;
        }

        return employee;
    }

    @Override
    public void write(Employee employee) {
        mainRootElement.appendChild(getEmployee(writeXmlDoc, employee.getFirstName(), employee.getLastName(), employee.getDateOfBirth().toString(), String.valueOf(employee.getExperience())));
    }

    @Override
    public void closeAll() throws TransformerException {
        // output DOM XML to console
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(writeXmlDoc);
        StreamResult console = new StreamResult(new File(XML_FILE_PATH));
        transformer.transform(source, console);
    }

    private static Node getEmployee(Document doc, String firstname, String lastname, String dateOfBirth, String experience) {
        Element employee = doc.createElement("employee");
        employee.appendChild(getEmployeeElements(doc, employee, CommonVar.FIRSTNAME, firstname));
        employee.appendChild(getEmployeeElements(doc, employee, CommonVar.LASTNAME, lastname));
        employee.appendChild(getEmployeeElements(doc, employee, CommonVar.DATE_OF_BIRTH, dateOfBirth));
        employee.appendChild(getEmployeeElements(doc, employee, CommonVar.EXPERIENCE, experience));
        return employee;
    }

    // utility method to create text node
    private static Node getEmployeeElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}