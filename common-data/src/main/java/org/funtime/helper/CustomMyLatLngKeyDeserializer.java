package org.funtime.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.funtime.data.MyLatLng;

import java.io.IOException;

/**
 * Created by uv on 23/12/2015 for awstest
 */
public class CustomMyLatLngKeyDeserializer extends KeyDeserializer {

    //  takes a string lat/lng: (52.0,13.0) and reads the values into new LatLng type
    @Override
    public MyLatLng deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        int start = key.indexOf("(");
        int end = key.indexOf(")");
        String fields[]  = key.substring(start+1, end).split(",");
        double latitude = Double.parseDouble(fields[0]);
        double longitude = Double.parseDouble(fields[1]);
        MyLatLng myLatLng = new MyLatLng(latitude, longitude);
        return myLatLng;
    }
}
