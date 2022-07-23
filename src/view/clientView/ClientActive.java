package view.clientView;

import connection.ConnectionManager;
import person.Client;
import program.Main;
import view.MainView;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientActive {

    static Client activeClient;

    public static void initClient() {
        System.out.println("Sign in (1) / Register (2) ?");
        int type = Main.getInputInt();
        if (type == 1) signClient();
        else if (type == 2) registerClient();
        else {
            System.out.println("Input '1' or '2'");
            initClient();
        }
    }

    private static void signClient() {
        System.out.println("Input phone number (+123456789):");
        String clientPhone = Main.getInputString();
        if (clientPhone.equals("reg")) {
            registerClient();
        } else {
            System.out.println("Input password :");
            String clientPass = Main.getInputString();

            if (MainView.signClient(clientPhone, clientPass)) {
                activeClient = (Client) MainView.activePerson;
                System.out.printf("Welcome back %s %s!",
                        activeClient.getLast_name(),
                        activeClient.getFirst_name());
                choiceActive();
            } else {
                System.out.println("Client with data is not exists. Retry... or Register ('reg')");
                signClient();
            }
        }
    }

    private static void registerClient() {
        System.out.println("To register please follow the instruction:");
        System.out.println("1/6 Enter your first and last name (like 'Andrew Johns')");
        String[] full_name = Main.getInputString().split("\\s+");
        if (full_name.length != 2) {
            System.out.println("Please enter your correct name (Lastname FirstName)");
            registerClient();
            return;
        }
        System.out.println("2/6 Enter your phone (+89342438582)");
        String phone = Main.getInputString();
        if (!phone.matches("^[\\d+]+$")) {
            System.out.println("Please enter correct phone.");
            registerClient();
            return;
        }
        System.out.println("3/6 (Unnecessary) Enter your email (example@mail.com)");
        System.out.println("Or skip ('none')");
        String email = Main.getInputString();
        if (email.equals("none"))  email = null;
        else if (!email.matches("^.+@.+\\..+$")) {
            System.out.println("Please enter correct email.");
            registerClient();
            return;
        }
        System.out.println("4/6 Enter your country");
        String country = Main.getInputString();
        String[] locales = Locale.getISOCountries();
        boolean isExists = false;

//       ----------------- List of Countries in English ----------------------
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
//            System.out.println("Country Code = " + obj.getCountry()
//                    + ", Country Name = " + obj.getDisplayCountry(Locale.ENGLISH));
            if (country.equals(obj.getDisplayCountry(Locale.ENGLISH))) isExists = true;
        }

        if (!isExists) {
            System.out.println("Enter correct country");
            registerClient();
            return;
        }
        System.out.println("5/6 Enter your city");
        String city = Main.getInputString();
        if (city.length() < 2 || city.length() > 64) {
            System.out.println("Enter correct city");
            registerClient();
            return;
        }
        System.out.println("6/6 And enter your password (between 4 and 20 characters)");
        String password = Main.getInputString();
        if (password.length() < 4 || password.length() > 20) {
            System.out.println("Think of the correct password");
            registerClient();
            return;
        }
        Client client = new Client(full_name[0], full_name[1], phone, email, country, city, password);
        int res = ConnectionManager.regClient(full_name[0], full_name[1], phone, email, country, city, password);
        if (res == -2) {
            System.out.println("Person with this phone / email is exists");
            System.out.println("If you had account - sign in (1) or exit (exit)");
            if (Main.getInputInt() == 1) signClient();
        }
        else if (res != 1) {
            System.out.println("Sorry, database is not working");
            System.out.println("Try later...");
        } else {
            MainView.activePerson = client;
            activeClient = client;
            System.out.println("Welcome to our app!");
            choiceActive();
        }
    }

    private static void choiceActive() {
    }


}
