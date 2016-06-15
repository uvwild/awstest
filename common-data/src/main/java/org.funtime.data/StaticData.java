package org.funtime.data;

import com.google.android.gms.maps.model.LatLng;

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
    public static MyLatLng testCoordM = new MyLatLng(52.f, 13.f);
    public static LatLng fusionCoord = LatLngConstants.FusionFestival;
    public static List<Long> testMeasurements = Arrays.asList(13l,12l,15l,14l);
    public static LatLngValueMap latLngValueTestMap = new LatLngValueMap(testCoord, testMeasurements);
    public static MyLatLngValueMap myLatLngValueTestMap = new MyLatLngValueMap(testCoordM, testMeasurements);
    public static TimedLatLngValueMap timedLatLngValueTestMap = new TimedLatLngValueMap(testdate, latLngValueTestMap);

    public static String latLngValueTestMapJson = "{\"lat/lng: (52.0,13.0)\":[13,12,15,14]}";
    public static String timedLatLngValueTestMapJson = "{\"123467890\":{\"lat/lng: (52.0,13.0)\":[13,12,15,14]}}";
}
