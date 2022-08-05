package org.processmining.estminer.specpp.datastructures.tree.nodegen;

import org.processmining.estminer.specpp.datastructures.encoding.BitEncodedSet;
import org.processmining.estminer.specpp.datastructures.encoding.IntEncodings;
import org.processmining.estminer.specpp.datastructures.petri.Transition;

public class TransitionBlacklister implements PotentialSetExpansionsFilter {

    private final BitEncodedSet<Transition> presetBlacklist, postsetBlacklist;

    public TransitionBlacklister(IntEncodings<Transition> transitionEncodings) {
        presetBlacklist = BitEncodedSet.empty(transitionEncodings.pre());
        postsetBlacklist = BitEncodedSet.empty(transitionEncodings.post());
    }

    public void blacklist(Transition transition) {
        presetBlacklist.add(transition);
        postsetBlacklist.add(transition);
    }

    @Override
    public void filterPotentialSetExpansions(BitEncodedSet<Transition> expansions, boolean isPostsetExpansion) {
        if (expansions.isEmpty()) return;

        if (isPostsetExpansion) expansions.setminus(postsetBlacklist);
        else expansions.setminus(presetBlacklist);
    }
}