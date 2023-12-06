/**
 * This class represents a node inside a binary tree that will
 * act as a question answer format when called. Each node
 * points to another node until it finishes the inquiry at the
 * leaf.
 */
public class TreeNode {
    private TreeNode[] references;
    private String label;
    private String message;
    private String prompt;

    /**
     * This is the constructor for creating one of the nodes,
     * where there are parameters required to construct
     * the question answer node.
     *
     * @param label the label of the node
     * @param message the message of the node
     * @param prompt the prompt of the node
     */
    public TreeNode(String label, String message, String prompt) {
        references = new TreeNode[9];
        this.label = label;
        this.message = message;
        this.prompt = prompt;
    }

    /**
     * The getter for the node reference array, used for traversing tree
     *
     * @return the array of references for each node
     */
    public TreeNode[] getReferences() {
        return references;
    }

    /**
     * The setter for the node reference array, set a new array for a node
     *
     * @param references the new reference array
     */
    public void setReferences(TreeNode[] references) {
        this.references = references;
    }

    /**
     * The getter for the node label
     *
     * @return the node label
     */
    public String getLabel() {
        return label;
    }

    /**
     * The setter for the node label, sets a new label
     *
     * @param label the new label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * The getter for the node message
     *
     * @return the node message
     */
    public String getMessage() {
        return message;
    }

    /**
     * The setter for the node message, sets a new message
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * The getter for the node prompt
     *
     * @return the node prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * The setter for the node prompt, sets a new prompt
     *
     * @param prompt the new prompt
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * This method checks if the current node is a leaf
     *
     * @return true or false depending if node is leaf
     */
    public boolean isLeaf() {
        for (int i = 0; i < references.length; i++) {
            if (references[i] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method traverses and prints
     * the tree in preorder format.
     */
    public void preOrder() {
        System.out.println(getLabel());
        System.out.println(getPrompt());
        System.out.println(getMessage());
        System.out.println();
        for (int i = 0; i < references.length; i++) {
            if (references[i] != null) {
                references[i].preOrder();
            }
        }
    }

    /**
     * This is a helper method for traversing the tree and finding
     * a certain node specified in the parameters
     *
     * @param label the label of the node attempting to find
     * @param current where we want to start traversing
     * @return the node we are trying to find
     */
    public TreeNode traversalHelper(String label, TreeNode current) {
        if (current == null) {
            return null;
        }
        if (current.getLabel().equals(label)) {
            return current;
        }
        for (int i = 0; i < current.getReferences().length; i++) {
            TreeNode answer = traversalHelper
                    (label,current.getReferences()[i]);
            if (answer != null) {
                return answer;
            }
        }
        return null;
    }

    /**
     * The method that returns the size of a node specified
     *
     * @param treeNode the node we are trying to find size of
     * @return the actual size of the node array
     */
    public int size(TreeNode[] treeNode) {
        int counter = 0;
        for(int i = 0; i < treeNode.length; i++) {
            if (treeNode[i] != null) {
                counter++;
            }
        }
        return counter;
    }

}
