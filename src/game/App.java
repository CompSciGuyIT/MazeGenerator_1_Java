package game;

public class App {

	public static int size = 16;
	
	public static void main(String[] args) {

		Block[] Maze = new Block[size];
		
		for (int i = 0; i < size; i++) {
			Maze[i] = new Block(i);
		}

		// increment through each block in the maze
		// until all are no longer vacant
		for (Block block : Maze) {
			
			// check if current block is vacant
			if (block.isVacant()) {
				
				// setVacant to false
				block.setVacant(false);
				
				// check if adjacent blocks are occupied
				
					// if a block is occupied check if that side's value is set to open or closed
					// set same side in current block the same value
				
					// decrement number of sides variable in current block
				
				// if number of sides > 0 randomise that number
				
					// check each side of current block
				
						// if side is unassigned, check if random number is greater than zero
				
							// assign that side to be closed
							// decrement random number
							// decrement number of sides
				
						// if side is unassigned and random number is not greater than zero
				
							// assign that side to open
							// close all unassigned sides
				
				/////////////////////////////
				
				// Move to next block through side that just been assigned to be open
				
			}
		}

	}

}
