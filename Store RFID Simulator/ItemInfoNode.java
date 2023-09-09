// Jason Zheng 114907558 R30 Section 8

/**
 * This class represents a node that contains item information
 * for a department store
 */
public class ItemInfoNode {
    private ItemInfo itemInfo;
    private ItemInfoNode prev;
    private ItemInfoNode next;

    /**
     * This is a constructor for an item information node
     *
     * @param itemInfo the item information that will be stored in the node
     */
    public ItemInfoNode(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
        this.prev = null;
        this.next = null;
    }

    /**
     * The getter method for the item information
     *
     * @return the item information
     */
    public ItemInfo getItemInfo() {
        return this.itemInfo;
    }

    /**
     * The setter for the item information inside a node
     *
     * @param info the item information
     */
    public void setItemInfo(ItemInfo info) {
        this.itemInfo = info;
    }

    /**
     * The getter for the previous link of an item node
     *
     * @return the previous link of current node
     */
    public ItemInfoNode getPrev() {
        return this.prev;
    }

    /**
     * The setter for the previous link of an item node
     *
     * @param prev the new previous link for an item node
     */
    public void setPrev(ItemInfoNode prev) {
        this.prev = prev;
    }

    /**
     * The getter method for the next link of an item node
     *
     * @return the next link of an item node
     */
    public ItemInfoNode getNext() {
        return next;
    }

    /**
     * The setter method for the next link of an item node
     *
     * @param next the next link of an item node
     */
    public void setNext(ItemInfoNode next) {
        this.next = next;
    }

}