package docxjavamapper;

import com.fasterxml.jackson.databind.util.Converter;
import docxjavamapper.model.DJMDocument;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class DocxJM {

    private final static Logger LOGGER = Logger.getLogger(Converter.class.getName());

    public DocxJM() {
    }

//    public static void main(String[] args) throws IOException {
//        DocxMapper mapper = new DocxMapper();
//        Document doc = mapper.map("ad");
//        System.out.println("stop");
//    }
    /**
     * Maps a given Docx to a Pojo and returns it.
     *
     * @param str path to the Docx file
     * @return the mapped Pojo
     * @throws java.io.IOException if file doesn't exist
     */
    public DJMDocument map(String str) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            throw new IOException("File doesn't exist: " + file);
        }

        DJMDocument document = null;

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(DJMDocument.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            document = (DJMDocument) jaxbUnmarshaller.unmarshal(file);

            System.out.println("stop");
        } catch (JAXBException ex) {
            Logger.getLogger(DocxJM.class.getName()).log(Level.SEVERE, null, ex);
        }

        return document;
    }

//    private String getFile(InputStream is) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        String line;
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//        br.close();
//        return sb.toString();
//    }

}
