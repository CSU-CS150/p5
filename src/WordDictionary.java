import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

// DO NOT MODIFY THIS FILE - but you should look through it, especially the constant variables

/**
 *
 * WordDictionary loads a dictionary in the format of
 *
 * <pre>
 *     (word) (Part of Speech): optional words
 * </pre>
 *
 *  The part of speech is a single character which is represented as follows:
 *  <ul>
 *      <li> A = adjective or adverb</li>
 *      <li> C = a conjunction</li>
 *      <li> P = pronoun </li>
 *      <li> V = verb </li>
 *      <li> N = noun </li>
 *      <li> I = interjection</li>*
 *  </ul>
 *
 * It leads the dictionary into a 'map' that allows for quick access of the words and their part. For simplicity
 * it only takes the first time it sees the word - thus there is an inherent bias on the part of the speech
 * for that word.
 *
 * <b>CS 150 Students do not need to modify this file</b>
 *
 * @author Albert Lionelle <br>
 *         lionelle@colostate.edu <br>
 *         Computer Science Department <br>
 *         Colorado State University
 * @version 1.0
 */
public class WordDictionary {

    private Map<String, String> dict = new TreeMap<>();


    /**
     * These are easy access to the parts of speech and how the words are identified.
     * Students should call them when determining the part of speech for the word.
     */
    public static final String ADJECTIVE = "A"; //"adjective";
    public static final String CONJUNCTION = "C"; //conjunction";
    public static final String PRONOUN = "P"; //'"pronoun";
    public static final String VERB =  "V"; //"verb";
    public static final String NOUN = "N"; //"noun";
    public static final String INTERJECTION = "I";
    public static final String UNKNOWN = "xxUNKNOWNNxx";

    private static final String DICTIONARY_DELIMITER = ":";


    /**
     * Returns the type of the word based on the word passed it.
     * @param word word to check
     * @return Returns the part of speech. It will either match one of the ones listed in the constant
     *         variables or UNKNOWN
     */
    public String getWordType(String word) {
        if(dict.containsKey(word)) {
            return dict.get(word);
        }
        return UNKNOWN;
    }


    /**
     * Splits a line and stores it in the dictionary map
     * @param line the line to look at
     */
    private void splitline(String line) {
        if(line.startsWith("//")) { // this is a comment - ignore it.
            return;
        }
        String word = line.substring(0, line.indexOf(" ")); // format is "word Part: variant words"
        if(! Character.isLetter(word.charAt(0))) {
            word = word.substring(1); // ignore first letter if it is a special character - dictionary stuff.
        }
        int index = line.indexOf(DICTIONARY_DELIMITER); // look for the the : key in finding it
        String part = line.substring(index -1, index);  // it is only a single character with the : after the part
        if(! dict.containsKey(word)) dict.put(word, part); // only add words once for this class
    }

    /**
     * Loads a passed in file into the dictionary
     * @param filename the name of the file to load
     */
    public void loadFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            String line;
            while(scanner.hasNext()) {
                line = scanner.nextLine().trim();  // grab te line, removing whitespace from beginning and end
                if(!line.isEmpty()) { // if the line is not empty
                    this.splitline(line); // attempt to split it.
                }
            }
            scanner.close();
        }catch(Exception ex) {
            System.err.printf("Dictionary file %s not found!", filename);
            System.exit(1);// exit with error
        }

    }
}
