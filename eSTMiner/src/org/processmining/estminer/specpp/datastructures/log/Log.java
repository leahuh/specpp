package org.processmining.estminer.specpp.datastructures.log;

import org.processmining.estminer.specpp.datastructures.BitMask;
import org.processmining.estminer.specpp.datastructures.vectorization.IntVector;
import org.processmining.estminer.specpp.traits.Immutable;
import org.processmining.estminer.specpp.traits.Streamable;

import java.util.stream.IntStream;

public interface Log extends Iterable<IndexedVariant>, Streamable<IndexedVariant>, Immutable {

    int variantCount();

    int totalTraceCount();

    BitMask variantIndices();

    IntStream streamIndices();

    int getVariantFrequency(int index);

    Variant getVariant(int index);

    IntVector getVariantFrequencies();

}