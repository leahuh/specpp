package org.processmining.estminer.specpp.componenting.evaluation;

import org.processmining.estminer.specpp.base.Evaluable;
import org.processmining.estminer.specpp.base.Evaluation;
import org.processmining.estminer.specpp.base.Evaluator;
import org.processmining.estminer.specpp.componenting.system.ComponentType;
import org.processmining.estminer.specpp.componenting.system.FulfilledRequirement;
import org.processmining.estminer.specpp.componenting.system.FulfilledRequirementsCollection;

public class EvaluatorCollection extends FulfilledRequirementsCollection<EvaluatorRequirement<?, ?>> {

    @Override
    public ComponentType componentType() {
        return ComponentType.Evaluation;
    }

    public <I extends Evaluable, E extends Evaluation> void register(EvaluatorRequirement<I, E> requirement, Evaluator<? super I, ? extends E> evaluator) {
        add(EvaluationRequirements.evaluator(requirement, evaluator::eval));
    }

    public <I extends Evaluable, E extends Evaluation> void register(FulfilledEvaluatorRequirement<I, E> fulfilledEvaluatorRequirement) {
        add(fulfilledEvaluatorRequirement);
    }

    public <I extends Evaluable, E extends Evaluation> Evaluator<I, E> askForEvaluator(EvaluatorRequirement<I, E> requirement) {
        FulfilledRequirement<Evaluator<I, E>, EvaluatorRequirement<?, ?>> fulfilledRequirement = satisfyRequirement(requirement);
        return fulfilledRequirement.getContent();
    }

}
