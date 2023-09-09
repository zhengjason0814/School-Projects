// Jason Zheng 114907558 R30 Section 8
/**
 * This class represents the exception that will be thrown if an
 * incorrect location format was given to an item
 */
public class IncorrectLocationException extends Exception {
    public IncorrectLocationException(String str) {
        super(str);
    }
}
