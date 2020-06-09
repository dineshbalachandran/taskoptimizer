package com.dineshkb.taskoptimizer.taskassignschedule.domain;

import java.util.Map;

//Parameters to tune optimization score
public class OptControlParameters {

    // Relative score factor (integral numbers) assigned by the planner for each optimization objective.
    // Can assign 0 but at least one must be non-zero.
    // A higher value indicates a higher preference
    public int startDeviationCost;
    public int endDeviationCost;
    public int relocationCost;
    public int timeTolerance; // tolerance or flexibility for time (start, complete), value in periods

    // cost factor assigned to each task priority, keyed by task priority index and value of cost factor
    // if task priority is immaterial assign a value of 1
    public Map<Integer, Integer> taskPriorityFactors;

    public OptControlParameters() {}

    public OptControlParameters(int startDeviationCost,
                                int endDeviationCost,
                                int relocationCost,
                                int timeTolerance,
                                Map<Integer, Integer> taskPriorityFactors) {
        this.startDeviationCost = startDeviationCost;
        this.endDeviationCost = endDeviationCost;
        this.relocationCost = relocationCost;
        this.timeTolerance = timeTolerance;
        this.taskPriorityFactors = taskPriorityFactors;
    }

    public int getTaskPriorityFactor(int priority) {
        return taskPriorityFactors.get(priority);
    }
}
