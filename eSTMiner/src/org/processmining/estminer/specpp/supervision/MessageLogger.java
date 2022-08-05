package org.processmining.estminer.specpp.supervision;

import org.apache.log4j.Logger;
import org.processmining.estminer.specpp.supervision.observations.LogMessage;
import org.processmining.estminer.specpp.supervision.piping.AsyncObserver;

import java.util.concurrent.CompletableFuture;

public abstract class MessageLogger implements AsyncObserver<LogMessage> {
    protected final Logger loggerInstance;

    public MessageLogger(Logger loggerInstance) {
        this.loggerInstance = loggerInstance;
    }

    private void log(LogMessage message) {
        loggerInstance.log(message.getLoglevel(), message.toString());
    }

    @Override
    public void observeAsync(CompletableFuture<LogMessage> futureObservation) {
        futureObservation.thenAcceptAsync(this::log);
    }

    @Override
    public void observe(LogMessage message) {
        log(message);
    }
}
