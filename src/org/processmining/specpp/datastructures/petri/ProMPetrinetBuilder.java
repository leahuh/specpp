package org.processmining.specpp.datastructures.petri;

import com.google.common.collect.ImmutableSet;
import org.processmining.models.graphbased.AttributeMap;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetFactory;
import org.processmining.models.semantics.petrinet.Marking;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProMPetrinetBuilder {

    private final Set<Place> places;
    private final Map<org.processmining.specpp.datastructures.petri.Transition, Transition> transitionMap;
    private final Map<Place, org.processmining.models.graphbased.directed.petrinet.elements.Place> placeMap;
    private final Petrinet net;

    public ProMPetrinetBuilder(Set<Place> places) {
        this.places = places;
        transitionMap = new HashMap<>();
        placeMap = new HashMap<>();
        net = PetrinetFactory.newPetrinet("Model");
    }


    private Transition correspondingTransition(org.processmining.specpp.datastructures.petri.Transition t) {
        if (!transitionMap.containsKey(t)) transitionMap.put(t, net.addTransition(t.toString()));
        return transitionMap.get(t);
    }

    public ProMPetrinetWrapper build() {
        org.processmining.models.graphbased.directed.petrinet.elements.Place uniqueStartPlace = net.addPlace("start");
        uniqueStartPlace.getAttributeMap().put(AttributeMap.LABEL, "START");
        uniqueStartPlace.getAttributeMap().put(AttributeMap.SHOWLABEL, "true");
        org.processmining.models.graphbased.directed.petrinet.elements.Place uniqueEndPlace = net.addPlace("end");
        uniqueEndPlace.getAttributeMap().put(AttributeMap.LABEL, "END");
        uniqueEndPlace.getAttributeMap().put(AttributeMap.SHOWLABEL, "true");
        Marking initialMarking = new Marking(ImmutableSet.of(uniqueStartPlace)), finalMarking = new Marking(ImmutableSet.of(uniqueEndPlace));
        Set<org.processmining.specpp.datastructures.petri.Transition> alreadyConnected = new HashSet<>();
        for (Place p : places) {
            org.processmining.models.graphbased.directed.petrinet.elements.Place correspondingPlace = net.addPlace(p.toString());
            for (org.processmining.specpp.datastructures.petri.Transition t : p.preset()) {
                if (t instanceof InitialTransition && !alreadyConnected.contains(t)) {
                    net.addArc(uniqueStartPlace, correspondingTransition(t));
                    alreadyConnected.add(t);
                }
                net.addArc(correspondingTransition(t), correspondingPlace);
            }
            for (org.processmining.specpp.datastructures.petri.Transition t : p.postset()) {
                if (t instanceof FinalTransition && !alreadyConnected.contains(t)) {
                    net.addArc(correspondingTransition(t), uniqueEndPlace);
                    alreadyConnected.add(t);
                }
                net.addArc(correspondingPlace, correspondingTransition(t));
            }
        }

        // AcceptingPetriNet acceptingPetriNet = AcceptingPetriNetFactory.createAcceptingPetriNet(net, initialMarking, finalMarking);
        return new ProMPetrinetWrapper(net, initialMarking, finalMarking);
    }
}
