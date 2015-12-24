package org.funtime.services;

import org.funtime.data.LatLngValueMap;

/**
 * Created by uv on 08/12/2015 for awstest
 * simply use long for timestamp
 */
public interface AccelerometerPersistenceService {
    boolean put(long date, LatLngValueMap data);
    boolean has(long date);
    LatLngValueMap get (long date);
}
