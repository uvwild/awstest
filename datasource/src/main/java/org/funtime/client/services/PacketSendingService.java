package org.funtime.client.services;

import org.funtime.data.LatLngValueMap;

/**
 * Created by uv on 15.06.2016 for awstest
 */
public interface PacketSendingService {
    Long sendRandomPacketRandomTime();

    void sendRandomPacket(Long when);

    LatLngValueMap readPacket(Long when);
}
