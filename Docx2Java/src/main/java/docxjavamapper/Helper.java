package docxjavamapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

public class Helper {

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



}
