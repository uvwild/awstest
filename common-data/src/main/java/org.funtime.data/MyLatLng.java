package org.funtime.data;

import com.google.android.gms.maps.model.LatLng;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by uv on 23/12/2015 for awstest
 * extra pojo class for json mapping or transfer
 */
@RequiredArgsConstructor
@Getter
@Setter
public class MyLatLng {
    final private double latitude;
    final private double longitude;

    public MyLatLng(LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude= latLng.longitude;
    }

    @Override
    public String toString() {
        String value = String.format("lat/lng: (%.1f,%.1f)", latitude, longitude);
        return value;
    }
}

