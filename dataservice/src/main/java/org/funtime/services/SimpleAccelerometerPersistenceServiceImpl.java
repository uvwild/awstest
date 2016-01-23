package org.funtime.services;

import org.funtime.data.LatLngValueMap;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by uv on 08/12/2015 for awstest
 */
@Service
public class SimpleAccelerometerPersistenceServiceImpl implements AccelerometerPersistenceService {

    private static HashMap<Long,LatLngValueMap> storage;

    static HashMap<Long,LatLngValueMap> getInstance() {
        if (storage == null) {
            storage = new HashMap();
            // put default element!
            storage.put(0l, LatLngValueMap.defaultMap);
        }
        return storage;
    }
    @Override
    public boolean put(long when, LatLngValueMap data) {
//        boolean existed = has(when);
        getInstance().put(when, data);
        return has(when); // existed;
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
    public HashMap<Long, LatLngValueMap> getAll() {
        return getInstance();
    }
}
