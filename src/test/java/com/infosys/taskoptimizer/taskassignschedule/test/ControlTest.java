package com.infosys.taskoptimizer.taskassignschedule.test;

import org.junit.Test;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;

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
        HardMediumSoftLongScore score = HardMediumSoftLongScore.of(0, -5, 0);

        Utility.executeTest(taskfile, technicianfile, controlfile, periodFrom, periodTo, durationsPerPeriod, taskassignmentfile, score);
    }

}
