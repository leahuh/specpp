package org.processmining.estminer.specpp.supervision;

import org.processmining.estminer.specpp.supervision.observations.Observation;
import org.processmining.estminer.specpp.supervision.piping.AsyncObserver;
import org.processmining.estminer.specpp.supervision.piping.Buffer;
import org.processmining.estminer.specpp.supervision.piping.Buffering;
import org.processmining.estminer.specpp.supervision.piping.ConcurrentBuffer;
import org.processmining.estminer.specpp.util.FileUtils;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CSVWriter<O extends Observation> implements AsyncObserver<O>, Buffering {

    private final Function<O, String[]> rowMapper;
    private final Buffer<String[]> buffer;
    private final com.opencsv.CSVWriter csvWriter;


    public CSVWriter(String filePath, String[] columnLabels, Function<O, String[]> rowMapper) {
        this.rowMapper = rowMapper;
        buffer = new ConcurrentBuffer<>();
        csvWriter = new com.opencsv.CSVWriter(FileUtils.createOutputFileWriter(filePath));
        csvWriter.writeNext(columnLabels);
    }

    private void handleObservation(O observation) {
        String[] row = rowMapper.apply(observation);
        buffer.store(row);
    }

    @Override
    public void observeAsync(CompletableFuture<O> futureObservation) {
        futureObservation.thenAccept(this::handleObservation);
    }

    @Override
    public void observe(O observation) {
        handleObservation(observation);
    }


    @Override
    public void flushBuffer() {
        csvWriter.writeAll(buffer.drain());
    }

    @Override
    public boolean isBufferNonEmpty() {
        return !buffer.isEmpty();
    }
}