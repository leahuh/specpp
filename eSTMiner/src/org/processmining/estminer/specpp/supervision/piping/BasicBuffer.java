package org.processmining.estminer.specpp.supervision.piping;

import com.google.common.collect.EvictingQueue;

import java.util.Collection;

public class BasicBuffer<E> implements Buffer<E> {

    private static final int MAX_BUFFER_SIZE = 10000;
    private EvictingQueue<E> buffer;

    public BasicBuffer(int maxBufferSize) {
        buffer = EvictingQueue.create(MAX_BUFFER_SIZE);
    }

    public BasicBuffer() {
        this(MAX_BUFFER_SIZE);
    }

    @Override
    public void store(E element) {
        buffer.add(element);
    }

    @Override
    public void storeAll(Collection<E> elements) {
        buffer.addAll(elements);
    }

    @Override
    public Collection<E> drain() {
        EvictingQueue<E> result = buffer;
        buffer = EvictingQueue.create(MAX_BUFFER_SIZE);
        return result;
    }

    @Override
    public int size() {
        return buffer.size();
    }
}
