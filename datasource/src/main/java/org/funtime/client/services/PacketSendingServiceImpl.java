package org.funtime.client.services;

import com.google.android.gms.maps.model.LatLng;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.funtime.client.DataServiceWebClient;
import org.funtime.data.LatLngConstants;
import org.funtime.data.LatLngValueMap;
import org.funtime.data.TimedLatLngValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by uv on 12.06.2016 for awstest
 * service implementation reading and sending packets from the feign webclient (@see DataServiceWebClient)
 */
@Component
public class PacketSendingServiceImpl implements PacketSendingService {
    Log logger = LogFactory.getLog(this.getClass());

    /**
     * the number of random values we attach to a packet
     */
    @Value(value = "${dataservice.listlength:1}")
    private Integer listLength;

    @Value(value = "${dataservice.offsetScaling:1000}")
    Double offsetScaling;

    Random random = new Random();

    @Autowired
    DataServiceWebClient webClient;

    @Override
    public Long sendRandomPacketRandomTime() {
        Long when = System.currentTimeMillis();
        sendRandomPacket(when);
        return when;
    }

    @Override
    public void sendRandomPacket(Long when) {
        LatLngValueMap latLngValueMap = new LatLngValueMap(getRandomPlace(), getRandomData());
        TimedLatLngValueMap timedLatLngValueMap = new TimedLatLngValueMap(when, latLngValueMap);
        // iterate through the timedLatLngValueMap which has only one member
        writePackets(timedLatLngValueMap);
    }

    @Override
    public LatLngValueMap readPacket(Long when) {
        LatLngValueMap result = webClient.readData(when);
        logger.info(String.format("readPacket: %d\t%s", when, result));
        return result;
    }

    @Override
    public void writePacket(Long when, LatLngValueMap latLngValueMap) {
        webClient.sendData(when, latLngValueMap);
        logger.info(String.format("sendPacket: %d\t%s", when, latLngValueMap));
    }

    @Override
    public void writePackets(TimedLatLngValueMap timedLatLngValueMap) {
        timedLatLngValueMap.keySet().stream().forEach(millitime -> {
            webClient.sendData(millitime, timedLatLngValueMap.get(millitime));
            logger.info(String.format("sendPacket: %d\t%s", millitime, timedLatLngValueMap.get(millitime)));
        });
    }

    private double randomOffset(double val) {
        return val + ((0.5 + random.nextDouble()) / offsetScaling);
    }

    private LatLng getRandomPlace() {
        return new LatLng(randomOffset(LatLngConstants.FusionFestival.latitude), randomOffset(LatLngConstants.FusionFestival.longitude));

    }

    private List<Long> getRandomData() {
        // collecting stream elements into a List  needs boxing to be collected !!!
        return LongStream.range(0, listLength).map(i -> (i * random.nextLong() / Integer.MAX_VALUE)).boxed().collect(Collectors.toList());
    }

}
