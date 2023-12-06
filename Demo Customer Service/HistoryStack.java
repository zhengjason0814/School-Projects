import java.util.EmptyStackException;

/**
 * This class represents a history stack, where it will store
 * actions made by the user into a stack and records what they
 * do
 */
public class HistoryStack implements Cloneable {
    private StringNode top;

    /**
     * This constructor creates a new empty stack
     * to represent the user's history.
     */
    public HistoryStack() {
        this.top = null;
    }

    /**
     * This method represents a push method for a stack,
     * adding a new element on top of the stack.
     *
     * @param item the item to be pushed onto the stack
     */
    public void push(String item) {
        StringNode newNode = new StringNode(item);
        if (top != null) {
            newNode.setLink(top);
        }
        top = newNode;
    }

    /**
     * This method represents the pop method of a stack,
     * removing and returning the element at the top of the
     * stack
     *
     * @return the top element of the history stack
     */
    public String pop() {
        String answer;
        if (top == null) {
            throw new EmptyStackException();
        }
        answer = top.getString();
        top = top.getLink();
        return answer;
    }

    /**
     * This method clones and returns a linked list
     * implemented stack method in order to be used
     * to display the history without altering the actual
     * stack.
     *
     * @return the clone of the stack
     */
    public Object clone() {
        HistoryStack answer;
        try {
            answer = (HistoryStack) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        StringNode current = this.top;
        StringNode newTop = null;
        StringNode prevNewNode = null;
        while (current != null) {
            StringNode newNode = new StringNode(current.getString());
            if (newTop == null) {
                newTop = newNode;
            } else {
                prevNewNode.setLink(newNode);
            }
            prevNewNode = newNode;
            current = current.getLink();
        }
        answer.top = newTop;
        return answer;
    }


    /**
     * The getter for the top reference of the history stack
     *
     * @return the top reference of the stack
     */
    public StringNode getTop() {
        return top;
    }

    /**
     * The setter for the top reference of the history stack,
     * sets a new top for the history stack
     *
     * @param top the new top reference to be assigned
     */
    public void setTop(StringNode top) {
        this.top = top;
    }
}
