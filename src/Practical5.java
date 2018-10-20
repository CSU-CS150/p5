import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * Practical 5 takes a look at Digital Humanities, and how computer science can help take
 * a look at grammar types and author 'fingerprints' as every author tends to have a unique
 * style. For this assignment, you will be looping through lines of files and then the
 * words in each line. Using a WordDictionary, it will determine the part of speech
 * of that word, and categorize them all. After that, it will print out the stats to
 * both the console and an html file.
 *
 * @author Albert Lionelle <br>
 *         lionelle@colostate.edu <br>
 *         Computer Science Department <br>
 *         Colorado State University
 * @version 1.0
 */
public class Practical5 {


    final static String AUTHOR          = "Albert Lionelle";
    final static String DEFAULT_OUTPUT  = "out.html"; // do not modify
    final static String DICTIONARY_FILE = "dictionary.txt"; // do not modify

    WordDictionary dictionary = null; // do not modify
    PrintWriter writer = null; // do not modify
    HtmlFormatter html = null; // do not modify

    // the following variables help you keep track of the stats in the file/input
    int nounCounter = 0;
    int verbCounter = 0;
    int interjectionCounter = 0;
    int conjunctionCounter = 0;
    int adjectiveCounter = 0;
    int pronounCounter = 0;
    int unknownCounter = 0;
    int wordCount = 0;
    int lineCounter = 0;


    /**
     *  Writes a string both to the System.out and to the PrintWriter which is {@link #writer}.
     * @param str the string to write to both system.out and a file via PrintWriter writer
     */
    void output(String str) {
        System.out.println(str);
        writer.println(str);
    }

    /**
     * Overloaded methos of {@link #processFile(String, String, String)}  Defaults
     * passing in by default, it passes in "No Author" and "No Title"
     * @param filename
     */
    void processFile(String filename) {
        processFile(filename, "No author", "No title");
    }

    /**
     * Reads a file or system in if Null is passed in for the file name. After the file is
     * loaded, then it will go through line by line and call {@link #processLine(String)}
     * for each line passed in. After it is done processing the file, it will print out
     * stats in order of Title, Author and Statistics for the file
     * @param filename name of file to read or null to read from the command line
     * @param author the author of the work
     * @param title the title of the work in the file
     */
    void processFile(String filename, String author, String title) {
        Scanner lines = getScanner(filename);  // gets either the file or system in
        if(lines == null) { return; } // if null is returned, then the file was not found

        while(lines.hasNext()) {  // keep looping until out of lines - which means for system in cntl D may be needed
            String line = lines.nextLine().trim(); // read a line, and remove leading and trailing white space

            if(line.isEmpty()) { continue; } // in case there is a blank line
            else if(line.startsWith("title:")) {  // title will override the title parameter
                title = line.substring(line.indexOf(':')+1).trim(); // trim it and store it in the parameter
            } else if(line.startsWith("author:")) {  // author: will override the author parameter
                author = "by " + line.substring(line.indexOf(':')+1).trim(); // trim it and store it in the parameter
            } else processLine(line); // call process line which modifies the stats
        }

        if(filename == null ) {writeHTMLHeader();} // in case the entry is command line  - else called outside this method
        // waiting to print to make sure everything is printed in order for valid HTML
        output(html.getHeading2(title)); // printing the name of the work analyzed
        output(html.getParagraph(author)); // printing the name of the author
        writeOutStats(); // print out stats
        resetStats(); // reset the variables, so the next file can be processed
    }

    /**
     * Resets the counters to zero
     */
    void resetStats() {
        nounCounter = 0;
        verbCounter = 0;
        interjectionCounter = 0;
        conjunctionCounter = 0;
        adjectiveCounter = 0;
        pronounCounter = 0;
        unknownCounter = 0;
        wordCount = 0;
        lineCounter = 0;
    }


    /**
     * Cleans a word removing anything that isn't a standard alpha-letter. Digits and special characters
     * are not removed. Additionally, it forces all characters to be lowercase.
     * @param word the word to be 'cleaned'
     * @return a lowercase word without digits or special characters
     */
    String cleanWord(String word) {
        String rtn = "";
        for(int i = 0; i < word.length(); i++) {
            char tmp = word.charAt(i);
            if(Character.isLetter(tmp)) {
                rtn += tmp;
            }
        }
        return rtn.toLowerCase();
    }

    /**
     * Processes a single line in the file by calling a Scanner on the String line, and then
     * cycling through each line. It will increment the counters based on the
     * word that has been cleaned by cleanWord into dictionary to get the wordType.
     * @param line The line to be processed and analysed
     */
    void processLine(String line) {
        lineCounter++;
        Scanner scanner = new Scanner(line);

        String word;
        while(scanner.hasNext()) {
            word = cleanWord(scanner.next());
            if(word.isEmpty()) continue;

            wordCount++;
            String type = dictionary.getWordType(word);

            switch(type) {
                case WordDictionary.ADJECTIVE:
                    adjectiveCounter++;
                    break;
                case WordDictionary.CONJUNCTION:
                    conjunctionCounter++;
                    break;
                case WordDictionary.NOUN:
                    nounCounter++;
                    break;
                case WordDictionary.PRONOUN:
                    pronounCounter++;
                    break;
                case WordDictionary.VERB:
                    verbCounter++;
                    break;
               case WordDictionary.INTERJECTION:
                    interjectionCounter++;
                    break;
                default:
                    unknownCounter++;
            }
        } // end loop
    }


    /**
     * Computes the percent  returns it as a String restricted by two decimals points. For example:
     * <pre>
     *     ints 2 and 19 are passed in, 10.52% is returned
     *     ints 5 and 10 are passed in, 50.00% is returned
     *     ints 6 and 100 are passed in, 6.00% is returned
     * </pre>
     *
     * String.format is a useful method to look at.
     *
     * @param x  the first variable to be divided by
     * @param y  the second variable to to divide the first by (x/y)
     * @return  Returns a percent formatted by two decimal points. 1.00% etc
     */
    String percent(int x, int y) {
        double dbl = (double)x / y;
        return String.format("%.2f%%", dbl*100);
    }


    /**
     * Write out the states as an HTML unordered list. Stats should be
     * written in alphabetical order with the exception of Unknown and Words per line being last. For example:
     * <pre>
     *     <ul>
     * 		<li>Adjective/Adverb Average: 33.33%</li>
     * 		<li>Conjunction/Preposition Average: 0.00%</li>
     * 		<li>Interjection Average: 16.67%</li>
     * 		<li>Noun Average: 50.00%</li>
     * 		<li>Pronoun Average: 0.00%</li>
     * 		<li>Verb Average: 0.00%</li>
     * 		<li>Unknown Average: 0.00%</li>
     * 		<li>Words per line: 1.20</li>
     * 	</ul>
     * </pre>
     * @see #percent(int, int)
     */
    void writeOutStats() {
        output(html.getListOpen());
        output(html.getListElement("Adjective/Adverb Average: " + percent(adjectiveCounter, wordCount)));
        output(html.getListElement("Conjunction/Preposition Average: " + percent(conjunctionCounter, wordCount)));
        output(html.getListElement("Interjection Average: " + percent(interjectionCounter, wordCount)));
        output(html.getListElement("Noun Average: " + percent(nounCounter, wordCount)));
        output(html.getListElement("Pronoun Average: " + percent(pronounCounter, wordCount)));
        output(html.getListElement("Verb Average: " + percent(verbCounter, wordCount)));
        output(html.getListElement("Unknown Average: " + percent(unknownCounter, wordCount)));
        output(html.getListElement(String.format("Words per line: %.2f", (double)wordCount / lineCounter)));
        output(html.getListClose());

    }




    /// -------------- do not modify ----------------

    /**
     * Tries to open a file and load it into a Scanner. If null is passed in for the filename
     * then it will load System.in
     * @param filename Name of file to load, or null for System.in
     * @return returns a scanner or null if a file cannot be found.
     */
    Scanner getScanner(String filename) {
        try {
            Scanner scanner;
            if(filename != null) scanner = new Scanner(new File(filename));
            else scanner = new Scanner(System.in);
            return scanner;
        } catch (FileNotFoundException e) {
            System.err.printf("File  %s not found. Skipping.", filename);
            return null;
        }
    }

    /**
     * Loads the dictionary file into a a WordDictionary. It will initialize
     * the dictionary to {@link #dictionary}.
     * @param dictionaryFile name of dictionary file
     * @return the Practical5 object.
     */
    public Practical5 loadDictionary(String dictionaryFile) {
        dictionary = new WordDictionary();
        dictionary.loadFile(dictionaryFile);
        return this;
    }

    /**
     * Creates an HTML formatter object, and initializes it to {@link #html}
     * @param author the author of the HTML file
     * @return the Practical5 object.
     */
    public Practical5 buildHTMLFormatter(String author) {
        html = new HtmlFormatter(author);
        return this;
    }

    /**
     * Builds a PrintWriter based on the file name. If it can't save the file out, it will exit the program
     * @param file name of file to write to
     * @return the Practical5 object
     */
    public Practical5 buildPrintWriter(String file) {
        try {
            writer = new PrintWriter(new File(file));
        } catch (FileNotFoundException e) {
            System.err.printf("Error creating %s to write.", file);
            System.exit(1); // exit with error code
        }
        return this;
    }

    /**
     * Writes the Header and Title of the HTML file
     */
    public void writeHTMLHeader() {
        output(html.getHead());
        output(html.getHeading1("Practical 5: Analysing Literature by " + AUTHOR));
    }

    /**
     * Writes the closing tags of the HTML file, and
     */
    public void saveHtml() {
        output(html.getClose());
        writer.close();
    }

    /**
     * Loads the program. Designed to read in files and calculate the basic averages of the
     * word types from the file.
     * @param args consists of file names it loads
     */
    public static void main(String[] args) {
       // first build the various objects we need for this to work.
        Practical5 p5 = new Practical5()  // builds the p5 object into instance memory
                            .buildPrintWriter(DEFAULT_OUTPUT)  // setups the PrintWriter to write out to files
                            .buildHTMLFormatter(AUTHOR)  // builds the HTML formatter
                            .loadDictionary(DICTIONARY_FILE); // loads the dictionary file

        if(args.length == 0) { // no files passed in, so instead ask for system input
            System.out.println("No program arguments, reading from System.in (type ctrl-D or cmd-D if interactive console)");
            p5.processFile(null, AUTHOR, "Command Line Entry");  // assumes the program writer is the author
        }
        else {
            p5.writeHTMLHeader();  // print headers, as it will now load multiple files
            // for every file passed in, we are going to generate statistics
            for (String file : args) {  // this is an example of a for:each loop
                p5.processFile(file);  // process each file in turn
            }
        }
        p5.saveHtml();// add the closing HTML tags



    }

}
