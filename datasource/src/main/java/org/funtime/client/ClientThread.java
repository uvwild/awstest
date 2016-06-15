package org.funtime.client;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by uv on 12.06.2016 for awstest
 */
public class ClientThread implements Runnable {

    @Autowired
    ThreadPool threadPool;

    @Autowired
    private PacketSendingService packetSendingService;

    private String command;
    private Integer id;


    public ClientThread(String s, Integer id) {
        this.command = s;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
    }

    private void processCommand() {
        if (command.contains("send") && packetSendingService != null) {
            packetSendingService.sendRandomPacket();
            System.out.println(Thread.currentThread().getName() + " Restart!!!!. Time = " + new Date());
            threadPool.startThread(this);   // restart
        } else
            System.out.println(Thread.currentThread().getName() + " Time = " + new Date() + this);
    }

    @Override
    public String toString() {
        return this.command + this.id;
    }
}
