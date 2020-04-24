package distance_vector_routing;

import java.util.ArrayList;

public class Entity0 extends Entity {
	// Perform any necessary initialization in the constructor
	private static final ArrayList<Integer> neighbors = new ArrayList<>();
	private int[] minCostEntity0 = new int[4];

	public Entity0() {
		System.out.println("Initializing entity 0 \n");
		// Adds all neighbors of node 0
		neighbors.add(1);
		neighbors.add(2);
		neighbors.add(3);
		int[] initialCosts = new int[] { 0, 1, 3, 7 }; // Initial Costs declaration
		// Link costs between each node is initialized and sent to the distance table.
		for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
			for (int j = 0; j < 4; j++) {

				if (i == j) {
					distanceTable[i][j] = initialCosts[i];
				} else {
					distanceTable[i][j] = 999;
				}
			}
		}
		printDT();
		for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
			minCostEntity0[i] = caluclateMinCostRecursive(distanceTable[i]);
		}
		// If next node changes, a new packet with new minimum cost values is created.
		// sent to all neighboring nodes.
		int k = 0;
		for (int j = 0; j < neighbors.size(); j++) {
			Packet p = new Packet(k, neighbors.get(j), minCostEntity0);
			NetworkSimulator.toLayer2(p);
		}
	}

	// Handle updates when a packet is received. Students will need to call
	// NetworkSimulator.toLayer2() with new packets based upon what they
	// send to update. Be careful to construct the source and destination of
	// the packet correctly. Read the warning in NetworkSimulator.java for more
	// details.
	public void update(Packet p) {
		// calls the method in Entity.java
		this.update(p, minCostEntity0, neighbors);
	}

	public void linkCostChangeHandler(int whichLink, int newCost) {
	}

	public void printDT() {
		System.out.println();
		System.out.println("           via");
		System.out.println(" D0 |   1   2   3");
		System.out.println("----+------------");
		for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++) {
			System.out.print("   " + i + "|");
			for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++) {
				if (distanceTable[i][j] < 10) {
					System.out.print("   ");
				} else if (distanceTable[i][j] < 100) {
					System.out.print("  ");
				} else {
					System.out.print(" ");
				}

				System.out.print(distanceTable[i][j]);
			}
			System.out.println();
		}
	}
}
