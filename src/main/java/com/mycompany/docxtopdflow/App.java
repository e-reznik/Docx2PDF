package com.mycompany.docxtopdflow;

import com.mycompany.docxtopdflow.model.Document;
import com.mycompany.docxtopdflow.model.Paragraph;
import com.mycompany.docxtopdflow.model.Run;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Named
public class App {

    public void bla() {

        File file = new File("/home/evgenij/docs/document.xml");

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Document.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Document document = (Document) jaxbUnmarshaller.unmarshal(file);

            for (Paragraph p : document.getBody().getParagraphs()) {
                for (Run r : p.getRuns()) {
                    if (r.getText() != null && !r.getText().equals("null") && !r.getText().equals("")) {
                        System.out.println(r.getText());
                    }

                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getFile(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

}
