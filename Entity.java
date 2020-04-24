package distance_vector_routing;

import java.util.ArrayList;

public abstract class Entity {
	// Each entity will have a distance table
	protected int[][] distanceTable = new int[NetworkSimulator.NUMENTITIES][NetworkSimulator.NUMENTITIES];

	// The update function. Will have to be written in subclasses by students
	public abstract void update(Packet p);

	// The link cost change handlder. Will have to be written in appropriate
	// subclasses by students. Note that only Entity0 and Entity1 will need
	// this, and only if extra credit is being done
	public abstract void linkCostChangeHandler(int whichLink, int newCost);

	// Print the distance table of the current entity.
	protected abstract void printDT();

	// method to calculate the minimum cost
	protected static int caluclateMinCostRecursive(int a[]) {
		int min = a[0];
		for (int m : a) {
			if (m < min) {
				min = m;
			}
		}
		return min;
	}

	public void update(Packet p, int[] minCostEntity, ArrayList<Integer> neighbors) {
		int entityId = 0; // Name of the node is 0
		int source = p.getSource(); // get the source ID
		int[] currentMinCost = new int[4];
		for (int m = 0; m < NetworkSimulator.NUMENTITIES; m++) {
			currentMinCost[m] = p.getMincost(m);
		}
		System.out.println(String.format("Entity %d update is called, dest = %d, source = %d", entityId, p.getDest(),
				p.getSource()));
		System.out.println("costs: ");
		for (int z = 0; z < 4; z++) {
			System.out.println(p.getMincost(z));
		}
		System.out.println("\n");
		boolean isUpdated = false;

		for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
			int updatableValue = this.distanceTable[source][source] + currentMinCost[i];

			if (updatableValue < 999) {
				this.distanceTable[i][source] = updatableValue;
			} else {
				this.distanceTable[i][source] = 999;
			}
		}
		printDT();
		// calculate the minimum cost between node 0 and its neighboring nodes
		int[] cost;
		cost = minCostEntity;
		for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
			minCostEntity[j] = caluclateMinCostRecursive(this.distanceTable[j]);
		}

		for (int k = 0; k < 4; k++) {
			if (cost[k] != minCostEntity[k]) {
				isUpdated = true; // Assert isUpdated to true.
			}
		}
		// After updating the costs, further the
		// distance table is printed with the new updated minimum costs.
		if (isUpdated) {
			for (int j : neighbors) {
				Packet updatedPacket = new Packet(entityId, j, minCostEntity);
				NetworkSimulator.toLayer2(updatedPacket);
			}
		}

		else {
			System.out.println("no updates");
		}
	}

}
