// Jason Zheng 114907558 R30 Section 8

/**
 * This class made to represent an item and its information,
 * such as the name of the item, price of the item, and the RFID ID number.
 */
public class ItemInfo {
    private String name;
    private double price;
    private String rfidTagNumber;
    private String OriginalLocation;
    private String CurrentLocation;

    /**
     * This is a constructor for creating an item and its information like
     * name, price, ID and location.
     *
     * @param name the name of the item
     * @param price the price of the item
     * @param rfidTagNumber the RFID of the item
     * @param OriginalLocation the original location of the item
     * @throws IncorrectRFIDException is thrown if an incorrect input was
     *                                given to the RFID, where the ID violates
     *                                the conditions of an RFID
     * @throws IncorrectLocationException is thrown if an incorrect
     *                                    input was given to the location,
     *                                    where the location
     *                                    violates the conditions of an RFID
     */
    public ItemInfo(String name, double price, String rfidTagNumber,
                    String OriginalLocation) throws IncorrectRFIDException,
            IncorrectLocationException {
        this.name = name;
        this.price = price;
        if (rfidTagNumber.length() == 9) {
            for (int i = 0; i < rfidTagNumber.length(); i++) {
                char currentCharacter = rfidTagNumber.charAt(i);
                if ((currentCharacter < 'A' || currentCharacter > 'F')
                        &&
                        (currentCharacter < '0' || currentCharacter > '9')) {
                    throw new IncorrectRFIDException("Incorrect RFID!");
                }
            }
        } else {
            throw new IncorrectRFIDException("Incorrect RFID!");
        }
        this.rfidTagNumber = rfidTagNumber;

        if (OriginalLocation.length() == 6) {
            if (OriginalLocation.charAt(0) == 's') {
                for (int i = 1; i < OriginalLocation.length(); i++) {
                    char currentCharacter = OriginalLocation.charAt(i);
                    if (currentCharacter < '0' || currentCharacter > '9') {
                        throw new IncorrectLocationException
                                ("Incorrect Location!");
                    }
                }
            } else {
                throw new IncorrectLocationException("Incorrect Location!");
            }
        } else {
            throw new IncorrectLocationException("Incorrect Location!");
        }
        this.OriginalLocation = OriginalLocation;
        this.CurrentLocation = OriginalLocation;
        
    }

    /**
     * Returns the name of the item information
     *
     * @return the name of the item
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the item information
     * @param name the name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the price of the item information
     *
     * @return the price of the item information
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Sets the price of an item
     *
     * @param price the price of the item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the RFID number of the item
     *
     * @return the RFID number of the item
     */
    public String getRfidTagNumber() {
        return this.rfidTagNumber;
    }

    /**
     * Sets the RFID number according to the conditions of what is an RFID
     *
     * @param newID the new RFID to set to the item information
     * @throws IncorrectRFIDException is thrown when the inputted RFID
     * is an invalid input for an RFID format
     */
    public void setRfidTagNumber(String newID) throws IncorrectRFIDException {
        if (newID.length() == 9) {
            for (int i = 0; i < newID.length(); i++) {
                char currentCharacter = newID.charAt(i);
                if ((currentCharacter < 'A' || currentCharacter > 'F') &&
                        (currentCharacter < '0' || currentCharacter > '9')) {
                    throw new IncorrectRFIDException("Incorrect RFID!");
                }
            }
        } else {
            throw new IncorrectRFIDException("Incorrect RFID!");
        }
        this.rfidTagNumber = newID;
    }

    /**
     * Returns the original location of the item
     *
     * @return the original location of the item
     */
    public String getOriginalLocation() {
        return this.OriginalLocation;
    }

    /**
     * Sets the original location of the item according to the conditions
     * of a location format
     *
     * @param newLocation the new location that is to be set to the item
     * @throws IncorrectLocationException is thrown when an invalid input
     *                                    has been given to the new location
     */
    public void setOriginalLocation(String newLocation)
            throws IncorrectLocationException {
        if (newLocation.length() == 6) {
            if (newLocation.charAt(0) == 's') {
                for (int i = 1; i < newLocation.length(); i++) {
                    char currentCharacter = newLocation.charAt(i);
                    if (currentCharacter < '0' || currentCharacter > '9') {
                        throw new IncorrectLocationException
                                ("Incorrect Location!");
                    }
                }
            } else {
                throw new IncorrectLocationException("Incorrect Location!");
            }
        } else {
            throw new IncorrectLocationException("Incorrect Location!");
        }
        this.OriginalLocation = newLocation;
    }

    /**
     * Returns the current location of the item
     *
     * @return the current location of the item
     */
    public String getCurrentLocation() {
        return this.CurrentLocation;
    }

    /**
     * Sets a new current location to an item according to certain
     * conditional format
     *
     * @param newLocation the new location
     * @throws IncorrectLocationException is thrown when an incorrect
     *                                    location format has been given
     *                                    to the method
     */
    public void setCurrentLocation(String newLocation)
            throws IncorrectLocationException {
        if (newLocation.length() == 4) {
            if (newLocation.charAt(0) == 'c') {
                for (int i = 1; i < newLocation.length(); i++) {
                    char currentCharacter = newLocation.charAt(i);
                    if (currentCharacter < '0' || currentCharacter > '9') {
                        throw new IncorrectLocationException
                                ("Incorrect Location!");
                    }
                }
            } else {
                throw new IncorrectLocationException
                        ("Incorrect Location!");
            }
        } else if (newLocation.length() == 6) {
            if (newLocation.length() == 6) {
                if (newLocation.charAt(0) == 's') {
                    for (int i = 1; i < newLocation.length(); i++) {
                        char currentCharacter = newLocation.charAt(i);
                        if (currentCharacter < '0' || currentCharacter > '9'){
                            throw new IncorrectLocationException
                                    ("Incorrect Location!");
                        }
                    }
                } else {
                    throw new IncorrectLocationException
                            ("Incorrect Location!");
                }
            } else {
                throw new IncorrectLocationException("Incorrect Location!");
            }
            this.CurrentLocation = newLocation;
        } else if (newLocation.equalsIgnoreCase("out")) {
            this.CurrentLocation = newLocation;
        } else {
            throw new IncorrectLocationException("Incorrect Location!");
        }
        this.CurrentLocation = newLocation;
    }

}