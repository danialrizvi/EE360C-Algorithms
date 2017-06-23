/**
 */

import java.util.*;
import java.lang.*;
import java.io.IOException;
import java.io.*;
//import java.nio.file.Path;
//import java.nio.file.Paths;

/*
     * This method should return true if the robber can rob all the houses in the neighborhood,
     * which are represented as a graph, and false if he cannot. The function should also print to the console the
     * order in which the robber should rob the houses if he can rob the houses. You do not need to print anything
     * if all the houses cannot be robbed.
     */

public class Robber {

    private ArrayList<String> RobbingList;


    public boolean canRobAllHouses(Graph neighborhood) {

        ArrayList<LinkedList<Graph.House>> Graph1 = neighborhood.getGraph();
        ArrayList<LinkedList<Graph.House>> sGraph1 = new ArrayList<LinkedList<Graph.House>>();

        ArrayList<String> RobbingList = new ArrayList<String>();

        int numHouses = Graph1.size();

        for (int i = 0; i < numHouses; i++) {
            if (Graph1.get(i).get(0).getUnlocked() == true) {
                LinkedList<Graph.House> HLL = Graph1.get(i);
                sGraph1.add(HLL);
            }
        }

        for (int i = 0; i < numHouses; i++) {
            if (Graph1.get(i).get(0).getUnlocked() == false) {
                LinkedList<Graph.House> HLL = Graph1.get(i);
                if (sGraph1.size() > 0) {
                    if ((HLL.get(0).getNumKeys()) < (sGraph1.get(sGraph1.size() - 1).get(0).getNumKeys())) { //NumKeys of this House is not greatest
                        for (int j = 0; j < sGraph1.size(); j++) {
                            if ((HLL.get(0).getNumKeys()) < sGraph1.get(j).get(0).getNumKeys()) {
                                sGraph1.add(j, HLL); //add at appropriate index
                                break;
                            }
                        }
                    } else {
                        sGraph1.add(HLL);
                    }
                } else {
                    sGraph1.add(HLL);
                }

            }
        }

        for (int i = 0; i < sGraph1.size(); i++) {
            if ((sGraph1.get(i).get(0).getUnlocked() == false) && (sGraph1.get(i).get(0).getNumKeys() == 0)) {
                //System.out.println("We've got a locked house with 0 Keys (impossible to rob all)");
                return false;
            }
        }

        boolean visitedAtLeastOne = true;
        //System.out.println("--- Done Sorting Graph ---");
        int loop = 0;

        while (visitedAtLeastOne == true) {
            visitedAtLeastOne = false;
            loop++;
            //System.out.println("Times through While Loop: "+loop);
            for (int i = 0; i < sGraph1.size(); i++) {
                Graph.House H1 = sGraph1.get(i).get(0);
                if ((H1.getUnlocked() == true) && (H1.getVisited() == false)) {
                    sGraph1.get(i).get(0).setVisited(); //visited this house
                    visitedAtLeastOne = true; //visited at least one house
                    RobbingList.add(H1.getName()); //add to list of houses to rob
                    //System.out.println("Added to Rob List: "+H1.getName());
                    LinkedList<Graph.House> H1LL = sGraph1.get(i); //LL of keys inside house
                    for (int j = 1; j < H1LL.size(); j++) {
                        String Hname = H1LL.get(j).getName(); //name of the house key
                        for (int k = 0; k < sGraph1.size(); k++) {
                            if (Hname.equals(sGraph1.get(k).get(0).getName())) {
                                sGraph1.get(k).get(0).AddKeysObtained(); //found a key for house
                            }
                        }
                    }
                }

                if ((H1.getUnlocked() == false) && (H1.getVisited() == false)) {
                    if ((H1.getNumKeys()) == (H1.getKeysObtained())) {
                        sGraph1.get(i).get(0).setVisited(); //visited this house
                        visitedAtLeastOne = true; //visited at least one house
                        RobbingList.add(H1.getName()); //add to list of houses to rob
                        //System.out.println("Added to Rob List: " + H1.getName());
                        LinkedList<Graph.House> H2LL = sGraph1.get(i); //LL of keys inside house
                        for (int j = 1; j < H2LL.size(); j++) {
                            String H2name = H2LL.get(j).getName(); //name of the house key
                            for (int k = 0; k < sGraph1.size(); k++) {
                                if (H2name.equals(sGraph1.get(k).get(0).getName())) {
                                    sGraph1.get(k).get(0).AddKeysObtained(); //found a key for house
                                }
                            }
                        }
                    }
                }
            }
        }


        /*System.out.println("-------------------------------");
        System.out.println("Sorted Graph by NumKeys:");
        System.out.println("NumHouses: " + sGraph1.size());
        for (int i = 0; i < sGraph1.size(); i++) {
            LinkedList<Graph.House> CurrentLL = sGraph1.get(i);
            for (int j = 0; j < CurrentLL.size(); j++) {
                Graph.House CurrentHouse = CurrentLL.get(j);
                System.out.print(CurrentHouse.getName() + ",");

            }
            System.out.println();
        }*/

        //System.out.println();
        if (RobbingList.size() != sGraph1.size()) {
            //System.out.println("WE CANT ROB ALL THE HOUSES!!!");
            return false;
        }


        // System.out.println();
        // System.out.println("Robbing Order (All)");
        System.out.print(RobbingList.get(0));
        for (int i = 1; i < RobbingList.size(); i++) {
            System.out.print(", "+RobbingList.get(i));
        }

        System.out.println();
        System.out.println();





        //System.out.println();
        /*System.out.println("Keys Obtained");
        for (int i = 0; i < sGraph1.size(); i++) {
            System.out.println(sGraph1.get(i).get(0).getName() + ": " + sGraph1.get(i).get(0).getKeysObtained());
        }*/

        return true;
    }

    public class LootItem {
        private String Item;
        private Float Amount;
        private Float Value;
        private Float TakeAmount;

        public LootItem(String Item, Float Amount, Float Value, Float TakeAmount) {
            this.Item = Item;
            this.Amount = Amount;
            this.Value = Value;
            this.TakeAmount = TakeAmount;
        }

        public String getItem() {
            return this.Item;
        }

        public Float getAmount() {
            return this.Amount;
        }

        public Float getValue() {
            return this.Value;
        }

        public Float getTakeAmount() {
            return this.TakeAmount;
        }

        public void setTakeAmount(Float TakeAmount) {
            this.TakeAmount = TakeAmount;
        }


    }


    public void maximizeLoot(String lootList) {

        // System.out.println("--------------------------------");
        // System.out.println();

        try {
            FileReader ll_path = new FileReader(lootList);

            /////////////////////////////////////////////
            //Scan through documents one line at a time
            /////////////////////////////////////////////
            Scanner ll_scanner = new Scanner(ll_path);
            ll_scanner.useDelimiter("\n");
            ArrayList<LootItem> LootList = new ArrayList<LootItem>();
            Float maxAmount = Float.parseFloat("0");

            /////////////////////////////////////////////////////////////////////////
            //Obtain each token
            //////////////////////////////////////////////////////////////////////////
            while (ll_scanner.hasNext()) {
                String line = ll_scanner.next();
                String[] tokens0 = line.split("[,]+"); //split line into House tokens
                List<String> tokens1 = new ArrayList<String>(Arrays.asList(tokens0));

                for (int i = 0; i < tokens1.size(); i++) {
                    tokens1.set(i, tokens1.get(i).trim());
                }
                tokens1.removeAll(Arrays.asList("", " ", null));

                String[] tokens = new String[tokens1.size()];
                tokens1.toArray(tokens);

                if (tokens.length == 1) {
                    maxAmount = Float.parseFloat(tokens[0]);
                    //System.out.println("FruitCake can carry max: " + maxAmount);
                } else if (tokens.length == 3) {
                    Float Amount1 = Float.parseFloat(tokens[1]);
                    Float Value1 = Float.parseFloat(tokens[2]);
                    Float Zero = Float.parseFloat("0");
                    LootItem L1 = new LootItem(tokens[0], Amount1, Value1, Zero);
                    LootList.add(L1);
                    // System.out.println("Item added: " + tokens[0] + ", Item Amount: " + Amount1 + ", Item Value: " + Value1);
                } else {
                    System.out.println("Wrong number of parameters in line of LootList!!!");
                }
            }

            ArrayList<LootItem> sLootList = new ArrayList<LootItem>();

            for (int i = 0; i < LootList.size(); i++) {
                LootItem L2 = LootList.get(i);
                Float ItemValue = LootList.get(i).getValue();
                if (sLootList.size() > 0) {
                    Float MaxItemValue = sLootList.get(sLootList.size() - 1).getValue();
                    if (ItemValue > MaxItemValue) {
                        for (int j = 0; j < sLootList.size(); j++) {
                            if (ItemValue > sLootList.get(j).getValue()) {
                                sLootList.add(j, L2);
                                break;
                            }
                        }
                    }
                    else {
                        sLootList.add(L2);
                    }
                }
                else {
                    sLootList.add(L2);
                }
            }

            Float AmountLeft = maxAmount;

            for (int i = 0; i < sLootList.size(); i++) {
                LootItem L4 = sLootList.get(i);
                if (AmountLeft > L4.getAmount()) {
                    L4.setTakeAmount(L4.getAmount());
                    AmountLeft = AmountLeft - L4.getAmount();
                } else {
                    L4.setTakeAmount(AmountLeft);
                    break;
                }
            }

            /*System.out.println();
            System.out.println("Sorted List by Value");
            for (int i = 0; i < sLootList.size(); i++) {
                LootItem L3 = sLootList.get(i);
                System.out.println("Item added: " + L3.getItem() + ", Item Amount: " + L3.getAmount() + ", Item Value: " + L3.getValue());
            }*/

            //System.out.println();
            //System.out.println("Take these Items:");
            for (int i = 0; i < sLootList.size(); i++) {
                if (sLootList.get(i).getTakeAmount() > 0) {
                    LootItem L3 = sLootList.get(i);
                    System.out.println(L3.getItem()+" "+L3.getTakeAmount());
                } else {
                    break;
                }
            }
            System.out.println();


        } catch (IOException exc) {
            System.out.println("Error accessing files");
        }

    }


    public class Meeting {
        private String Buyer;
        private Float StartTime;
        private Float EndTime;
        private int StartHour;
        private int StartMin;
        private int EndHour;
        private int EndMin;
        private Float BufStartTime;
        private Float BufEndTime;
        private boolean Attend;

        public Meeting(String Buyer, Float StartTime, Float EndTime,
                       Float StartHour, Float StartMin, Float EndHour, Float EndMin,
                       Float BufStartTime, Float BufEndTime) {
            this.Buyer = Buyer;
            this.StartTime = StartTime;
            this.EndTime = EndTime;
            this.StartHour = Float.floatToIntBits(StartHour);
            this.StartMin = Float.floatToIntBits(StartMin);
            this.EndHour = Float.floatToIntBits(EndHour);
            this.EndMin = Float.floatToIntBits(EndMin);
            this.BufStartTime = BufStartTime;
            this.BufEndTime = BufEndTime;
            this.Attend = false;
        }

        public String getBuyer() {
            return this.Buyer;
        }

        public Float getStartTime() {
            return this.StartTime;
        }

        public Float getEndTime() {
            return this.EndTime;
        }

        public int getStartHour() {
            return this.StartHour;
        }

        public int getStartMin() {
            return this.StartMin;
        }

        public int getEndHour() {
            return this.EndHour;
        }

        public int getEndMin() {
            return this.EndMin;
        }

        public Float getBufStartTime() {
            return this.BufStartTime;
        }

        public Float getBufEndTime() {
            return this.BufEndTime;
        }

        public boolean getAttend() {
            return this.Attend;
        }

        public void setAttend() {
            this.Attend = true;
        }

    }


    public void scheduleMeetings(String buyerList) {

        //System.out.println("--------------------------------");
        //System.out.println();

        try {
            FileReader b_path = new FileReader(buyerList);

            /////////////////////////////////////////////
            //Scan through documents one line at a time
            /////////////////////////////////////////////
            Scanner b_scanner = new Scanner(b_path);
            b_scanner.useDelimiter("\n");
            ArrayList<Meeting> MeetingsList = new ArrayList<Meeting>();

            /////////////////////////////////////////////////////////////////////////
            //Obtain each token
            //////////////////////////////////////////////////////////////////////////
            while (b_scanner.hasNext()) {
                String line = b_scanner.next();
                String[] tokens0 = line.split("[,-]+"); //split line into Meeting/Time tokens
                List<String> tokens1 = new ArrayList<String>(Arrays.asList(tokens0));

                for (int i = 0; i < tokens1.size(); i++) {
                    tokens1.set(i, tokens1.get(i).trim());
                }
                tokens1.removeAll(Arrays.asList("", " ", null));

                //String[] tokens = new String[tokens1.size()];
                //tokens1.toArray(tokens);

                if ((tokens1.size() != 3)) {
                    System.out.println("Error wrong number of tokens");
                }

                String BuyerName = tokens1.get(0);
                String Start = tokens1.get(1);
                String End = tokens1.get(2);

                Float S1hour = 0f;
                Float S1min = 0f;
                Float Stime = 0f;
                Float E1hour = 0f;
                Float E1min = 0f;
                Float Etime = 0f;


                ///////////////////////////////////////////////////////////////////
                if (Start.contains("pm")) {
                    String S1 = Start.replace("pm", "");
                    if (S1.contains(":")) {
                        String[] S1tokens = S1.split("[:]+");
                        if (S1tokens.length != 2) {
                            System.out.println("Error wrong number of tokens");
                        }
                        S1hour = Float.parseFloat(S1tokens[0]);
                        S1min = Float.parseFloat(S1tokens[1]);
                        if ((S1hour < 1) || (S1hour > 12)) {
                            System.out.println("Error incorrect hour");
                        }
                        if ((S1min < 0) || (S1min > 59)) {
                            System.out.println("Error incorrect minute");
                        }
                        if (S1hour != 12) {
                            S1hour = S1hour + 12;
                        }
                        Stime = S1hour + (S1min / 60);
                    } else {
                        S1hour = Float.parseFloat(S1);
                        S1min = 0f;
                        if ((S1hour < 1) || (S1hour > 12)) {
                            System.out.println("Error incorrect hour");
                        }
                        if (S1hour != 12) {
                            S1hour = S1hour + 12;
                        }
                        Stime = S1hour + (S1min / 60);
                    }
                } else if (Start.contains("am")) {
                    String S1 = Start.replace("am", "");
                    if (S1.contains(":")) {
                        String[] S1tokens = S1.split("[:]+");
                        if (S1tokens.length != 2) {
                            System.out.println("Error wrong number of tokens");
                        }
                        S1hour = Float.parseFloat(S1tokens[0]);
                        S1min = Float.parseFloat(S1tokens[1]);
                        if ((S1hour < 1) || (S1hour > 12)) {
                            System.out.println("Error incorrect hour");
                        }
                        if ((S1min < 0) || (S1min > 59)) {
                            System.out.println("Error incorrect minute");
                        }
                        if (S1hour == 12) {
                            S1hour = S1hour - 12;
                        }
                        Stime = S1hour + (S1min / 60);
                    } else {
                        S1hour = Float.parseFloat(S1);
                        S1min = 0f;
                        if ((S1hour < 1) || (S1hour > 12)) {
                            System.out.println("Error incorrect hour");
                        }
                        if (S1hour == 12) {
                            S1hour = S1hour - 12;
                        }
                        Stime = S1hour + (S1min / 60);
                    }
                }

                //////////

                if (End.contains("pm")) {
                    String E1 = End.replace("pm", "");
                    if (E1.contains(":")) {
                        String[] E1tokens = E1.split("[:]+");
                        if (E1tokens.length != 2) {
                            System.out.println("Error wrong number of tokens");
                        }
                        E1hour = Float.parseFloat(E1tokens[0]);
                        E1min = Float.parseFloat(E1tokens[1]);
                        if ((E1hour < 1) || (E1hour > 12)) {
                            System.out.println("Error incorrect hour");
                        }
                        if ((E1min < 0) || (E1min > 59)) {
                            System.out.println("Error incorrect minute");
                        }
                        if (E1hour != 12) {
                            E1hour = E1hour + 12;
                        }
                        Etime = E1hour + (E1min / 60);
                    } else {
                        E1hour = Float.parseFloat(E1);
                        E1min = 0f;
                        if ((E1hour < 1) || (E1hour > 12)) {
                            System.out.println("Error incorrect hour");
                        }
                        if (E1hour != 12) {
                            E1hour = E1hour + 12;
                        }
                        Etime = E1hour + (E1min / 60);
                    }
                } else if (End.contains("am")) {
                    String E1 = End.replace("am", "");
                    if (E1.contains(":")) {
                        String[] E1tokens = E1.split("[:]+");
                        if (E1tokens.length != 2) {
                            System.out.println("Error wrong number of tokens");
                        }
                        E1hour = Float.parseFloat(E1tokens[0]);
                        E1min = Float.parseFloat(E1tokens[1]);
                        if ((E1hour < 1) || (E1hour > 12)) {
                            System.out.println("Error incorrect hour");
                        }
                        if ((E1min < 0) || (E1min > 59)) {
                            System.out.println("Error incorrect minute");
                        }
                        if (E1hour == 12) {
                            E1hour = E1hour - 12;
                        }
                        Etime = E1hour + (E1min / 60);
                    } else {
                        E1hour = Float.parseFloat(E1);
                        E1min = 0f;
                        if ((E1hour < 1) || (E1hour > 12)) {
                            System.out.println("Error incorrect hour");
                        }
                        if (E1hour == 12) {
                            E1hour = E1hour - 12;
                        }
                        Etime = E1hour + (E1min / 60);
                    }
                }
                //////////////////////////////////////////////////////////////////
                Float BufStime = Stime - 0.124f;
                Float BufEtime = Etime + 0.124f;

                Meeting M1 = new Meeting(BuyerName, Stime, Etime, S1hour, S1min, E1hour, E1min, BufStime, BufEtime);
                MeetingsList.add(M1);

            }

            ArrayList<Meeting> sMeetingsList = new ArrayList<Meeting>();

            for (int i = 0; i < MeetingsList.size(); i++) {
                Meeting M2 = MeetingsList.get(i);
                Float MeetEndTime = MeetingsList.get(i).getEndTime();
                if (sMeetingsList.size() > 0) {
                    Float MaxEndTime = sMeetingsList.get(sMeetingsList.size() - 1).getEndTime();
                    if (MeetEndTime < MaxEndTime) {
                        for (int j = 0; j < sMeetingsList.size(); j++) {
                            if (MeetEndTime < sMeetingsList.get(j).getEndTime()) {
                                sMeetingsList.add(j, M2);
                                break;
                            }
                        }
                    }
                    else {
                        sMeetingsList.add(M2);
                    }
                }
                else {
                    sMeetingsList.add(M2);
                }
            }

            Float prevEndTime = -1f;
            for (int i = 0; i < sMeetingsList.size(); i++) {
                if(sMeetingsList.get(i).getBufStartTime() > prevEndTime){
                    sMeetingsList.get(i).setAttend();
                    prevEndTime = sMeetingsList.get(i).getBufEndTime();
                }
            }

            for (int i = 0; i < sMeetingsList.size(); i++) {
                if(sMeetingsList.get(i).getAttend() == true) {
                    Meeting M3 = sMeetingsList.get(i);
                    System.out.println(M3.getBuyer());
                }
            }

            System.out.println();

        } catch (IOException exc) {
            System.out.println("Error accessing files");
        }
    }
}
