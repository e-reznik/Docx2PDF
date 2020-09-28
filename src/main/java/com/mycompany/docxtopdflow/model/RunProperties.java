package com.mycompany.docxtopdflow.model;

import com.mycompany.docxtopdflow.model.properties.Color;
import com.mycompany.docxtopdflow.model.properties.adapter.BoldAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * rPr (Run Properties)
 *
 * This element specifies a set of run properties which shall be applied to the
 * contents of the parent run after all style formatting has been applied to the
 * text. These properties are defined as direct formatting, since they are
 * directly applied to the run and supersede any formatting from styles.
 *
 * This formatting is applied at the following location in the style hierarchy:
 *
 * Document defaults
 *
 * Table styles
 *
 * Numbering styles
 *
 * Paragraph styles
 *
 * Character styles
 *
 * Direct formatting (this element)
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class RunProperties {

    @XmlElement
    Color color;

    @XmlElement(name = "b")
    @XmlJavaTypeAdapter(BoldAdapter.class)
    @Getter(AccessLevel.NONE)
    Boolean isBold = false;

    public Boolean isBold() {
        return isBold;
    }

}
