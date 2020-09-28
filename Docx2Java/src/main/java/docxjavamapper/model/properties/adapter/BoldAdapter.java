package docxjavamapper.model.properties.adapter;

import docxjavamapper.model.properties.Bold;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BoldAdapter extends XmlAdapter<Bold, Boolean> {

    @Override
    public Bold marshal(final Boolean v) {
        return v != null && v ? new Bold() : null;
    }

    @Override
    public Boolean unmarshal(final Bold v) {
        return true;
    }
}
