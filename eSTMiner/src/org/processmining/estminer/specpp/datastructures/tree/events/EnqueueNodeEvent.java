package org.processmining.estminer.specpp.datastructures.tree.events;

import org.processmining.estminer.specpp.base.Evaluable;
import org.processmining.estminer.specpp.datastructures.tree.base.TreeNode;
import org.processmining.estminer.specpp.datastructures.tree.base.traits.LocallyExpandable;

public class EnqueueNodeEvent<N extends TreeNode & Evaluable & LocallyExpandable<N>> extends TreeHeuristicQueueingEvent<N> {

    public EnqueueNodeEvent(N source) {
        super(source);
    }

    @Override
    public String toString() {
        return "EnqueueNodeEvent(" + source + ")";
    }

    @Override
    public int getDelta() {
        return 1;
    }
}