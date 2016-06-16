package org.funtime.client.tasks;

import org.funtime.client.services.PacketSendingService;
import org.funtime.data.LatLngValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Date;

/**
 * Created by uv on 12.06.2016 for awstest
 * don't use autowired in a task like this
 */
@Configurable
public class ClientTask implements Runnable, Cloneable {


    private String command;
    private Integer id;
    private long when;
    @Autowired
    private ThreadPool threadPool;
    @Autowired
    private PacketSendingService packetSendingService;


    //    public ClientTask(String s, int id) {
    //        this.command = s;
    //        this.id = id;
    //    }

    public ClientTask(String s, int id, long when, ThreadPool threadPool, PacketSendingService packetSendingService) {
        this.command = s;
        this.id = id;
        this.when = when;
        this.threadPool = threadPool;
        this.packetSendingService = packetSendingService;
    }

    @Override
    public void run() {
        assert (packetSendingService != null);
        System.out.println(" >>>>>> " + Thread.currentThread().getName() + when);
        processCommand();
        System.out.println(" <<<<<< " + Thread.currentThread().getName() + when);
    }

    // @Async TODO think about using this annotation instead
    private void processCommand() {
        assert (packetSendingService != null);
        // unacceptable
        System.out.println(String.format("\tprocessCommand: %s", this.toString()));
        switch (command) {
            case "send":
                when = packetSendingService.sendRandomPacketRandomTime();
                if (threadPool.getRepeatStart() > 0) threadPool.startThreadMillis(this.clone(), 1000);   // start receiver in a sec

                command = "receive";
                Runnable receiver = this.clone();
                threadPool.startThreadMillis(receiver, 1000);   // start receiver in a sec
                break;
            case "receive":
                LatLngValueMap latLngValueMap = packetSendingService.readPacket(when);
                System.out.println(String.format("readValue: %s\n%s", this.toString(), latLngValueMap.toString()));

            default:
                System.out.println(Thread.currentThread().getName() + " NO ACTION  = " + new Date() + this);
        }
    }

    @Override
    protected ClientTask clone() {
        return new ClientTask(command, id, when, threadPool, packetSendingService);
    }

    @Override
    public String toString() {
        return "ClientTask{" +
                "command='" + command + '\'' +
                ", id=" + id +
                ", when=" + when +
                ", threadPool=" + threadPool +
                ", packetSendingService=" + packetSendingService +
                '}';
    }

}
