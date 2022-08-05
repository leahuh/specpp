package org.processmining.estminer.specpp.datastructures.tree.base;

import org.processmining.estminer.specpp.datastructures.tree.base.impls.GeneratingLocalNode;

public interface LocalNodeGenerator<P extends NodeProperties, S extends NodeState, N extends GeneratingLocalNode<P, S, N>> extends ChildGenerator<P, S, N>, ParentGenerator<P, S, N> {

    N generateRoot();

}