package com.infosys.taskoptimizer.taskassignschedule.test;

import org.junit.Test;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;

public class ControlTest {

    private static String basePath = "./src/test/data/taskassignschedule/control";

    /*
    High cost for missed start, no cost for relocation
     */
    @Test
    public void testControlTestCase1() throws Exception {
        String taskfile = basePath + "/tc1/tasks.json";
        String technicianfile = basePath + "/tc1/technicians.json";
        String controlfile = basePath + "/tc1/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "/tc1/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, -5);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

    /*
    No cost for missed start with a high cost for relocation
     */
    @Test
    public void testControlTestCase2() throws Exception {
        String taskfile = basePath + "/tc2/tasks.json";
        String technicianfile = basePath + "/tc2/technicians.json";
        String controlfile = basePath + "/tc2/optcontrolparameters.json";
        int periodFrom = 1;
        int periodTo = 7;
        int durationsPerPeriod = 5;

        String taskassignmentfile = basePath + "/tc2/taskassignment.json";
        HardSoftLongScore score = HardSoftLongScore.of(0, -106);

        TestUtil.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

}
