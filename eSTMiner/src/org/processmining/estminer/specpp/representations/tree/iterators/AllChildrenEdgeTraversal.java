package org.processmining.estminer.specpp.representations.tree.iterators;

import org.processmining.estminer.specpp.representations.tree.base.EdgeFactory;
import org.processmining.estminer.specpp.representations.tree.base.TreeEdge;
import org.processmining.estminer.specpp.representations.tree.base.UniDiTreeNode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class AllChildrenEdgeTraversal<N extends UniDiTreeNode<N>, E extends TreeEdge<N>> extends TreeEdgeTraversal<N, E> {
    private final Queue<N> currentChildren;

    public AllChildrenEdgeTraversal(Iterator<N> nodeTraversal, EdgeFactory<N, E> edgeFactory) {
        super(nodeTraversal, edgeFactory);
        currentChildren = new LinkedList<>();
        current = advance();
    }

    protected void advanceParent() {
        if (currentChildren.isEmpty()) {
            if (!nodeTraversal.hasNext()) currentParent = null;
            else {
                currentParent = nodeTraversal.next();
                currentChildren.addAll(currentParent.getChildren());
            }
        }
    }

    protected void advanceChild() {
        currentChild = currentChildren.poll();
    }

}
