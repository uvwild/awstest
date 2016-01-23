package org.funtime.services;

import org.funtime.data.LatLngValueMap;
import org.funtime.data.TimedLatLngValueMap;
import org.springframework.stereotype.Service;

/**
 * Created by uv on 08/12/2015 for awstest
 */
@Service
public class SimpleAccelerometerPersistenceServiceImpl implements AccelerometerPersistenceService {

    private static TimedLatLngValueMap storage;

    static TimedLatLngValueMap getInstance() {
        if (storage == null) {
            // put default element in CTOR
            storage = new TimedLatLngValueMap(0l, LatLngValueMap.defaultMap);
        }
        return storage;
    }
    @Override
    public boolean put(long when, LatLngValueMap data) {
        boolean existed = has(when);
        getInstance().put(when, data);
        return existed;
    }

    @Override
    public boolean has(long when) {
        return getInstance().containsKey(when);
    }

    @Override
    public LatLngValueMap get(long when) {
        return getInstance().get(when);
    }

    @Override
    public TimedLatLngValueMap getMapping(long date) {
        LatLngValueMap latLngValueMap = get(date);
        return (latLngValueMap!=null)?new TimedLatLngValueMap(date,latLngValueMap):null;
    }

    @Override
    public TimedLatLngValueMap getAll() {
        return getInstance();
    }
}
