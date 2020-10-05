
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

class Helper {

    /**
     * Converts HEX to RGB.
     *
     * @param hex
     * @return
     * @throws NumberFormatException if no color is provided (e.g. auto)
     * @throws Exception if an unexpected exception occurs
     */
    public static Color hexToRgb(String hex) throws NumberFormatException, Exception {
        if (hex != null && hex.length() != 6) {
            throw new NumberFormatException(hex + " is not a color");
        }
        int r = Integer.valueOf(hex.substring(0, 2), 16);
        int g = Integer.valueOf(hex.substring(2, 4), 16);
        int b = Integer.valueOf(hex.substring(4, 6), 16);

        Color rgb = new DeviceRgb(r, g, b);
        return rgb;
    }

    /**
     * Returns the stream to a document.xml from the DOCX-archive.
     *
     * @param docx the Docx file
     * @return the InputStream of the document.xml
     * @throws IOException
     */
    public static InputStream getDocument(File docx) throws IOException {
        ZipFile zipFile = new ZipFile(docx);
        InputStream is = zipFile.getInputStream(zipFile.getEntry("word/document.xml"));
        return is;
    }

    /**
     * Returns a stream to an image from the DOCX-archive.
     *
     * @param docx the Docx file
     * @param name name of the desired image
     * @return the InputStream of the document.xml
     * @throws IOException
     */
    public static InputStream getImage(File docx, String name) throws IOException {
        ZipFile zipFile = new ZipFile(docx);
        InputStream is = zipFile.getInputStream(zipFile.getEntry("word/media/" + name.toLowerCase() + ".png"));
        return is;
    }
}
