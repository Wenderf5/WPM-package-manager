package com.wenderfabiano.wpm.actions;

public class HelpAction implements Action {
    public void execute(){
        System.out.println("");
        System.out.println("------------Hello, welcome to wpm!------------");
        System.out.println("");
        System.out.println("Use the flags below:");
        System.out.println("");
        System.out.println("- Use 'run' flag to compile and execute your application.");
        System.out.println("- Use 'compile' flag to compile all '.java' files in your main package and sub packages to '.class' files, all compiled files go to './dist/classes' directory.");
        System.out.println("- Use 'package' flag to compile and make a '.jar' file of your application.");
        System.out.println("");
        System.out.println("Thanks for use wpm!");
        System.out.println("");
    }
}
