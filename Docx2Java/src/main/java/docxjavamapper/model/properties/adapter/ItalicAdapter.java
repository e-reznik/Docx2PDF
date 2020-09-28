package docxjavamapper.model.properties.adapter;

import docxjavamapper.model.properties.Italic;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ItalicAdapter extends XmlAdapter<Italic, Boolean> {

    @Override
    public Italic marshal(final Boolean v) {
        return v != null && v ? new Italic() : null;
    }

    @Override
    public Boolean unmarshal(final Italic v) {
        return true;
    }
}
