package docxjavamapper.model.drawing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DJMPosition {

    @XmlElement(name = "posOffset", namespace = "http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing")
    private int posOffset;

}
