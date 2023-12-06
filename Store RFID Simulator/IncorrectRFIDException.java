/**
 * This class represents the exception that will be thrown if
 * an incorrect RFID format was given to an item
 */
 class IncorrectRFIDException extends Exception {
    public IncorrectRFIDException(String str) {
        super(str);
    }
}
