package com.dineshkb.taskoptimizer.taskassignschedule;

import com.dineshkb.taskoptimizer.taskassignschedule.domain.OptControlParameters;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.Task;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.Technician;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.TaskAssignment;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

import java.util.ArrayList;
import java.util.List;

@PlanningSolution
public class TaskAssignSchedule {

    public OptControlParameters optControlParameters;

    public int periodFrom;

    public int periodTo;

    public int durationsPerPeriod;

    public List<Task> tasks;

    public List<Technician> technicians;

    public List<TaskAssignment> taskAssignments;

    public HardSoftLongScore score;

    public TaskAssignSchedule(List<Task> tasks,
                              List<Technician> technicians,
                              OptControlParameters optControlParameters,
                              int periodFrom,
                              int periodTo,
                              int durationsPerPeriod) {
        this.tasks = tasks;
        this.technicians = technicians;
        this.optControlParameters = optControlParameters;
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
        this.durationsPerPeriod = durationsPerPeriod;

        this.taskAssignments = new ArrayList<>();
        addTasks(tasks);
    }

    public TaskAssignSchedule() {}

    public OptControlParameters getOptControlParameters() {
        return optControlParameters;
    }

    public void setOptControlParameters(OptControlParameters optControlParameters) {
        this.optControlParameters = optControlParameters;
    }

    public int getDurationsPerPeriod() {
        return durationsPerPeriod;
    }

    public void setDurationsPerPeriod(int durationsPerPeriod) {
        this.durationsPerPeriod = durationsPerPeriod;
    }

    public int getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(int periodFrom) {
        this.periodFrom = periodFrom;
    }

    public int getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(int periodTo) {
        this.periodTo = periodTo;
    }

    @PlanningScore
    public HardSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }

    @ProblemFactCollectionProperty
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @ValueRangeProvider(id = "technicianRange")
    @ProblemFactCollectionProperty
    public List<Technician> getTechnicians() {
        return technicians;
    }

    public void setTechnicians(List<Technician> technicians) {
        this.technicians = technicians;
    }

    @PlanningEntityCollectionProperty
    public List<TaskAssignment> getTaskAssignments() {
        return taskAssignments;
    }

    public void setTaskAssignments(List<TaskAssignment> taskAssignments) {
        this.taskAssignments = taskAssignments;
    }

    @ProblemFactProperty
    public TaskAssignSchedule getTaskAssignSchedule() {
        return this;
    }

    private void addTasks(List<Task> tasks) {
        tasks.forEach(task -> {
            TaskAssignment ta = new TaskAssignment(task, durationsPerPeriod, optControlParameters.timeTolerance);
            taskAssignments.add(ta);
        });
    }
}
