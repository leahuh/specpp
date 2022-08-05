package org.processmining.estminer.specpp.supervision.observations;

public class EnumeratingTreeStatsEvent implements TreeStatsEvent {

    private final int leafCount;

    public EnumeratingTreeStatsEvent(int leafCount) {
        this.leafCount = leafCount;
    }

    public int getLeafCount() {
        return leafCount;
    }

    @Override
    public String toString() {
        return "EnumeratingTreeStats(" + "numLeaves=" + leafCount + ")";
    }
}
