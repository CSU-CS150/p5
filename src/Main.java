import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * Main driver file for Practical 5: Digital Humanities project.
 *
 * This file will read in files and help process them using Practical5.java
 *
 * @author Albert Lionelle <br>
 *        lionelle@colostate.edu <br>
 *        Computer Science Department <br>
 *        Colorado State University
 * @version 1.0
 */
public class Main {
    Practical5 p5 = new Practical5();


    void run(String[] files) {
        p5.testingMethod(); // so you can run your tests



        if(files.length == 0) { // no files passed in, so instead ask for system input
            System.out.println("No program arguments, reading from System.in (type ctrl-D or cmd-D if interactive console)");
            processFile(null, Practical5.AUTHOR, "Command Line Entry");  // assumes the program writer is the author
        }
        else {
            // print headers, as it will now load multiple files
            p5.writeHTMLHeader();
            // for every file passed in, we are going to generate statistics
            for (String file : files) {  // this is an example of a for:each loop
                processFile(file);  // process each file in turn
            }
        }
        p5.saveHtml();// add the closing HTML tags

    }

    /**
     * Tries to open a file and load it into a Scanner. If null is passed in for the filename
     * then it will load System.in
     * @param filename Name of file to load, or null for System.in
     * @return returns a scanner or null if a file cannot be found.
     */
    private Scanner getScanner(String filename) {
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
     * Overloaded method of {@link #processFile(String, String, String)}  Defaults
     * passing in by default, it passes in "No Author" and "No Title"
     * @param filename the file to look at or null if command line
     */
    private void processFile(String filename) {
        processFile(filename, "No author", "No title");
    }


    /**
     * Reads a file or system in if Null is passed in for the file name. After the file is
     * loaded, then it will go through line by line and call processLine(String)}
     * for each line passed in. After it is done processing the file, it will print out
     * stats in order of Title, Author and Statistics for the file
     * @param filename name of file to read or null to read from the command line
     * @param author the author of the work
     * @param title the title of the work in the file
     */
    private void processFile(String filename, String author, String title) {
        Scanner lines = getScanner(filename);  // gets either the file or system in
        if(lines == null) { return; } // if null is returned, then the file was not found

        while(lines.hasNext()) {  // keep looping until out of lines - which means for system in cntl D may be needed
            String line = lines.nextLine().trim(); // read a line, and remove leading and trailing white space

            if(line.isEmpty()) { continue; } // in case there is a blank line

            if(line.startsWith("title:")) {  // title will override the title parameter
                title = line.substring(line.indexOf(':')+1).trim(); // trim it and store it in the parameter
            } else if(line.startsWith("author:")) {  // author: will override the author parameter
                author = "by " + line.substring(line.indexOf(':')+1).trim(); // trim it and store it in the parameter
            } else p5.processLine(line); // call process line which modifies the stats
        }


        // waiting to print to make sure everything is printed in order for valid HTML
        if(filename == null) { p5.writeHTMLHeader();} // only print this for the System.in case - else it is printed before the loop
        p5.writeOutStats(title, author); // print out stats
        p5.resetStats(); // reset the variables, so the next file can be processed
    }


    /**
     * Loads the program. Designed to read in files and calculate the basic averages of the
     * word types from the file.
     * @param args consists of file names it loads
     */
    public static void main(String[] args) {
        Main mn = new Main();
        mn.run(args);



    }


}
