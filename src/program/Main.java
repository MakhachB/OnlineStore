package program;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    //  --------------------- Db initialize --------------------------
    static {
//        InitDB.init();
    }

    public static void main(String[] args) {
        printGreetings();

    }

    private static void printGreetings() {
        System.out.println("Welcome to our app!");
        System.out.println("To exit app print 'exit'");
        System.out.println("To continue answer the question:");
        System.out.println("You're client (1) or employee (2) ?");
    }
}
