package org.processmining.estminer.specpp.componenting.supervision;

import org.processmining.estminer.specpp.supervision.observations.Observation;
import org.processmining.estminer.specpp.supervision.piping.ObservationPipe;

public class FulfilledObservationPipeRequirement<I extends Observation, O extends Observation> extends AbstractFulfilledSupervisionRequirement<ObservationPipe<I, O>> {

    public FulfilledObservationPipeRequirement(ObservationPipeRequirement<?, ?> requirement, ObservationPipe<I, O> delegate) {
        super(requirement, delegate);
    }

}
