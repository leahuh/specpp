package org.processmining.estminer.specpp.datastructures;

import org.processmining.estminer.specpp.datastructures.encoding.IntEncodings;
import org.processmining.estminer.specpp.datastructures.log.Activity;
import org.processmining.estminer.specpp.datastructures.log.Log;
import org.processmining.estminer.specpp.datastructures.petri.Transition;

import java.util.Map;

public class InputDataBundle {

    private final Log log;
    private final Map<Activity, Transition> mapping;
    private final IntEncodings<Transition> transitionEncodings;

    public InputDataBundle(Log log, IntEncodings<Transition> transitionEncodings, Map<Activity, Transition> mapping) {
        this.log = log;
        this.mapping = mapping;
        this.transitionEncodings = transitionEncodings;
    }

    public Log getLog() {
        return log;
    }

    public Map<Activity, Transition> getMapping() {
        return mapping;
    }

    public IntEncodings<Transition> getTransitionEncodings() {
        return transitionEncodings;
    }


    @Override
    public String toString() {
        return "InputDataBundle{" + "A=" + mapping.keySet() + ", |A|=" + mapping.size() + " " + "encodings=" + transitionEncodings + "\n" + log + "}";
    }
}