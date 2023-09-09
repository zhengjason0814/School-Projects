// Jason Zheng 114907558 R30 Section 8

/**
 * This class represents a linked list class that will be used to
 * contain item information and manipulate it
 */
public class ItemList {
    private ItemInfoNode head;
    private ItemInfoNode tail;
    private ItemInfoNode cursor;

    /**
     * This is a constructor for creating a new item linked list
     */
    public ItemList() {
        head = null;
        tail = null;
        cursor = null;
    }

    /**
     * This method inserts an item's information into the
     * linked list item system
     *
     * TIME COMPLEXITY: O(n) , the method traverses through
     * the list in order to find the right place to put the
     * new node, so worst-case is the entire list, which is n
     *
     * @param name the name of the new item
     * @param rfidTag the RFID of the new item
     * @param price the price of the new item
     * @param initialPos the initial location of the new item
     * @throws IncorrectRFIDException is thrown whenever an invalid
     *                                input is given for an RFID
     * @throws IncorrectLocationException is thrown whenever an invalid
     *                                    input is given for a location
     */
    public void insertInfo
            (String name, String rfidTag, double price, String initialPos)
            throws IncorrectRFIDException, IncorrectLocationException {
        ItemInfo newInfo = new ItemInfo(name, price, rfidTag, initialPos);
        ItemInfoNode newInfoNode = new ItemInfoNode(newInfo);
        if (head == null) {
            head = newInfoNode;
            tail = newInfoNode;
            cursor = newInfoNode;
        } else if
        (head.getItemInfo().getRfidTagNumber().compareTo
                        (newInfoNode.getItemInfo().getRfidTagNumber()) > 0) {
            newInfoNode.setNext(head);
            head.setPrev(newInfoNode);
            head = newInfoNode;
        } else if (tail.getItemInfo().getRfidTagNumber().compareTo
                (newInfoNode.getItemInfo().getRfidTagNumber()) < 0) {
            newInfoNode.setPrev(tail);
            tail.setNext(newInfoNode);
            tail = newInfoNode;
        } else {
            cursor = head.getNext();
            while (cursor != null) {
                if (newInfoNode.getItemInfo().getRfidTagNumber().compareTo
                        (cursor.getItemInfo().getRfidTagNumber()) <= 0) {
                    newInfoNode.setNext(cursor);
                    newInfoNode.setPrev(cursor.getPrev());
                    cursor.getPrev().setNext(newInfoNode);
                    cursor.setPrev(newInfoNode);
                    break;
                } else {
                    cursor = cursor.getNext();
                }
            }
        }
    }

    /**
     * A helper method to remove nodes from the middle of a linked list
     *
     * TIME COMPLEXITY : O(1), removes a single node from a
     * list that was given in the parameters, no iterations so its constant
     *
     * @param node the node to be removed
     * @return the node that was removed
     */
    public ItemInfoNode removeSinglePurchased(ItemInfoNode node) {
        ItemInfoNode removed = node;
        node.setPrev(node.getNext());
        return removed;
    }

    /**
     * The method to remove all nodes that have an item inside
     * them that have been checked out
     *
     * TIME COMPLEXITY : O(n) , this method iterates through the whole
     * linked list and removes any nodes that have been purchased,
     * worst case scenario is going through entire list to find
     * purchased items to remove, which is n
     *
     * @throws EmptyLinkedListException is thrown when linked list is empty
     */
    public void removeAllPurchased() throws EmptyLinkedListException {
        if (head == null) {
            throw new EmptyLinkedListException("Linked List is empty!");
        }
        cursor = head.getNext();
        while (cursor != null && cursor.getNext() != null) {
            if (cursor.getItemInfo().getCurrentLocation().
                    compareToIgnoreCase("out") == 0) {
                ItemInfoNode temp = cursor.getNext();
                removeSinglePurchased(cursor);
                cursor = temp;
            } else {
                cursor = cursor.getNext();
            }
        }
        if (head != null && head.getItemInfo().getCurrentLocation()
                .equalsIgnoreCase("out")) {
            System.out.printf("%-20s%-20s%-20s%-25s%-25s\n",
                    "Item Name",
                    "RFID",
                    "Original Location",
                    "Current Location",
                    "Price");
            System.out.printf("%-20s%-20s%-20s%-25s%.2f\n" ,
                    head.getItemInfo().getName(),
                    head.getItemInfo().getRfidTagNumber(),
                    head.getItemInfo().getOriginalLocation(),
                    head.getItemInfo().getCurrentLocation(),
                    head.getItemInfo().getPrice());
            head = head.getNext();
        } else if (tail != null && tail.getItemInfo().getCurrentLocation()
                .equalsIgnoreCase("out")){
                System.out.printf("%-20s%-20s%-20s%-25s%-25s\n",
                        "Item Name",
                        "RFID",
                        "Original Location",
                        "Current Location",
                        "Price");
                System.out.printf("%-20s%-20s%-20s%-25s%.2f\n" ,
                        tail.getItemInfo().getName(),
                        tail.getItemInfo().getRfidTagNumber(),
                        tail.getItemInfo().getOriginalLocation(),
                        tail.getItemInfo().getCurrentLocation(),
                        tail.getItemInfo().getPrice());
                tail = tail.getPrev();
        }
    }

    /**
     * This method is for moving an item within the linked list to a new location
     *
     * TIME COMPLEXITY : O(n), this method needs to find a node with
     * the corresponding RFID and original location to move,
     * worst case scenario is the entire list, which is n
     *
     * @param rfidTag the RFID tag of the item we are moving
     * @param source the original location of the item we are moving
     * @param dest the location we want to move the item to
     * @return a boolean value for whether the item was moved or not
     * @throws EmptyLinkedListException is thrown if the linked list is empty
     * @throws IncorrectLocationException is thrown if an
     *                                    invalid input for location is given
     * @throws IncorrectRFIDException is thrown if an invalid input
     *                                for an RFID is given
     */
    public boolean moveItem(String rfidTag, String source, String dest)
            throws
            EmptyLinkedListException,
            IncorrectLocationException,
            IncorrectRFIDException {
        if (head == null) {
            throw new EmptyLinkedListException("Empty List!");
        } else if (rfidTag.length() == 9) {
            for (int i = 0; i < rfidTag.length(); i++) {
                char currentCharacter = rfidTag.charAt(i);
                if ((currentCharacter < 'A' || currentCharacter > 'F')
                        && (currentCharacter < '0' ||
                        currentCharacter > '9')) {
                    throw new IncorrectRFIDException("Incorrect RFID!");
                }
            }
        } else {
            throw new IncorrectRFIDException("Incorrect RFID!");
        }
        locationTest(source);
        locationTest(dest);
        cursor = head;
        while (cursor != null) {
            if (cursor.getItemInfo().getRfidTagNumber().compareTo(rfidTag)
                    == 0 &&
                    cursor.getItemInfo().getCurrentLocation().
                            compareTo(source) == 0) {
                if (cursor.getItemInfo().getCurrentLocation().
                        compareToIgnoreCase("out") == 0) {
                    throw new IncorrectLocationException
                            ("Out is not a valid entry for movement!");
                }
                cursor.getItemInfo().setCurrentLocation(dest);
                return true;
            } else {
                cursor = cursor.getNext();
            }
        }
        return false;
    }

    /**
     * The method that will print all items and its information into a table
     *
     * TIME COMPLEXITY : O(n) , this method has to go through the entire list
     * in order to print all the item information inside the linked list,
     * the entire list is n.
     *
     * @throws EmptyLinkedListException is thrown when linked list is empty
     */
    public void printAll() throws EmptyLinkedListException {
        if (head == null) {
            throw new EmptyLinkedListException("Empty List!");
        }
        System.out.printf("%-20s%-20s%-20s%-25s%-25s\n",
                "Item Name",
                "RFID",
                "Original Location",
                "Current Location",
                "Price");
        cursor = head;
        while(cursor != null) {
            System.out.printf("%-20s%-20s%-20s%-25s%.2f\n" ,
                    cursor.getItemInfo().getName(),
                    cursor.getItemInfo().getRfidTagNumber(),
                    cursor.getItemInfo().getOriginalLocation(),
                    cursor.getItemInfo().getCurrentLocation(),
                    cursor.getItemInfo().getPrice());
            cursor = cursor.getNext();
        }
    }

    /**
     * The method that will print out all items in a certain
     * user-inputted location
     *
     * TIME COMPLEXITY : O(n) , this method needs to find all the
     * items that have a certain location, and worst case scenario
     * is having to traverse entire list in order to find all the
     * items with a specified location, which list is n
     *
     * @param location the location of the items we want to show
     * @throws IncorrectLocationException is thrown when an invalid
     *                                    input for location is given
     * @throws EmptyLinkedListException is thrown when the linked
     *                                  list is empty
     */
    public void printByLocation(String location) throws
            IncorrectLocationException,
            EmptyLinkedListException {
        if (head == null) {
            throw new EmptyLinkedListException("Empty List!");
        }
        if (location.length() == 4) {
            if (location.charAt(0) == 'c') {
                for (int i = 1; i < location.length(); i++) {
                    char currentCharacter = location.charAt(i);
                    if (currentCharacter < '0' || currentCharacter > '9') {
                        throw new IncorrectLocationException
                                ("Incorrect Location!");
                    }
                }
            } else {
                throw new IncorrectLocationException("Incorrect Location!");
            }
        } else if (location.length() == 6) {
            if (location.length() == 6) {
                if (location.charAt(0) == 's') {
                    for (int i = 1; i < location.length(); i++) {
                        char currentCharacter = location.charAt(i);
                        if
                        (currentCharacter < '0' || currentCharacter > '9') {
                            throw new IncorrectLocationException
                                    ("Incorrect Location!");
                        }
                    }
                } else {
                    throw new IncorrectLocationException
                            ("Incorrect Location!");
                }
            } else {
                throw new IncorrectLocationException
                        ("Incorrect Location!");
            }
        } else {
            throw new IncorrectLocationException
                    ("Incorrect Location!");
        }
        System.out.printf("%-20s%-20s%-20s%-25s%-25s\n",
                "Item Name",
                "RFID",
                "Original Location",
                "Current Location",
                "Price");
        cursor = head;
        while(cursor != null) {
            if (cursor.getItemInfo().getCurrentLocation()
                    .compareTo(location) == 0) {
                System.out.printf("%-20s%-20s%-20s%-25s%.2f\n" ,
                        cursor.getItemInfo().getName(),
                        cursor.getItemInfo().getRfidTagNumber(),
                        cursor.getItemInfo().getOriginalLocation(),
                        cursor.getItemInfo().getCurrentLocation(),
                        cursor.getItemInfo().getPrice());
            }
            cursor = cursor.getNext();
        }
    }

    /**
     * The method that cleans the store, putting all items in
     * their original location
     *
     * TIME COMPLEXITY : O(n) , this method needs to traverse the
     * entire list in order to find the items that need to be
     * put into its original location, and list is n
     *
     * @throws IncorrectLocationException is thrown when an invalid
     *                                    input for location is given
     * @throws EmptyLinkedListException is thrown when the linked
     *                                  list is empty
     */
    public void cleanStore() throws
            IncorrectLocationException,
            EmptyLinkedListException {
        if (head == null) {
            throw new EmptyLinkedListException("Empty List!");
        }
        cursor = head;
        System.out.printf("%-20s%-20s%-20s%-25s%-25s\n",
                "Item Name",
                "RFID",
                "Original Location",
                "Current Location",
                "Price");
        while (cursor != null) {
            if (cursor.getItemInfo().getCurrentLocation().charAt(0) == 's'
                    && cursor.getItemInfo().getOriginalLocation().compareTo
                    (cursor.getItemInfo().getCurrentLocation()) != 0) {
                cursor.getItemInfo().setCurrentLocation
                        (cursor.getItemInfo().getOriginalLocation());
                System.out.printf("%-20s%-20s%-20s%-25s%.2f\n" ,
                        cursor.getItemInfo().getName(),
                        cursor.getItemInfo().getRfidTagNumber(),
                        cursor.getItemInfo().getOriginalLocation(),
                        cursor.getItemInfo().getCurrentLocation(),
                        cursor.getItemInfo().getPrice());
                cursor = cursor.getNext();
            } else {
                cursor = cursor.getNext();
            }
        }
    }

    /**
     * The method that checks out all items inside a cart location,
     * and returns the price of all items in the cart
     *
     * TIME COMPLEXITY : O(n) , this method checks out items inside a cart,
     * and worst case scenario is traversing the entire list to find all
     * items inside the cart that need to be checked out, which is n
     *
     * @param cartNumber the cart number of the items you want to check out
     * @return the price of the items inside the cart
     * @throws IncorrectLocationException is thrown when an invalid
     *                                    input for location is given
     * @throws EmptyLinkedListException is thrown when the linked list is
     *                                  empty
     */
    public double checkOut(String cartNumber) throws
            IncorrectLocationException,
            EmptyLinkedListException {
        if (head == null) {
            throw new EmptyLinkedListException("Empty List!");
        }
        if (cartNumber.charAt(0) != 'c') {
            throw new IncorrectLocationException
                    ("This is not a existing cart!");
        }
        double cost = 0;
        cursor = head;
        System.out.printf("%-20s%-20s%-20s%-25s%-25s\n",
                "Item Name",
                "RFID",
                "Original Location",
                "Current Location",
                "Price");
        while (cursor != null) {
            if (cursor.getItemInfo().getCurrentLocation().
                    compareTo(cartNumber) == 0) {
                cost += cursor.getItemInfo().getPrice();
                System.out.printf("%-20s%-20s%-20s%-25s%.2f\n" ,
                        cursor.getItemInfo().getName(),
                        cursor.getItemInfo().getRfidTagNumber(),
                        cursor.getItemInfo().getOriginalLocation(),
                        cursor.getItemInfo().getCurrentLocation(),
                        cursor.getItemInfo().getPrice());
                cursor.getItemInfo().setCurrentLocation("out");
            }
            cursor = cursor.getNext();
        }
        return cost;
    }

    /**
     * The optional method that prints out all the items that the user
     * had requested via RFID
     *
     * TIME COMPLEXITY : O(n) , the method that goes through the entire list,
     * finding all items that have the specified RFID and prints them,
     * which list is n
     *
     * @param rfid the RFID that the user inputted
     * @throws EmptyLinkedListException is thrown whenever the
     *                                  linked list is empty
     * @throws IncorrectRFIDException is thrown whenever an invalid
     *                                RFID is given for input
     */
    public void printRFID(String rfid) throws
            EmptyLinkedListException,
            IncorrectRFIDException {
        if (head == null) {
            throw new EmptyLinkedListException("Linked List is empty!");
        } else if (rfid.length() == 9) {
            for (int i = 0; i < rfid.length(); i++) {
                char currentCharacter = rfid.charAt(i);
                if ((currentCharacter < 'A' || currentCharacter > 'F')
                        && (currentCharacter < '0'
                        || currentCharacter > '9')) {
                    throw new IncorrectRFIDException("Incorrect RFID!");
                }
            }
        } else {
            throw new IncorrectRFIDException("Incorrect RFID!");
        }
        System.out.printf("%-20s%-20s%-20s%-25s%-25s\n",
                "Item Name",
                "RFID",
                "Original Location",
                "Current Location",
                "Price");
        cursor = head;
        while (cursor != null) {
            if (cursor.getItemInfo().getRfidTagNumber().
                    compareTo(rfid) == 0) {
                System.out.printf("%-20s%-20s%-20s%-25s%.2f\n" ,
                        cursor.getItemInfo().getName(),
                        cursor.getItemInfo().getRfidTagNumber(),
                        cursor.getItemInfo().getOriginalLocation(),
                        cursor.getItemInfo().getCurrentLocation(),
                        cursor.getItemInfo().getPrice());
            }
            cursor = cursor.getNext();
        }
    }

    /**
     * A helper method to check if given locations are valid
     *
     * TIME COMPLEXITY : O(1) , this method only checks for if a location
     * is a valid input for a method. Since there is no iterations,
     * this method is in constant time.
     *
     * @param location the location we are checking
     * @throws IncorrectLocationException is thrown if the location
     * given is invalid
     */

    public void locationTest(String location) throws
            IncorrectLocationException {
        if (location.length() == 4) {
            if (location.charAt(0) == 'c') {
                for (int i = 1; i < location.length(); i++) {
                    char currentCharacter = location.charAt(i);
                    if (currentCharacter < '0' || currentCharacter > '9') {
                        throw new IncorrectLocationException
                                ("Incorrect Location!");
                    }
                }
            } else {
                throw new IncorrectLocationException("Incorrect Location!");
            }
        } else if (location.length() == 6) {
            if (location.length() == 6) {
                if (location.charAt(0) == 's') {
                    for (int i = 1; i < location.length(); i++) {
                        char currentCharacter = location.charAt(i);
                        if
                        (currentCharacter < '0' || currentCharacter > '9') {
                            throw new IncorrectLocationException
                                    ("Incorrect Location!");
                        }
                    }
                } else {
                    throw new IncorrectLocationException
                            ("Incorrect Location!");
                }
            } else {
                throw new IncorrectLocationException
                        ("Incorrect Location!");
            }
        } else {
            throw new IncorrectLocationException
                    ("Incorrect Location!");
        }
    }
}
