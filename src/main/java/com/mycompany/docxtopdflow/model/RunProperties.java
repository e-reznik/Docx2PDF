package com.mycompany.docxtopdflow.model;

import lombok.Data;

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
public class RunProperties {

}
