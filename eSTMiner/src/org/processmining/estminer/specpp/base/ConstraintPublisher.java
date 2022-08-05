package org.processmining.estminer.specpp.base;

import org.processmining.estminer.specpp.supervision.piping.Observable;
import org.processmining.estminer.specpp.traits.ExposesObservable;

public interface ConstraintPublisher<L extends ConstraintEvent> extends ExposesObservable<L> {

    Observable<L> getConstraintPublisher();

    @Override
    default Observable<L> getObservable() {
        return getConstraintPublisher();
    }
}
