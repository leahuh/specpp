package org.processmining.estminer.specpp.base;

public interface ConstrainingComposer<C extends Candidate, I extends Composition<C>, R extends Result, L extends ConstraintEvent> extends Composer<C, I, R>, ConstraintPublisher<L> {
}
