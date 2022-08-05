package org.processmining.estminer.specpp.supervision.transformers;

import org.processmining.estminer.specpp.supervision.observations.Observation;
import org.processmining.estminer.specpp.supervision.piping.ObservationSummarizer;
import org.processmining.estminer.specpp.supervision.piping.Observations;
import org.processmining.estminer.specpp.traits.Mergeable;

import java.util.function.Supplier;

public class AccumulatingSummarizer<O extends Observation & Mergeable> implements ObservationSummarizer<O, O> {

    private final O accumulator;

    public AccumulatingSummarizer(Supplier<O> initial) {
        this.accumulator = initial.get();
    }

    @Override
    public O summarize(Observations<? extends O> observations) {
        for (O observation : observations) {
            accumulator.merge(observation);
        }
        return accumulator;
    }

}
