package org.processmining.estminer.specpp.representations.tree.iterators;

import org.processmining.estminer.specpp.representations.tree.base.UniDiTreeNode;

public abstract class TreeNodeTraversal<N extends UniDiTreeNode<N>> extends PreAdvancingIterator<N> {

    public TreeNodeTraversal() {
    }

    public TreeNodeTraversal(N root) {
        super(root);
    }

    protected N getCurrentNode() {
        return current;
    }

    protected abstract N getNextNode();

    @Override
    protected N advance() {
        return getNextNode();
    }

}
