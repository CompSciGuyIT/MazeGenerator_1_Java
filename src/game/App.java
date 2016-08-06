package game;

import java.util.Random;

public class App {

	public static int size = 10;
	public static int num_spaces = size * size;
	public static boolean dead_end = false;
	public static boolean open_path = false;
	
	public static void main(String[] args) {

		int blockNumber = 0;

		Block[][] Maze = initialiseMaze(blockNumber);
		generateMaze(Maze);
	}

	// increment through each block in the maze
	// until all are no longer vacant
	private static void generateMaze(Block[][] Maze) {
		do {			
			// increment through spaces in maze
			for (int block_row = 0; block_row < size; block_row++) {
				for (int block_col = 0; block_col < size; block_col++) {
				
					// create new references for when block is not a dead end
					int new_row = block_row;
					int new_col = block_col;

					// reset flag
					if (dead_end) {
						dead_end = false;
						open_path = true;						
					}
					
					// Assign maximum length of a path
					int max_length = size;
					
					// check if current block is vacant
					while (Maze[new_row][new_col].isVacant() && max_length > 0) {
						
						// if a dead end occurred, open path to new block
						if (open_path) {							
							linkBlock(Maze, new_row, new_col);
						}
						
						// setVacant to false and decrement number of spaces in maze
						Maze[new_row][new_col].setVacant(false);
						num_spaces--;
						
						// check sides of adjacent blocks and assign same value to current block's side
						// and decrement "number of sides" variable in current block
						alignSides(Maze, new_row, new_col);	
						
						// if number of sides > 0 randomise that number
						if (Maze[new_row][new_col].getNum_sides() > 0) {
							Allocation allocated_side = null;
							Random rand = new Random();
							int pathway = rand.nextInt(Maze[new_row][new_col].getNum_sides());
							
							// check each side of current block and assign to either open or closed, until a side is assigned to open.
							allocated_side = findOpenSide(Maze, new_row, new_col, allocated_side, pathway);

							// close all remaining unassigned sides once a side is assigned to open
							closeSides(Maze, new_row, new_col);
							
							// Reference new block
							switch (allocated_side) {
							case NORTH:
								new_row = new_row - 1;
								break;
							case SOUTH:
								new_row = new_row + 1;
								break;
							case EAST:
								new_col = new_col + 1;
								break;
							case WEST:
								new_col = new_col - 1;
								break;
							default:
								// dead end so no allocation
								break;
							};
						}
						// else flag dead_end and break
						else {
							dead_end = true;
							break;
						}
						
						max_length--;
						
						// If maximum length is exceeded we will need to open a new path
						if (max_length == 0) {
							open_path = true;
						}
					}
					if (dead_end) {
						break;
					}
				}
				if (dead_end) {
					break;
				}
			}
		} while (num_spaces > 0);
	}

	private static void closeSides(Block[][] Maze, int new_row, int new_col) {
		if (Maze[new_row][new_col].getN_side() == Side.UNASSIGNED) {
			Maze[new_row][new_col].setN_side(Side.CLOSED);
		}
		if (Maze[new_row][new_col].getE_side() == Side.UNASSIGNED) {
			Maze[new_row][new_col].setE_side(Side.CLOSED);
		}
		if (Maze[new_row][new_col].getS_side() == Side.UNASSIGNED) {
			Maze[new_row][new_col].setS_side(Side.CLOSED);
		}
		if (Maze[new_row][new_col].getW_side() == Side.UNASSIGNED) {
			Maze[new_row][new_col].setW_side(Side.CLOSED);
		}
	}

	private static void alignSides(Block[][] Maze, int new_row, int new_col) {
		// check North block
		if (new_row == 0 || Maze[new_row - 1][new_col].getS_side() == Side.CLOSED) {
			Maze[new_row][new_col].setN_side(Side.CLOSED);
			Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);
		}
		else if (Maze[new_row - 1][new_col].getS_side() == Side.OPEN) {
			Maze[new_row][new_col].setN_side(Side.OPEN);
			Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);						
		}
		
		// check South block
		if (new_row == size - 1 || Maze[new_row + 1][new_col].getN_side() == Side.CLOSED) {
			Maze[new_row][new_col].setS_side(Side.CLOSED);
			Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);
		}
		else if (Maze[new_row + 1][new_col].getN_side() == Side.OPEN) {
			Maze[new_row][new_col].setS_side(Side.OPEN);
			Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);
		}
		
		// check West block
		if (new_col == 0 || Maze[new_row][new_col - 1].getE_side() == Side.CLOSED) {
			Maze[new_row][new_col].setW_side(Side.CLOSED);
			Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);
		}
		else if (Maze[new_row][new_col - 1].getE_side() == Side.OPEN) {
			Maze[new_row][new_col].setW_side(Side.OPEN);
			Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);
		}
		
		// check East block
		if (new_col == size - 1 || Maze[new_row][new_col + 1].getW_side() == Side.CLOSED) {
			Maze[new_row][new_col].setE_side(Side.CLOSED);
			Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);
		}
		else if (Maze[new_row][new_col + 1].getW_side() == Side.OPEN) {
			Maze[new_row][new_col].setE_side(Side.OPEN);
			Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);
		}
	}

	private static Allocation findOpenSide(Block[][] Maze, int new_row, int new_col, Allocation allocated_side,
			int pathway) {
		do {
			if (Maze[new_row][new_col].getN_side() == Side.UNASSIGNED && pathway == 0) {
				Maze[new_row][new_col].setN_side(Side.OPEN);
				allocated_side = Allocation.NORTH;
				break;
			}
			else if (Maze[new_row][new_col].getN_side() == Side.UNASSIGNED){
				Maze[new_row][new_col].setN_side(Side.CLOSED);
				pathway--;
			}
			
			if (Maze[new_row][new_col].getE_side() == Side.UNASSIGNED && pathway == 0) {
				Maze[new_row][new_col].setE_side(Side.OPEN);
				allocated_side = Allocation.EAST;
				break;
			}
			else if (Maze[new_row][new_col].getE_side() == Side.UNASSIGNED){
				Maze[new_row][new_col].setE_side(Side.CLOSED);
				pathway--;
			}
			
			if (Maze[new_row][new_col].getS_side() == Side.UNASSIGNED && pathway == 0) {
				Maze[new_row][new_col].setS_side(Side.OPEN);
				allocated_side = Allocation.SOUTH;
				break;
			}
			else if (Maze[new_row][new_col].getS_side() == Side.UNASSIGNED){
				Maze[new_row][new_col].setS_side(Side.CLOSED);
				pathway--;
			}
			
			if (Maze[new_row][new_col].getW_side() == Side.UNASSIGNED && pathway == 0) {
				Maze[new_row][new_col].setW_side(Side.OPEN);
				allocated_side = Allocation.WEST;
				break;
			}
			else if (Maze[new_row][new_col].getW_side() == Side.UNASSIGNED){
				Maze[new_row][new_col].setW_side(Side.CLOSED);
				pathway--;
			}
		} while (pathway > 0);	// this condition is irrelevant, will break as soon as path is found 
		return allocated_side;
	}

	private static void linkBlock(Block[][] Maze, int new_row, int new_col) {
		int possible_paths = 4;
		
		// find number of allocated blocks
		// check North block
		if ((new_row == 0) || Maze[new_row - 1][new_col].isVacant()) {
			possible_paths--;
		}
		
		// check South block
		if ((new_row == size - 1) || Maze[new_row + 1][new_col].isVacant()) {
			possible_paths--;
		}
		
		// check West block
		if ((new_col == 0) || Maze[new_row][new_col - 1].isVacant()) {
			possible_paths--;
		}
		
		// check East block
		if (new_col == size - 1 || Maze[new_row][new_col + 1].isVacant()) {
			possible_paths--;
		}
		
		// Determine adjacent block to connect to
		Random ran = new Random();
		int path = ran.nextInt(possible_paths);
		
		// Choose random adjacent block and open adjoining sides
		path = chooseBlock(Maze, new_row, new_col, path);
		
		// reset flag
		open_path = false;
	}

	private static int chooseBlock(Block[][] Maze, int new_row, int new_col, int path) {
		do {
			// check North block
			if (new_row == 0 || Maze[new_row - 1][new_col].isVacant() || (Maze[new_row - 1][new_col].isVacant() == false && path > 0)) {
				path--;
			}
			else {
				Maze[new_row - 1][new_col].setS_side(Side.OPEN);
				Maze[new_row][new_col].setN_side(Side.OPEN);
				break;
			}

			// check South block
			if (new_row == size - 1 || Maze[new_row + 1][new_col].isVacant() || (Maze[new_row + 1][new_col].isVacant() == false && path > 0)) {
				path--;
			}
			else {
				Maze[new_row + 1][new_col].setN_side(Side.OPEN);
				Maze[new_row][new_col].setS_side(Side.OPEN);
				break;
			}

			// check East block
			if (new_col == size - 1 || Maze[new_row][new_col + 1].isVacant() || (Maze[new_row][new_col].isVacant() == false && path > 0)) {
				path--;
			}
			else {
				Maze[new_row][new_col + 1].setW_side(Side.OPEN);
				Maze[new_row][new_col].setE_side(Side.OPEN);
				break;
			}

			// check West block
			if (new_col == 0 || Maze[new_row][new_col - 1].isVacant() || (Maze[new_row][new_col - 1].isVacant() == false && path > 0)) {
				path--;
			}
			else {
				Maze[new_row][new_col - 1].setE_side(Side.OPEN);
				Maze[new_row][new_col].setW_side(Side.OPEN);
				break;
			}
		} while (path > 0);	// this condition is irrelevant, will break as soon as path is found
		return path;
	}

	// Initialise maze with all unassigned blocks
	private static Block[][] initialiseMaze(int blockNumber) {
		Block[][] Maze = new Block[size][size];
		for (int block_row = 0; block_row < size; block_row++) {
			for (int block_col = 0; block_col < size; block_col++) {
				blockNumber++;
				Maze[block_row][block_col] = new Block(blockNumber);				
			}
		}
		return Maze;
	}
}
