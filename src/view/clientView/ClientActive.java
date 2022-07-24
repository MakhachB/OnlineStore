package view.clientView;

import connection.ConnectionManager;
import person.Client;
import product.Product;
import program.Main;
import view.MainView;
import view.productsView.ProductsActive;

import java.util.Locale;

public class ClientActive {

    public static Client activeClient;

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
                System.out.printf("Welcome back %s %s!\n",
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
        if (full_name.length != 2 || !full_name[0].matches("^[\\p{Alpha}\\s]+$")) {
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
        if (email.equals("none")) email = "unknown-" + phone;
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
        int res = ConnectionManager.regClient(full_name[0], full_name[1], phone, email, country, city, password);
        if (res == -2) {
            System.out.println("Person with this phone / email is exists");
            System.out.println("If you had account - sign in (1) or exit (exit)");
            if (Main.getInputInt() == 1) signClient();
        } else if (res != 1) {
            System.out.println("Sorry, database is not working");
            System.out.println("Try later...");
        } else {
            MainView.activePerson = activeClient;
            System.out.println("Welcome to our app!");
            choiceActive();
        }
    }

    private static void choiceActive() {
        System.out.println("Choice action (Type required number)");
        System.out.println("(1)Print my info");
        System.out.println("(2)Make an order");

        int choice = Main.getInputInt();
        if (choice == 1) printClientInfo();
        else if (choice == 2) makeOrder();
        else {
            System.out.println("Input '1' or '2'");
            choiceActive();
        }
    }

    private static void printClientInfo() {
        System.out.println("==========================================");
        System.out.printf("Full name: %s %s\nPhone: %s\nEmail: %s\nCountry: %s\nCity: %s\n",
                activeClient.getLast_name(),
                activeClient.getFirst_name(),
                activeClient.getPhone(),
                activeClient.getEmail().startsWith("unknown-") ? "none" : activeClient.getEmail(),
                activeClient.getCountry(),
                activeClient.getCity());
        System.out.println("==========================================");
        choiceActive();
    }

    private static void makeOrder() {
        System.out.println("Choice product to buy (print number)");
        ProductsActive.printProductsInfo();
        int id = Main.getInputInt();
        if (ProductsActive.getProductById(id) == null) {
            System.out.println("Theres no products with this id. Retry");
            makeOrder();
        } else {
            selectCount(ProductsActive.getProductById(id));
        }

    }

    private static void selectCount(Product product) {
        System.out.println("Print count of products");
        int count = Main.getInputInt();
        if (count > product.getUnits_in_stock()) {
            System.out.println("We don't have this count of products. Retry");
            makeOrder();
        } else {
            askAgreement(product, count);
        }
    }

    private static void askAgreement(Product product, int count) {
        System.out.printf("Do you agree to buy %d goods of %s with a price of %.2f$\n",
                count, product.getProduct_name(), product.getUnit_price() * count);
        System.out.println("Yes (1) / No (0)");
        int answer = Main.getInputInt();
        if (answer == 0) {
            System.out.println("Deal canceled.");
            choiceActive();
        } else if (answer == 1) {
            buyProductInDB(product, count);
        } else {
            System.out.println("Print '1' or '0'");
            askAgreement(product, count);
        }
    }

    private static void buyProductInDB(Product active, int count) {
        if (ConnectionManager.buyProduct(active, count)) {
            System.out.println("Congrats on your purchase, good luck!");
            System.out.println("Print 'exit' to exit app");
            choiceActive();
        } else {
            System.out.println("Error in our database. Sorry try later");
        }
    }


}
