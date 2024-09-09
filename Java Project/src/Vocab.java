/**
 * This class represents a vocabulary list associated with a specific topic.
 * It implements the Comparable interface to allow sorting based on topic.
 */
public class Vocab implements Comparable<Vocab> {
    private SinglyLinkedList words; // List of words related to the topic
    private String topic; // Topic of the vocabulary list

    /**
     * Constructs a new Vocab object with an empty list of words.
     */
    public Vocab() {
        words = new SinglyLinkedList();
    }

    /**
     * Retrieves the list of words.
     *
     * @return The list of words related to the topic.
     */
    public SinglyLinkedList getWords() {
        return words;
    }

    /**
     * Sets the list of words.
     *
     * @param words The list of words to set.
     */
    public void setWords(SinglyLinkedList words) {
        this.words = words;
    }

    /**
     * Retrieves the topic of the vocabulary list.
     *
     * @return The topic of the vocabulary list.
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Sets the topic of the vocabulary list.
     *
     * @param topic The topic to set.
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Generates a string representation of the vocabulary list.
     *
     * @return A string containing the topic and all words in the list.
     */
    @Override
    public String toString() {
        return "#" + topic + " \n" + words.StringAllNodes() + "\n";
    }

    /**
     * Compares this Vocab object with another based on their topics.
     *
     * @param other The Vocab object to compare to.
     * @return A negative integer, zero, or a positive integer if this object is less than, equal to,
     *         or greater than the specified object respectively, based on their topics.
     */
    @Override
    public int compareTo(Vocab other) {
        return this.topic.compareTo(other.getTopic());
    }
}
