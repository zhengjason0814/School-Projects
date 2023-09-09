// Jason Zheng 114907558 R30 Section 8
/**
 * This class represents a string node for
 * the stack class for the history implementation
 */
public class StringNode implements Cloneable {
    private String string;
    private StringNode link;

    /**
     * This represents the constructor for the string node
     *
     * @param string the string inside the string node
     */
    public StringNode(String string) {
        this.string = string;
        this.link = null;
    }

    /**
     * This method represents the getter for the string node
     *
     * @return the string for the string node
     */
    public String getString() {
        return string;
    }

    /**
     * This method represents the setter for the string node, setting
     * a new string for the node
     *
     * @param string the new string to replace the current one
     */
    public void setString(String string) {
        this.string = string;
    }

    /**
     * This method represents the getter for the next string node
     *
     * @return the reference for the next string node
     */
    public StringNode getLink() {
        return link;
    }

    /**
     * This method represents the setter for the next string node,
     * setting a new reference for the current node
     *
     * @param next the new reference for the next string node
     */
    public void setLink(StringNode next) {
        this.link = next;
    }
}
