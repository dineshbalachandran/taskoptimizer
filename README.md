#### TaskOptimizer

This is a Java based application that performs linear optimization to schedule and assign tasks to technicians/operators. 
It is essentially an application that matches supply (technicians) to the demand (tasks) while optimizing objectives 
like low cost at the same meeting hard constraints like technician availability & skill and task timeliness & skill 
requirement.

- The linear optimization engine used is Optaplanner (https://github.com/kiegroup/optaplanner).
- The constraints are written as Drools rules that Optaplanner uses for score calculation of the generated solution.

Unit tests are in JUnit.

###### Running app (via tests)
mvn test

