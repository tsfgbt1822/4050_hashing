package assignment.dictionary;

import java.io.*;
import java.util.*;
import javafx.application.Platform;

/**
 * A Thread that contains the application we are going to animate
 *
 */

public class MisSpellActionThread implements Runnable {

    DictionaryController controller;
    private final String textFileName;
    private final String dictionaryFileName;

    private LinesToDisplay myLines;
    private DictionaryInterface<String, String> myDictionary;
    private boolean dictionaryLoaded;

    /**
     * Constructor for objects of class MisspellActionThread
     *
     * @param controller
     */
    public MisSpellActionThread(DictionaryController controller) {
        super();

        this.controller = controller;
        textFileName = "Dictionary/src/main/resources/assignment/dictionary/check.txt";
        dictionaryFileName = "Dictionary/src/main/resources/assignment/dictionary/sampleDictionary.txt";

        myDictionary = new HashedMapAdaptor<String, String>();
        myLines = new LinesToDisplay();
        dictionaryLoaded = false;

    }

    @Override
    public void run() {

        loadDictionary(dictionaryFileName, myDictionary);


        Platform.runLater(() -> {
            if (dictionaryLoaded) {
               controller.SetMsg("The Dictionary has been loaded"); 
            } else {
               controller.SetMsg("No Dictionary is loaded"); 
            }
        });
        
        checkWords(textFileName, myDictionary);

    }

    /**
     * Load the words into the dictionary.
     *
     * @param theFileName The name of the file holding the words to put in the
     * dictionary.
     * @param theDictionary The dictionary to load.
     */
    public void loadDictionary(String theFileName, DictionaryInterface<String, String> theDictionary) {
        try {
            File file = new File(theFileName);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                theDictionary.add(line.trim(), line.trim()); // Assuming the key and value are the same for the dictionary
            }
            input.close();
            dictionaryLoaded = true;
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary file not found: " + theFileName);
            e.printStackTrace();
        }
    }


    /**
     * Get the words to check, check them, then put Wordlets into myLines. When
     * a single line has been read do an animation step to wait for the user.
     *
     */
    public void checkWords(String theFileName, DictionaryInterface<String, String> theDictionary) {
        try {
            File file = new File(theFileName);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] words = line.split("\\s+"); // Split line into words based on whitespace

                for (String word : words) {
                    boolean isCorrect = checkWord(word, theDictionary);
                    Wordlet wordlet = new Wordlet(word, isCorrect); // Assuming Wordlet has a constructor that takes the word and a boolean for correctness

                    myLines.addWordlet(wordlet); // Add the wordlet to the current line
                }

                myLines.nextLine(); // Move to the next line after processing one line of text

                // Show the lines on UI
                showLines(myLines); // You might want to throttle this so it doesn't update too quickly for the user to follow
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Text file not found: " + theFileName);
            e.printStackTrace();
        }
    }



    /**
     * Check the spelling of a single word.
     *
     */
    public boolean checkWord(String word, DictionaryInterface<String, String> theDictionary) {
        return theDictionary.contains(word);
    }

    private void showLines(LinesToDisplay lines) {
        try {
            Thread.sleep(500);
            Platform.runLater(() -> {
                if (myLines != null) {
                    controller.UpdateView(lines);
                }
            });
        } catch (InterruptedException ex) {
        }
    }

} // end class MisspellActionThread

