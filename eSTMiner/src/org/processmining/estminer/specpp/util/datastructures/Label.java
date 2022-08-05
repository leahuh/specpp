package org.processmining.estminer.specpp.util.datastructures;

import org.processmining.estminer.specpp.traits.PartiallyOrdered;
import org.processmining.estminer.specpp.traits.ProperlyHashable;
import org.processmining.estminer.specpp.traits.ProperlyPrintable;

import java.util.Objects;

public class Label implements ProperlyPrintable, ProperlyHashable, PartiallyOrdered<Label> {

    protected final String text;

    public Label(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Label label = (Label) o;

        return Objects.equals(text, label.text);
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean gt(Label other) {
        if (other instanceof RegexLabel) return other.lt(this);
        return text.equals(other.text);
    }

    @Override
    public boolean lt(Label other) {
        return other.gt(this);
    }
}
