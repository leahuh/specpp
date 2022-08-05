package org.processmining.estminer.specpp.representations.tree.heuristic;

import org.processmining.estminer.specpp.base.Evaluable;
import org.processmining.estminer.specpp.representations.tree.base.HeuristicStrategy;
import org.processmining.estminer.specpp.representations.tree.base.TreeNode;
import org.processmining.estminer.specpp.representations.tree.base.traits.KnowsDepth;

public class HeuristicUtils {

    public static <N extends TreeNode & KnowsDepth & Evaluable> HeuristicStrategy<N, DoubleScore> dfs() {
        return n -> new DoubleScore(-n.getDepth());
    }

    public static <N extends TreeNode & KnowsDepth & Evaluable> HeuristicStrategy<N, DoubleScore> bfs() {
        return n -> new DoubleScore(n.getDepth());
    }

}
