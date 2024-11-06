/**
 * The UI class is responsible for handling user input and output, directing commands to the `Manager` class.
 *
 * - **Controller**: This class receives input from the user and translates it into actions, but it delegates actual business logic to the `Manager`.
 * - **Low Coupling**: This class relies only on the `Manager` class for processing commands, maintaining a simple interaction model with other components.
 */

package org.example;

import java.util.Scanner;


/**
 * The type Ui.
 */
public class UI
{
    private static Manager Manager;

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args)
    {

        Manager = new Manager();

        Scanner scanner = new Scanner(System.in);
        String input = "";

        System.out.print(">");

        // Loop until the user types "exit"
        while (!input.equalsIgnoreCase("exit")) 
        {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) 
            {
                break;
            }

            processInput(input);
        }

        System.out.println("Exiting the application.");
        scanner.close();
    }

    private static void processInput(final String input) 
    {
        String[] ins = input.split("\\s+");

        switch (ins[0]) 
        {
            case "addBook":
                System.out.println(Manager.addBook(ins[1]));
                break;

            case "addCopies":
                System.out.println(Manager.addCopies(ins[1], Integer.parseInt(ins[2])));
                break;

            case "addReader":
                System.out.println(Manager.addReader(Integer.parseInt(ins[1])));
                break;

            case "lendBook":
                System.out.println(Manager.lendBook(Integer.parseInt(ins[1]), ins[2]));
                break;

            case "displayBooks":
                System.out.println(Manager.displayBooks());
                break;

            case "displayReaders":
                System.out.println(Manager.displayReaders());
                break;

            default:
                System.out.println("Command not found, "
                        + "Try: addBook <title>, " +
                        "addCopies <title> <count>, " +
                        "addReader <id>, " +
                        "lendBook <readerID> <title>, " +
                        "displayBooks, " +
                        "displayReaders");
                break;
        }
        System.out.print(">");
    }
}
