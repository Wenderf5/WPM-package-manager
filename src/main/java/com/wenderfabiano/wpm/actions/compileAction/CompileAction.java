package com.wenderfabiano.wpm.actions.compileAction;

import com.wenderfabiano.wpm.actions.Action;

import java.io.File;

public class CompileAction implements Action {
    public void execute() {
        try {
            String command = "$javaFiles = Get-ChildItem -Recurse -Filter *.java -Path src | Select-Object -ExpandProperty FullName; javac -cp \"./libs/*\" -d dist/classes $javaFiles";
            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", command);
            processBuilder.directory(new File("./"));

            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0){
                System.out.println("Error compiling application!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
