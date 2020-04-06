package com.infosys.taskoptimizer.taskassignschedule.domain;

import com.infosys.taskoptimizer.taskassignschedule.domain.solver.TaskAssignmentDifficultyComparator;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.valuerange.CountableValueRange;
import org.optaplanner.core.api.domain.valuerange.ValueRangeFactory;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.io.Serializable;
import java.util.Objects;

@PlanningEntity(difficultyComparatorClass = TaskAssignmentDifficultyComparator.class)
public class TaskAssignment implements Serializable {

    //Task, cannot be null
    public Task task;

    //Technician assigned to the task, may be null
    public Technician technician = null;

    //period index representing the start period of the task
    public Integer start;

    //value representing no of time slots or durations within a period of time (number of hours in a day for e.g.)
    int durationsPerPeriod;

    // tolerance or flexibility for time (start, complete), value in periods
    int timeTolerance;

    public TaskAssignment() {}

    public TaskAssignment(Task task, int durationsPerPeriod, int timeTolerance, Technician technician, Integer start) {
        this.task = task;
        this.technician = technician;
        this.start = start;
        this.durationsPerPeriod = durationsPerPeriod;
        this.timeTolerance = timeTolerance;
    }

    public TaskAssignment(Task task, int durationsPerPeriod, int timeTolerance) {
        this(task, durationsPerPeriod, timeTolerance, null,null);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @PlanningVariable(valueRangeProviderRefs = {"technicianRange"})
    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    @PlanningVariable(valueRangeProviderRefs = {"startRange"})
    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public boolean getMatchingSkill() {
        return technician != null && technician.skills.containsKey(task.skill);
    }

    //TODO: consider merging skill and token check into one method
    public boolean getMatchingToken() {
        return technician != null && technician.tokens.contains(task.token);
    }

    public Integer getEnd() {
        if (start == null)
            return null;

        return start + Math.round((float)Math.ceil(1.0 * getDuration() / durationsPerPeriod)) - 1;
    }

    public Integer getDuration() {
        if (technician == null)
            return task.duration;

        if (!technician.skills.containsKey(task.skill))
            return task.duration * 2; // takes a lot more time if the skill does not match
        else
            return Math.round(task.duration * technician.skills.get(task.skill));
    }

    public int getStartDeviation() { return start != null ? task.latestStart - start : -1; }

    public int getFactoredStartDeviation(int factor) { return factor * getStartDeviation();
    }

    public int getEndDeviation() {
        return getEnd() != null ? task.latestEnd - getEnd() : -1;
    }

    public int getFactoredEndDeviation(int factor) {
        return factor * getEndDeviation();
    }

    @ValueRangeProvider(id = "startRange")
    public CountableValueRange<Integer> getStartPeriodRange() {
        return ValueRangeFactory.createIntValueRange(task.earliestStart, task.latestStart + timeTolerance + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TaskAssignment other = (TaskAssignment) o;

        return  Objects.equals(start, other.start) &&
                Objects.equals(technician, other.technician) &&
                Objects.equals(task, other.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, technician, start);
    }
}
