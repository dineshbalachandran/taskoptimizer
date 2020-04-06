package com.infosys.taskoptimizer.taskassignschedule.domain;

import com.infosys.taskoptimizer.taskassignschedule.drools.TechnicianPeriod;

import java.io.Serializable;
import java.util.Objects;

public class Task implements Serializable {

    //A unique identifier for each task
    public int id;

    //Task priority represented as a number from 1 to n, with 1 being the highest priority
    public int priority;

    //Skill Name or PlanningCategory i.e. any relevant indicator of skill, represented as a number index
    public int skill;

    //District, Region, or ESA represented as a number index
    public int location;

    //Optional – if the task requires a token to be performed, represented as a number index
    public int token = -1;

    //The earliest date a task can start  - represented as an index within the periods in the planning time range
    public int earliestStart;

    //The latest date a task can start - represented as an index within the periods in the planning time range
    //It should be >= earliestStart
    public int latestStart;

    //The no. of periods it is estimated for the task to be completed
    public int duration;

    //The due date a task should be completed by; represented as a period index
    public int latestEnd;

    public Task() {};

    public Task(int id,
                int priority,
                int skill,
                int location,
                int earliestStart,
                int latestStart,
                int duration,
                int latestEnd,
                int token) {
        this.id = id;
        this.priority = priority;
        this.skill = skill;
        this.location = location;
        this.earliestStart = earliestStart;
        this.latestStart = latestStart;
        this.duration = duration;
        this.latestEnd = latestEnd;
        this.token = token;
    }

    public Task(int id,
                int priority,
                int skill,
                int location,
                int earliestStart,
                int latestStart,
                int duration,
                int latestEnd) {
       this(id, priority, skill, location, earliestStart, latestStart, duration, latestEnd, -1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getEarliestStart() {
        return earliestStart;
    }

    public void setEarliestStart(int earliestStart) {
        this.earliestStart = earliestStart;
    }

    public int getLatestStart() {
        return latestStart;
    }

    public void setLatestStart(int latestStart) {
        this.latestStart = latestStart;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getLatestEnd() {
        return latestEnd;
    }

    public void setLatestEnd(int latestEnd) {
        this.latestEnd = latestEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Task other = (Task) o;
        return  id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
