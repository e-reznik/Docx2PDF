package com.mycompany.docxtopdflow.model;

import lombok.Data;

/**
 * pPr (Paragraph Properties)
 *
 * This element specifies a set of paragraph properties which shall be applied
 * to the contents of the parent paragraph after all style/numbering/table
 * properties have been applied to the text. These properties are defined as
 * direct formatting, since they are directly applied to the paragraph and
 * supersede any formatting from styles.
 *
 */
@Data
public class ParagraphProperties {

    private String pStyle;

}
