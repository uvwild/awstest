package org.funtime.data;

import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1) override equals for a smarter comparison
 * 2) add useful CTORs
 * Created by uv on 22/12/2015 for awstest
 */
public class LatLngValueMap extends HashMap<LatLng, List<Long>> implements LatLngConstants {

    public static LatLngValueMap defaultMap = new LatLngValueMap(FusionFestival, Arrays.asList(1l, 2l, 3l, 4l, 5l));

    public LatLngValueMap() {
        super();
    }

    public LatLngValueMap(LatLng where, List<Long> what) {
        super();
        this.put(where, what);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LatLngValueMap))
            return false;
        LatLngValueMap otherMap = (LatLngValueMap) object;
//        System.err.print(this); System.err.print(" === "); System.err.println(otherMap);
        if (otherMap.keySet().size() != this.keySet().size())
            return false;
        for (Map.Entry<LatLng,List<Long>> entry : this.entrySet()) {
            LatLng key = entry.getKey();
            List<Long> value = entry.getValue();
            List<Long> otherValue = otherMap.get(key);
            if (otherValue.size() != value.size())
                return false;
            // walk list to compare
            for (Long l : value) {
                if (!otherValue.contains(l))
                    return false;
            }
        }
        return true;
    }

}
