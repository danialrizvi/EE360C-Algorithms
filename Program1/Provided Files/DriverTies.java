import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DriverTies {
    public static String filename;
    public static boolean testBruteForce;
    public static boolean testGS;
    
    public static void main(String[] args) throws Exception {
        parseArgs(args);
        
        Matching problem = parseMatchingProblem(filename);
        testRun(problem);
    }

    private static void usage() {
        System.err.println("usage: java Driver [-g] [-b] <filename>");
        System.err.println("\t-b\tTest Brute Force implementation");
        System.err.println("\t-g\tTest Gale-Shapley implementation");
        System.exit(1);
    }
    
    public static void parseArgs(String[] args) {
        if (args.length == 0) {
            usage();
        }
        
        filename = "";
        testBruteForce = false;
        testGS = false;
        boolean flagsPresent = false;
        
        for (String s : args) {
            if(s.equals("-g")) {
                flagsPresent = true;
                testGS = true;
            } else if(s.equals("-b")) {
                flagsPresent = true;
                testBruteForce = true;
            } else if(!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }
        
        if(!flagsPresent) {
            testBruteForce = true;
            testGS = true;
        }
    }

    public static Matching parseMatchingProblem(String inputFile)
            throws Exception {
        int m = 0;
        int n = 0;
        ArrayList<ArrayList<Integer>> jobPrefs, workerPrefs;
        jobPrefs = new ArrayList<>();
        workerPrefs = new ArrayList<>();

        Scanner sc = new Scanner(new File(inputFile));
        String[] inputSizes = sc.nextLine().split(" ");

        m = Integer.parseInt(inputSizes[0]);
        n = Integer.parseInt(inputSizes[1]);

        ArrayList<Boolean> jobFulltime = new ArrayList<>();
        ArrayList<Boolean> workerHardworking = new ArrayList<>();
        readPreferenceLists(sc, m, jobPrefs, jobFulltime);
        readPreferenceLists(sc, n, workerPrefs, workerHardworking);

        Matching problem = new Matching(m, n, jobPrefs, workerPrefs, jobFulltime, workerHardworking);

        return problem;
    }

    private static void readPreferenceLists(Scanner sc, int m, ArrayList<ArrayList<Integer>> preferenceLists,
                                            ArrayList<Boolean> status) {
        for (int i = 0; i < m; i++) {
            String line = sc.nextLine();
            String[] preferences = line.split(" ");
            ArrayList<Integer> preferenceList = new ArrayList<Integer>(0);

            for (Integer j = 0; j < preferences.length; j++) {
                preferenceList.add(Integer.parseInt(preferences[j]));
            }

            // The first number of the preference list is the status (hardworking/full-time)
            status.add(preferenceList.remove(0) == 1);

            preferenceLists.add(preferenceList);
        }
    }

    public static void testRun(Matching problem) {
        Program1Ties program = new Program1Ties();
        boolean isStable;
        double time1;
        double time2;
        double timems;

        if (testGS) {
            time1 = System.nanoTime();
            Matching GSMatching = program.stableHiringGaleShapley(problem);
            time2 = System.nanoTime();
            //System.out.println("Elapsed Time: " + (time2 - time1) + "ns");
            timems = (time2 - time1)/1000000;
            //System.out.println("Elapsed Time: " + timems + "ms");
            System.out.println(GSMatching);
            isStable = program.isStableMatching(GSMatching);
            System.out.printf("%s: stable? %s\n", "Gale-Shapley", isStable);
            System.out.println();
        }

        if (testBruteForce) {
            time1 = System.nanoTime();
            Matching BFMatching = program.stableMarriageBruteForce(problem);
            time2 = System.nanoTime();
            //System.out.println("Elapsed Time: " + (time2 - time1) + "ns");
            timems = (time2 - time1)/1000000;
            //System.out.println("Elapsed Time: " + timems + "ms");
            System.out.println(BFMatching);
            isStable = program.isStableMatching(BFMatching);
            System.out.printf("%s: stable? %s\n", "Brute Force", isStable);
            System.out.println();

            /*Matching BFMatching2 = program.stableMarriageBruteForce(problem);
            System.out.println(BFMatching2);
            isStable = program.isStableMatching(BFMatching2);
            System.out.printf("%s: stable? %s\n", "Brute Force", isStable);
            System.out.println();*/
        }
    }
}
