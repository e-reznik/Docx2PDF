
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

class Helper {

    /**
     * Validates a given color. Valid color formats are: #000080, #fff, #FFFFFF,
     * red etc.
     *
     * The color constants are taken from the ColorConstants class from iText
     *
     * @param color the color to validate
     * @return result
     */
    public static boolean validateColor(String color) {
        if (color != null
                && (color.length() != 6 && color.length() != 3)) {
            throw new NumberFormatException(color + " is not a color");
        }
        Pattern pattern = Pattern.compile("^(#?[a-f0-9]{6}|#?[a-f0-9]{3}"
                + "|rgb *\\( *[0-9]{1,3}%? *, *[0-9]{1,3}%? *, *[0-9]{1,3}%? *\\)"
                + "|rgba *\\( *[0-9]{1,3}%? *, *[0-9]{1,3}%? *, *[0-9]{1,3}%? *, *[0-9]{1,3}%? *\\)"
                + "|BLACK|BLUE|CYAN|DARK_GRAY|GRAY|GREEN|LIGHT_GRAY|MAGENTA|ORANGE|PINK|RED|WHITE|YELLOW)$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(color);

        return matcher.matches();
    }

    /**
     * Converts a color from String to RGB. A color can be in HEX format or as a
     * word (constant). First it will be checked, if that constant is
     * recognizable by iText. If that's not the case, it should be HEX format,
     * so it will be parsed.
     *
     * @param hex
     * @return
     * @throws Exception if an unexpected exception occurs
     */
    public static Color hexToRgb(String hex) throws NumberFormatException, Exception {
        Color rgb;

        /* Checks, if that color exists as constant in iText class */
        try {
            Field f = ColorConstants.class.getField(hex.toUpperCase());
            rgb = (Color) f.get(null);
        } catch (NoSuchFieldException e) {
            /* If the color constant doesn't exist, this part will be executed */
            int r = Integer.valueOf(hex.substring(0, 2), 16);
            int g = Integer.valueOf(hex.substring(2, 4), 16);
            int b = Integer.valueOf(hex.substring(4, 6), 16);
            rgb = new DeviceRgb(r, g, b);
        }

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
        // TODO: Load images dynamically (extension)
        InputStream is = zipFile.getInputStream(zipFile.getEntry("word/media/" + name.toLowerCase() + ".png"));
        return is;
    }
}
