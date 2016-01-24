package org.funtime.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.android.gms.maps.model.LatLng;
import org.funtime.helper.CustomLatLngDeserializer;
import org.funtime.helper.CustomLatLngKeyDeserializer;
import org.funtime.helper.CustomLatLngKeySerializer;
import org.funtime.helper.CustomLatLngSerializer;
import org.springframework.stereotype.Component;

/**
 * FIXME: this is not executed in integration test context
 * Created by uv on 23/12/2015 for awstest
 */
@Component
public class CustomObjectMapper extends ObjectMapper {


    public CustomObjectMapper() {
        super();
        // Create the module
        SimpleModule mod = new SimpleModule("awsTest Module");

        // Add the custom serializer to the module
        mod.addSerializer(new CustomLatLngSerializer(LatLng.class));
        mod.addDeserializer(LatLng.class, new CustomLatLngDeserializer(LatLng.class));
        mod.addKeySerializer(LatLng.class, new CustomLatLngKeySerializer());
        mod.addKeyDeserializer(LatLng.class, new CustomLatLngKeyDeserializer());

        this.registerModule(mod);    // Register the module on the mapper
    }

}
