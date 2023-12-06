import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

/**
 * This method is the main method for all the system processes
 * for this question and answer system, where it takes a file,
 * reads the contents and inputs it into a tree and allows the
 * user to enter a questionnaire.
 *
 * SPECIAL ADDITIONAL FUNCTION : History Record
 * There is an additional past history option inside the menu that tells you
 * of everything the user has either attempted or has completed,
 * as well as how many total actions they've performed in decreasing
 * order.
 */
public class TreeDriver {
    private static Tree tree = new Tree();
    private static HistoryStack history = new HistoryStack();
    public static void main (String [] args) {
        Scanner input = new Scanner(System.in);
        String label, message, prompt, option, filePath;
        int actions = 0;
        do {
            System.out.println("Menu:");
            System.out.println("(L) Load File");
            System.out.println("(H) Start Help Session");
            System.out.println("(T) Traverse in pre-order");
            System.out.println("(P) Past History");
            System.out.println("(Q) Quit");
            option = input.nextLine();
            switch (option) {
                case "L":
                    try {
                        HistoryStack check = (HistoryStack) history.clone();
                        for (int i = 0; i < actions; i++) {
                            if (check.pop().equals("Added a new file")) {
                                throw new IllegalStateException();
                            }
                        }
                        System.out.println("Please enter the file path!");
                        filePath = input.nextLine();
                        File file = new File(filePath);
                        Scanner fileScan = new Scanner(file);
                        label = skipEmptyLine(fileScan);
                        prompt = skipEmptyLine(fileScan);
                        message = skipEmptyLine(fileScan);
                        String temp = skipEmptyLine(fileScan);
                        int numChild = Integer.parseInt
                                (temp.substring
                                        (temp.indexOf(" ") + 1));
                        String labelToAdd = temp.substring
                                (0,temp.indexOf(" "));
                        tree.addNode(label,prompt,message,null);

                        while (fileScan.hasNextLine()) {
                            for (int i = 0; i < numChild; i++) {
                                label = skipEmptyLine(fileScan);
                                prompt = skipEmptyLine(fileScan);
                                message = skipEmptyLine(fileScan);
                                tree.addNode(label,prompt,message,labelToAdd);
                            }
                            if (fileScan.hasNextLine()) {
                                temp = skipEmptyLine(fileScan);
                                labelToAdd = temp.substring
                                        (0,temp.indexOf(" "));
                                numChild = Integer.parseInt
                                        ((temp.substring(temp.indexOf
                                                (" ") + 1)).trim());
                            } else {
                                break;
                            }
                        }
                        System.out.println("Successfully loaded the " +
                                "file into the system!");
                        history.push("Added a new file");
                        actions++;
                    } catch (FileNotFoundException e) {
                        System.out.println("That file path is " +
                                "incorrect!");
                        history.push("Attempted file input," +
                                " file path was incorrect.");
                        actions++;
                    } catch (NumberFormatException e) {
                        System.out.println("Something inside of " +
                                "the file was not created" +
                                " correctly!");
                        history.push("Attempted file input," +
                                " file is in incorrect format");
                        actions++;
                    } catch (IllegalStateException e) {
                        System.out.println("File has already been added!");
                        System.out.println("Please restart the program" +
                                " to insert new file.");
                        history.push("User attempted to input" +
                                "an extra file, returned to menu.");
                        actions++;
                    }
                    System.out.println();
                    break;
                case "H":
                    try {
                        if (tree.getRoot() == null) {
                            throw new IllegalStateException();
                        }
                        tree.beginSession();
                        history.push("Requested a help session");
                        actions++;
                    } catch (InputMismatchException e) {
                        System.out.println("Please input a valid input" +
                                " for the system to understand!");
                        history.push("Attempted questionnaire," +
                                " inputted incorrectly" +
                                " and returned to menu.");
                        actions++;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Wrong choice! Please choose " +
                                "a valid choice next time.");
                        history.push("User inputted incorrect " +
                                "choice for questionnaire," +
                                " returned to menu.");
                        actions++;
                    } catch (IllegalStateException e) {
                        System.out.println("Please input a file before" +
                                " trying to start questions!");
                        history.push("Attempted questionnaire," +
                                " no file input into system," +
                                " and returned to menu.");
                        actions++;
                    }
                    System.out.println();
                    break;
                case "T":
                    try {
                        if (tree.getRoot() == null) {
                            throw new IllegalStateException();
                        }
                        tree.getRoot().preOrder();
                        history.push("Requested a preorder" +
                                " format overview");
                        actions++;
                    } catch (IllegalStateException e) {
                        System.out.println("Please input a file before" +
                                " trying to print in preorder format!");
                        history.push("Attempted to print tree in" +
                                " preorder format, no file to print.");
                        actions++;
                    }
                    System.out.println();
                    break;
                case "P":
                    history.push("Requested a history check");
                    actions++;
                    HistoryStack temp = (HistoryStack) history.clone();
                    System.out.println("Your history:");
                    for (int i = actions; i > 0; i--) {
                        System.out.println("(" + (i) + ") " + temp.pop());
                    }
                    System.out.println();
                    System.out.println("Total Actions : " + actions);
                    System.out.println();
                    break;
                default:
                    if (option.compareTo("Q") != 0) {
                        System.out.println("Try again!" +
                                " Not a valid option!!!");
                        history.push("User inputted " +
                                "incorrect menu option.");
                        actions++;
                    } else {
                        System.out.println("Goodbye!");
                    }
                    break;
            }
        } while (option.compareTo("Q") != 0);
    }

    /**
     * This method is used when assigning variables, so it can be used for
     * creating a new node, skipping empty lines so the input file can be
     * read more clearly.
     * @param scan the scanner object being used to scan the file
     * @return the string of the next non-empty line of the input file
     */
    public static String skipEmptyLine(Scanner scan) {
        String line = scan.nextLine().trim();
        while (line.isEmpty()) {
            line = scan.nextLine().trim();
        }
        return line;
    }

}
