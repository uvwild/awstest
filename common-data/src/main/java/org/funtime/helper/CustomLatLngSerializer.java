package org.funtime.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.android.gms.maps.model.LatLng;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.IOException;

/**
 * Created by uv on 23/12/2015 for awstest
 */
public class CustomLatLngSerializer extends StdSerializer<LatLng> {
    public CustomLatLngSerializer(Class<LatLng> t) {
        super(t);
    }

    @Override
    public void serialize(LatLng latLng, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        MyLatLng latLngLocal = new MyLatLng(latLng.latitude, latLng.longitude);
        jsonGenerator.writeString(latLngLocal.toString());
    }

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class MyLatLng {
        final private double latitude;
        final private double longitude;

        @Override
        public String toString() {
            return String.format("{latitude:%f,longitude:%f}", latitude, longitude);
        }
    }
}
