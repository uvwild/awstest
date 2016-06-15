package org.funtime.client;

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
    ScheduledExecutorService executorService;

    @Autowired
    DatasourceConfiguration configuration;

    @PostConstruct
    public ScheduledFuture[] startExecutor() {
        Stream<ScheduledFuture<?>> scheduledFutureStream = IntStream.range(0, configuration.threadPoolSize)
                .mapToObj(i -> executorService.schedule(new ClientThread("send:", i),
                                                        random.nextInt(1000) + (1000 * i),
                                                        TimeUnit.MILLISECONDS));
        return (ScheduledFuture[]) scheduledFutureStream.toArray();     // TODO casting ok here?
    }

    /**
     * @param runnable schedules runnable with random delay up to 1 second
     */
    public void startThread(Runnable runnable) {
        executorService.schedule(runnable, random.nextInt(1000) + 1000, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void destroy() {
        List<Runnable> runningJobs = executorService.shutdownNow();
        System.out.println(String.format("destroy: %d : %s",
                                         runningJobs.size(),
                                         runningJobs.stream().map(runnable -> runnable.toString()).collect(Collectors.joining(", "))));
    }

}
