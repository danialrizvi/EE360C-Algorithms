/*
 * Name: Danial Rizvi
 * EID: DR28944
 */

import java.util.ArrayList;
//import java.util.LinkedList;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the
     * Stable Marriage problem. Study the description of a Matching in the
     * project documentation to help you with this.
     */

    public boolean isStableMatching(Matching marriage) {
        /* TODO implement this function */

        ArrayList<Integer> solutionmatch = marriage.getWorkerMatching();

        ArrayList<ArrayList<Integer>> w_pref = marriage.getWorkerPreference();
        ArrayList<ArrayList<Integer>> j_pref = marriage.getJobPreference();

        int j_num = marriage.getJobCount(); //number of jobs
        int w_num = marriage.getWorkerCount(); //number of workers

        for(int i = 0; i < w_num; i++){
            int worker = i;
            int job = solutionmatch.get(i); //job worker is matched to
            ArrayList<Integer> j_jobpref = j_pref.get(job); //array of job's preferences

            for(int j = 0; j < j_jobpref.indexOf(worker); j++){
                int w_prefferedworker = j_jobpref.get(j); //worker ranked higher in job pref list than matched worker
                ArrayList<Integer> w_workpref = w_pref.get(w_prefferedworker); //array of that (preferred) worker's preferences
                int j_prefworkersmatch = solutionmatch.get(w_prefferedworker); //job that the preferred worker is matched to

                if(w_workpref.indexOf(job) < w_workpref.indexOf(j_prefworkersmatch)){
                    return false; //found that the worker prefers other job to current job (and job preferes other worker to current worker
                }

            }
        }

        return true; //no unstable matches found

    }

    /**
     * Determines a solution to the Stable Marriage problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    public Matching stableHiringGaleShapley(Matching marriage) {
        int j_num = marriage.getJobCount(); //number of jobs
        int w_num = marriage.getWorkerCount(); //number of workers

        ArrayList<Integer> w_solutionmatch = new ArrayList<Integer>();//marriage.getWorkerMatching();
        ArrayList<Integer> j_solutionmatch = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> w_pref = marriage.getWorkerPreference();
        ArrayList<ArrayList<Integer>> j_pref = marriage.getJobPreference();

        int iteration = 0;

        for(int h = 0; h < w_num; h++){
            w_solutionmatch.add(-1); //intialize worker's matchings to -1
            j_solutionmatch.add(-1); //intialize job's matchings to -1
        }
        //System.out.println(w_solutionmatch.size());
        //System.out.println(j_solutionmatch.size());

        boolean allmatched = false;

        while((allmatched == false)) {
            //System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            //System.out.println("Iteration: " + iteration);
            for (int i = 0; i < w_num; i++) {
                int w_current = i; //index through workers
                //System.out.println("====================");
                //System.out.println("Current Worker: " + w_current);
                ArrayList<Integer> w_currentpref = w_pref.get(i); //array of current worker's preferences
                boolean w_matched = false;
                if (w_solutionmatch.get(w_current) != -1) {
                    w_matched = true;
                    //System.out.println("Worker " + w_current + " already matched!");
                }

                int j = 0; //index through worker's preferences
                while ((w_matched == false) && (j < j_num)) {
                    int j_workersprefjob = w_currentpref.get(j); //job that worker prefers
                    int w_jobcurrentmatch = j_solutionmatch.get(j_workersprefjob); //worker who is currently matched to job that current worker prefers
                    ArrayList<Integer> j_prefjobpref = j_pref.get(j_workersprefjob); //array of preferred job's preferences
                    //System.out.println("Preferred Job: " + j_workersprefjob);

                    if (w_jobcurrentmatch == -1) { //job has not been matched to anyone
                        j_solutionmatch.set(j_workersprefjob, w_current); //pair worker to job
                        w_solutionmatch.set(w_current, j_workersprefjob); //pair job to worker
                        w_matched = true; //worker found a job to match with
                    }
                    else if (j_prefjobpref.indexOf(w_current) < j_prefjobpref.indexOf(w_jobcurrentmatch)) { //job prefers current worker to original match
                        j_solutionmatch.set(j_workersprefjob, w_current); //pair worker to job
                        w_solutionmatch.set(w_current, j_workersprefjob); //pair job to worker
                        w_solutionmatch.set(w_jobcurrentmatch, -1); //original worker no longer has the job
                        w_matched = true; //worker found a job to match with
                    }
                    if (w_solutionmatch.get(w_current) != -1) {
                        w_matched = true; //check to see if worker has been matched
                        //System.out.println("Worker " + w_current + " paired to job " + j_workersprefjob);
                    }
                    j++; //if not matched try next job on worker's pref list
                }
            }
            allmatched = true;
            for (int h = 0; h < w_num; h++) {
                if (w_solutionmatch.get(h) == -1) {
                    allmatched = false;
                    //System.out.println("Worker " + h + " unmatched");
                }

            }
            iteration++;
        }
        Matching solution = new Matching(marriage);
        solution.setWorkerMatching(w_solutionmatch);
        return solution;

    }
}
