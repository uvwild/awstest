package org.funtime.client;

import com.google.android.gms.maps.model.LatLng;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 */
@Component
public class PacketSendingService {
    Log logger = LogFactory.getLog(this.getClass());

    @Value(value = "${dataservice.listlength}")
    private Integer listLength;

    @Value(value = "${dataservice.offsetScaling}")
    Double offsetScaling;

    Random random = new Random();

    @Autowired
    DataServiceWebClient webClient;

    public void sendRandomPacket() {
        // Accumulate names into a List  needs boxing to be collected !!!
        List<Long> randomData = LongStream.range(0, listLength).map(i -> (i * random.nextLong() / Integer.MAX_VALUE)).boxed().collect(Collectors.toList());
        LatLng randomPlace = new LatLng(randomOffset(LatLngConstants.FusionFestival.latitude), randomOffset(LatLngConstants.FusionFestival.longitude));

        LatLngValueMap latLngValueMap = new LatLngValueMap(randomPlace, randomData);
        TimedLatLngValueMap timedLatLngValueMap = new TimedLatLngValueMap(System.currentTimeMillis(), latLngValueMap);
        // iterate through the timedLatLngValueMap which has only one member
        timedLatLngValueMap.keySet().stream().forEach(millitime -> webClient.sendData(millitime, timedLatLngValueMap.get(millitime)));
    }

    public void sendRandomPacket(Long when) {
        // Accumulate names into a List  needs boxing to be collected !!!
        List<Long> randomData = LongStream.range(0, listLength).map(i -> (i * random.nextLong())).boxed().collect(Collectors.toList());
        LatLng randomPlace = new LatLng(randomOffset(LatLngConstants.FusionFestival.latitude), randomOffset(LatLngConstants.FusionFestival.longitude));

        LatLngValueMap latLngValueMap = new LatLngValueMap(randomPlace, randomData);
        TimedLatLngValueMap timedLatLngValueMap = new TimedLatLngValueMap(when, latLngValueMap);
        // iterate through the timedLatLngValueMap which has only one member
        timedLatLngValueMap.keySet().stream().forEach(millitime -> {
            logger.info(String.format("t: %d\t%s", millitime, timedLatLngValueMap));
            webClient.sendData(millitime, timedLatLngValueMap.get(millitime));
        });
    }

    public LatLngValueMap readPacket(Long when) {
        return webClient.readData(when);
    }

    private double randomOffset(double val) {
        return val + ((0.5 + random.nextDouble()) / offsetScaling);
    }

}
