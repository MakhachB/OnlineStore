package view.employeeView;

import connection.ConnectionManager;
import person.Employee;
import program.Main;
import view.MainView;
import view.productsView.ProductsActive;

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
            System.out.printf("Welcome back to work %s %s!\n",
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
        System.out.println("Choice action (Type required number)");
        System.out.println("(1)Print my info");
        System.out.println("(2)Show all products");

        int choice = Main.getInputInt();
        if (choice == 1) printEmployeeInfo();
        else if (choice == 2) showProducts();
        else {
            System.out.println("Input '1' or '2'");
            choiceActive();
        }
    }

    private static void printEmployeeInfo() {
        System.out.println("==========================================");
        System.out.printf("Full name: %s %s\nPhone: %s\nEmail: %s\nCountry: %s\nCity: %s\nTitle : %s\n",
                activeEmployee.getLast_name(),
                activeEmployee.getFirst_name(),
                activeEmployee.getPhone(),
                activeEmployee.getEmail().startsWith("unknown-") ? "none" : activeEmployee.getEmail(),
                activeEmployee.getCountry(),
                activeEmployee.getCity(),
                activeEmployee.getTitle());
        System.out.println("==========================================");
        choiceActive();
    }

    private static void showProducts() {
        System.out.println("======================================");
        ProductsActive.printProductsInfo();
        System.out.println("======================================");
        choiceActive();
    }


}
