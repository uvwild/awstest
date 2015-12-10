package org.funtime.services;

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
    public boolean put(long when, HashMap data) {
        boolean exists = has(when);
        getInstance().put(when, data);
        return exists;
    }

    @Override
    public boolean has(long when) {
        if (getInstance().containsKey(when)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public HashMap get(long when) {
        return (HashMap) getInstance().get(when);
    }
}
