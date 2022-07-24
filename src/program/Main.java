package program;

import connection.InitDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Main {

    private static final String EXIT_COMMAND = "exit";

    private static final BufferedReader CONSOLE_READER = new BufferedReader(new InputStreamReader(System.in));

    //  --------------------- Db initialize --------------------------
    static {
//        InitDB.init();
    }

    public static void main(String[] args) {
        GreetPerson.printGreetings();
    }

    public static String getInputString() {
        try {
            String input = CONSOLE_READER.readLine();
            if (input.equals(EXIT_COMMAND)) {
                System.out.println("Shutdown...");
                System.exit(-1);
            }
            return input;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public static int getInputInt () {
            try {
                String input = CONSOLE_READER.readLine();
                if (input.equals(EXIT_COMMAND)) {
                    System.out.println("Shutdown...");
                    System.exit(-1);
                }
                return Integer.parseInt(input);

            } catch (NumberFormatException ignored) {
                return -1;
            }
            catch (IOException e) {
                System.out.println("Incorrect Input");
//                throw new RuntimeException(e);
                return -1;
            }
        }


    }
