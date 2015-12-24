package org.funtime.services;

import org.funtime.data.LatLngValueMap;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by uv on 08/12/2015 for awstest
 */
@Service
public class SimpleAccelerometerPersistenceServiceImpl implements AccelerometerPersistenceService {

    private static HashMap storage;

    static HashMap getInstance() {
        if (storage == null) {
            storage = new HashMap();
        }
        return storage;
    }
    @Override
    public boolean put(long when, LatLngValueMap data) {
//        boolean exists = has(when);
        getInstance().put(when, data);
        return has(when);
    }

    @Override
    public boolean has(long when) {
        return getInstance().containsKey(when);
    }

    @Override
    public LatLngValueMap get(long when) {
        return (LatLngValueMap) getInstance().get(when);
    }
}
