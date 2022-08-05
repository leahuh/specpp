package org.processmining.estminer.specpp.componenting.delegators;

import org.processmining.estminer.specpp.supervision.observations.Observation;
import org.processmining.estminer.specpp.supervision.piping.AsyncAwareObservable;
import org.processmining.estminer.specpp.supervision.piping.AsyncObserver;
import org.processmining.estminer.specpp.supervision.piping.Observer;

import java.util.Collection;
import java.util.List;

public class DelegatingObservable<O extends Observation> extends AbstractDelegator<AsyncAwareObservable<O>> implements AsyncAwareObservable<O> {

    public DelegatingObservable() {
    }

    public DelegatingObservable(AsyncAwareObservable<O> delegate) {
        super(delegate);
    }

    public void addObserver(Observer<O> observer) {
        delegate.addObserver(observer);
    }

    public Collection<Observer<O>> getObservers() {
        return delegate.getObservers();
    }

    public void removeObserver(Observer<O> observer) {
        delegate.removeObserver(observer);
    }

    public void clearObservers() {
        delegate.clearObservers();
    }

    public void publish(O observation) {
        delegate.publish(observation);
    }

    @Override
    public List<AsyncObserver<O>> getAsyncObservers() {
        return delegate.getAsyncObservers();
    }

    @Override
    public List<Observer<O>> getNonAsyncObservers() {
        return delegate.getNonAsyncObservers();
    }
}
