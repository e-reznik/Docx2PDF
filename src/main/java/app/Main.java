package app;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String docIn = "/home/docs/in.docx";
        String docOut = "/home/docs/out.pdf";
        String fontsPath = "/home/docs/fonts/";

        try {
            Converter app = new Converter(docIn, docOut, fontsPath);

            // fontPath can be null. In this case, a standard font will be used: Helvetica
            // Converter app = new Converter(docIn, docOut, null);
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "File not found", ex);
        }
    }

}
