package game;

import java.util.Random;

public class App {

	public static int size = 3;
	public static int num_spaces = size * size;
	public static boolean dead_end;
	
	public static void main(String[] args) {

		int blockNumber = 0;

		// Generate maze with all unassigned blocks
		Block[][] Maze = new Block[size][size];
		for (int block_row = 0; block_row < size; block_row++) {
			for (int block_col = 0; block_col < size; block_col++) {
				blockNumber++;
				Maze[block_row][block_col] = new Block(blockNumber);				
			}
		}

		// increment through each block in the maze
		// until all are no longer vacant
		do {
			// reset flag and engage a new tunnel
			dead_end = false;
			
			// begin incrementing through spaces in maze
			for (int block_row = 0; block_row < size; block_row++) {
				for (int block_col = 0; block_col < size; block_col++) {
				
					// create new references for when block is not a dead end
					int new_row = block_row;
					int new_col = block_col;
					
					// check if current block is vacant
					while (Maze[new_row][new_col].isVacant()) {
						
						// setVacant to false and decrement number of spaces in maze
						Maze[new_row][new_col].setVacant(false);
						num_spaces--;
						
						// check sides of adjacent blocks and assign same value to current block's side
						// and decrement "number of sides" variable in current block
						
						// check North block
						if ((new_row == 0) || Maze[new_row - 1][new_col].getS_side() == Side.CLOSED) {
							Maze[new_row][new_col].setN_side(Side.CLOSED);
							Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);
						}
						else if (Maze[new_row - 1][new_col].getS_side() == Side.OPEN) {
							Maze[new_row][new_col].setN_side(Side.OPEN);
							Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);						
						}
						
						// check South block
						if ((new_row == size - 1) || Maze[new_row + 1][new_col].getN_side() == Side.CLOSED) {
							Maze[new_row][new_col].setS_side(Side.CLOSED);
							Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);
						}
						else if (Maze[new_row + 1][new_col].getN_side() == Side.OPEN) {
							Maze[new_row][new_col].setS_side(Side.OPEN);
							Maze[new_row][new_col].setNum_sides(Maze[new_row][new_col].getNum_sides() - 1);
						}
						
						// check West block
						if ((new_col == 0) || Maze[new_row][new_col - 1].getE_side() == Side.CLOSED) {
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
						
						// if number of sides > 0 randomise that number
						if (Maze[new_row][new_col].getNum_sides() > 0) {
							Random rand = new Random();
							int pathway = rand.nextInt(Maze[new_row][new_col].getNum_sides());
							
							// check each side of current block and assign to either open or closed.
							for (int sides = 4; sides > 0; sides--) {
								if (Maze[new_row][new_col].getN_side() == Side.UNASSIGNED && pathway == 0) {
									Maze[new_row][new_col].setN_side(Side.OPEN);
									break;
								}
								else if (Maze[new_row][new_col].getN_side() == Side.UNASSIGNED){
									Maze[new_row][new_col].setN_side(Side.CLOSED);
									pathway--;
								}
								
								if (Maze[new_row][new_col].getE_side() == Side.UNASSIGNED && pathway == 0) {
									Maze[new_row][new_col].setE_side(Side.OPEN);
									break;
								}
								else if (Maze[new_row][new_col].getE_side() == Side.UNASSIGNED){
									Maze[new_row][new_col].setN_side(Side.CLOSED);
									pathway--;
								}
								
								if (Maze[new_row][new_col].getS_side() == Side.UNASSIGNED && pathway == 0) {
									Maze[new_row][new_col].setS_side(Side.OPEN);
									break;
								}
								else if (Maze[new_row][new_col].getS_side() == Side.UNASSIGNED){
									Maze[new_row][new_col].setN_side(Side.CLOSED);
									pathway--;
								}
								
								if (Maze[new_row][new_col].getW_side() == Side.UNASSIGNED && pathway == 0) {
									Maze[new_row][new_col].setW_side(Side.OPEN);
									break;
								}
								else if (Maze[new_row][new_col].getW_side() == Side.UNASSIGNED){
									Maze[new_row][new_col].setN_side(Side.CLOSED);
									pathway--;
								}
							}

							// close all unassigned sides once a side is assigned to open
							for (int sides = 4; sides > 0; sides--) {
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
						}
						// else flag dead_end and break
						else {
							dead_end = true;
							break;
						}
						/*
						 * Code breaks at the incorrect moment
						 * it needs to go to the next space that has been opened
						 * it should only break when all sides are assigned and only one is open 
						 */
						// Move to next block through side that just been assigned to be open
					}
				}
				/*
				if (dead_end) {
					break;
				}
				*/
			}
		} while (num_spaces > 0);
		
		/*
		 * Error check
		 */
		System.out.println(Maze[0][0].getN_side() + " | " + Maze[0][0].getE_side() + " | " + Maze[0][0].getS_side() + " | " + Maze[0][0].getW_side());
		System.out.println(Maze[0][1].getN_side() + " | " + Maze[0][1].getE_side() + " | " + Maze[0][1].getS_side() + " | " + Maze[0][1].getW_side());
		System.out.println(Maze[0][2].getN_side() + " | " + Maze[0][2].getE_side() + " | " + Maze[0][2].getS_side() + " | " + Maze[0][2].getW_side());
		// System.out.println(Maze[0][3].getN_side() + " | " + Maze[0][3].getE_side() + " | " + Maze[0][3].getS_side() + " | " + Maze[0][3].getW_side());
		
	}
}
