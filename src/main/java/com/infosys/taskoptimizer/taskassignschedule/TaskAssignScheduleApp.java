package com.infosys.taskoptimizer.taskassignschedule;

import com.infosys.taskoptimizer.taskassignschedule.domain.OptControlParameters;
import com.infosys.taskoptimizer.taskassignschedule.domain.Task;
import com.infosys.taskoptimizer.taskassignschedule.domain.TaskAssignment;
import com.infosys.taskoptimizer.taskassignschedule.domain.Technician;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.util.List;

public class TaskAssignScheduleApp {

    private static SolverFactory<TaskAssignSchedule> solverFactory = SolverFactory.createFromXmlResource(
            "com/infosys/taskoptimizer/taskassignschedule/solver/taskAssignScheduleSolverConfig.xml");

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
                .append("Latest end")
                .append("\n");
        for (TaskAssignment taskAssignment : taskAssignSchedule.getTaskAssignments()) {
            Task task = taskAssignment.task;
            Technician technician = taskAssignment.technician;
            displayString.append("  ").append(task.id).append(" -> ")
                    .append(technician == null ? null : technician.id).append(" : ")
                    .append(taskAssignment.start).append(" -> ")
                    .append(task.latestStart).append(" : ")
                    .append(taskAssignment.getEnd()).append(" -> ")
                    .append(task.latestEnd)
                    .append("\n");
        }
        return displayString.toString();
    }
}
