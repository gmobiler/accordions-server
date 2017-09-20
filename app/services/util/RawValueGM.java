package services.util;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.util.RawValue;

public class RawValueGM extends RawValue {
    public RawValueGM(String v) {
        super(v);
    }

    public RawValueGM(SerializableString v) {
        super(v);
    }

    public RawValueGM(JsonSerializable v) {
        super(v);
    }

    protected RawValueGM(Object value, boolean bogus) {
        super(value, bogus);
    }

    @Override
    public String toString() {
        return _value.toString();
    }
}
