import java.util.Scanner;
public class Tree {
    private TreeNode root;

    /**
     * This constructor creates a new tree for placement
     * of new nodes, represents a branch system of question and
     * answers
     */
    public Tree() {
        root = null;
    }

    public TreeNode getRoot() {
        return this.root;
    }
    public void setRoot(TreeNode newRoot) {
        this.root = newRoot;
    }
    /**
     * A method to add a TreeNode to the tree. The location of this node will
     * be specified according to the user, and once specified, will
     * input the new node in the left most open part of the reference array
     *
     * @param label the label of the new node
     * @param prompt the prompt of the new node
     * @param message the message of the new node
     * @param parentLabel the parent of the new node
     * @return a boolean value for whether the new node was added or not
     */
    public boolean addNode
    (String label, String prompt, String message, String parentLabel) {
        TreeNode newNode = new TreeNode(label, message, prompt);
        if (root == null) {
            root = newNode;
            return true;
        } else {
            for(int i = 0; i < getNodeReference(parentLabel).
                    getReferences().length; i++) {
                if (getNodeReference
                        (parentLabel).getReferences()[i] == null) {
                    getNodeReference
                            (parentLabel).getReferences()[i] = newNode;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method returns the node we are trying
     * to get the reference of
     *
     * @param label the label of the node we are trying to find
     * @return the node we are trying to find
     */
    public TreeNode getNodeReference(String label) {
        TreeNode answer = root.traversalHelper(label,root);
        return answer;
    }

    /**
     * This method starts the question and answer system,
     * where it will prompt the user to respond to questions with answers
     */
    public void beginSession() {
        Scanner input = new Scanner(System.in);
        TreeNode current = getRoot();
        System.out.println();
        System.out.println("Help Session Initiating...");

        while (current != null) {
            System.out.println(current.getMessage());

            if (current.isLeaf()) {
                System.out.println("Thank you for using our support " +
                        "system, have a nice day!");
                break;
            }

            TreeNode[] childNode = current.getReferences();
            for (int i = 0; i < childNode.length; i++) {
                if (childNode[i] != null) {
                    System.out.println("(" + (i + 1) + ") " +
                            childNode[i].getPrompt());
                }
            }
            System.out.println("(B) Go back");
            System.out.println("(0) Quit Session");

            String selection = input.nextLine();
            if (isNumber(selection)) {
                if (Integer.parseInt(selection) < 0 ||
                        Integer.parseInt(selection) >
                                current.size(childNode)) {
                    throw new IllegalArgumentException
                            ("Wrong choice! Please choose a valid " +
                                    "choice next time.");
                }
            } else {
                if (!selection.equals("B")) {
                    throw new IllegalArgumentException
                            ("Wrong choice! Please choose " +
                                    "a valid choice next time.");
                }
            }
            System.out.println("You have chosen choice " + selection);
            System.out.println();

            if (isNumber(selection)) {
                if (Integer.parseInt(selection) == 0) {
                    System.out.println("Exiting...");
                    break;
                }
            } else {
                if (selection.equals("B") && current == root) {
                    System.out.println("You cannot go back " +
                            "further than this!" +
                            " Returning to menu and exiting help session...");
                    break;
                } else if (selection.equals("B")) {
                    System.out.println("Going back...");
                    current = findParent(getRoot(),current);
                    continue;
                }
            }
                current = childNode[Integer.parseInt(selection) - 1];
        }

        System.out.println("Help Session Concluded!");
    }

    /**
     * This method returns the parent of the node we are trying to find
     *
     * @param current the current node in the recursive method
     * @param child the child of the node we are using to find the parent
     * @return the parent node of the child we put into the method parameter
     */
    public TreeNode findParent(TreeNode current, TreeNode child) {
        if (current == null) {
            return null;
        }
        TreeNode[] children = current.getReferences();
        for (int i = 0; i < children.length; i++) {

            if (children[i] == child) {
                return current;
            } else {
                TreeNode parent = findParent(children[i], child);
                if (parent != null) {
                    return parent;
                }
            }

        }
        return null;
    }

    /**
     * This method checks if the string inputted inside the parameter
     * of this method is an integer when parsed to an int.
     *
     * @param numberString the string we are going to parse to check
     * @return a boolean value based on whether the string is an int
     */
    public boolean isNumber(String numberString) {
        try {
            int newNum = Integer.parseInt(numberString);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

}
