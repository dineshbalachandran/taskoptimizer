package com.infosys.taskoptimizer.taskassignschedule.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.taskoptimizer.taskassignschedule.TaskAssignSchedule;
import com.infosys.taskoptimizer.taskassignschedule.TaskAssignScheduleApp;
import com.infosys.taskoptimizer.taskassignschedule.domain.OptControlParameters;
import com.infosys.taskoptimizer.taskassignschedule.domain.Task;
import com.infosys.taskoptimizer.taskassignschedule.domain.TaskAssignment;
import com.infosys.taskoptimizer.taskassignschedule.domain.Technician;
import org.javatuples.Triplet;
import org.junit.Test;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaskAssignScheduleTest {

    private void executeTest(String taskfile, String technicianfile, String controlfile,
                             int periodFrom, int periodTo, int durationsPerPeriod,
                             String taskassignmentfile, HardSoftLongScore score)
            throws IOException {

        Triplet<List<Task>, List<Technician>, OptControlParameters> input =
                                                                loadInput(taskfile, technicianfile, controlfile);

        TaskAssignSchedule solved =
                TaskAssignScheduleApp.solve(input.getValue0(), input.getValue1(),
                                            input.getValue2(), periodFrom, periodTo, durationsPerPeriod);

        System.out.println(TaskAssignScheduleApp.outputString(solved, durationsPerPeriod));

        assertEquals(score, solved.score);

        List<TaskAssignment> expectedAssignments = loadSolution(taskassignmentfile);
        assertEquals(expectedAssignments.size(), solved.taskAssignments.size());

        for (int i = 0; i < expectedAssignments.size(); i++) {
            assertTrue(expectedAssignments.get(i).deepEquals(solved.taskAssignments.get(i)));
        }
    }

    private static Triplet<List<Task>, List<Technician>, OptControlParameters>
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

    private static List<TaskAssignment> loadSolution(String taskassignmentfile) throws IOException {
        String taskAssignmentsjson = new String(Files.readAllBytes(Paths.get(taskassignmentfile)));
        ObjectMapper mapper = new ObjectMapper();

        return Arrays.asList(mapper.readValue(taskAssignmentsjson, TaskAssignment[].class));
    }

    /*
     A basic test with two tasks and one technician that matches all hard and soft constraints
     */
    @Test
    public void testSimpleMatch() throws Exception {
        String taskfile = "./src/test/data/taskassignschedule/simplematch/tasks.json";
        String technicianfile = "./src/test/data/taskassignschedule/simplematch/technicians.json";
        String controlfile = "./src/test/data/taskassignschedule/simplematch/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = "./src/test/data/taskassignschedule/simplematch/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, 0);

        executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

     /*
     A test with two tasks and one technician that does not match the skill hard constraint on both tasks
     */
    @Test
    public void testSkillNoMatch() throws Exception {
        String taskfile = "./src/test/data/taskassignschedule/skillnomatch/tasks.json";
        String technicianfile = "./src/test/data/taskassignschedule/skillnomatch/technicians.json";
        String controlfile = "./src/test/data/taskassignschedule/skillnomatch/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = "./src/test/data/taskassignschedule/skillnomatch/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(-2, 0);

        executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A test with two tasks and one technician where one task requires a token that the technician does not possess.
     */
    @Test
    public void testTokenNoMatch() throws Exception {
        String taskfile = "./src/test/data/taskassignschedule/tokennomatch/tasks.json";
        String technicianfile = "./src/test/data/taskassignschedule/tokennomatch/technicians.json";
        String controlfile = "./src/test/data/taskassignschedule/tokennomatch/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = "./src/test/data/taskassignschedule/tokennomatch/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(-1, 0);

        executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A test with two tasks and one technician where one task is in different location to the technician's.
     Both tasks have the same start date, which should lead to breaking the hard constraint of technician
     not being present in the different locations within the same period.
     */
    @Test
    public void testTechLocationNotMet() throws Exception {
        String taskfile = "./src/test/data/taskassignschedule/techlocationnotmet/tasks.json";
        String technicianfile = "./src/test/data/taskassignschedule/techlocationnotmet/technicians.json";
        String controlfile = "./src/test/data/taskassignschedule/techlocationnotmet/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = "./src/test/data/taskassignschedule/techlocationnotmet/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(-1, 0);

        executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

     /*
     A variation of the above test (testTechLocatioNotMet) with two tasks and one technician where one task is
     in different location to the technician.  The task in the different location though has flexibility in
     the start date.
     This should allow the optimizer to shift the start date without breaking the hard constraint of technician
     not being present in the different locations within the same period.
     This should trigger the technician relocation soft constraint cost
     */
    @Test
    public void testStartShiftForLocation() throws Exception {
        String taskfile = "./src/test/data/taskassignschedule/startshiftforlocation/tasks.json";
        String technicianfile = "./src/test/data/taskassignschedule/startshiftforlocation/technicians.json";
        String controlfile = "./src/test/data/taskassignschedule/startshiftforlocation/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = "./src/test/data/taskassignschedule/startshiftforlocation/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, -1);

        executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A test with two tasks and one technician. Tasks starting on same date requiring capacity more than the technician
     capacity leading to breaking the tech capacity hard constraint within a period.
     */
    @Test
    public void testTechCapacityNotMet() throws Exception {
        String taskfile = "./src/test/data/taskassignschedule/techcapacitynotmet/tasks.json";
        String technicianfile = "./src/test/data/taskassignschedule/techcapacitynotmet/technicians.json";
        String controlfile = "./src/test/data/taskassignschedule/techcapacitynotmet/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = "./src/test/data/taskassignschedule/techcapacitynotmet/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(-5, 0);

        executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A variation of the above test (testTechCapacityNotMet) with two tasks and one technician. Tasks starting on
     same date requiring capacity more than the technician capacity available.  One of the tasks though has flexibility
     in the start date.
     This should allow the optimizer to shift the start date without breaking the hard constraint of not exceeding
     the technician capacity within a period.
     */
    @Test
    public void testStartShiftForCapacity() throws Exception {
        String taskfile = "./src/test/data/taskassignschedule/startshiftforcapacity/tasks.json";
        String technicianfile = "./src/test/data/taskassignschedule/startshiftforcapacity/technicians.json";
        String controlfile = "./src/test/data/taskassignschedule/startshiftforcapacity/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = "./src/test/data/taskassignschedule/startshiftforcapacity/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, 0);

        executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A test with two tasks and one technician, with the latest start date not met for one of the tasks.
     This should trigger the on time start soft constraint score.
     */
    @Test
    public void testOnTimeStartCost() throws Exception {
        String taskfile = "./src/test/data/taskassignschedule/ontimestartcost/tasks.json";
        String technicianfile = "./src/test/data/taskassignschedule/ontimestartcost/technicians.json";
        String controlfile = "./src/test/data/taskassignschedule/ontimestartcost/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = "./src/test/data/taskassignschedule/ontimestartcost/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, -1);

        executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }


    /*
     A test with two tasks and one technician, with the latest end date not met for one of the tasks.
     This should trigger the on time complete soft constraint score.
     */
    @Test
    public void testOnTimeCompleteCost() throws Exception {
        String taskfile = "./src/test/data/taskassignschedule/ontimecompletecost/tasks.json";
        String technicianfile = "./src/test/data/taskassignschedule/ontimecompletecost/technicians.json";
        String controlfile = "./src/test/data/taskassignschedule/ontimecompletecost/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = "./src/test/data/taskassignschedule/ontimecompletecost/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, -1);

        executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A test with one task and two technicians. The task should be assigned to the lower cost technician.
     */
    @Test
    public void testTechnicianCost() throws Exception {
        String taskfile = "./src/test/data/taskassignschedule/techniciancost/tasks.json";
        String technicianfile = "./src/test/data/taskassignschedule/techniciancost/technicians.json";
        String controlfile = "./src/test/data/taskassignschedule/techniciancost/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = "./src/test/data/taskassignschedule/techniciancost/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, -2);

        executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

    //TODO: meeting all hard constraints while preferring start-time to utilization, not minding relocation to meet start-time

}