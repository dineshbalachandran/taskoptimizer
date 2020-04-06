package com.infosys.taskoptimizer.taskassignschedule.domain;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Technician {

    //A unique identifier for each technician
    public int id;

    //A map of skills, with key representing skills possessed by the technician expressed as a skill index.
    // The value is the efficiency of the technician in the skill expressed as a decimal fraction (1.1, 1, 0.8 etc)
    public Map<Integer, Float> skills;

    //District, Region, or ESA represented as a number index that the Technician belongs to
    public int location;

    //Map of capacity, with key representing the period-group index and
    // The value the capacity is the no. of periods in the technician is available in that period-group
    public Map<Integer, Integer> capacities;

    //Token indices possessed by the technician, if any
    public Set<Integer> tokens;

    private int totalCapacity;

    public Technician() {};

    public Technician(int id,
                      Map<Integer, Float> skills,
                      int location, Map<Integer, Integer> capacities,
                      Set<Integer> tokens) {
        this.id = id;
        this.skills = skills;
        this.location = location;
        this.capacities = capacities;
        this.tokens = tokens;

        setTotalCapacity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Float> getSkills() {
        return skills;
    }

    public void setSkills(Map<Integer, Float> skills) {
        this.skills = skills;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public Map<Integer, Integer> getCapacities() {
        return capacities;
    }

    public void setCapacities(Map<Integer, Integer> capacities) {
        this.capacities = capacities;
        setTotalCapacity();
    }

    public Set<Integer> getTokens() {
        return tokens;
    }

    public void setTokens(Set<Integer> tokens) {
        this.tokens = tokens;
    }

    public int getTotalCapacity() { return totalCapacity; }

    private void setTotalCapacity() {
        totalCapacity = 0;
        capacities.values().forEach(capacity -> totalCapacity += capacity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Technician other = (Technician) o;
        return  id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
