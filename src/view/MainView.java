package view;

import connection.ConnectionManager;
import person.AbstractPerson;

public class MainView {

    public static AbstractPerson activePerson;

    public static boolean signClient(String clientPhone, String clientPass) {
        //            System.out.println(activePerson.toString());
        return ConnectionManager.isSignedClient(clientPhone, clientPass);
    }
    public static boolean signEmployee(String employeePhone, String employeePass) {
        //            System.out.println(activePerson.toString());
        return ConnectionManager.isSignedEmployee(employeePhone, employeePass);
    }

}
