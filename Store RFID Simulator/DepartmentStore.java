import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is the main method class where it performs all the actions
 * inside a would be department store.
 */
public class DepartmentStore {
    private static ItemList linkedItems = new ItemList();

    /**
     * This method represents the menu of a department store system and
     * its methods inside a store, where it can register, remove,
     * checkout, etc.
     *
     * @param args the arguments inside the main method
     */
    public static void main(String[] args) {
        String name, rfidNumber, originalLocation, currentLocation;
        double price;
        Scanner input = new Scanner(System.in);
        String option;
        do {
            System.out.println("Menu : Select an option");
            System.out.println();
            System.out.println("(I) - Insert Item");
            System.out.println("(M) - Move an Item");
            System.out.println("(L) - List by Location");
            System.out.println("(P) - Print All Items");
            System.out.println("(O) - Checkout");
            System.out.println("(C) - Clean Store");
            System.out.println("(U) - Update Inventory System");
            System.out.println("(R) - Print by RFID Number");
            System.out.println("(Q) - Quit");

            option = input.nextLine();
            switch (option) {
                case "I":
                    try {
                        System.out.println("What is the name of the item?");
                        name = input.nextLine();
                        System.out.println("What is the RFID number of " +
                                "the item?");
                        rfidNumber = input.nextLine();
                        System.out.println("What will be the original " +
                                "location of the item?");
                        originalLocation = input.nextLine();
                        System.out.println("What is the price of the " +
                                "item? (A double value, decimals!)");
                        price = input.nextDouble();
                        input.nextLine();
                        linkedItems.insertInfo
                                (name, rfidNumber, price, originalLocation);
                        System.out.println
                                ("Successfully added a new item to the " +
                                        "list!");
                    } catch (InputMismatchException e) {
                        System.out.println
                                ("You've inputted the incorrect format for" +
                                        " the price of the item! " +
                                        "(Only doubles!)");
                        input.nextLine();
                    } catch (IncorrectRFIDException e) {
                        System.out.println
                                ("You've inputted an incorrect format for " +
                                        "an RFID number! " +
                                        "(Remember it's a fixed 9 " +
                                        "character length in hexadecimal" +
                                        " format!)");
                    } catch (IncorrectLocationException e) {
                        System.out.println
                                ("You've inputted an incorrect format " +
                                        "for an initial location! " +
                                        "(Remember it's a fixed 6 " +
                                        "character length in the following " +
                                        "format : sXXXXX ");
                    }
                    System.out.println();
                    break;
                case "M":
                    try {
                        System.out.println("What is the RFID number of " +
                                "the item you wish to move?");
                        rfidNumber = input.nextLine();
                        System.out.println("What was the source location?");
                        originalLocation = input.nextLine();
                        System.out.println("Where do you want to move " +
                                "this item?");
                        currentLocation = input.nextLine();
                        linkedItems.moveItem
                                (rfidNumber, originalLocation,
                                        currentLocation);
                        System.out.println("Successfully moved the " +
                                "requested item!");
                    } catch (IncorrectRFIDException e) {
                        System.out.println("You've inputted an incorrect " +
                                "format for an RFID number! " +
                                "(Remember it's a fixed 9 character " +
                                "length in hexadecimal format!)");
                    } catch (IncorrectLocationException e) {
                        System.out.println("You've inputted an incorrect " +
                                "format for an initial location! " +
                                "(Remember it's a fixed 6 character " +
                                "length in the following format : sXXXXX ");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Out is not a valid entry for " +
                                "movement!");
                    } catch (EmptyLinkedListException e) {
                        System.out.println("Can't move any items, list " +
                                "is empty!");
                    }
                    System.out.println();
                    break;
                case "L":
                    try {
                        System.out.println("Enter a location to find all " +
                                "items in the location");
                        currentLocation = input.nextLine();
                        linkedItems.printByLocation(currentLocation);
                        System.out.println("Successfully printed all items" +
                                " inside the location : " + currentLocation);
                    } catch (IncorrectLocationException e) {
                        System.out.println("That is not a valid location" +
                                " input!");
                    } catch (EmptyLinkedListException e) {
                        System.out.println("Can't print any items, " +
                                "list is empty!");
                    }
                    System.out.println();
                    break;
                case "P":
                    try {
                        System.out.println("Printing all items inside " +
                                "of the list...");
                        linkedItems.printAll();
                    } catch (EmptyLinkedListException e) {
                        System.out.println("No items to print!");
                    }
                    System.out.println();
                    break;
                case "O":
                    try {
                        System.out.println("Enter a cart number to check " +
                                "out items inside the cart!");
                        currentLocation = input.nextLine();
                        String priceFormat = String.format("%.2f",
                                linkedItems.checkOut(currentLocation));
                        System.out.println("The price of all the " +
                                "items in the cart is : $" + priceFormat);
                    } catch (IncorrectLocationException e) {
                        System.out.println("This is not an existing " +
                                "cart input!");
                    } catch (EmptyLinkedListException e) {
                        System.out.println("Can't check out, list is empty!");
                    }
                    System.out.println();
                    break;
                case "C":
                    try {
                        System.out.println("Cleaning store...");
                        linkedItems.cleanStore();
                        System.out.println("Successfully cleaned the " +
                                "store and moved items back to their " +
                                "original locations!");
                    } catch (IncorrectLocationException e) {
                        System.out.println("This really shouldn't happen " +
                                "at all, check code if it does");
                    } catch (EmptyLinkedListException e) {
                        System.out.println("Can't clean, list is " +
                                "empty!");
                    }
                    System.out.println();
                    break;
                case "U":
                    try {
                        System.out.println("Removing all purchased items" +
                                " and updating inventory...");
                        linkedItems.removeAllPurchased();
                        System.out.println("Successfully removed all" +
                                " items that have been checked out from" +
                                " the system!");
                    } catch (EmptyLinkedListException e) {
                        System.out.println("The list is empty!");
                    }
                    System.out.println();
                    break;
                case "R":
                    try {
                        System.out.println("What is the RFID number?");
                        rfidNumber = input.nextLine();
                        linkedItems.printRFID(rfidNumber);
                        System.out.println("Printing all items with the " +
                                "inputted RFID number...");
                    } catch (IncorrectRFIDException e) {
                        System.out.println("That is an incorrect RFID " +
                                "input!");
                    } catch (EmptyLinkedListException e) {
                        System.out.println("The list is empty, can't " +
                                "print by RFID!");
                    }
                    break;
                default:
                    if (!(option.compareTo("Q") == 0)) {
                        System.out.println("That is not a valid " +
                                "option! Try again.");
                    }
                    System.out.println();
                    break;
            }
        } while (!(option.compareTo("Q") == 0));
        System.out.println("Goodbye!");
    }
}
