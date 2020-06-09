package com.dineshkb.taskoptimizer.taskassignschedule.domain.solver;

import com.dineshkb.taskoptimizer.taskassignschedule.domain.Task;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.TaskAssignment;

import java.io.Serializable;
import java.util.Comparator;

public class TaskAssignmentDifficultyComparator implements Comparator<TaskAssignment>, Serializable {

    private static final Comparator<Task> TASK_COMPARATOR = Comparator.comparingInt(Task::getDuration);

    private static final Comparator<TaskAssignment> COMPARATOR =
            Comparator.comparing(TaskAssignment::getTask, TASK_COMPARATOR);

    @Override
    public int compare(TaskAssignment a, TaskAssignment b) {
        return COMPARATOR.compare(a, b);
    }
}