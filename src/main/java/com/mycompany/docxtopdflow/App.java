package com.mycompany.docxtopdflow;

import com.mycompany.docxtopdflow.model.Document;
import com.mycompany.docxtopdflow.model.Paragraph;
import com.mycompany.docxtopdflow.model.Run;
import com.mycompany.docxtopdflow.model.RunProperties;
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

//            System.out.println("Stop");
//            for (Paragraph p : document.getBody().getParagraphs()) {
//                for (Run r : p.getRuns()) {
//                    RunProperties rp = r.getRunProperties();
//                    if (rp != null) {
//                        System.out.println(rp.isBold());
//                    }
//                }
//            }
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
