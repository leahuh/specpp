package org.processmining.specpp.base.impls;

import org.processmining.specpp.base.PostProcessor;
import org.processmining.specpp.base.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PostProcessingPipeline<R extends Result, F extends Result> implements PostProcessor<R, F> {
    private final PostProcessor<R, ?> head;
    private final PostProcessor<?, F> tail;
    private final List<PostProcessor<?, ?>> line;
    private List<Result> results;

    public PostProcessingPipeline(PostProcessor<R, F> head) {
        this.head = head;
        this.tail = head;
        line = new ArrayList<>();
        line.add(head);
    }

    public PostProcessingPipeline(PostProcessor<R, ?> head, List<PostProcessor<?, ?>> line, PostProcessor<?, F> tail) {
        this.head = head;
        this.tail = tail;
        this.line = line;
    }

    public <K extends Result> PostProcessingPipeline<R, K> add(PostProcessor<? super F, K> next) {
        if (next.getInputClass().isAssignableFrom(tail.getOutputClass())) {
            line.add(next);
            return new PostProcessingPipeline<>(head, line, next);
        } else
            throw new IncompatiblePostProcessorException(next + " cannot be appended to " + tail + " because " + next.getInputClass() + " is not assignable from " + tail.getOutputClass());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public F postProcess(R result) {
        Result r = result;
        for (PostProcessor postProcessor : line) {
            r = postProcessor.postProcess(r);
            results.add(r);
        }
        return (F) r;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public F postProcess(R result, Consumer<Result> callback) {
        Result r = result;
        for (PostProcessor postProcessor : line) {
            r = postProcessor.postProcess(r);
            callback.accept(r);
        }
        return (F) r;
    }

    public int getPipelineLength() {
        return line.size();
    }

    @Override
    public Class<R> getInputClass() {
        return head.getInputClass();
    }

    @Override
    public Class<F> getOutputClass() {
        return tail.getOutputClass();
    }

    public static class IncompatiblePostProcessorException extends RuntimeException {
        public IncompatiblePostProcessorException() {
        }

        public IncompatiblePostProcessorException(String message) {
            super(message);
        }
    }
}
