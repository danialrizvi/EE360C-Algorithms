import java.util.*;
/**
 * Created by nathanielwendt on 4/2/17.
 */
public class Program3 {

    public ActivityResult selectActivity(ActivityProblem activityProblem){

        String[] Activities = activityProblem.getActivities();
        int[] FunLevels = activityProblem.getFunLevels();
        int[] RiskLevels = activityProblem.getRiskLevels();
        int RiskBudget = activityProblem.getRiskBudget();
        int numActivities = FunLevels.length;

        String[] sActivities;
        int[] sFunLevels;
        int[] sRiskLevels;




        int[][] FunArray = new int[numActivities][RiskBudget+1];
        boolean[][] SelectActivity = new boolean[numActivities][RiskBudget+1];

        int row = 0; int column = 0;
        int activity = 0;

        for(activity = 0; activity < numActivities; activity++){
            for(column = 0; column < (RiskBudget + 1); column++){
                //System.out.println("Row: "+activity+" Column: "+column);
                int ActivityRisk = RiskLevels[activity];
                int ActivityValue = FunLevels[activity];
                if(ActivityRisk > column){
                    if(activity == 0){
                        FunArray[activity][column] = 0;
                        SelectActivity[activity][column] = false;
                    }
                    else{
                        FunArray[activity][column] = FunArray[activity-1][column];
                        SelectActivity[activity][column] = false;
                    }
                }
                else{
                    if(activity == 0){
                        FunArray[activity][column] = ActivityValue;
                        SelectActivity[activity][column] = true;
                    }
                    else{
                        int ActivityValueAbove = FunArray[activity - 1][column];
                        int RiskLeft = column;
                        int maxActivityValue = ActivityValue;
                        RiskLeft = RiskLeft - ActivityRisk;
                        if(RiskLeft >= 0){
                            maxActivityValue = maxActivityValue + FunArray[activity - 1][RiskLeft];
                        }
                        if(maxActivityValue > ActivityValueAbove){
                            FunArray[activity][column] = maxActivityValue;
                            SelectActivity[activity][column] = true;
                        }
                        else{
                            FunArray[activity][column] = ActivityValueAbove;
                            SelectActivity[activity][column] = false;
                        }
                    }
                }
            }
        }

        Set<String> ActivitiesResult = new HashSet<String>();

        int RiskRemaining = RiskBudget;
        int thisActivity = numActivities - 1;

        while((thisActivity >= 0) && (RiskRemaining >= 0)){
            boolean select = SelectActivity[thisActivity][RiskRemaining];
            if(select == true){
                ActivitiesResult.add(Activities[thisActivity]);
                RiskRemaining = RiskRemaining - RiskLevels[thisActivity];
                //System.out.println("Selected: "+ Activities[thisActivity]);
            }
            thisActivity -= 1;

        }

        int MaxFunLevel = FunArray[numActivities-1][RiskBudget];


        /*
        for(activity = 0; activity < numActivities; activity++) {
            for (column = 0; column < (RiskBudget + 1); column++) {
                System.out.print(FunArray[activity][column] + " ");
            }
            System.out.println();
        }
        for(activity = 0; activity < numActivities; activity++) {
            for (column = 0; column < (RiskBudget + 1); column++) {
                System.out.print(SelectActivity[activity][column] + " ");
            }
            System.out.println();
        }
        */



        return new ActivityResult(MaxFunLevel, ActivitiesResult);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////



    public SchedulingResult selectScheduling(SchedulingProblem schedulingProblem){

        int[] mCosts = schedulingProblem.getMauiCosts();
        int[] oCosts = schedulingProblem.getOahuCosts();
        int transferCost = schedulingProblem.getTransferCost();

        int length = mCosts.length;
        if(length != oCosts.length){
            System.out.println("ERROR: Length of two arrays different");
        }

        int[][] mTotalCost = new int[length][length];
        int[][] oTotalCost = new int[length][length];

        char[][] mLocation = new char[length][length];
        char[][] oLocation = new char[length][length];

        int[][] mLink = new int[length][length];
        int[][] oLink = new int[length][length];

        int row = 0;
        int column = 0;

        for(int j = 0; j < length; j++){
            for(int i = 0; i < length; i++){
                if(i > j) {
                    mTotalCost[i][j] = -1;
                    oTotalCost[i][j] = -1;
                    oLink[i][j] = -1;
                    mLink[i][j] = -1;
                }
            }
        }

        for(column = 0; column < length; column++){
            for(row = 0; row < length; row++){
                if(column >= row){
                    if((column == 0) && (row == 0)){
                        mTotalCost[row][column] = mCosts[column];
                        mLocation[row][column] = 'm';
                        mLink[row][column] = row;

                        oTotalCost[row][column] = oCosts[column];
                        oLocation[row][column] = 'o';
                        oLink[row][column] = row;
                    }

                    //Compute m Arrays
                    int mStayRow = row;
                    int mStayColumn = column - 1;
                    int mSwitchRow = row - 1;
                    int mSwitchColumn = column - 1;
                    boolean mStayValid = true;
                    boolean mSwitchValid = true;

                    if((mStayRow < 0) || (mStayColumn < 0) || (mStayRow > mStayColumn)){
                        mStayValid = false;
                    }
                    if((mSwitchRow < 0) || (mSwitchColumn < 0) || (mSwitchRow > mSwitchColumn)){
                        mSwitchValid = false;
                    }

                    if((mStayValid == true) && (mSwitchValid == false)){
                        int mPrevCost = mTotalCost[mStayRow][mStayColumn];
                        char mPrevLocation = mLocation[mStayRow][mStayColumn];

                        if(mPrevLocation == 'm'){
                            mTotalCost[row][column] = mPrevCost + mCosts[column];
                            mLocation[row][column] = 'm';
                            mLink[row][column] = mStayRow;
                        }
                        else if(mPrevLocation == 'o'){
                            mTotalCost[row][column] = mPrevCost + oCosts[column];
                            mLocation[row][column] = 'o';
                            mLink[row][column] = mStayRow;
                        }
                        else{
                            System.out.println("ERROR: Invalid Previous Location");
                        }
                    }

                    if((mSwitchValid == true) && (mStayValid == false)){
                        int mPrevCost = mTotalCost[mSwitchRow][mSwitchColumn];
                        char mPrevLocation = mLocation[mSwitchRow][mSwitchColumn];

                        if(mPrevLocation == 'm'){
                            mTotalCost[row][column] = mPrevCost + transferCost + oCosts[column];
                            mLocation[row][column] = 'o';
                            mLink[row][column] = mSwitchRow;
                        }
                        else if(mPrevLocation == 'o'){
                            mTotalCost[row][column] = mPrevCost + transferCost + mCosts[column];
                            mLocation[row][column] = 'm';
                            mLink[row][column] = mSwitchRow;
                        }
                        else{
                            System.out.println("ERROR: Invalid Previous Location");
                        }
                    }

                    if((mSwitchValid == true) && (mStayValid == true)) {
                        char mSwitchLocation = mLocation[mSwitchRow][mSwitchColumn];
                        char mStayLocation = mLocation[mStayRow][mStayColumn];
                        int mSwitchCost = 0;
                        int mStayCost = 0;

                        if (mSwitchLocation == 'm') {
                            mSwitchCost = mTotalCost[mSwitchRow][mSwitchColumn] + transferCost + oCosts[column];
                        } else if (mSwitchLocation == 'o') {
                            mSwitchCost = mTotalCost[mSwitchRow][mSwitchColumn] + transferCost + mCosts[column];
                        } else {
                            System.out.println("ERROR: Invalid Previous Location");
                        }
                        /////////////

                        if (mStayLocation == 'm') {
                            mStayCost = mTotalCost[mStayRow][mStayColumn] + mCosts[column];
                        } else if (mStayLocation == 'o') {
                            mStayCost = mTotalCost[mStayRow][mStayColumn] + oCosts[column];
                        } else {
                            System.out.println("ERROR: Invalid Previous Location");
                        }

                        ////////////

                        if (mStayCost > mSwitchCost) {
                            //switch
                            mTotalCost[row][column] = mSwitchCost;
                            mLink[row][column] = mSwitchRow;
                            if (mSwitchLocation == 'm') {
                                mLocation[row][column] = 'o';
                            } else {
                                mLocation[row][column] = 'm';
                            }
                        }
                        else{
                            //stay
                            mTotalCost[row][column] = mStayCost;
                            mLink[row][column] = mStayRow;
                            if (mStayLocation == 'm') {
                                mLocation[row][column] = 'm';
                            } else {
                                mLocation[row][column] = 'o';
                            }
                        }
                    }




                    //Compute o Arrays
                    int oStayRow = row;
                    int oStayColumn = column - 1;
                    int oSwitchRow = row - 1;
                    int oSwitchColumn = column - 1;
                    boolean oStayValid = true;
                    boolean oSwitchValid = true;

                    if((oStayRow < 0) || (oStayColumn < 0) || (oStayRow > oStayColumn)){
                        oStayValid = false;
                    }
                    if((oSwitchRow < 0) || (oSwitchColumn < 0) || (oSwitchRow > oSwitchColumn)){
                        oSwitchValid = false;
                    }

                    if((oStayValid == true) && (oSwitchValid == false)){
                        int oPrevCost = oTotalCost[oStayRow][oStayColumn];
                        char oPrevLocation = oLocation[oStayRow][oStayColumn];

                        if(oPrevLocation == 'm'){
                            oTotalCost[row][column] = oPrevCost + mCosts[column];
                            oLocation[row][column] = 'm';
                            oLink[row][column] = oStayRow;
                        }
                        else if(oPrevLocation == 'o'){
                            oTotalCost[row][column] = oPrevCost + oCosts[column];
                            oLocation[row][column] = 'o';
                            oLink[row][column] = oStayRow;
                        }
                        else{
                            System.out.println("ERROR: Invalid Previous Location");
                        }
                    }

                    if((oSwitchValid == true) && (oStayValid == false)){
                        int oPrevCost = oTotalCost[oSwitchRow][oSwitchColumn];
                        char oPrevLocation = oLocation[oSwitchRow][oSwitchColumn];

                        if(oPrevLocation == 'm'){
                            oTotalCost[row][column] = oPrevCost + transferCost + oCosts[column];
                            oLocation[row][column] = 'o';
                            oLink[row][column] = oSwitchRow;
                        }
                        else if(oPrevLocation == 'o'){
                            oTotalCost[row][column] = oPrevCost + transferCost + mCosts[column];
                            oLocation[row][column] = 'm';
                            oLink[row][column] = oSwitchRow;
                        }
                        else{
                            System.out.println("ERROR: Invalid Previous Location");
                        }
                    }

                    if((oSwitchValid == true) && (oStayValid == true)) {
                        char oSwitchLocation = oLocation[oSwitchRow][oSwitchColumn];
                        char oStayLocation = oLocation[oStayRow][oStayColumn];
                        int oSwitchCost = 0;
                        int oStayCost = 0;

                        if (oSwitchLocation == 'm') {
                            oSwitchCost = oTotalCost[oSwitchRow][oSwitchColumn] + transferCost + oCosts[column];
                        }
                        else if (oSwitchLocation == 'o') {
                            oSwitchCost = oTotalCost[oSwitchRow][oSwitchColumn] + transferCost + mCosts[column];
                        }
                        else {
                            System.out.println("ERROR: Invalid Previous Location");
                        }
                        /////////////

                        if (oStayLocation == 'm') {
                            oStayCost = oTotalCost[oStayRow][oStayColumn] + mCosts[column];
                        }
                        else if (oStayLocation == 'o') {
                            oStayCost = oTotalCost[oStayRow][oStayColumn] + oCosts[column];
                        }
                        else {
                            System.out.println("ERROR: Invalid Previous Location");
                        }

                        ////////////

                        if (oStayCost > oSwitchCost) {
                            //switch
                            oTotalCost[row][column] = oSwitchCost;
                            oLink[row][column] = oSwitchRow;
                            if (oSwitchLocation == 'm') {
                                oLocation[row][column] = 'o';
                            }
                            else {
                                oLocation[row][column] = 'm';
                            }
                        }
                        else{
                            //stay
                            oTotalCost[row][column] = oStayCost;
                            oLink[row][column] = oStayRow;
                            if (oStayLocation == 'm') {
                                oLocation[row][column] = 'm';
                            }
                            else {
                                oLocation[row][column] = 'o';
                            }
                        }
                    }




                }

            }
        }

        /*
        System.out.println("---------------------------");
        System.out.println("M-Arrays:");
        System.out.println();
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                System.out.print(mTotalCost[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                System.out.print(mLocation[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("---------------------------");
        System.out.println("O-Arrays:");
        System.out.println();
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                System.out.print(oTotalCost[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                System.out.print(oLocation[i][j] + " ");
            }
            System.out.println();
        }
        */
        int minCost = mTotalCost[0][length-1];
        char minArray = 'm';
        int minRow = 0;
        char order[] = new char[length];


        for(int i = 0; i < length; i++){
            if(mTotalCost[i][length-1] < minCost){
                minCost = mTotalCost[i][length-1];
                minArray = 'm';
                minRow = i;
            }
            if(oTotalCost[i][length-1] < minCost){
                minCost = oTotalCost[i][length-1];
                minArray = 'o';
                minRow = i;
            }
        }

        //System.out.println();
        //System.out.println("minCost: " + minCost);

        for(int i = (length - 1); i >= 0; i--){
            if(minArray == 'm'){
                order[i] = mLocation[minRow][i];
                minRow = mLink[minRow][i];
            }
            else{
                order[i] = oLocation[minRow][i];
                minRow = oLink[minRow][i];
            }
        }

        /*
        System.out.println();
        System.out.print("Order: ");
        for(int i = 0; i < length; i++){
            System.out.print(order[i] + " ");
        }
        System.out.println();
        System.out.println();
        */

        boolean TForder[] = new boolean[length];

        for(int i = 0; i < length; i++){
            if(order[i] == 'm'){
                TForder[i] = true;
            }
            else{
                TForder[i] = false;
            }
        }







        return new SchedulingResult(TForder);
    }

}
