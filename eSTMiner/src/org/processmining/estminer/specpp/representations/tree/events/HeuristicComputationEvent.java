package org.processmining.estminer.specpp.representations.tree.events;

import org.processmining.estminer.specpp.representations.tree.base.TreeNode;
import org.processmining.estminer.specpp.representations.tree.heuristic.NodeHeuristic;

public class HeuristicComputationEvent<H extends NodeHeuristic<H>> extends TreeNodeEvent<TreeNode> implements TreeHeuristicsEvent {

    private final H heuristic;

    public H getHeuristic() {
        return heuristic;
    }

    public HeuristicComputationEvent(TreeNode node, H heuristic) {
        super(node);
        this.heuristic = heuristic;
    }

    @Override
    public String toString() {
        return "H(" + source + ") = " + heuristic;
    }

}
