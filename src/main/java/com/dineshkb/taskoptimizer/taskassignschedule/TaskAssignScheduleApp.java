package com.dineshkb.taskoptimizer.taskassignschedule;

import com.dineshkb.taskoptimizer.taskassignschedule.domain.OptControlParameters;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.Task;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.TaskAssignment;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.Technician;
import org.optaplanner.core.api.solver.SolverFactory;

import java.util.List;

public class TaskAssignScheduleApp {

    private static SolverFactory<TaskAssignSchedule> solverFactory = SolverFactory.createFromXmlResource(
            "com/dineshkb/taskoptimizer/taskassignschedule/solver/taskAssignScheduleSolverConfig.xml");

    public static TaskAssignSchedule solve(List<Task> tasks,
                                           List<Technician> technicians,
                                           OptControlParameters optControlParameters,
                                           int periodFrom,
                                           int periodTo,
                                           int durationsPerPeriod) {

        TaskAssignSchedule problem = new TaskAssignSchedule(tasks, technicians, optControlParameters,
                                                            periodFrom, periodTo, durationsPerPeriod);

        return solverFactory.buildSolver().solve(problem);
    }

    public static String outputString(TaskAssignSchedule taskAssignSchedule, int durationsPerPeriod) {
        StringBuilder displayString = new StringBuilder();
        displayString.append("Score :")
                .append(taskAssignSchedule.score)
                .append("\n");
        displayString.append("Task Id").append(" -> ")
                .append("Tech Id").append(" : ")
                .append("Planned start").append(" -> ")
                .append("Required start").append(" : ")
                .append("Planned end").append(" -> ")
                .append("Latest end").append(" -> ").append(" : ")
                .append("Task Location").append(" -> ")
                .append("Tech Location")
                .append("\n");
        for (TaskAssignment taskAssignment : taskAssignSchedule.getTaskAssignments()) {
            Task task = taskAssignment.task;
            Technician technician = taskAssignment.technician;
            displayString.append("  ").append(task.id).append(" -> ")
                    .append(technician == null ? null : technician.id).append(" : ")
                    .append(taskAssignment.start).append(" -> ")
                    .append(task.latestStart).append(" : ")
                    .append(taskAssignment.getEnd()).append(" -> ")
                    .append(task.latestEnd).append(" : ")
                    .append(task.location).append(" -> ")
                    .append(technician == null ? null : technician.location)
                    .append("\n");
        }
        return displayString.toString();
    }
}
