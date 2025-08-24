package com.wenderfabiano.wpm.actions.compileAction;

import com.wenderfabiano.wpm.actions.Action;

import java.io.File;

public class CompileAction implements Action {
    public void execute() {
        try {
            //Clean the previous build!
            clear();

            String command = "$javaFiles = Get-ChildItem -Recurse -Filter *.java -Path src | Select-Object -ExpandProperty FullName; javac -cp \"./libs/*\" -d ./dist/classes/java $javaFiles";
            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", command);
            processBuilder.directory(new File("./"));

            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Error compiling application!");
            }

            //Move resources to build!
            moveResourcesToBuild();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void clear() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", "if (Test-Path \"./dist\") { Remove-Item \"./dist\" -Recurse -Force }\n");
            processBuilder.directory(new File("./"));

            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Error deleting previous build!");
            }
        } catch (Exception e) {
            System.out.println("Error deleting previous build!");
        }
    }

    private void moveResourcesToBuild() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", "New-Item -Path \"./dist/classes/resources\" -ItemType Directory -Force; Copy-Item -Path \"./src/main/resources/*\" -Destination \"./dist/classes/resources\" -Recurse -Force\n");
            processBuilder.directory(new File("./"));

            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Error moving resources to build!");
            }
        } catch (Exception e) {
            System.out.println("Error moving resources to build!");
        }
    }
}
