package org.processmining.estminer.specpp.base.impls;

import org.processmining.estminer.specpp.base.Candidate;
import org.processmining.estminer.specpp.base.Composer;
import org.processmining.estminer.specpp.base.Result;
import org.processmining.estminer.specpp.componenting.system.link.AbstractBaseClass;
import org.processmining.estminer.specpp.componenting.system.link.ComposerComponent;
import org.processmining.estminer.specpp.componenting.system.link.CompositionComponent;

public abstract class RecursiveComposer<C extends Candidate, I extends CompositionComponent<C>, R extends Result> extends AbstractBaseClass implements ComposerComponent<C, I, R> {

    protected final Composer<C, I, R> childComposer;

    public RecursiveComposer(ComposerComponent<C, I, R> childComposer) {
        this.childComposer = childComposer;
        registerSubComponent(childComposer);
    }


    @Override
    public boolean isFinished() {
        return childComposer.isFinished();
    }

    @Override
    public I getIntermediateResult() {
        return childComposer.getIntermediateResult();
    }

    @Override
    public R generateResult() {
        return childComposer.generateResult();
    }

    protected void forward(C c) {
        childComposer.accept(c);
    }

    @Override
    public abstract void accept(C c);
}

