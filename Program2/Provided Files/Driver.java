/**
 *
 */
public class Driver {

    /* This driver is provided to help you test your program. You can make changes to this file, but we will not use
     * them for grading your program. We will use our own driver for grading your program. Hence, the correctness of
     * your algorithms should not be dependent on the code in this file.
     */
    public static void main(String[] args) {
       // C:/Users/DRizvi/Desktop/EE360C/Lab2/
       Graph neighborhood = new Graph("unlocked2.txt", "keys.txt");
       /*boolean locked = neighborhood.isLocked("House A");
       System.out.println("House A is Locked: "+locked);
       boolean vertex = neighborhood.containsVertex("House F");
       System.out.println("House A is Vertex: "+vertex);
       boolean edge = neighborhood.containsEdge("House A","House D");
       System.out.println("House A to House E is Edge: "+edge);*/
       Robber fruitcake = new Robber();
       fruitcake.canRobAllHouses(neighborhood);
       fruitcake.maximizeLoot("lootList.txt");
       fruitcake.scheduleMeetings("buyerList.txt");
    }
}
