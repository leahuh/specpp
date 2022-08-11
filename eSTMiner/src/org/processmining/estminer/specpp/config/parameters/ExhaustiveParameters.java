package org.processmining.estminer.specpp.config.parameters;

import org.processmining.estminer.specpp.componenting.data.ParameterRequirements;
import org.processmining.estminer.specpp.componenting.data.StaticDataSource;
import org.processmining.estminer.specpp.componenting.system.AbstractComponentSystemUser;
import org.processmining.estminer.specpp.componenting.traits.ProvidesParameters;

public class ExhaustiveParameters extends AbstractComponentSystemUser implements ProvidesParameters {

    public ExhaustiveParameters() {
        componentSystemAdapter().provide(ParameterRequirements.parameters(ParameterRequirements.FITNESS_THRESHOLDS, StaticDataSource.of(FitnessThresholds.tau(1))))
                                .provide(ParameterRequirements.parameters("placegenerator.parameters", PlaceGeneratorParameters.class, StaticDataSource.of(PlaceGeneratorParameters.getDefault())));
    }

}
