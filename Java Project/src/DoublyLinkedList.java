
/**
 * This class represents a doubly linked list data structure.
 * Each node of the list contains a piece of vocabulary data.
 */
public class DoublyLinkedList {
    private DNode head;
    private DNode tail;
    private int size;


    /**
     * Constructs an empty doubly linked list.
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds a new node with vocabulary data to the end of the list.
     *
     * @param data The vocabulary data to be added.
     */
    public void addToEnd(Vocab data) {
        DNode newDNode = new DNode(data);
        if (head == null) {
            head = newDNode;
            tail = newDNode;
        } else {
            tail.next = newDNode;
            newDNode.prev = tail;
            tail = newDNode;
        }
        size++;
    }

    /**
     * Adds a new node with vocabulary data to the beginning of the list.
     *
     * @param data The vocabulary data to be added.
     */
    public void addToStart(Vocab data) {
        DNode newDNode = new DNode(data);
        if (head == null) {
            head = newDNode;
            tail = newDNode;
        } else {
            newDNode.next = head;
            head.prev = newDNode;
            head = newDNode;
        }
        size++;
    }

    /**
     * Retrieves the vocabulary data at the specified index in the list.
     *
     * @param index The index of the node to retrieve.
     * @return The vocabulary data at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public Vocab getData(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }
        DNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }


    /**
     * Returns the size of the list.
     *
     * @return The number of nodes in the list.
     */
    public int getSize() {

        return size;
    }

    /**
     * Inserts a new node with vocabulary data before the node at the specified index.
     *
     * @param index The index before which the new node should be inserted.
     * @param data  The vocabulary data to be inserted.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void insertBeforeIndex(int index, Vocab data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }

        DNode newDNode = new DNode(data);
        if (index == 0) {
            addToStart(data);
        } else if (index == size) {
            addToEnd(data);
        } else {
            DNode current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            DNode prevDNode = current.prev;

            prevDNode.next = newDNode;
            newDNode.prev = prevDNode;
            newDNode.next = current;
            current.prev = newDNode;
            size++;
        }
    }


    /**
     * Inserts a new node with vocabulary data after the node at the specified index.
     *
     * @param index The index after which the new node should be inserted.
     * @param data  The vocabulary data to be inserted.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void insertAfterIndex(int index, Vocab data) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }

        DNode newDNode = new DNode(data);
        if (index == size - 1) {
            addToEnd(data);
        } else {
            DNode current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            DNode nextDNode = current.next;

            current.next = newDNode;
            newDNode.prev = current;
            newDNode.next = nextDNode;
            nextDNode.prev = newDNode;
            size++;
        }
    }



    /**
     * Removes the node at the specified index from the list.
     *
     * @param index The index of the node to remove.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }
        if (index == 0) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null; // If the list becomes empty after removing the first node
            }
        } else if (index == size - 1) {
            tail = tail.prev;
            tail.next = null;
        } else {
            DNode current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            DNode prevDNode = current.prev;
            DNode nextDNode = current.next;

            prevDNode.next = nextDNode;
            nextDNode.prev = prevDNode;
        }
        size--;
    }

    // Inner class representing a node in the doubly linked list
    private static class DNode {
        Vocab data;
        DNode prev;
        DNode next;

        /**
         * Constructs a new node with the given vocabulary data.
         *
         * @param data The vocabulary data to be stored in the node.
         */
        DNode(Vocab data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

}