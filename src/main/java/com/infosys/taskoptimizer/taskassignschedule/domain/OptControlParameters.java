package com.infosys.taskoptimizer.taskassignschedule.domain;

import java.util.Map;

//Parameters to tune optimization score
public class OptControlParameters {

    // Relative score factor (integral numbers) assigned by the planner for each optimization objective.
    // Can assign 0 but at least one must be non-zero.
    // A higher value indicates a higher preference
    public int onTimeStart;
    public int onTimeComplete;
    public int technicianUtilization;
    public int technicianRelocation;
    public int timeTolerance; // tolerance or flexibility for time (start, complete), value in periods

    // cost factor assigned to each task priority, keyed by task priority index and value of cost factor
    // if task priority is immaterial assign a value of 1
    public Map<Integer, Integer> taskPriorityFactors;

    public OptControlParameters() {};

    public OptControlParameters(int onTimeStart,
                                int onTimeComplete,
                                int technicianUtilization,
                                int technicianRelocation,
                                int timeTolerance,
                                Map<Integer, Integer> taskPriorityFactors) {
        this.onTimeStart = onTimeStart;
        this.onTimeComplete = onTimeComplete;
        this.technicianUtilization = technicianUtilization;
        this.technicianRelocation = technicianRelocation;
        this.timeTolerance = timeTolerance;
        this.taskPriorityFactors = taskPriorityFactors;
    }

    public int getTaskPriorityFactor(int priority) {
        return taskPriorityFactors.get(priority).intValue();
    }
}
