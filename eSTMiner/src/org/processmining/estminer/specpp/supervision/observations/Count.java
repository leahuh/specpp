package org.processmining.estminer.specpp.supervision.observations;

import org.processmining.estminer.specpp.traits.Mergeable;

public class Count implements Statistic, Mergeable {

    private int count;

    public Count() {
        this(0);
    }

    public Count(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Count count1 = (Count) o;

        return count == count1.count;
    }

    @Override
    public int hashCode() {
        return count;
    }

    @Override
    public String toString() {
        return Integer.toString(count);
    }

    public int getCount() {
        return count;
    }

    @Override
    public void merge(Object other) {
        if (other instanceof Count) count += ((Count) other).count;
    }

}
