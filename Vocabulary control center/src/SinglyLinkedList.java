

/**
 * This class represents a singly linked list data structure.
 * Each node of the list contains a string value.
 */
public class SinglyLinkedList {
    private SNode head;
    private int size;

    /**
     * Constructs an empty singly linked list.
     */
    public SinglyLinkedList() {
        head = null;
    }

    /**
     * Adds a new node with the given string value to the beginning of the list.
     *
     * @param v The string value to be added.
     */
    public void addToStart(String v) {
        head = new SNode(v, head);
    }

    /**
     * Returns the number of nodes in the list.
     *
     * @return The number of nodes in the list.
     */
    public int getSize() {
        int count = 0;
        SNode temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }


    /**
     * Returns a string containing the string values of all nodes in the list.
     *
     * @return A string containing the string values of all nodes in the list.
     */
    public String StringAllNodes() {
        SNode temp = head;
        StringBuilder str = new StringBuilder();
        while (temp != null) {
            str.append(temp.data).append("\n");
            temp = temp.next;
        }
        return str.toString();
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return getSize() == 0;
    }

    /**
     * Finds and returns the last node in the list.
     *
     * @return The last node in the list, or null if the list is empty.
     */
    private SNode findLastNode() {
        if (isEmpty()) {
            return null;
        }
        SNode prev = head;
        SNode curr = head.next;
        while (curr != null) {
            prev = curr;
            curr = curr.next;
        }
        return prev;
    }

    /**
     * Adds a new node with the given string value to the end of the list.
     *
     * @param v The string value to be added.
     */
    public void addToEnd(String v) {
        if (isEmpty()) {
            this.addToStart(v);
        }
        else {
            SNode last = findLastNode();
            if (last != null) {
                last.next = new SNode(v, null);
            }
        }
    }


    /**
     * Retrieves the node at the specified index in the list.
     *
     * @param i The index of the node to retrieve.
     * @return The node at the specified index.
     * @throws IllegalArgumentException if the index is invalid.
     */
    private SNode getNode(int i) {
        if (i < 0 || i >= this.getSize()) {
            throw new IllegalArgumentException("invalid index: " + i);
        }
        SNode temp = head;
        int k = 0;
        while (k != i) {
            temp = temp.next;
            k = k + 1;
        }
        return temp;
    }

    /**
     * Retrieves the string value at the specified index in the list.
     *
     * @param index The index of the string value to retrieve.
     * @return The string value at the specified index.
     */
    public String getData(int index) {
        SNode temp = getNode(index);
        return temp.data;
    }

    /**
     * Removes the node at the specified index in the list.
     *
     * @param i The index of the node to remove.
     */
    public void removeAtIndex(int i) {
        if (i == 0)
            head = head.next;
        else {
            SNode previous = getNode(i - 1);
            previous.next = previous.next.next;
        }
        size = size - 1;
    }

    /**
     * Finds the index of the first occurrence of the given string value in the list.
     *
     * @param word The string value to search for.
     * @return The index of the first occurrence of the string value, or -1 if not found.
     */
    public int indexOf(String word) {
        SNode current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(word)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    /**
     * Replaces the first occurrence of the old word with the new word in the list.
     *
     * @param oldWord The word to be replaced.
     * @param newWord The word to replace the old word.
     * @return true if the replacement was successful, false otherwise.
     */
    public boolean replaceWord(String oldWord, String newWord) {
        SNode current = head;
        while (current != null) {
            if (current.data.equals(oldWord)) {
                current.data = newWord;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Inner class representing a node in the singly linked list
    private static class SNode {
        SNode next;
        String data;

        public SNode() {
            data = null;
            next = null;
        }

        /**
         * Constructs a new node with the given string value and next node reference.
         *
         * @param v The string value to be stored in the node.
         * @param n The reference to the next node.
         */
        public SNode(String v, SNode n) {
            data = v;
            next = n;
        }
    }
}
