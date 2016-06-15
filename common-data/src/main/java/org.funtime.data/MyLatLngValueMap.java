package org.funtime.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 1) override equals for a smarter comparison
 * 2) add useful CTORs
 * Created by uv on 22/12/2015 for awstest
 */
public class MyLatLngValueMap extends HashMap<MyLatLng, List<Long>> implements LatLngConstants {

    public static MyLatLngValueMap defaultMap = new MyLatLngValueMap(FusionFestivalM, Arrays.asList(1l, 2l, 3l, 4l, 5l));

    public MyLatLngValueMap() {
        super();
    }

    public MyLatLngValueMap(MyLatLng where, List<Long> what) {
        super();
        this.put(where, what);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MyLatLngValueMap))
            return false;
        MyLatLngValueMap otherMap = (MyLatLngValueMap) object;
//        System.err.print(this); System.err.print(" === "); System.err.println(otherMap);
        if (otherMap.keySet().size() != this.keySet().size())
            return false;
        for (Entry<MyLatLng,List<Long>> entry : this.entrySet()) {
            MyLatLng key = entry.getKey();
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
