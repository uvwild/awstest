package org.funtime.config;

import com.google.android.gms.maps.model.LatLng;
import org.funtime.data.LatLngValueMap;
import org.funtime.data.TimedLatLngValueMap;

import java.util.Arrays;
import java.util.List;

/**
 * Created by uv on 23/12/2015 for awstest
 */
public class StaticData {
    public static long testdate = 123467890;
//    @JsonSerialize(using = CustomLatLngSerializer.class)           ... non functional in this place !!!
//    @JsonDeserialize(using = CustomLatLngDeserializer.class)
    public static LatLng testCoord = new LatLng(52.f, 13.f);
    public static List<Long> testMeasurements = Arrays.asList(13l,12l,15l,14l);
    public static LatLngValueMap latLngValueTestMap;
    public static TimedLatLngValueMap timedLatLngValueTestMap;

    public static String latLngValueTestMapJson = "{\"lat/lng: (52.0,13.0)\":[13,12,15,14]}";
    public static String timedLatLngValueTestMapJson = "{\"123467890\":{\"lat/lng: (52.0,13.0)\":[13,12,15,14]}}";

    static {
        latLngValueTestMap = new LatLngValueMap();
        latLngValueTestMap.put(testCoord, testMeasurements);
        timedLatLngValueTestMap = new TimedLatLngValueMap(testdate, latLngValueTestMap);
    }
}
