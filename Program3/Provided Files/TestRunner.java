import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by nathanielwendt on 4/2/17.
 */
public class TestRunner {
    public static void main(String[] args){
        Program3 prog3 = new Program3();

        System.out.println("----- Running Activity Tests ------");

        List<ActivityTestCase> activityTests = new ArrayList<ActivityTestCase>();

        // Sample activity test case using the example in the assignment handout

        // Initialize the problem
        ActivityProblem actProblem0 = new ActivityProblem(
                new String[] {"I1", "I2", "I3", "I4", "I5", "I6"},
                new int[] {1, 3, 6, 2, 7, 10}, //fun level
                new int[] {0, 2, 3, 1, 5, 8}, //risk level
                10);

        //ActivityProblem actProblem0 = new ActivityProblem(
        //        new String[] {"I1", "I2", "I3", "I4"},
        //        new int[] {5,7,4,1},
        //        new int[] {4,5,3,1},
        //        7);

        // Initialize the solution
        ActivityResult actSolution0 = new ActivityResult(
                17,
                new HashSet<String>(Arrays.asList(new String[] {"I1", "I2", "I3", "I5"})));

        //ActivityResult actSolution0 = new ActivityResult(
        //        9,
        //        new HashSet<String>(Arrays.asList(new String[] {"I1", "I3"})));

        // Create and add test case based on specified problem and solution
        activityTests.add(new ActivityTestCase(actProblem0, actSolution0));

        // Compare your Program3 activity selector against the solutions specified above
        for(ActivityTestCase activityTest : activityTests){
            ActivityResult res = prog3.selectActivity(activityTest.getProblem());
            activityTest.check(res);
        }

        System.out.println("----- Running Scheduling Tests ------");

        List<SchedulingTestCase> schedulingTests = new ArrayList<SchedulingTestCase>();

        // Sample scheduling test case using the example in the assignment handout

        // Initialize the problem
        SchedulingProblem schProblem0 = new SchedulingProblem(
                new int[] {1,3,11,2,4},
                new int[] {4,6,6,5,5},
                2);

        // Initialize the solution
        SchedulingResult schSolution0 = new SchedulingResult(
                new boolean[] {true,true,false,true,true});

        // Create and add test case based on specified problem and solution
        schedulingTests.add(new SchedulingTestCase(schProblem0, schSolution0));

        // Compare your Program3 schedule selector against the solutions specified above
        for(SchedulingTestCase schedulingTest: schedulingTests){
            SchedulingResult res = prog3.selectScheduling(schedulingTest.getProblem());
            schedulingTest.check(res);
        }
    }
}
