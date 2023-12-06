import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents a folder of emails used inside
 * a email system.
 */
public class Folder implements Serializable {
    private ArrayList<Email> emails;
    private String name;
    private String currentSortingMethod;

    /**
     * This is the constructor of the folder object,
     * where it creates a new folder with a arraylist
     * of emails, a name, and a sorting method.
     *
     * @param name the name of the folder
     */
    public Folder(String name) {
        emails = new ArrayList<Email>();
        this.name = name;
        currentSortingMethod = "date descending";
    }

    /**
     * This method adds an email to a folder based on
     * the sorting method of the folder
     *
     * @param email the email that is to be added
     */
    public void addEmail(Email email) {
        if (emails.isEmpty()) {
            emails.add(email);
        } else {
            switch (currentSortingMethod) {
                case "subject ascending":
                    for (int i = 0; i < emails.size(); i++) {
                        if (emails.get(i).getSubject().compareTo
                                (email.getSubject()) > 0) {
                            emails.add(i,email);
                        }
                    }
                    break;
                case "subject descending":
                    for (int i = 0; i < emails.size(); i++) {
                        if (emails.get(i).getSubject().compareTo
                                (email.getSubject()) < 0) {
                            emails.add(i,email);
                        }
                    }
                    break;
                case "date ascending":
                    for (int i = 0; i < emails.size(); i++) {
                        if (emails.get(i).getTimeStamp().compareTo
                                (email.getTimeStamp()) > 0) {
                            emails.add(i,email);
                        }
                    }
                    break;
                case "date descending":
                    int originalSize = emails.size();
                    for (int i = 0; i < originalSize; i++) {
                        if (emails.get(i).getTimeStamp().compareTo
                                (email.getTimeStamp()) < 0) {
                            emails.add(i,email);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * This method removes an email from the
     * folder into the trash
     *
     * @param index the index of the email we want to delete
     *
     * @return returns the email that we've removed
     */
    public Email removeEmail(int index) {
        if (emails.isEmpty()) {
            throw new IllegalStateException("Empty Arraylist");
        }
        Email answer;
        answer = emails.remove(index);
        return answer;
    }

    /**
     * This method sorts the folder by subjects ascending
     */
    public void sortBySubjectAscending() {
        if (emails.size() == 0) {
            throw new IllegalStateException
                    ("There are no emails to sort!");
        }
        for (int i = 0; i < emails.size() - 1; i++) {
            for (int j = emails.size() - 1; j > i; j--) {
                if (emails.get(j).getSubject().compareTo
                        (emails.get(j - 1).getSubject()) < 0) {
                    Collections.swap(emails, j, j - 1);
                }
            }
        }
    }
    /**
     * This method sorts the folder by subjects descending
     */
    public void sortBySubjectDescending() {
        if (emails.size() == 0) {
            throw new IllegalStateException("There are no emails to sort!");
        }
        for (int i = 0; i < emails.size() - 1; i++) {
            for (int j = emails.size() - 1; j > i; j--) {
                if (emails.get(j).getSubject().compareTo
                        (emails.get(j - 1).getSubject()) > 0) {
                    Collections.swap(emails, j, j - 1);
                }
            }
        }
    }
    /**
     * This method sorts the folder by date descending
     */
    public void sortByDateAscending() {
        if (emails.size() == 0) {
            throw new IllegalStateException
                    ("There are no emails to sort!");
        }
        for (int i = 0; i < emails.size() - 1; i++) {
            for (int j = emails.size() - 1; j > i; j--) {
                if (emails.get(j).getTimeStamp().compareTo
                        (emails.get(j - 1).getTimeStamp()) < 0) {
                    Collections.swap(emails, j, j - 1);
                }
            }
        }
    }
    /**
     * This method sorts the folder by date descending
     */
    public void sortByDateDescending() {
        if (emails.size() == 0) {
            throw new IllegalStateException("There are no emails to sort!");
        }
        for (int i = 0; i < emails.size() - 1; i++) {
            for (int j = emails.size() - 1; j > i; j--) {
                if (emails.get(j).getTimeStamp().compareTo
                        (emails.get(j - 1).getTimeStamp()) > 0) {
                    Collections.swap(emails, j, j - 1);
                }
            }
        }
    }

    /**
     * This method returns the arraylist of the emails
     *
     * @return the arraylist of emails
     */
    public ArrayList<Email> getEmails() {
        return emails;
    }

    /**
     * This method sets the arraylist to a new arraylist
     *
     * @param emails the new arraylist of emails
     */
    public void setEmails(ArrayList<Email> emails) {
        this.emails = emails;
    }

    /**
     * This method returns the name of the folder
     *
     * @return the name of the folder
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets a new name to the folder
     *
     * @param name the new name of the folder
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns the current sorting method of the
     * folder
     *
     * @return the current sorting method
     */
    public String getCurrentSortingMethod() {
        return currentSortingMethod;
    }

    /**
     * This method sets a new sorting method to the folder
     *
     * @param currentSortingMethod the new sorting method to be set
     */
    public void setCurrentSortingMethod(String currentSortingMethod) {
        this.currentSortingMethod = currentSortingMethod;
    }
}
