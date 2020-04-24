package distance_vector_routing;

import java.util.ArrayList;

public class Entity3 extends Entity {
	// Perform any necessary initialization in the constructor
	private static final ArrayList<Integer> neighbors = new ArrayList<>();
	private int[] minCostEntity3 = new int[4];

	public Entity3() {
		System.out.println("\nInitializing entity 3 ");
		int[] initialCosts = new int[] { 7, 999, 2, 0 }; // Initial Costs declaration

		// Adds all neighbors of node 3
		neighbors.add(2);
		neighbors.add(0);

		// Link costs between each node is initialized and sent to the distance table.
		for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
			for (int j = 0; j < 4; j++) {

				if (i == j) {
					this.distanceTable[i][j] = initialCosts[i];
				} else {
					this.distanceTable[i][j] = 999;
				}
			}
		}
		printDT();
		for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
			minCostEntity3[i] = caluclateMinCostRecursive(this.distanceTable[i]);
		}

		// If next node changes, a new packet with new minimum cost values is created.
		// sent to all neighboring nodes.
		int k = 3;
		for (int j : neighbors) {
			Packet p = new Packet(k, j, minCostEntity3);
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
		this.update(p, minCostEntity3, neighbors);
	}

	public void linkCostChangeHandler(int whichLink, int newCost) {
	}

	public void printDT() {
		System.out.println("         via");
		System.out.println(" D3 |   0   2");
		System.out.println("----+--------");
		for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
			if (i == 3) {
				continue;
			}

			System.out.print("   " + i + "|");
			for (int j = 0; j < NetworkSimulator.NUMENTITIES; j += 2) {

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
