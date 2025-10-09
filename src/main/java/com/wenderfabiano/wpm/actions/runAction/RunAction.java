package com.wenderfabiano.wpm.actions.runAction;

import com.wenderfabiano.wpm.actions.Action;
import com.wenderfabiano.wpm.actions.compileAction.CompileAction;
import com.wenderfabiano.wpm.actions.runAction.exceptions.ConfigFileNotFoundException;
import com.wenderfabiano.wpm.actions.runAction.exceptions.MainClassNotFoundException;
import com.wenderfabiano.wpm.utils.createProcess.CreateProcess;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class RunAction implements Action {
    public void execute() {
        try {
            CompileAction compileAction = new CompileAction();
            compileAction.execute();

            Document document = getConfigFile();
            NodeList nodes = document.getElementsByTagName("mainClass");
            if (nodes.item(0) == null) {
                throw new MainClassNotFoundException("Error: Main class not found.");
            }
            String mainClass = nodes.item(0).getTextContent();

            String command = "java -cp '.;../resources;' " + mainClass;
            ProcessBuilder processBuilder = new CreateProcess().create(
                    "./dist/classes/java",
                    "powershell.exe",
                    "-Command",
                    command
            );

            Process process = processBuilder.start();
            process.waitFor();
        } catch (MainClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Failed to run program.");
        }
    }

    private Document getConfigFile() {
        try {
            File wpmConfigFile = new File("wpm-config.xml");
            if (!wpmConfigFile.exists()) {
                throw new ConfigFileNotFoundException("The configuration file 'wpm-config.xml' was not found");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(wpmConfigFile);

            return document;
        } catch (ConfigFileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException("Error reading configuration file.");
        }
    }
}
