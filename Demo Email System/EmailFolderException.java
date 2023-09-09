// Jason Zheng 114907558 R30 Section 8

/**
 * This class represents a custom exception that will
 * be thrown whenever an error related to an email
 * or folder has been encountered
 */
public class EmailFolderException extends Exception {
    public EmailFolderException(String str) {
        super(str);
    }
}
