package com.wenderfabiano.wpm.actions.compileAction;

import com.wenderfabiano.wpm.actions.Action;
import com.wenderfabiano.wpm.actions.compileAction.exceptions.ClearPreviousBuildException;
import com.wenderfabiano.wpm.actions.compileAction.exceptions.MoveResourcesToBuildException;
import com.wenderfabiano.wpm.actions.compileAction.exceptions.CompileException;
import com.wenderfabiano.wpm.utils.createProcess.CreateProcess;

public class CompileAction implements Action {
    public void execute() {
        try {
            clear();
            compile();
            moveResourcesToBuild();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void clear() {
        try {
            String command = "if (Test-Path \"./dist\") { Remove-Item \"./dist\" -Recurse -Force > $null }";
            ProcessBuilder processBuilder = new CreateProcess().create(
                    "./",
                    "powershell.exe",
                    "-Command",
                    command
            );

            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            throw new ClearPreviousBuildException("Error deleting previous build!");
        }
    }

    private void compile() {
        try {
            String command = "$javaFiles = Get-ChildItem -Recurse -Filter *.java -Path src | Select-Object -ExpandProperty FullName; javac -cp \"./libs/*\" -d ./dist/classes/java $javaFiles";
            ProcessBuilder processBuilder = new CreateProcess().create(
                    "./",
                    "powershell.exe",
                    "-Command",
                    command
            );

            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            throw new CompileException("Error compiling application!");
        }
    }

    private void moveResourcesToBuild() {
        try {
            String command = "New-Item -Path \"./dist/classes/resources\" -ItemType Directory -Force > $null; Copy-Item -Path \"./src/main/resources/*\" -Destination \"./dist/classes/resources\" -Recurse -Force > $null";
            ProcessBuilder processBuilder = new CreateProcess().create(
                    "./",
                    "powershell.exe",
                    "-Command",
                    command
            );

            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            throw new MoveResourcesToBuildException("Error moving resources to build!");
        }
    }
}
