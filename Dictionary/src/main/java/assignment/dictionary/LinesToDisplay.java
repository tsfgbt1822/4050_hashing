package assignment.dictionary;

import java.util.Iterator;

/**
 * A class that will be used to display the lines of text that are corrected.
 */
public class LinesToDisplay {

    public static final int LINES = 10; // Display 10 lines
    private AList<Wordlet>[] lines;     // Assuming AList is similar to ArrayList
    private int currentLine;

    /**
     * Constructor for objects of class LinesToDisplay.
     */
    public LinesToDisplay() {
        // Initialize the array of lists
        lines = new AList[LINES];
        for (int i = 0; i < LINES; i++) {
            lines[i] = new AList<Wordlet>(); // Assuming AList has a default constructor
        }
        currentLine = 0;
    }

    /**
     * Add a new wordlet to the current line.
     */
    public void addWordlet(Wordlet w) {
        // Add the wordlet to the current line
        if (lines[currentLine] != null) {
            lines[currentLine].add(w); // Assuming AList has an add method
        }
    }

    /**
     * Go to the next line, if the number of lines has exceeded LINES, shift
     * them all up by one.
     */
    public void nextLine() {
        currentLine++;
        if (currentLine >= LINES) {
            // Shift all lines up by one
            for (int i = 1; i < LINES; i++) {
                lines[i - 1] = lines[i];
            }
            // Initialize a new line at the end
            lines[LINES - 1] = new AList<Wordlet>();
            currentLine = LINES - 1;
        }
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public AList<Wordlet>[] getLines() {
        return lines;
    }
}
