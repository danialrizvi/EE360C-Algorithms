/**
 */

import java.io.IOException;
import java.io.*;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

public class Graph {

    /*
     * Creates a graph to represent the neighborhood, where unlocked is the file name for the unlocked houses
     * and keys is the file name for which houses have which keys.
     */

    public class House{
        private String Name;
        private int NumKeys;
        private int KeysObtained;
        private boolean Unlocked;
        private boolean Visited;

        ////////////////////////////////////////
        //CONSTRUCTOR, GET, AND SET FUNCTIONS
        ////////////////////////////////////////
        public House(String Name, int NumKeys, int KeysObtained, boolean Unlocked, boolean Visited){
            this.Name = Name;
            this.NumKeys = NumKeys;
            this.KeysObtained = KeysObtained;
            this.Unlocked = Unlocked;
            this.Visited = Visited;
        }

        public String getName(){
            return this.Name;
        }

        public int getNumKeys(){
            return this.NumKeys;
        }

        public int getKeysObtained(){
            return this.KeysObtained;
        }

        public boolean getUnlocked(){
            return this.Unlocked;
        }

        public boolean getVisited(){
            return this.Visited;
        }

        public void AddKey(){
            this.NumKeys += 1;
        }

        public void AddKeysObtained(){
            this.KeysObtained += 1;
        }

        public void setVisited(){
            this.Visited = true;
        }
    }



    private ArrayList<LinkedList<House>> Graph; //Construct Graph of Houses

    public ArrayList<LinkedList<House>> getGraph(){
        return this.Graph;
    }

    public void setGraph(ArrayList<LinkedList<House>> Graph){
        this.Graph = Graph;
    }

    public LinkedList<House> getGraphLL(int i){
        return this.Graph.get(i);
    }



    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    //CONSTRUCT GRAPH
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    public Graph(String unlocked, String keys) {

        //////////////////////////////////////////////
        //Initialize and File Paths
        //////////////////////////////////////////////
        ArrayList<LinkedList<House>> Graph = new ArrayList<LinkedList<House>>();
        ArrayList<String> UnlockedH = new ArrayList<String>();
        try {
        FileReader u_path = new FileReader(unlocked);
        FileReader k_path = new FileReader(keys);



            /////////////////////////////////////////////
            //Scan through documents one line at a time
            /////////////////////////////////////////////
            Scanner k1_scanner = new Scanner(k_path);
            k1_scanner.useDelimiter("\n");

            Scanner u_scanner = new Scanner(u_path);
            u_scanner.useDelimiter("\n");

            /////////////////////////////////////////////
            //Make an array of Unlocked houses
            /////////////////////////////////////////////
            while(u_scanner.hasNext()){
                String u_house = u_scanner.next();
                u_house = u_house.trim();
                UnlockedH.add(u_house);

            }

            /////////////////////////////////////////////////////////////////////////
            //Obtain each house, make house class, add to LinkedList, add to Graph
            //////////////////////////////////////////////////////////////////////////
            while (k1_scanner.hasNext()) {
                String line = k1_scanner.next();
                String[] tokens0 = line.split("[,:]+"); //split line into House tokens
                List<String> tokens1 = new ArrayList<String>(Arrays.asList(tokens0));

                for (int i = 0; i < tokens1.size(); i++){
                    tokens1.set(i, tokens1.get(i).trim());
                    //tokens[i] = tokens[i].trim(); //remove leading whitespace
                }
                tokens1.removeAll(Arrays.asList(""," ",null));

                String[] tokens = new String[tokens1.size()];
                tokens1.toArray(tokens);

                LinkedList<House> HouseLL = new LinkedList<House>();  //Create Linked list of Houses

                for (int i = 0; i < tokens.length; i++){
                    if(i < 1) {
                        //Add first house regardless of unlocked status
                        if(UnlockedH.contains(tokens[i]) == true) {
                            House H1 = new House(tokens[i], 0, 0, true, false);
                            HouseLL.add(H1);
                        }
                        else{
                            House H1 = new House(tokens[i], 0, 0, false, false);
                            HouseLL.add(H1);
                        }

                    }
                    //Do not link unlocked houses
                    else if(UnlockedH.contains(tokens[i]) != true){ //do not add an unlocked house
                        House H1 = new House(tokens[i], 0, 0, false, false);
                        HouseLL.add(H1);
                    }
                }

                Graph.add(HouseLL); //Add LinkedList to Graph
            }


            ///////////////////////////////////////////////////
            //Calculate NumKeys for each House
            ///////////////////////////////////////////////////
            for(int i = 0; i < Graph.size(); i++){
                LinkedList<House> CurrentLL = Graph.get(i);
                for(int j = 1; j < CurrentLL.size(); j++){
                    String CurrentHouseName = CurrentLL.get(j).getName();
                    for(int k = 0; k < Graph.size(); k++){
                        String IndexName = Graph.get(k).get(0).getName();
                        if(CurrentHouseName.equals(IndexName)){
                            Graph.get(k).get(0).AddKey();
                        }

                    }
                }
            }

            /////////////////////////////////////////////////
            //Print Unlocked Houses
            /////////////////////////////////////////////////
            /*System.out.println();
            for(int i = 0; i < Graph.size(); i++){
                if(Graph.get(i).get(0).getUnlocked() == true) {
                    System.out.println("Unlocked: " +Graph.get(i).get(0).getName());
                }
            }*/

            /////////////////////////////////////////////////
            //Print NumKeys for each House
            /////////////////////////////////////////////////
            /*
            System.out.println();
            for(int i = 0; i < Graph.size(); i++){
                LinkedList<House> CurrentLL = Graph.get(i);
                House CurrentHouse = CurrentLL.get(0);
                System.out.println("Num Keys for "+CurrentHouse.getName()+": "+CurrentHouse.getNumKeys());
            }
            */
            ///////////////////////////////////////////////////
            //Print Graph
            ///////////////////////////////////////////////////
            /*
            System.out.println();
            System.out.println("Original Graph:");
            System.out.println("NumHouses: "+Graph.size());
            for(int i = 0; i < Graph.size(); i++){
                LinkedList<House> CurrentLL = Graph.get(i);
                for(int j = 0; j < CurrentLL.size(); j++){
                    House CurrentHouse = CurrentLL.get(j);
                    System.out.print(CurrentHouse.getName()+",");

                }
                System.out.println();
            }
            */
            ////////////////////////////////////////////////////////////
            setGraph(Graph); //assign new constructed Graph to Graph
            ////////////////////////////////////////////////////////////

            /////////////////////////////////////////////////
            k1_scanner.close();
            u_scanner.close();
        }
        catch(IOException exc){
            System.out.println("Error accessing files");
        }


    }

    /*
     * This method should return true if the Graph contains the vertex described by the input String.
     */
    public boolean containsVertex(String node) {
        node = node.trim();
        for(int i = 0; i < Graph.size(); i++){
            if(Graph.get(i).get(0).getName().equals(node)){
                return true;
            }
        }
        return false;
    }

    /*
     * This method should return true if there is a direct edge from the vertex
     * represented by start String and end String.
     */
    public boolean containsEdge(String start, String end) {
        start = start.trim();
        end = end.trim();
        for(int i = 0; i < Graph.size(); i++){
            if(Graph.get(i).get(0).getName().equals(start)){
                LinkedList<House> CurrentLL = Graph.get(i);
                for(int j = 0; j < CurrentLL.size(); j++){
                    if(CurrentLL.get(j).getName().equals(end)){
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /*
     * This method returns true if the house represented by the input String is locked
     * and false if the house has been left unlocked.
     */
    public boolean isLocked(String house) {
        house = house.trim();
        for(int i = 0; i < Graph.size(); i++) {
            if(Graph.get(i).get(0).getName().equals(house)){
                if(Graph.get(i).get(0).getUnlocked() == true){
                    return false;
                }
            }
        }
        return true;

    }
}
