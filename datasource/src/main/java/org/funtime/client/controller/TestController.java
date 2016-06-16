package org.funtime.client.controller;

import org.funtime.client.services.PacketSendingService;
import org.funtime.client.tasks.ThreadPool;
import org.funtime.data.LatLngValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by uv on 22.01.2016 for awstest
 * this URL can be configured for AWS elastic load balancer
 */
@RestController
public class TestController {

    @Autowired
    PacketSendingService packetSendingService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private ThreadPool threadPool;

    @RequestMapping(value = "/beans", method = RequestMethod.GET)
    public ResponseEntity<List<String>> listOfDefinedBeans() {
        return ResponseEntity.ok(Arrays.asList(applicationContext.getBeanDefinitionNames()));
    }

    @RequestMapping(value = "/restart", method = RequestMethod.GET)
    public ResponseEntity<List<String>> restart() {
        List<String> names = threadPool.startExecutorStream().map(Object::toString).collect(Collectors.toList());
        return ResponseEntity.ok(names);
    }

    @RequestMapping(value = "/repeat", method = RequestMethod.GET)
    public ResponseEntity<List<String>> repeat() {
        List<String> names = threadPool.startExecutorStreamRepeat().map(Object::toString).collect(Collectors.toList());
        return ResponseEntity.ok(names);
    }

    @RequestMapping(value = "/dataset/{when}", method = RequestMethod.GET)
    public LatLngValueMap read(@PathVariable Long when) {
        return packetSendingService.readPacket(when);
    }

    @RequestMapping(value = "/dataset/{when}", method = RequestMethod.POST)
    public ResponseEntity<String> write(@PathVariable Long when) {
        packetSendingService.sendRandomPacket(when);
        return ResponseEntity.ok("randomPacket Sent?");

    }

}
