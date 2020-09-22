package com.mycompany.docxtopdflow.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * r (Text Run)
 *
 * This element specifies a run of content in the parent field, hyperlink,
 * custom XML element, structured document tag, smart tag, or paragraph.
 *
 * The contents of a run in a WordprocessingML document shall consist of any
 * combination of run content.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Run {

    @XmlElement(name="rPr")
    RunProperties runProperties;
    @XmlElement(name="t")
    String text;

}
