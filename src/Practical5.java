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
 * @author YOUR NAME <br>
 *         YOUR EMAIL <br>
 *         Computer Science Department <br>
 *         Colorado State University
 * @version 201910
 */
public class Practical5 {


    final static String AUTHOR = "YOUR NAME";

    WordDictionary dictionary = new WordDictionary("dictionary.txt"); // do not modify
    PrintWriter writer = buildPrintWriter("out.html"); // do not modify
    HtmlFormatter html = new HtmlFormatter(AUTHOR); // do not modify

    // the following variables help you keep track of the stats in the file/input
    int nounCounter = 0;
    int verbCounter = 0;
    int interjectionCounter = 0;
    int conjunctionCounter = 0;
    int adjectiveCounter = 0;
    int pronounCounter = 0;
    int spokenCounter = 0;
    int unknownCounter = 0;
    int wordCount = 0;
    int lineCounter = 0;

    public void testingMethod() {
        //System.out.println("TESTING");
        // use this method to test various features as you implement them. You should then remove it on your final submission
        // example System.out.println(html.heading1("Testing Heading 1"));
        // example System.out.println(html.heading2("Testing Heading 2"));

    }





    /// -------------- do not modify ----------------
    /// we did this one for you, as you haven't learned Exception Handling which is required for
    /// file input and output.


    /**
     * Builds a PrintWriter based on the file name. If it can't save the file out, it will exit the program
     * @param file name of file to write to
     * @return the a PrintWriter Object
     */
    private static PrintWriter buildPrintWriter(String file) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File(file));
        } catch (FileNotFoundException e) {
            System.err.printf("Error creating %s to write.", file);
            System.exit(1); // exit with error code
        }
        return writer;
    }





}
