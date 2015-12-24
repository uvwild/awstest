package org.funtime.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

/**
 * Created by uv on 23/12/2015 for awstest
 */
public class CustomLatLngDeserializer extends StdDeserializer<LatLng> {

    public CustomLatLngDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LatLng deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        // read string as hashmap
//        HashMap<String,Object> latLngMap = new ObjectMapper().readValue(jsonParser.getText(), HashMap.class);
        LatLng latLng = new LatLng(0.,0.);
        return latLng;
    }
}
