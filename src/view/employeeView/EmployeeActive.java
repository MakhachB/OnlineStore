package view.employeeView;

import connection.ConnectionManager;
import person.Employee;
import program.Main;
import view.MainView;

public class EmployeeActive {

    static Employee activeEmployee;

    /**
     * the employee can only enter, because it is assumed that they will be registered in the database
     */
    public static void signEmployee() {
        System.out.println("Input phone number (+123456789):");
        String clientPhone = Main.getInputString();
        System.out.println("Input password :");
        String clientPass = Main.getInputString();

        if (MainView.signEmployee(clientPhone, clientPass)) {
            activeEmployee = (Employee) MainView.activePerson;
            System.out.printf("Welcome back to work %s %s!",
                    activeEmployee.getLast_name(),
                    activeEmployee.getFirst_name());
            choiceActive();
        }
        else {
            System.out.println("Employee with data is not exists. Retry...");
            signEmployee();
        }

    }

    private static void choiceActive() {

    }


}
