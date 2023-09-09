// Jason Zheng 114907558 R30 Section 8
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a mailbox object inside
 * emailing system that also acts as a main method.
 */

public class Mailbox implements Serializable {
    private Folder inbox;
    private Folder trash;
    private ArrayList<Folder> folders;
    public static Mailbox mailbox;

    /**
     * This constructor constructs a new Mailbox object
     * that will act as our main system as well as
     * the obj. that we will search and save for.
     */
    public Mailbox() {
        inbox = new Folder("inbox");
        trash = new Folder("trash");
        folders = new ArrayList<Folder>();
    }

    /**
     * This method adds a new folder to the mailbox object, checking
     * if the folder name already exists as well.
     *
     * @param folder the folder that we are trying to add
     */
    public void addFolder(Folder folder) {
        for (int i = 0; i < folders.size(); i++) {
            if (folders.get(i).getName().equals(folder.getName())) {
                throw new IllegalStateException
                        ("Folder with that name already exists!");
            }
        }
        folders.add(folder);
    }

    /**
     * This method deletes a folder from the mailbox class and throws
     * exceptions if there are no valid folders to delete
     *
     * @param name the name of the folder to delete
     * @throws EmailFolderException is thrown if there are no valid folders
     * to delete
     */
    public void deleteFolder(String name) throws EmailFolderException {
        if (folders.isEmpty()) {
            throw new IllegalStateException
                    ("There are no folders to delete from the list!");
        }
        if (name.equals("inbox") || name.equals("trash")) {
            throw new IllegalArgumentException
                    ("You cannot delete the inbox or trash!");
        }
        int counter = 0;
        for(int i = 0; i < folders.size(); i++) {
            if (folders.get(i).getName().equals(name)) {
                folders.remove(i);
                System.out.println
                        ("Successfully deleted folder " + name + "!");
                break;
            }
            counter++;
            if (counter == folders.size()) {
                throw new EmailFolderException
                        ("Did not find folder, didn't delete.");
            }
        }
    }

    /**
     * This method asks for user input and then composes a new email
     * which will then be added to the inbox folder of the mailbox
     */
    public void composeEmail() {
        String to, cc, bbc, subject, body;
        Scanner compose = new Scanner(System.in);
        try {
            System.out.println
                    ("What is the email of the person you want to email?");
            to = compose.nextLine();
            System.out.println
                    ("Please enter additional people you " +
                            "would like to cc this email to.");
            cc = compose.nextLine();
            System.out.println
                    ("Please enter additional people " +
                            "for blind copy recipients.");
            bbc = compose.nextLine();
            System.out.println
                    ("What is the subject of this email?");
            subject = compose.nextLine();
            System.out.println
                    ("What would you like to write inside of the email?");
            body = compose.nextLine();
            Email newEmail = new Email(to,cc,bbc,subject,body);
            inbox.addEmail(newEmail);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal input! " +
                    "Try again, please only enter strings!");
        }
    }

    /**
     * This method deletes an email from a folder and throws an exception
     * if it cannot find/delete the folder, email, etc.
     *
     * @param email the email to be deleted
     * @throws EmailFolderException is thrown
     *                              if email removal was unsuccessful
     */
    public void deleteEmail(Email email) throws EmailFolderException {
        boolean success = false;
        for (int i = 0; i < folders.size(); i++) {
            int currentSize = folders.get(i).getEmails().size();
            for (int j = 0; j < currentSize; j++) {
                if (folders.get(i).getEmails().get(j) == email) {
                    trash.addEmail(folders.get(i).removeEmail(j));
                    success = true;
                }
            }
            if (success) {
                break;
            }
        }
        if (!success) {
            throw new EmailFolderException
                    ("Removal of email was unsuccessful!");
        }
    }

    /**
     * This method clears the trash folder of all emails and tells the user
     * how many items were discarded.
     */
    public void clearTrash() {
        if (trash.getEmails().isEmpty()) {
            throw new IllegalStateException("Trash is empty!");
        }
        int counter = 0;
        while(!trash.getEmails().isEmpty()) {
            trash.removeEmail(0);
            counter++;
        }
        System.out.println("Removed " + counter + " item(s).");
    }

    /**
     * This method moves an email from one folder to another, and throws
     * an exception if a folder or email was not found
     *
     * @param email the email to be moved
     * @param target the folder that the email will be moved to
     * @throws EmailFolderException is thrown if the email or folder
     *                              was not found
     */
    public void moveEmail(Email email, Folder target)
            throws EmailFolderException {
        boolean success = false;
        Email toMove = email;
        for (int i = 0; i < folders.size(); i++) {
            int currentSize = folders.get(i).getEmails().size();
            for (int j = 0; j < currentSize; j++) {
                if (folders.get(i).getEmails().get(j) == email) {
                    toMove = folders.get(i).removeEmail(j);
                    success = true;
                }
            }
            if (success) {
                break;
            }
        }
        for (int i = 0; i < inbox.getEmails().size(); i++) {
            if (inbox.getEmails().get(i) == email) {
                toMove = inbox.getEmails().remove(i);
                success = true;
            }
        }
        for (int i = 0; i < trash.getEmails().size(); i++) {
            if (trash.getEmails().get(i) == email) {
                toMove = trash.getEmails().remove(i);
                success = true;
            }
        }
        if (success) {
            target.addEmail(toMove);
        } else {
            throw new EmailFolderException
                    ("Folder could not be found! Did not move email.");
        }
    }

    /**
     * This method gets a folder that has the given name inside the parameter,
     * this method throws an exception if it cannot find the folder.
     *
     * @param name the name of the folder that we are trying to get
     * @return the folder we are trying to get
     * @throws EmailFolderException is thrown if folder is not found
     */
    public Folder getFolder(String name) throws EmailFolderException {
        int counter = 0;
        for (int i = 0; i < folders.size(); i++) {
            if (folders.get(i).getName().equals(name)) {
                return folders.get(i);
            }
            counter++;
            if (counter == folders.size()) {
                throw new EmailFolderException("Folder could not be found!");
            }
        }
        return null;
    }

    /**
     * This method is to search for a mailbox.obj file and deserialize
     * it in order to open the file and load it into the main method's
     * mailbox instead.
     *
     * @return the mailbox that was previously saved
     * @throws IOException is thrown if there was a problem with the
     *                     input/output.
     * @throws ClassNotFoundException is thrown if a necessary class was
     *                                not found
     */
    public static Mailbox deserializeMailIn()
            throws IOException, ClassNotFoundException {
        String fileName = "mailbox.obj";
        File file = new File(fileName);

        if (file.exists()) {
            FileInputStream fileInput = new FileInputStream(file);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            Mailbox mailbox = (Mailbox) objectInput.readObject();
            objectInput.close();
            return mailbox;
        } else {
            return null;
        }
    }

    /**
     * This method takes the current mailbox object that is
     * inside the main method that the user has altered and
     * created, and saves it for future use inside
     * another program run.
     *
     * @throws IOException is thrown if there was a problem with the
     *                     input/output.
     */
    public static void serializeAndSave() throws IOException {
        FileOutputStream fileOutput = new FileOutputStream("mailbox.obj");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOutput);
        objectOut.writeObject(mailbox);
        fileOutput.close();
        objectOut.close();
    }

    /**
     * This is the main method that will actually allow for interaction
     * and simulation of an email system, it also searches and saves
     * a mailbox.obj once entering and exiting the program correctly.
     *
     * @param args the arguments inside the main method
     * @throws IOException is thrown if there was a problem with the
                           input/output.
     * @throws ClassNotFoundException is thrown if a necessary class was
     *                                not found
     * @throws EmailFolderException is thrown if an email/folder error
     *                              has occurred, varies between actions
     */
    public static void main(String[] args)
            throws IOException, ClassNotFoundException, EmailFolderException {
        String option, name;
        Scanner input = new Scanner(System.in);
        if (deserializeMailIn() != null) {
            mailbox = deserializeMailIn();
            System.out.println
                    ("Loaded in a mailbox object from previous creations!");
        } else {
            mailbox = new Mailbox();
            System.out.println
                    ("No previous mailboxes found, creating new one!");
        }
        do {
            System.out.println("Email System Menu:");
            System.out.println("(A) Add Folder");
            System.out.println("(R) Remove Folder");
            System.out.println("(C) Compose Email");
            System.out.println("(F) View Folder");
            System.out.println("(I) View Inbox");
            System.out.println("(T) View Trash");
            System.out.println("(E) Empty Trash");
            System.out.println("(Q) Quit and Save");
            option = input.nextLine();
            switch (option) {
                case "A":
                    try {
                        System.out.println
                                ("What is the name of this folder?");
                        name = input.nextLine();
                        Folder newFolder = new Folder(name);
                        mailbox.addFolder(newFolder);
                        System.out.println
                                ("Successfully added a new folder!");
                        System.out.println("Current Mailbox Folders:");
                        System.out.println("------------------------");
                        System.out.println("Inbox");
                        System.out.println("Trash");
                        for (int i = 0; i < mailbox.folders.size();i++) {
                            System.out.println(mailbox.folders.get(i)
                                    .getName());
                        }
                    } catch (IllegalStateException e) {
                        System.out.println
                                ("There is already a folder" +
                                        " with that name! Cannot add.");
                    }
                    System.out.println();
                    break;
                case "R":
                    try {
                        System.out.println
                                ("What is the name of the " +
                                        "folder you want to delete?");
                        name = input.nextLine();
                        mailbox.deleteFolder(name);
                        System.out.println("Successfully " +
                                "deleted the folder : " + name);
                    } catch (IllegalStateException e) {
                        System.out.println("There are no " +
                                "folders to delete in the list!");
                    } catch (EmailFolderException e) {
                        System.out.println("Did not find the " +
                                "folder to remove, didn't delete.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("You cannot delete " +
                                "the inbox or the trash!");
                    }
                    System.out.println();
                    break;
                case "C":
                    System.out.println("Composing new email...");
                    mailbox.composeEmail();
                    System.out.println("Successfully composed a " +
                            "new email and put in the inbox!");
                    System.out.println();
                    break;
                case "F":
                    try {
                        System.out.println("What is the name of " +
                                "the folder you want to open?");
                        name = input.nextLine();
                        if(name.equals("inbox") || name.equals("trash")) {
                            System.out.println("Please use the " +
                                    "menu option, not view folder option!");
                            System.out.println();
                            break;
                        }
                        System.out.println("Displaying all " +
                                "emails inside of the folder...");
                        System.out.printf
                                ("%-8s | %-22s | %-40s%n",
                                        "Index", "Time", "Subject");
                        System.out.println("---------------------" +
                                "-------------------------");
                        if (mailbox.getFolder(name).getEmails().isEmpty()
                                || mailbox.getFolder(name) == null) {
                            System.out.println("None");
                        } else {
                            for (int i = 0; i < mailbox.getFolder(name)
                                    .getEmails().size(); i++) {
                                System.out.printf("%-8d | %-22s | %-40s%n",
                                        i + 1,
                                        mailbox.getFolder(name).getEmails()
                                                .get(i).gregorianConverter(),
                                        mailbox.getFolder(name).getEmails()
                                                .get(i).getSubject());
                            }
                        }
                        Folder currentFolder = mailbox.getFolder(name);
                        do {
                            System.out.println("What would you like to do " +
                                    "with these emails inside of " +
                                    "the " + name + " folder?");
                            System.out.println();
                            System.out.println("(M) Move Email");
                            System.out.println("(D) Delete Email");
                            System.out.println("(V) View Email Contents");
                            System.out.println("(SA) Sort by " +
                                    "subject ascending");
                            System.out.println("(SD) Sort by " +
                                    "subject descending");
                            System.out.println("(DA) Sort by " +
                                    "date ascending");
                            System.out.println("(DD) Sort by " +
                                    "date descending");
                            System.out.println("(R) Return to main menu");
                            option = input.nextLine();
                            switch (option) {
                                case "M":
                                    try {
                                        if(currentFolder.getEmails().isEmpty()
                                                || currentFolder.
                                                getEmails() == null) {
                                            throw new IllegalStateException
                                                    ("Empty List!");
                                        }
                                        System.out.println
                                                ("What index is the " +
                                                        "desired email in?");
                                        int index = input.nextInt();
                                        if (index < 1 || index >
                                                currentFolder.
                                                        getEmails().size()) {
                                     throw new ArrayIndexOutOfBoundsException
                                                    ("That is not a" +
                                                            " valid index!" +
                                                            " Try again.");
                                        }
                                        input.nextLine();
                                        System.out.println("Emails :");
                                        System.out.println("Inbox");
                                        System.out.println("Trash");
                                        for (int i = 0; i < mailbox.
                                                folders.size(); i++) {
                                            System.out.println(mailbox.
                                                    folders.get(i).getName());
                                        }
                                        System.out.println();
                                        System.out.println("Select a " +
                                                "folder you would like " +
                                                "to move the email to:");
                                        name = input.nextLine();
                                        mailbox.moveEmail(currentFolder.
                                                getEmails().get(index - 1),
                                                mailbox.getFolder(name));
                                        System.out.println("Successfully " +
                                                "moved the email to " +
                                                name + "!");
                                    } catch (EmailFolderException e) {
                                        System.out.println("Folder " +
                                                "not found!" +
                                                " Returning to menu...");
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("Please only " +
                                                "input integers when asked" +
                                                " for the email index!");
                                    } catch
                                    (ArrayIndexOutOfBoundsException e) {
                                        System.out.println("There is " +
                                                "nothing in that " +
                                                "index, try again.");
                                        input.nextLine();
                                    } catch (IllegalStateException e) {
                                        System.out.println
                                                ("The folder is empty!");
                                    }
                                    System.out.println();
                                    break;
                                case "D":
                                    try {
                                        if (currentFolder.
                                                getEmails()
                                                .isEmpty()) {
                                            throw new IllegalStateException
                                                    ("Cannot delete an " +
                                                            "empty folder!");
                                        }
                                        System.out.println
                                                ("What index is the email" +
                                                        " you want to " +
                                                        "delete in?");
                                        int index = input.nextInt();
                                        if
                                        (index < 1 ||
                                                index > currentFolder.
                                                        getEmails().
                                                        size()) {
                                     throw new ArrayIndexOutOfBoundsException
                                                    ("That is not a " +
                                                            "valid " +
                                                            "index! " +
                                                            "Try again.");
                                        }
                                        input.nextLine();
                                        mailbox.moveEmail
                                                (currentFolder.getEmails()
                                                        .get(index - 1),
                                                        mailbox.trash);
                                        System.out.println("Successfully " +
                                                "deleted and moved email" +
                                                " to the trash!");
                                    } catch (EmailFolderException e) {
                                        System.out.println("Folder " +
                                                "not found!");
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("Please " +
                                                "only input integers " +
                                                "when asked for " +
                                                "the email index!");
                                    } catch
                                    (ArrayIndexOutOfBoundsException e) {
                                        System.out.println("There " +
                                                "is nothing in that" +
                                                " index, try again.");
                                        input.nextLine();
                                    } catch (IllegalStateException
                                            e) {
                                        System.out.println("Cannot " +
                                                "delete from a " +
                                                "empty email list!");
                                    }
                                    System.out.println();
                                    break;
                                case "V":
                                    try {
                                        if(currentFolder.getEmails()
                                                .isEmpty()) {
                                            throw new IllegalStateException
                                                    ("Empty Folder!");
                                        }
                                        System.out.println("What index " +
                                                "is the email you want" +
                                                " to view?");
                                        int index = input.nextInt();
                                        if (index < 1 || index > currentFolder
                                                .getEmails().size()) {
                                            throw new
                                          ArrayIndexOutOfBoundsException
                                 ("That is not a valid index! Try again.");
                                        }
                                        input.nextLine();
                                        System.out.println
                                                ("To: " +
                                        currentFolder
                                           .getEmails()
                                              .get(index - 1)
                                                     .getTo());
                                        System.out.println
                                                ("Cc: " +
                                                        currentFolder
                                                                .getEmails().
                                                                get(index - 1)
                                                                .getCc());
                                        System.out.println
                                                ("Bcc: " +
                                                        currentFolder
                                                                .getEmails()
                                                              .get(index - 1)
                                                                .getBcc());
                                        System.out.println
                                                ("Subject: " +
                                                        currentFolder
                                                                .getEmails()
                                                            .get(index - 1)
                                                              .getSubject());
                                        System.out.println
                                                ("Body: " +
                                                        currentFolder.
                                                                getEmails().
                                                      get(index - 1).
                                                                getBody());

                                    } catch (IllegalArgumentException e) {
                                        System.out.println
                                                ("Please only input" +
                                                        " integers " +
                                                        "when asked " +
                                                        "for the email" +
                                                        " index!");
                                    } catch (IllegalStateException e) {
                                        System.out.println("Empty Folder!");
                                    } catch
                                    (ArrayIndexOutOfBoundsException e) {
                                        System.out.println("There is " +
                                                "nothing in that index," +
                                                " try again.");
                                        input.nextLine();
                                    }
                                    System.out.println();
                                    break;
                                case "SA":
                                    try {
                                        System.out.println("Sorting " +
                                                "inbox by specified " +
                                                "option...");
                                        currentFolder.
                                                sortBySubjectAscending();
                                        currentFolder.
                                                setCurrentSortingMethod
                                                        ("subject ascending");
                                        System.out.println
                                                ("Successfully sorted by" +
                                                        " subjects " +
                                                        "ascending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println
                                                ("There is no " +
                                                        "emails to sort!");
                                    }
                                    break;
                                case "SD":
                                    try {
                                        System.out.println
                                                ("Sorting inbox" +
                                                        " by " +
                                                        "specified " +
                                                        "option...");
                                        currentFolder.
                                                sortBySubjectDescending();
                                        currentFolder.
                                                setCurrentSortingMethod
                                                        ("subject" +
                                                                " " +
                                                                "descending");
                                        System.out.println
                                                ("Successfully " +
                                                        "sorted by subjects" +
                                                        " descending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println
                                                ("There is no " +
                                                        "emails to sort!");
                                    }
                                    break;
                                case "DA":
                                    try {
                                        System.out.println("Sorting inbox" +
                                                " by specified option...");
                                        currentFolder.sortByDateAscending();
                                        currentFolder.
                                                setCurrentSortingMethod
                                                        ("date ascending");
                                        System.out.println
                                                ("Successfully sorted" +
                                                        " by date " +
                                                        "ascending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println
                                                ("There is no emails" +
                                                        " to sort!");
                                    }
                                    break;
                                case "DD":
                                    try {
                                        System.out.println
                                                ("Sorting inbox by " +
                                                        "specified " +
                                                        "option...");
                                        currentFolder.
                                                sortByDateDescending();
                                        currentFolder.
                                                setCurrentSortingMethod
                                                        ("date descending");
                                        System.out.println
                                                ("Successfully " +
                                                        "sorted by " +
                                                        "date " +
                                                        "descending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println
                                                ("There is no " +
                                                        "emails to sort!");
                                    }
                                    break;
                                default:
                                    if (!option.equals("R")) {
                                        System.out.println
                                                ("Incorrect Option!" +
                                                        " Try again.");
                                    } else {
                                        System.out.println
                                                ("Returning back " +
                                                        "to menu...");
                                    }
                                    break;
                            }
                        } while(!option.equals("R"));
                    } catch (EmailFolderException e) {
                        System.out.println("Folder not found!");
                    }
                    System.out.println();
                    break;
                case "I":
                    try {
                        if (mailbox.inbox.getEmails().isEmpty()) {
                            throw new EmailFolderException
                                    ("The inbox is empty!");
                        }
                        System.out.println("Displaying all emails" +
                                " inside of the inbox...");
                        System.out.printf("%-8s | %-22s | %-40s%n",
                                "Index", "Time", "Subject");
                        System.out.println("--------------------" +
                                "--------------------------");
                        for (int i = 0; i < mailbox.inbox.
                                getEmails().size(); i++) {
                            System.out.printf("%-8d | %-22s | %-40s%n",
                                    i + 1,
                                    mailbox.inbox.getEmails().get(i).
                                            gregorianConverter(),
                                    mailbox.inbox.getEmails().get(i).
                                            getSubject());
                        }
                        do {
                            System.out.println("What would you like to" +
                                    " do with these emails inside of" +
                                    " the inbox?");
                            System.out.println();
                            System.out.println("(M) Move Email");
                            System.out.println("(D) Delete Email");
                            System.out.println("(V) View Email Contents");
                            System.out.println("(SA) Sort by subject" +
                                    " ascending");
                            System.out.println("(SD) Sort by subject" +
                                    " descending");
                            System.out.println("(DA) Sort by date" +
                                    " ascending");
                            System.out.println("(DD) Sort by date" +
                                    " descending");
                            System.out.println("(R) Return to" +
                                    " main menu");
                            option = input.nextLine();
                            switch (option) {
                                case "M":
                                    try {
                                        if(mailbox.inbox.
                                                getEmails().isEmpty()) {
                                            throw new IllegalStateException
                                                    ("Empty Trash");
                                        }
                                        System.out.println
                                                ("What index is the" +
                                                        " desired email in?");
                                        int index = input.nextInt();
                                        if (index < 1 || index > mailbox.
                                                inbox.getEmails().size()) {
                                 throw new ArrayIndexOutOfBoundsException
                                                    ("That is not a valid " +
                                                            "index! " +
                                                            "Try again.");
                                        }
                                        input.nextLine();
                                        System.out.println("Emails :");
                                        System.out.println("Inbox");
                                        System.out.println("Trash");
                                        for (int i = 0; i < mailbox.folders.
                                                size(); i++) {
                                            System.out.println(mailbox.
                                                    folders.get(i).
                                                    getName());
                                        }
                                        System.out.println();
                                        System.out.println
                                                ("Select a folder " +
                                                "you would like to move " +
                                                "the email to:");
                                        name = input.nextLine();
                                        mailbox.moveEmail(mailbox.inbox.
                                                getEmails().get(index - 1),
                                                mailbox.getFolder(name));
                                        System.out.println("Successfully " +
                                                "moved the email to " + name
                                                + "!");
                                    } catch (EmailFolderException e) {
                                        System.out.println("Folder not " +
                                                "found! Returning to " +
                                                "menu...");
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("Please " +
                                                "only input integers " +
                                                "when asked for the " +
                                                "email index!");
                                    } catch
                                    (ArrayIndexOutOfBoundsException e) {
                                        System.out.println
                                                ("There is nothing" +
                                                        " in that " +
                                                        "index, " +
                                                        "try again.");
                                        input.nextLine();
                                    } catch
                                    (IllegalStateException e) {
                                        System.out.println
                                                ("Cannot move " +
                                                        "from an " +
                                                        "empty inbox!");
                                    }
                                    System.out.println();
                                    break;
                                case "D":
                                    try {
                                        if (mailbox.inbox.getEmails()
                                                .isEmpty()) {
                                            throw new IllegalStateException
                                                    ("Cannot delete an " +
                                                            "empty trash!");
                                        }
                                        System.out.println("What index " +
                                                "is the email you want" +
                                                " to delete in?");
                                        int index = input.nextInt();
                                        if (index < 1 || index >
                                                mailbox.inbox.getEmails()
                                                        .size()) {
                      throw new ArrayIndexOutOfBoundsException
                              ("That is not a valid index! Try again.");
                                        }
                                        input.nextLine();
                                        mailbox.moveEmail(mailbox.
                                                inbox.getEmails().
                                                get(index - 1),
                                                mailbox.trash);
                                        System.out.println
                                                ("Successfully " +
                                                        "deleted" +
                                                        " and moved" +
                                                        " email to" +
                                                        " the trash!");
                                    } catch (EmailFolderException e) {
                                        System.out.println
                                                ("Folder not found!");
                                    } catch
                                    (IllegalArgumentException e) {
                                        System.out.println
                                                ("Please only input " +
                                                        "integers " +
                                                        "when asked " +
                                                        "for the " +
                                                        "email index!");
                                    } catch
                                    (ArrayIndexOutOfBoundsException e) {
                                        System.out.println
                                                ("There is nothing " +
                                                        "in that" +
                                                        " index, " +
                                                        "try again.");
                                        input.nextLine();
                                    } catch
                                    (IllegalStateException e) {
                                        System.out.println
                                                ("You cannot" +
                                                        " delete" +
                                                        " from an" +
                                                        " empty inbox!");
                                    }
                                    System.out.println();
                                    break;
                                case "V":
                                    try {
                                        if(mailbox.inbox.getEmails().
                                                isEmpty()) {
                                            throw new IllegalStateException
                                                    ("Empty Trash");
                                        }
                                        System.out.println
                                                ("What index is the " +
                                                        "email you " +
                                                        "want to view?");
                                        int index = input.nextInt();
                                        if (index < 1 || index > mailbox
                                                .inbox.getEmails().size()
                                        ) {
                              throw new ArrayIndexOutOfBoundsException
                                      ("That is not a valid index! " +
                                              "Try again.");
                                        }
                                        input.nextLine();
                                        System.out.println("To :" +
                                                mailbox.inbox.getEmails()
                                                        .get(index - 1)
                                                        .getTo());
                                        System.out.println("Cc :" +
                                                mailbox.inbox.getEmails()
                                                        .get(index - 1)
                                                        .getCc());
                                        System.out.println("Bcc :" +
                                                mailbox.inbox.getEmails()
                                                        .get(index - 1)
                                                        .getBcc());
                                        System.out.println("Subject :" +
                                                mailbox.inbox.getEmails()
                                                        .get(index - 1)
                                                        .getSubject());
                                        System.out.println(mailbox.
                                                inbox.getEmails().
                                                get(index - 1).
                                                getBody());
                                    } catch
                                    (IllegalArgumentException e) {
                                        System.out.println
                                                ("Please only " +
                                                        "input integers" +
                                                        " when asked " +
                                                        "for the email" +
                                                        " index!");
                                    } catch
                                    (IllegalStateException e) {
                                        System.out.println
                                                ("Inbox is empty. " +
                                                        "Cannot view.");
                                    } catch
                                    (ArrayIndexOutOfBoundsException e) {
                                        System.out.println
                                                ("There is nothing in" +
                                                        " that index, " +
                                                        "try again.");
                                        input.nextLine();
                                    }
                                    System.out.println();
                                    break;
                                case "SA":
                                    try {
                                        System.out.println("Sorting " +
                                                "inbox by specified " +
                                                "option...");
                                        mailbox.inbox.
                                                sortBySubjectAscending();
                                        mailbox.inbox.
                                                setCurrentSortingMethod
                                                        ("subject ascending");
                                        System.out.println
                                                ("Successfully " +
                                                        "sorted by " +
                                                        "subjects " +
                                                        "ascending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println("There " +
                                                "is no emails to " +
                                                "sort!");
                                    }
                                    break;
                                case "SD":
                                    try {
                                        System.out.println("Sorting " +
                                                "inbox by specified " +
                                                "option...");
                                        mailbox.inbox.
                                                sortBySubjectDescending();
                                        mailbox.inbox.
                                                setCurrentSortingMethod
                                                    ("subject descending");
                                        System.out.println
                                                ("Successfully " +
                                                        "sorted " +
                                                        "by " +
                                                        "subjects " +
                                                        "descending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println("There is no" +
                                                " emails to sort!");
                                    }
                                    break;
                                case "DA":
                                    try {
                                        System.out.println
                                                ("Sorting inbox " +
                                                        "by " +
                                                        "specified " +
                                                        "option...");
                                        mailbox.inbox.
                                                sortByDateAscending();
                                        mailbox.inbox.
                                                setCurrentSortingMethod
                                                        ("date ascending");
                                        System.out.println
                                                ("Successfully" +
                                                        " sorted" +
                                                        " by date" +
                                                        " ascending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println
                                                ("There is " +
                                                        "no " +
                                                        "emails" +
                                                        " to " +
                                                        "sort!");
                                    }
                                    break;
                                case "DD":
                                    try {
                                        System.out.println
                                                ("Sorting " +
                                                        "inbox " +
                                                        "by " +
                                                        "specified " +
                                                        "option...");
                                        mailbox.inbox.sortByDateDescending();
                                        mailbox.inbox.
                                                setCurrentSortingMethod
                                                        ("date descending");
                                        System.out.println
                                                ("Successfully sorted" +
                                                        " by date " +
                                                        "descending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println("There is " +
                                                "no emails to sort!");
                                    }
                                    break;
                                default:
                                    if (!option.equals("R")) {
                                        System.out.println("Incorrect " +
                                                "Option! Try again.");
                                    } else {
                                        System.out.println("Returning" +
                                                " back to menu...");
                                    }
                                    break;
                            }
                        } while(!option.equals("R"));
                    } catch (EmailFolderException e) {
                        System.out.println("The inbox is empty!");
                    }

                    System.out.println();
                    break;
                case "T":
                    try {
                        if (mailbox.trash.getEmails().isEmpty()) {
                            throw new EmailFolderException("Trash Empty!");
                        }
                        System.out.println("Displaying all emails inside " +
                                "of the trash...");
                        System.out.printf("%-8s | %-22s | %-40s%n",
                                "Index", "Time", "Subject");
                        System.out.println("----------------------" +
                                "------------------------");
                        for (int i = 0; i < mailbox.trash.getEmails()
                                .size(); i++) {
                            System.out.printf("%-8d | %-22s | %-40s%n",
                                    i + 1, mailbox.trash.getEmails().
                                            get(i).gregorianConverter(),
                                    mailbox.trash.getEmails().get(i).
                                            getSubject());
                        }
                        do {
                            System.out.println("What would you like " +
                                    "to do with these emails inside " +
                                    "of the trash?");
                            System.out.println();
                            System.out.println("(M) Move Email");
                            System.out.println("(D) Delete Email");
                            System.out.println("(V) View Email Contents");
                            System.out.println("(SA) Sort by subject " +
                                    "ascending");
                            System.out.println("(SD) Sort by subject " +
                                    "descending");
                            System.out.println("(DA) Sort by date " +
                                    "ascending");
                            System.out.println("(DD) Sort by date " +
                                    "descending");
                            System.out.println("(R) Return to main menu");
                            option = input.nextLine();
                            switch (option) {
                                case "M":
                                    try {
                                        if(mailbox.trash.getEmails()
                                                .isEmpty()) {
                                            throw new IllegalStateException
                                                    ("Empty Trash");
                                        }
                                        System.out.println
                                                ("What index is the " +
                                                        "desired email in?");
                                        int index = input.nextInt();
                                        if (index < 1 || index > mailbox
                                                .trash.getEmails()
                                                .size()) {
                             throw new ArrayIndexOutOfBoundsException
                                     ("That is not a valid index!" +
                                             " Try again.");
                                        }
                                        input.nextLine();
                                        System.out.println("Emails :");
                                        System.out.println("Inbox");
                                        System.out.println("Trash");
                                        for (int i = 0; i < mailbox.
                                                folders.size(); i++) {
                                            System.out.println(mailbox.
                                                    folders.get(i).
                                                    getName());
                                        }
                                        System.out.println();
                                        System.out.println("Select a" +
                                                " folder you would " +
                                                "like to move the " +
                                                "email to:");
                                        name = input.nextLine();
                                        mailbox.moveEmail(mailbox.
                                                trash.getEmails().
                                                get(index - 1),
                                                mailbox.getFolder
                                                        (name));
                                        System.out.println
                                                ("Successfully moved" +
                                                        " the email " +
                                                        "to " + name + "!");
                                    } catch (EmailFolderException e) {
                                        System.out.println
                                                ("Folder not found!" +
                                                        " Returning " +
                                                        "to menu...");
                                    } catch (IllegalArgumentException e) {
                                        System.out.println
                                                ("Please only input " +
                                                        "integers when" +
                                                        " asked for " +
                                                        "the email index!");
                                    } catch
                                    (ArrayIndexOutOfBoundsException e) {
                                        System.out.println
                                                ("There is nothing" +
                                                        " in that " +
                                                        "index, try " +
                                                        "again.");
                                        input.nextLine();
                                    } catch (IllegalStateException e) {
                                        System.out.println("Cannot " +
                                                "move from an empty" +
                                                " trash!");
                                    }
                                    System.out.println();
                                    break;
                                case "D":
                                    try {
                                        if (mailbox.trash.getEmails()
                                                .isEmpty()) {
                                            throw new IllegalStateException
                                                    ("Cannot delete an " +
                                                            "empty trash!");
                                        }
                                        System.out.println
                                                ("What index is " +
                                                        "the email " +
                                                        "you want " +
                                                        "to delete in?");
                                        int index = input.nextInt();
                                        if (index < 1 || index > mailbox
                                                .trash.getEmails().size()) {
                                     throw new ArrayIndexOutOfBoundsException
                                                    ("That is not a " +
                                                            "valid index!" +
                                                            " Try again.");
                                        }
                                        input.nextLine();
                                        mailbox.moveEmail(mailbox.trash
                                                .getEmails().get
                                                        (index - 1),
                                                mailbox.trash);
                                        System.out.println
                                                ("Successfully " +
                                                        "deleted and " +
                                                        "moved email " +
                                                        "to the trash!");
                                    } catch (EmailFolderException e) {
                                        System.out.println("Folder" +
                                                " not found!");
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("Please only" +
                                                " input integers when" +
                                                " asked for the email" +
                                                " index!");
                                } catch (ArrayIndexOutOfBoundsException e) {
                                        System.out.println("There is " +
                                                "nothing in that index," +
                                                " try again.");
                                        input.nextLine();
                                    } catch (IllegalStateException e) {
                                        System.out.println("Cannot " +
                                                "delete from an " +
                                                "empty trash!");
                                    }
                                    System.out.println();
                                    break;
                                case "V":
                                    try {
                                        if(mailbox.trash.
                                                getEmails().
                                                isEmpty()) {
                                            throw new IllegalStateException
                                                    ("Empty Trash");
                                        }
                                        System.out.println
                                                ("What index is " +
                                                        "the email " +
                                                        "you want to view?");
                                        int index = input.nextInt();
                                        if (index < 1 || index > mailbox.
                                                trash.getEmails().
                                                size()) {
                                    throw new ArrayIndexOutOfBoundsException
                                            ("That is not a valid index! " +
                                                    "Try again.");
                                        }
                                        input.nextLine();
                                        System.out.println("To :" + mailbox.
                                                trash.getEmails().
                                                get(index - 1).getTo());
                                        System.out.println("Cc :" + mailbox.
                                                trash.getEmails().
                                                get(index - 1).getCc());
                                        System.out.println("Bcc :" + mailbox.
                                                trash.getEmails().
                                                get(index - 1).getBcc());
                                        System.out.println("Subject :" +
                                                mailbox.
                                                trash.getEmails().
                                                get(index - 1).getSubject());
                                        System.out.println(mailbox.
                                                trash.getEmails().
                                                get(index - 1).getBody());
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("Please " +
                                                "only input integers" +
                                                " when asked for the" +
                                                " email index!");
                                    } catch (IllegalStateException e) {
                                        System.out.println("Trash is" +
                                                " empty. Cannot view.");
                                    } catch
                                    (ArrayIndexOutOfBoundsException e) {
                                        System.out.println
                                                ("There is nothing" +
                                                        " in that index," +
                                                        " try again.");
                                        input.nextLine();
                                    }
                                    System.out.println();
                                    break;
                                case "SA":
                                    try {
                                        System.out.println("Sorting inbox" +
                                                " by specified option...");
                                        mailbox.trash.
                                                sortBySubjectAscending();
                                        mailbox.trash.setCurrentSortingMethod
                                                ("subject ascending");
                                        System.out.println
                                                ("Successfully sorted" +
                                                        " by subjects" +
                                                        " ascending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println
                                                ("There is no " +
                                                        "emails to sort!");
                                    }
                                    break;
                                case "SD":
                                    try {
                                        System.out.println
                                                ("Sorting inbox by " +
                                                        "specified " +
                                                        "option...");
                                        mailbox.trash.
                                                sortBySubjectDescending();
                                        mailbox.trash.setCurrentSortingMethod
                                                ("subject descending");
                                        System.out.println
                                                ("Successfully " +
                                                        "sorted by " +
                                                        "subjects " +
                                                        "descending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println
                                                ("There is no " +
                                                        "emails to sort!");
                                    }
                                    break;
                                case "DA":
                                    try {
                                        System.out.println
                                                ("Sorting inbox " +
                                                        "by specified" +
                                                        " option...");
                                        mailbox.trash.
                                                sortByDateAscending();
                                        mailbox.trash.
                                                setCurrentSortingMethod
                                                        ("date ascending");
                                        System.out.println
                                                ("Successfully sorted" +
                                                        " by date " +
                                                        "ascending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println
                                                ("There is no " +
                                                        "emails to sort!");
                                    }
                                    break;
                                case "DD":
                                    try {
                                        System.out.println
                                                ("Sorting inbox by " +
                                                        "specified " +
                                                        "option...");
                                        mailbox.trash.
                                                sortByDateDescending();
                                        mailbox.trash.
                                                setCurrentSortingMethod
                                                        ("date descending");
                                        System.out.println
                                                ("Successfully " +
                                                        "sorted by date" +
                                                        " descending!");
                                    } catch (IllegalStateException e) {
                                        System.out.println
                                                ("There is no " +
                                                        "emails to sort!");
                                    }
                                    break;
                                default:
                                    if (!option.equals("R")) {
                                        System.out.println
                                                ("Incorrect Option!" +
                                                        " Try again.");
                                    } else {
                                        System.out.println
                                                ("Returning back" +
                                                        " to menu...");
                                    }
                                    break;
                            }
                        } while (!option.equals("R"));
                    } catch (EmailFolderException e) {
                        System.out.println("The trash is empty!");
                    }
                    System.out.println();
                    break;
                case "E":
                    try {
                        if (mailbox.trash.getEmails().size() == 0) {
                            throw new IllegalStateException
                                    ("Empty Arraylist");
                        }
                        System.out.println("Emptying trash...");
                        mailbox.clearTrash();
                        System.out.println("Successfully emptied trash!");
                    } catch (IllegalStateException e) {
                        System.out.println("There is no trash to empty!");
                    }
                    System.out.println();
                    break;
                case "Q":
                    try {
                        System.out.println("Quitting and saving the file...");
                        serializeAndSave();
                    } catch (IOException e) {
                        System.out.println("Something went wrong with " +
                                "the input/output of this file!");
                    }
                    break;
                default:
                    if (!option.equals("Q")) {
                        System.out.println("Wrong Option! " +
                                "Please enter a valid option given.");
                        System.out.println("Try again.");
                    }
                    break;
            }
        } while (!option.equals("Q"));
        System.out.println("Goodbye!");
        input.close();
    }
}
