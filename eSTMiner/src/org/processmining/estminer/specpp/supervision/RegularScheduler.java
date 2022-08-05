package org.processmining.estminer.specpp.supervision;

import org.processmining.estminer.specpp.traits.Joinable;
import org.processmining.estminer.specpp.traits.StartStoppable;
import org.processmining.estminer.specpp.util.datastructures.Tuple2;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RegularScheduler implements StartStoppable, Joinable {

    private final ScheduledExecutorService scheduledExecutorService;
    protected final List<Tuple2<Runnable, Duration>> tasksToSchedule;
    protected final List<ScheduledFuture<?>> scheduledFutureList;

    protected RegularScheduler() {
        tasksToSchedule = new LinkedList<>();
        scheduledFutureList = new LinkedList<>();
        scheduledExecutorService = Executors.newScheduledThreadPool(4);
    }

    public static RegularScheduler inst() {
        return new RegularScheduler();
    }

    public RegularScheduler schedule(Runnable r, Duration timeInterval) {
        if (tasksToSchedule.stream().noneMatch(t -> t.getT1().equals(r)))
            tasksToSchedule.add(new Tuple2<>(r, timeInterval));
        return this;
    }

    @Override
    public void start() {
        for (Tuple2<Runnable, Duration> tuple : tasksToSchedule) {
            Runnable r = tuple.getT1();
            long millis = tuple.getT2().toMillis();
            ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(r, 0, millis, TimeUnit.MILLISECONDS);
            scheduledFutureList.add(scheduledFuture);
        }
    }

    @Override
    public void stop() {
        scheduledFutureList.forEach(sf -> sf.cancel(false));
        tasksToSchedule.forEach(p -> scheduledExecutorService.submit(p.getT1()));
        scheduledExecutorService.shutdown();
    }

    @Override
    public void join() throws InterruptedException {
        scheduledExecutorService.awaitTermination(1L, TimeUnit.SECONDS);
    }

}
