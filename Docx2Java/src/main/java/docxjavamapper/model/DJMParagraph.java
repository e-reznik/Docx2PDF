package docxjavamapper.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import lombok.Data;

/**
 * p (Paragraph)
 *
 * This element specifies a paragraph of content in the document.
 *
 * The contents of a paragraph in a WordprocessingML document shall consist of
 * any combination of the following four kinds of content:
 *
 * Paragraph properties
 *
 * Annotations (bookmarks, comments, revisions)
 *
 * Custom markup
 *
 * Run level content (fields, hyperlinks, runs)
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DJMParagraph {

    @XmlElement(name = "P")
    private DJMParagraphProperties paragraphProperties;
    @XmlElement(name = "r")
    private List<DJMRun> runs;

}
