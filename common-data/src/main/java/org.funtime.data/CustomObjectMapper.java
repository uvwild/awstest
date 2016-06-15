package org.funtime.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.android.gms.maps.model.LatLng;
import org.funtime.helper.*;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * FIXME: this is not executed in integration test context
 * Created by uv on 23/12/2015 for awstest
 */
@Component
@Primary
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
        mod.addKeyDeserializer(MyLatLng.class, new CustomMyLatLngKeyDeserializer());

        this.registerModule(mod);    // Register the module on the mapper
        this.addMixIn(ResponseEntity.class, ResponseEntityMixin.class);
    }


}
