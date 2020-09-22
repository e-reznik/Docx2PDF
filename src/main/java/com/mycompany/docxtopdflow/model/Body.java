package com.mycompany.docxtopdflow.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * The container for the block level structures such as paragraphs, tables,
 * annotations, and others specified in the ISO/IEC 29500 specification.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Body {

    @XmlElement(name = "p")
    private List<Paragraph> paragraphs;

}
