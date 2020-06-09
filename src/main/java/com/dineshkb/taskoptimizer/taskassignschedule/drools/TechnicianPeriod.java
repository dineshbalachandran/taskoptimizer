package com.dineshkb.taskoptimizer.taskassignschedule.drools;

import com.dineshkb.taskoptimizer.taskassignschedule.domain.TaskAssignment;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.Technician;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Comparator.comparing;

public class TechnicianPeriod  implements Comparable<TechnicianPeriod> {

    public final Technician technician;
    public final int period;

    private int capacityRemaining;
    private Set<Integer> taskLocations = new HashSet<>();
    private int location;

    private void setLocation(TechnicianPeriod prevTechPeriod) {
        if (getNoOfTaskLocations() == 1)
            location = taskLocations.iterator().next();
        else if (prevTechPeriod != null)
            location = prevTechPeriod.location;
        else
            location = technician.location;
    }

    public TechnicianPeriod(Technician technician, int period, List<TaskAssignment> taskAssignments, TechnicianPeriod prevTechPeriod) {
        this.technician = technician;
        this.period = period;

        capacityRemaining = technician.capacities.getOrDefault(period, 0);

        for (TaskAssignment taskAssignment : taskAssignments) {
            addTaskAssignment(taskAssignment);
        }

        setLocation(prevTechPeriod);
    }

    private void addTaskAssignment(TaskAssignment taskAssignment) {
        capacityRemaining -= taskAssignment.getDuration();
        taskLocations.add(taskAssignment.task.location);
    }

    public Technician getTechnician() {
        return technician;
    }

    public int getPeriod() {
        return period;
    }

    public int getCapacityRemaining() {
        return capacityRemaining;
    }

    public Set<Integer> getTaskLocations() {
        return taskLocations;
    }

    public int getNoOfTaskLocations() {
        return taskLocations.size();
    }

    //call this method only if there is one location, i.e. the size of locations is 1.
    public int getLocation() {
        return location;
    }

    private static final Comparator<TechnicianPeriod> COMPARATOR =
            comparing((TechnicianPeriod technicianPeriod) -> technicianPeriod.technician.id)
                    .thenComparingInt(TechnicianPeriod::getPeriod)
                    .thenComparingInt(TechnicianPeriod::getLocation)
                    .thenComparingInt(TechnicianPeriod::getCapacityRemaining)
                    .thenComparingInt(TechnicianPeriod::getNoOfTaskLocations);

    /*private static final Comparator<TechnicianPeriod> COMPARATOR =
            comparing((TechnicianPeriod technicianPeriod) -> technicianPeriod.technician.id)
                    .thenComparingInt(TechnicianPeriod::getPeriod)
                    .thenComparing(TechnicianPeriod::isTaskAssigned);*/
    @Override
    public int compareTo(TechnicianPeriod o) {
        return COMPARATOR.compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TechnicianPeriod other = (TechnicianPeriod) o;
        return Objects.equals(technician, other.technician) &&
                period == other.period &&
                location == other.location &&
                capacityRemaining == other.capacityRemaining &&
                taskLocations.equals(other.taskLocations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(technician, period, location, capacityRemaining, taskLocations.hashCode());
    }

/*
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TechnicianPeriod other = (TechnicianPeriod) o;
        return Objects.equals(technician, other.technician) &&
                period == other.period &&
                taskAssigned == other.taskAssigned;
    }

    @Override
    public int hashCode() {
        return Objects.hash(technician, period, taskAssigned);
    }
*/
}
