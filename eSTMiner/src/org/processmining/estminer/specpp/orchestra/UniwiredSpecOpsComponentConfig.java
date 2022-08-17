package org.processmining.estminer.specpp.orchestra;

import org.processmining.estminer.specpp.base.AdvancedComposition;
import org.processmining.estminer.specpp.base.impls.LightweightPlaceCollection;
import org.processmining.estminer.specpp.componenting.system.ComponentSystemAdapter;
import org.processmining.estminer.specpp.composition.PlacesComposer;
import org.processmining.estminer.specpp.config.Configurators;
import org.processmining.estminer.specpp.config.PostProcessingConfiguration;
import org.processmining.estminer.specpp.config.ProposerComposerConfiguration;
import org.processmining.estminer.specpp.datastructures.petri.PetriNet;
import org.processmining.estminer.specpp.datastructures.petri.Place;
import org.processmining.estminer.specpp.datastructures.petri.ProMPetrinetWrapper;
import org.processmining.estminer.specpp.postprocessing.ProMConverter;
import org.processmining.estminer.specpp.proposal.ConstrainablePlaceProposer;

public class UniwiredSpecOpsComponentConfig extends BaseSpecOpsComponentConfig {

    @Override
    public ProposerComposerConfiguration<Place, AdvancedComposition<Place>, PetriNet> getProposerComposerConfiguration(ComponentSystemAdapter csa) {
        return Configurators.<Place, AdvancedComposition<Place>, PetriNet>proposerComposer()
                            .proposer(new ConstrainablePlaceProposer.Builder())
                            .composition(LightweightPlaceCollection::new)
                            .composer(PlacesComposer::new)
                            .build(csa);
    }

    @Override
    public PostProcessingConfiguration<PetriNet, ProMPetrinetWrapper> getPostProcessingConfiguration(ComponentSystemAdapter csa) {
        return Configurators.<PetriNet>postProcessing()
                            //.instrumentedProcessor("ReplayBasedImplicitness", new ReplayBasedImplicitnessPostProcessing.Builder())
                            //.instrumentedProcessor("SelfLoopPlaceMerger", SelfLoopPlaceMerger::new)
                            .processor(ProMConverter::new)
                            .build(csa);
    }

}
