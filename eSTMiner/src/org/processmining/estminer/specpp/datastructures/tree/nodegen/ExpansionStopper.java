package org.processmining.estminer.specpp.datastructures.tree.nodegen;

public interface ExpansionStopper {

    boolean allowedToExpand(PlaceNode placeNode);

}
