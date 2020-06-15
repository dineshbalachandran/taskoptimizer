package com.dineshkb.taskoptimizer.taskassignschedule.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dineshkb.taskoptimizer.taskassignschedule.TaskAssignSchedule;
import com.dineshkb.taskoptimizer.taskassignschedule.TaskAssignScheduleApp;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.OptControlParameters;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.Task;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.TaskAssignment;
import com.dineshkb.taskoptimizer.taskassignschedule.domain.Technician;
import org.javatuples.Triplet;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtil {

    private TestUtil() {}

    public static void executeTest(String testDesc, String taskfile, String technicianfile, String controlfile,
                             int periodFrom, int periodTo, int durationsPerPeriod,
                             String taskassignmentfile, HardSoftLongScore score)
            throws IOException {

        Triplet<List<Task>, List<Technician>, OptControlParameters> input =
                loadInput(taskfile, technicianfile, controlfile);

        TaskAssignSchedule solved =
                TaskAssignScheduleApp.solve(input.getValue0(), input.getValue1(),
                        input.getValue2(), periodFrom, periodTo, durationsPerPeriod);

        System.out.println(testDesc);
        System.out.println(TaskAssignScheduleApp.outputString(solved, durationsPerPeriod));

        assertEquals(score, solved.score);

        List<TaskAssignment> expectedAssignments = loadSolution(taskassignmentfile);
        assertEquals(expectedAssignments.size(), solved.taskAssignments.size());

        for (int i = 0; i < expectedAssignments.size(); i++) {
            assertTrue(expectedAssignments.get(i).deepEquals(solved.taskAssignments.get(i)));
        }
    }

    public static Triplet<List<Task>, List<Technician>, OptControlParameters>
    loadInput(String taskfile,
              String technicianfile,
              String controlfile) throws IOException {
        String taskjson = new String(Files.readAllBytes(Paths.get(taskfile)));
        String technicianjson = new String(Files.readAllBytes(Paths.get(technicianfile)));
        String controljson = new String(Files.readAllBytes(Paths.get(controlfile)));

        ObjectMapper mapper = new ObjectMapper();
        List<Task> tasks = Arrays.asList(mapper.readValue(taskjson, Task[].class));
        List<Technician> technicians = Arrays.asList(mapper.readValue(technicianjson, Technician[].class));
        OptControlParameters optControlParameters = mapper.readValue(controljson, OptControlParameters.class);

        return new Triplet<>(tasks, technicians, optControlParameters);
    }

    public static List<TaskAssignment> loadSolution(String taskassignmentfile) throws IOException {
        String taskAssignmentsjson = new String(Files.readAllBytes(Paths.get(taskassignmentfile)));
        ObjectMapper mapper = new ObjectMapper();

        return Arrays.asList(mapper.readValue(taskAssignmentsjson, TaskAssignment[].class));
    }
}
