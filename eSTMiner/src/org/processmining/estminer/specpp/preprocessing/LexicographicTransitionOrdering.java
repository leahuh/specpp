package org.processmining.estminer.specpp.preprocessing;

import org.apache.commons.collections4.BidiMap;
import org.processmining.estminer.specpp.datastructures.log.Activity;
import org.processmining.estminer.specpp.datastructures.log.Log;
import org.processmining.estminer.specpp.datastructures.petri.Transition;
import org.processmining.estminer.specpp.datastructures.util.ImmutablePair;
import org.processmining.estminer.specpp.datastructures.util.Pair;

import java.util.Comparator;
import java.util.Map;

public class LexicographicTransitionOrdering extends TransitionEncodingsBuilder {
    public LexicographicTransitionOrdering(Log log, Map<String, Activity> activityMapping, BidiMap<Activity, Transition> transitionMapping) {
        super(log, activityMapping, transitionMapping);
    }

    @Override
    protected Pair<Comparator<Activity>> computeActivityOrderings(Log log, Map<String, Activity> mapping) {
        Comparator<Activity> presetComparator = Comparator.comparing(Activity::toString);
        return new ImmutablePair<>(presetComparator, presetComparator);
    }
}