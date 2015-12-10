package org.funtime.services;

import java.util.HashMap;

/**
 * Created by uv on 08/12/2015 for awstest
 * simply use long for timestamp
 */
public interface AccelerometerPersistenceService {
    boolean put(long date, HashMap data);
    boolean has(long date);
    HashMap get (long date);
}
