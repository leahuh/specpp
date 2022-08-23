package org.processmining.estminer.specpp.datastructures.tree.base;

import org.processmining.estminer.specpp.componenting.system.link.AbstractBaseClass;
import org.processmining.estminer.specpp.datastructures.petri.Place;
import org.processmining.estminer.specpp.datastructures.tree.nodegen.PlaceNode;
import org.processmining.estminer.specpp.datastructures.tree.nodegen.PlaceState;

public abstract class PlaceGenerationLogic extends AbstractBaseClass implements ConstrainableChildGenerationLogic<Place, PlaceState, PlaceNode, GenerationConstraint> {

}
