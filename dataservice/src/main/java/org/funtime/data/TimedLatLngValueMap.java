package org.funtime.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by uv on 23/12/2015 for awstest
 */
public class TimedLatLngValueMap extends HashMap<Long,LatLngValueMap> {
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TimedLatLngValueMap))
            return false;
        TimedLatLngValueMap otherMap = (TimedLatLngValueMap) object;
        if (otherMap.keySet().size() != this.keySet().size())
            return false;
        for (Map.Entry<Long, LatLngValueMap> entry : this.entrySet()) {
            Long key = entry.getKey();
            LatLngValueMap value = entry.getValue();
            LatLngValueMap otherValue = otherMap.get(key);
            if (!value.equals(otherValue))
                return false;
        }
        return true;
    }
}
