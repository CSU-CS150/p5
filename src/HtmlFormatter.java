/**
 *
 * This file converts passed in text by surrounding it with the correct HTML tags. It does not do
 *  any writing to an HTML file on its only, but simply return strings.
 *
 * @author YOUR NAME <br>
 *          YOUR EMAIL <br>
 *         Computer Science Department <br>
 *         Colorado State University
 * @version 1.0
 */
public class HtmlFormatter {
    String author = "";  // stores the page author


    /**
     * Constructor that sets the author of the HTML page for use in the {@link #getHead()} method
     * @param author The author of the html page
     */
    public HtmlFormatter(String author) {
        this.author = author;
    }

    /**
     * Returns the basic head of the HTML file as required for the CS 150 assignment.
     * @return returns a String containing the header information needed.
     */
    public String getHead() {
        String rtn = "<!DOCTYPE html>\n<html>\n<head>\n";
        rtn += "\t<meta charset=\"UTF-8\">";
        rtn += String.format("\t<meta name=\"author\" content=\"%s\">\n", author); // here is an example of String.format
        rtn += "\t<link rel=\"stylesheet\" href=\"http://static.colostate.edu/fonts/proxima-nova/proxima.css\">\n";
        rtn += "\t<link href=http://www.cs.colostate.edu/~cs150/.Fall18/assignments/p5/support/style.css type=text/css rel=stylesheet>\n";
        rtn += "</head>\n<body>";
        return rtn;
    }

    /**
     * Closes the body of the HTML document
     * @return <pre></body></html></pre>
     */
    public String getClose() {
       return "</body>\n</html>";
    }

    // Implement the methods - as specified in the assignment specifications. String.format may be needed.






}
