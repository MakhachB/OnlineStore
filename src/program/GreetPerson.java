package program;

import person.Employee;
import view.MainView;
import view.clientView.ClientActive;
import view.employeeView.EmployeeActive;

public final class GreetPerson {

    public static void printGreetings() {
        System.out.println("Welcome to our app!");
        System.out.println("To exit app print 'exit'");
        System.out.println("To continue answer the question:");
        System.out.println("You're client (1) or employee (2) ?");

        int type = Main.getInputInt();
        if (type == 1) ClientActive.initClient();
        else if (type == 2) EmployeeActive.signEmployee();
        else {
            System.out.println("Input '1' or '2'");
            printGreetings();
        }
    }





}
