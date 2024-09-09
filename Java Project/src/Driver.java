// -----------------------------------------------------
// Assignment 3
// Question: Main
// Written by: Firas Al Haddad (40283180) Section S, Danial Kouba (4027789) Section QQ
// -----------------------------------------------------
//
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This class is the driver class for the vocabulary control center. It contains the main method and the user interface
 * for the vocabulary control center. The user can browse topics, insert a new topic before or after another one, remove a
 * topic, modify a topic, search topics for a word, load from a file, show all words starting with a given letter, save to
 * a file, and exit the program.
 */
public class Driver {
    static DoublyLinkedList vocabs = new DoublyLinkedList();
    static ArrayList<String> sameLetter = new ArrayList<>();
    static Scanner scanner = null;
    static final String FILENAME = "input.txt";

    /**
     * The main method that initializes the program and interacts with the user through a menu system.
     *
     * @param args Command-line arguments.
     * @throws IOException If an I/O error occurs while reading from the input file.
     */
    public static void main(String[] args) throws IOException {
        scanner = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);

        readAllWords(FILENAME);

        int userChoice;
        do {
            displayMenu();
            userChoice = getUserChoice();

            switch (userChoice) {
                case 1:
                    browseTopic();
                    break;
                case 2:
                    insertTopicBefore();
                    break;
                case 3:
                    insertTopicAfter();
                    break;
                case 4:
                    removeTopic();
                    break;
                case 5:
                    modifyTopic();
                    break;
                case 6:
                    searchForWord();
                    break;
                case 7:
                    System.out.println("Please enter the name of the file :");
                    String name = sc2.nextLine();
                    readAllWords(name);
                    break;
                case 8:
                    showWordsWithLetter();
                    break;
                case 9:
                    saveToFile(FILENAME);
                    break;
                case 0:
                    System.out.println("Thank you for using our vocabulary control center.");
                    System.exit(1);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (true);
    }

    /**
     * This method displays the main menu of the Vocabulary Control Center.
     * It lists all the available options for the user to choose from.
     */
    private static void displayMenu() {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Vocabulary Control Center");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("1 Browse a topic");
        System.out.println("2 Insert a new topic before another one");
        System.out.println("3 Insert a new topic after another one");
        System.out.println("4 Remove a topic");
        System.out.println("5 Modify a topic");
        System.out.println("6 Search topics for a word");
        System.out.println("7 Load from a file");
        System.out.println("8 Show all words starting with a given letter");
        System.out.println("9 Save to file");
        System.out.println("0 Exit");
        System.out.println("-------------------------------------------------------------------------");
        System.out.print("Enter Your Choice : ");
    }

    /**
     * Retrieves the user's choice from the menu.
     *
     * @return The user's choice.
     */
    private static int getUserChoice() {
        return scanner.nextInt();
    }

    /**
     * This method allows the user to browse through the topics available in the vocabulary control center.
     * The user can select a topic to view its associated words. The browsing continues until the user chooses to exit.
     */
    private static void browseTopic() {
        int userInput;
        do {
            printPickATopic();
            userInput = scanner.nextInt();
            if (userInput != 0) {
                displayTopic(userInput);
            }
        } while (userInput != 0);
    }

    /**
     * This method displays the selected topic and its associated words.
     * It formats the output for better readability.
     *
     * @param topicIndex The index of the topic to be displayed.
     */
    private static void displayTopic(int topicIndex) {
        Vocab vocab = vocabs.getData(topicIndex - 1);
        System.out.println("Topic :" + vocab.getTopic());
        for (int i = 0; i < vocab.getWords().getSize(); i++) {
            if (i % 4 == 0) {
                System.out.println();
            }
            System.out.printf("%-2d: %-35s", (i + 1), vocab.getWords().getData(i));
        }
        System.out.println();
    }
    /**
     * This method allows the user to insert a new topic before another one in the vocabulary control center.
     * The user is prompted to enter the topic name and the words associated with the topic.
     * The new topic is then inserted before the topic chosen by the user.
     */
    private static void insertTopicBefore() {
        int userInput;
        String topicName, word;
        do {
            Vocab v = new Vocab();
            SinglyLinkedList wordsList = new SinglyLinkedList();
            printPickATopic();
            userInput = scanner.nextInt();

            if (userInput != 0) {
                System.out.println("Enter a topic name");
                scanner.nextLine(); // Consume newline
                topicName = scanner.nextLine();
                v.setTopic(topicName);
                System.out.println("Enter a word - to quit press Enter: ");
                while (true) {
                    word = scanner.nextLine();
                    if (word.isEmpty()) {
                        break;
                    }
                    wordsList.addToEnd(word);
                }
                v.setWords(wordsList);
                vocabs.insertBeforeIndex(userInput - 1, v);
            }
        } while (userInput != 0);
    }

    /**
     * This method allows the user to insert a new topic after another one in the vocabulary control center.
     * The user is prompted to enter the topic name and the words associated with the topic.
     * The new topic is then inserted after the topic chosen by the user.
     */
    private static void insertTopicAfter() {
        int userInput;
        String topicName, word;
        do {
            Vocab v = new Vocab();
            SinglyLinkedList wordsList = new SinglyLinkedList();
            printPickATopic();
            userInput = scanner.nextInt();

            if (userInput != 0) {
                System.out.println("Enter a topic name");
                scanner.nextLine(); // Consume newline
                topicName = scanner.nextLine();
                v.setTopic(topicName);
                System.out.println("Enter a word - to quit press Enter: ");
                while (true) {
                    word = scanner.nextLine();
                    if (word.isEmpty()) {
                        break;
                    }
                    wordsList.addToEnd(word);
                }
                v.setWords(wordsList);
                vocabs.insertAfterIndex(userInput - 1, v);
            }
        } while (userInput != 0);
    }

    /**
     * This method allows the user to remove a topic from the vocabulary control center.
     * The user is prompted to select a topic to remove.
     * The selected topic is then removed from the vocabulary control center.
     */
    private static void removeTopic() {
        int userInput;
        do {
            printPickATopic();
            userInput = scanner.nextInt();
            if (userInput != 0) {
                vocabs.removeAtIndex(userInput - 1);
            }
        } while (userInput != 0);
    }

    /**
     * This method allows the user to modify a topic in the vocabulary control center.
     * The user is prompted to select a topic to modify.
     * The selected topic can then be modified by adding a word, removing a word, or changing a word.
     */
    private static void modifyTopic() {
        int userInput;
        do {
            printPickATopic();
            userInput = scanner.nextInt();
            if (userInput != 0) {
                String input;
                do {
                    System.out.println("-------------------------------------------------------------------------");
                    System.out.println("\t Modify Topics Menu");
                    System.out.println("-------------------------------------------------------------------------");
                    System.out.println(" a Add a word \n r Remove a word \n c Change a word \n 0 Exit");
                    input = scanner.next();
                    switch (input.toLowerCase()) {
                        case "a":
                            addWordToTopic(userInput);
                            break;
                        case "r":
                            removeWordFromTopic(userInput);
                            break;
                        case "c":
                            changeWordInTopic(userInput);
                            break;
                        case "0":
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } while (!input.equals("0"));
            }
        } while (userInput != 0);
    }

    /**
     * This method allows the user to add a new word to a selected topic in the vocabulary control center.
     * The user is prompted to enter the word to be added.
     *
     * @param topicIndex The index of the topic to which the word will be added.
     */
    private static void addWordToTopic(int topicIndex) {
        System.out.println("Enter a word to add to the topic:");
        String word = scanner.next();
        vocabs.getData(topicIndex - 1).getWords().addToEnd(word);
        System.out.println("Word added successfully.");
    }

    /**
     * This method allows the user to remove a word from a selected topic in the vocabulary control center.
     * The user is prompted to enter the word to be removed.
     *
     * @param topicIndex The index of the topic from which the word will be removed.
     */
    private static void removeWordFromTopic(int topicIndex) {
        System.out.println("Enter a word to remove from the topic:");
        String word = scanner.next();
        Vocab vocab = vocabs.getData(topicIndex - 1);
        int wordIndex = vocab.getWords().indexOf(word);
        if (wordIndex != -1) {
            vocab.getWords().removeAtIndex(wordIndex);
            System.out.println("Word removed successfully.");
        } else {
            System.out.println("Word not found in the topic.");
        }
    }

    /**
     * This method allows the user to change a word in a selected topic in the vocabulary control center.
     * The user is prompted to enter the word they want to change.
     *
     * @param topicIndex The index of the topic in which the word will be changed.
     */
    private static void changeWordInTopic(int topicIndex) {
        System.out.println("Enter the word you want to change:");
        String oldWord = scanner.next();
        System.out.println("Enter the new word:");
        String newWord = scanner.next();
        Vocab vocab = vocabs.getData(topicIndex - 1);
        if (vocab.getWords().replaceWord(oldWord, newWord)) {
            System.out.println("Word changed successfully.");
        } else {
            System.out.println("Word not found in the topic.");
        }
    }

    /**
     * This method allows the user to search for a word across all topics in the vocabulary control center.
     * The user is prompted to enter the word they want to search for.
     */
    private static void searchForWord() {
        System.out.println("Please enter the word:");
        String word = scanner.next();
        findAllTopicsContainingWord(word);
    }

    /**
     * This method allows the user to search for all words that start with a specific letter across all topics in the vocabulary control center.
     * The user is prompted to enter the letter they want to search for.
     */
    private static void showWordsWithLetter() {
        System.out.println("Which letter would you like to search for:");
        String letter = scanner.next().substring(0, 1);
        clearSameLetterList(letter);
        Collections.sort(sameLetter);
        if (sameLetter.isEmpty()) {
            System.out.println("There are no words that start with the letter: " + letter);
        } else {
            System.out.println("Words starting with the letter " + letter + ":");
            for (int i = 0; i < sameLetter.size(); i++) {
                System.out.println((i + 1) + ". " + sameLetter.get(i));
            }
        }
    }


    /**
     * This method saves the current state of the vocabulary control center to a file.
     * The file will contain all the topics and their associated words.
     *
     * @param file The name of the file to which the data will be saved.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    private static void saveToFile(String file) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            for (int i = 0; i < vocabs.getSize(); i++) {
                fw.write(vocabs.getData(i).toString());
            }
        }
    }

    /**
     * This method prints the list of topics available in the vocabulary control center.
     * It formats the output for better readability and user interaction.
     * The user can select a topic by entering the corresponding number.
     */
    private static void printPickATopic() {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("\t\tPick a topic");
        System.out.println("-------------------------------------------------------------------------");
        for (int i = 0; i < vocabs.getSize(); i++) {
            System.out.println(" " + (i + 1) + "  " + vocabs.getData(i).getTopic());
        }
        System.out.println(" 0  Exit");
    }

    /**
     * This method reads all the words from a given file and stores them in the vocabulary control center.
     * Each line in the file represents a word. Topics are indicated by a line starting with '#'.
     * The words following a topic line are associated with that topic.
     *
     * @param file The name of the file from which the words will be read.
     * @throws IOException If an I/O error occurs while reading from the file.
     */
    private static void readAllWords(String file) throws IOException {
        BufferedReader br = null;
        String topic;
        try {
            String line;
            br = new BufferedReader(new FileReader(file));
            Vocab v = null;
            vocabs = new DoublyLinkedList();

            while ((line = br.readLine()) != null) {
                if (line.contains("#")) {
                    topic = line.substring(1);
                    v = new Vocab();
                    v.setTopic(topic);
                    vocabs.addToEnd(v);
                } else if (!line.isEmpty()) {
                    if (v != null) {
                        v.getWords().addToEnd(line);
                    }
                }
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        System.out.println("Done loading");
    }

    /**
     * This method searches for all topics that contain a specific word in the vocabulary control center.
     * It creates a list of topics where the word is found.
     *
     * @param word The word to be searched for across all topics.
     */
    private static void findAllTopicsContainingWord(String word) {
        ArrayList<String> topics = new ArrayList<>();
        for (int i = 0; i < vocabs.getSize(); i++) {
            for (int j = 0; j < vocabs.getData(i).getWords().getSize(); j++) {
                if (word.equalsIgnoreCase(vocabs.getData(i).getWords().getData(j))) {
                    topics.add(vocabs.getData(i).getTopic());
                    if (i == vocabs.getSize() - 1) {
                        break;
                    } else {
                        i++;
                        j = 0;
                    }
                }
            }
        }
        if (topics.isEmpty()) {
            System.out.println("The word: " + word + " is not in any topics!");
        } else {
            System.out.println("The word " + word + " is found in:");
            for (int i = 0; i < topics.size(); i++) {
                System.out.println((i + 1) + ". " + topics.get(i));
            }
        }
    }

    /**
     * This method clears the list of words that start with a specific letter.
     * It is used before a new search for words starting with a specific letter is initiated.
     *
     * @param letter The letter that was used for the previous search.
     */
    private static void clearSameLetterList(String letter) {
        sameLetter.removeAll(sameLetter);
        for (int i = 0; i < vocabs.getSize(); i++) {
            for (int j = 0; j < vocabs.getData(i).getWords().getSize(); j++) {
                if (letter.equalsIgnoreCase(vocabs.getData(i).getWords().getData(j).substring(0, 1))) {
                    sameLetter.add(vocabs.getData(i).getWords().getData(j));
                }
            }
        }
        if (sameLetter.isEmpty()) {
            System.out.println("There are no words that start with the letter: " + letter);
        }
    }
}
