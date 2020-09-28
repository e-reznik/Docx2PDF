package com.mycompany.docxtopdflow.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * document (Document)
 *
 * This element specifies the contents of a main document part in a
 * WordprocessingML document.
 *
 */
@Data
@XmlRootElement(name = "document")
@XmlAccessorType(XmlAccessType.FIELD)
public class Document {

    @XmlElement(name = "body")
    private Body body;

}
