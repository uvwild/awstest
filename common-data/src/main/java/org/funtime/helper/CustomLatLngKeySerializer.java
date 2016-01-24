package org.funtime.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializer;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

/**
 * We have to write a fieldName for the key as a simple string!  (http://stackoverflow.com/questions/6574636/serializing-mapdate-string-with-jackson)
 * Created by uv on 23/12/2015 for awstest
 */
public class CustomLatLngKeySerializer extends StdKeySerializer {

    @Override
    public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        assert (obj instanceof LatLng);
        LatLng latLng = (LatLng) obj;
        jsonGenerator.writeFieldName(latLng.toString());
    }
}
