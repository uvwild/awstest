package org.funtime.client.tasks;

import lombok.Getter;
import org.funtime.client.DatasourceConfiguration;
import org.funtime.client.services.PacketSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by uv on 12.06.2016 for awstest
 */
@Component
public class ThreadPool {

    Random random = new Random();

    @Autowired
    private PacketSendingService packetSendingService;

    @Autowired
    ScheduledExecutorService executorService;

    @Autowired
    DatasourceConfiguration configuration;

    @Getter
    private long repeatStart;

    @PostConstruct
    public ScheduledFuture[] startExecutor() {
        Stream<ScheduledFuture<?>> scheduledFutureStream = startExecutorStream();
        return scheduledFutureStream.toArray(ScheduledFuture[]::new);
    }

    public Stream<ScheduledFuture<?>> startExecutorStream() {
        Stream<ScheduledFuture<?>> scheduledFutureStream = IntStream.range(0, configuration.getThreadPoolSize()).mapToObj(i -> {
            long delay = random.nextInt(1000) + (1000 * i);
            long when = System.currentTimeMillis();
            return executorService.schedule(new ClientTask("send", i, when, this, packetSendingService),
                                            delay,
                                            TimeUnit.MILLISECONDS);
        });
        return scheduledFutureStream;
    }

    public Stream<ScheduledFuture<?>> startExecutorStream(Integer threadCount) {
        Stream<ScheduledFuture<?>> scheduledFutureStream = IntStream.range(0, threadCount).mapToObj(i -> {
            long delay = random.nextInt(1000) + (1000 * i);
            long when = System.currentTimeMillis();
            return executorService.schedule(new ClientTask("send", i, when, this, packetSendingService),
                                            delay,
                                            TimeUnit.MILLISECONDS);
        });
        return scheduledFutureStream;
    }


    /**
     * @param runnable schedules runnable with random delay up to 1 second
     */
    public void startThread(Runnable runnable) {
        executorService.schedule(runnable, random.nextInt(1000) + 1000, TimeUnit.MILLISECONDS);
    }

    /**
     * @param runnable schedules runnable with random delay up to 1 second
     */
    public void startThreadMillis(Runnable runnable, long delay) {
        executorService.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }


    @PreDestroy
    public void destroy() {
        List<Runnable> runningJobs = executorService.shutdownNow();
        System.out.println(String.format("destroy: %d : %s",
                                         runningJobs.size(),
                                         runningJobs.stream().map(Object::toString).collect(Collectors.joining(", "))));
    }

    public Stream<ScheduledFuture<?>> startExecutorStreamRepeat() {
        this.repeatStart = System.currentTimeMillis();
        Stream<ScheduledFuture<?>> startExecutorStream = startExecutorStream();
        return startExecutorStream;
    }
}
