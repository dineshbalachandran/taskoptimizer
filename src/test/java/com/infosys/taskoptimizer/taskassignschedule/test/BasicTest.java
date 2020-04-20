package com.infosys.taskoptimizer.taskassignschedule.test;

import org.junit.Test;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

public class BasicTest {

    private static String basePath = "./src/test/data/taskassignschedule/basic";

    /*
     A basic test with two tasks and one technician that matches all hard and soft constraints
     */
    @Test
    public void testSimpleMatch() throws Exception {
        String taskfile = basePath + "/simplematch/tasks.json";
        String technicianfile = basePath + "/simplematch/technicians.json";
        String controlfile = basePath + "/simplematch/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "/simplematch/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, 0);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo,
                            durationsPerPeriod, taskassignmentfile, score);
    }

    /*
    A test with two tasks and one technician that does not match the skill hard constraint on both tasks
    */
    @Test
    public void testSkillNoMatch() throws Exception {
        String taskfile = basePath + "/skillnomatch/tasks.json";
        String technicianfile = basePath + "/skillnomatch/technicians.json";
        String controlfile = basePath + "/skillnomatch/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "/skillnomatch/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(-2, 0);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo,
                            durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A test with two tasks and one technician where one task requires a token that the technician does not possess.
     */
    @Test
    public void testTokenNoMatch() throws Exception {
        String taskfile = basePath + "/tokennomatch/tasks.json";
        String technicianfile = basePath + "/tokennomatch/technicians.json";
        String controlfile = basePath + "/tokennomatch/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "/tokennomatch/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(-1, 0);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo,
                            durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A test with two tasks and one technician where one task is in different location to the technician's.
     Both tasks have the same start date, which should lead to breaking the hard constraint of technician
     not being present in the different locations within the same period.
     */
    @Test
    public void testTechLocationNotMet() throws Exception {
        String taskfile = basePath + "/techlocationnotmet/tasks.json";
        String technicianfile = basePath + "/techlocationnotmet/technicians.json";
        String controlfile = basePath + "/techlocationnotmet/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "/techlocationnotmet/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(-1, 0);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo,
                            durationsPerPeriod, taskassignmentfile, score);
    }

    /*
    A variation of the above test (testTechLocationNotMet) with two tasks and one technician where one task is
    in different location to the technician.  The task in the different location though has flexibility in
    the start date.
    This should allow the optimizer to shift the start date without breaking the hard constraint of technician
    not being present in the different locations within the same period.
    This should trigger the technician relocation soft constraint cost
    */
    @Test
    public void testStartShiftForLocation() throws Exception {
        String taskfile = basePath + "/startshiftforlocation/tasks.json";
        String technicianfile = basePath + "/startshiftforlocation/technicians.json";
        String controlfile = basePath + "/startshiftforlocation/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "/startshiftforlocation/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, -1);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo,
                            durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A test with two tasks and one technician. Tasks starting on same date requiring capacity more than the technician
     capacity leading to breaking the tech capacity hard constraint within a period.
     */
    @Test
    public void testTechCapacityNotMet() throws Exception {
        String taskfile = basePath + "/techcapacitynotmet/tasks.json";
        String technicianfile = basePath + "/techcapacitynotmet/technicians.json";
        String controlfile = basePath + "/techcapacitynotmet/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "/techcapacitynotmet/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(-5, 0);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo,
                            durationsPerPeriod, taskassignmentfile, score);
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
        String taskfile = basePath + "/startshiftforcapacity/tasks.json";
        String technicianfile = basePath + "/startshiftforcapacity/technicians.json";
        String controlfile = basePath + "/startshiftforcapacity/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "/startshiftforcapacity/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, 0);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo,
                            durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A test with two tasks and one technician, with the latest start date not met for one of the tasks.
     This should trigger the on time start soft constraint score.
     */
    @Test
    public void testOnTimeStartCost() throws Exception {
        String taskfile = basePath + "/ontimestartcost/tasks.json";
        String technicianfile = basePath + "/ontimestartcost/technicians.json";
        String controlfile = basePath + "/ontimestartcost/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "/ontimestartcost/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, -1);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo,
                            durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A test with two tasks and one technician, with the latest end date not met for one of the tasks.
     This should trigger the on time complete soft constraint score.
     */
    @Test
    public void testOnTimeCompleteCost() throws Exception {
        String taskfile = basePath + "/ontimecompletecost/tasks.json";
        String technicianfile = basePath + "/ontimecompletecost/technicians.json";
        String controlfile = basePath + "/ontimecompletecost/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "/ontimecompletecost/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, -1);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo,
                            durationsPerPeriod, taskassignmentfile, score);
    }

    /*
     A test with one task and two technicians with the same capabilities but for cost.
      The task should be assigned to the lower cost technician.
     */
    @Test
    public void testTechnicianCost() throws Exception {
        String taskfile = basePath + "/techniciancost/tasks.json";
        String technicianfile = basePath + "/techniciancost/technicians.json";
        String controlfile = "basePath + \"/techniciancost/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "le/techniciancost/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, -2);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo,
                            durationsPerPeriod, taskassignmentfile, score);
    }
}