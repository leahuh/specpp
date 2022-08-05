package org.processmining.estminer.specpp.representations.tree.iterators;

import org.processmining.estminer.specpp.representations.tree.base.EdgeFactory;
import org.processmining.estminer.specpp.representations.tree.base.TreeEdge;
import org.processmining.estminer.specpp.representations.tree.base.UniDiTreeNode;

import java.util.Iterator;

public abstract class TreeEdgeTraversal<N extends UniDiTreeNode<N>, E extends TreeEdge<N>> extends PreAdvancingIterator<E> {

    private final EdgeFactory<N, E> edgeFactory;
    protected final Iterator<N> nodeTraversal;
    protected N currentParent, currentChild;


    public TreeEdgeTraversal(Iterator<N> nodeTraversal, EdgeFactory<N, E> edgeFactory) {
        this.nodeTraversal = nodeTraversal;
        this.edgeFactory = edgeFactory;
    }

    protected abstract void advanceParent();

    protected abstract void advanceChild();

    @Override
    protected E advance() {
        advanceParent();
        advanceChild();
        return currentParent == null || currentChild == null ? null : edgeFactory.create(currentParent, currentChild);
    }


}
