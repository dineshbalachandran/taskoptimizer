package com.infosys.taskoptimizer.taskassignschedule.drools;

import java.util.Comparator;
import java.util.Objects;

public class PeriodWrapper {

    private static final Comparator<PeriodWrapper> COMPARATOR = Comparator.comparingInt(PeriodWrapper::getPeriod);

    private int period;

    public PeriodWrapper(int period) {
        this.period = period;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PeriodWrapper other = (PeriodWrapper) o;
        return period == other.period;
    }

    @Override
    public int hashCode() {
        return Objects.hash(period);
    }

    public int compareTo(PeriodWrapper other) {
        return COMPARATOR.compare(this, other);
    }

    @Override
    public String toString() {
        return String.valueOf(period);
    }

}
