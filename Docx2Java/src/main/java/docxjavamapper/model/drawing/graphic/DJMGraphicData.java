package docxjavamapper.model.drawing.graphic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DJMGraphicData {

    @XmlElement(namespace = "http://schemas.openxmlformats.org/drawingml/2006/picture")
    private DJMPic pic;

}
