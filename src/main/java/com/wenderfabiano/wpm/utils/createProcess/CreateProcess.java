package com.wenderfabiano.wpm.utils.createProcess;

import java.io.File;

public class CreateProcess {
    public ProcessBuilder create(String workingDirectory, String... command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.directory(new File(workingDirectory));

        return processBuilder;
    }
}
