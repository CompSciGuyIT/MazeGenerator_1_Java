package game;

import java.util.Random;

public class App {

	public static int size = 5;
	public static int num_spaces = size * size;
	public static boolean dead_end;
	
	public static void main(String[] args) {

		Block[][] Maze = new Block[size][size];
		int count = 0;
		
		for (int block_row = 0; block_row < size; block_row++) {
			for (int block_col = 0; block_col < size; block_col++) {
				count++;
				Maze[block_row][block_col] = new Block(count);				
			}
		}

		// increment through each block in the maze
		// until all are no longer vacant
		do {
			// reset flag to engage a new tunnel
			dead_end = false;
			
			// begin incrementing through spaces in maze
			for (int block_row = 0; block_row < size; block_row++) {
				for (int block_col = 0; block_col < size; block_col++) {
				
					// check if current block is vacant
					if (Maze[block_row][block_col].isVacant()) {
						
						// setVacant to false and decrement number of spaces in maze
						Maze[block_row][block_col].setVacant(false);
						num_spaces--;
						
						/*
						 * check if adjacent blocks are occupied or a boundary
						 * if that side's value is set to open or closed
						 * set same side in current block the same value
						 * and decrement "number of sides" variable in current block
						*/
						
						// check North block
						if ((block_row == 0) || Maze[block_row - 1][block_col].getS_side() == Side.CLOSED) {
							Maze[block_row][block_col].setN_side(Side.CLOSED);
							Maze[block_row][block_col].setNum_sides(Maze[block_row][block_col].getNum_sides() - 1);
						}
						else if (Maze[block_row - 1][block_col].getS_side() == Side.OPEN) {
							Maze[block_row][block_col].setN_side(Side.OPEN);
							Maze[block_row][block_col].setNum_sides(Maze[block_row][block_col].getNum_sides() - 1);						
						}
						
						// check South block
						if ((block_row == size - 1) || Maze[block_row + 1][block_col].getN_side() == Side.CLOSED) {
							Maze[block_row][block_col].setS_side(Side.CLOSED);
							Maze[block_row][block_col].setNum_sides(Maze[block_row][block_col].getNum_sides() - 1);
						}
						else if (Maze[block_row + 1][block_col].getN_side() == Side.OPEN) {
							Maze[block_row][block_col].setS_side(Side.OPEN);
							Maze[block_row][block_col].setNum_sides(Maze[block_row][block_col].getNum_sides() - 1);
						}
						
						// check West block
						if ((block_col == 0) || Maze[block_row][block_col - 1].getE_side() == Side.CLOSED) {
							Maze[block_row][block_col].setW_side(Side.CLOSED);
							Maze[block_row][block_col].setNum_sides(Maze[block_row][block_col].getNum_sides() - 1);
						}
						else if (Maze[block_row][block_col - 1].getE_side() == Side.OPEN) {
							Maze[block_row][block_col].setW_side(Side.OPEN);
							Maze[block_row][block_col].setNum_sides(Maze[block_row][block_col].getNum_sides() - 1);
						}
						
						// check East block
						if (block_col == size - 1 || Maze[block_row][block_col + 1].getW_side() == Side.CLOSED) {
							Maze[block_row][block_col].setE_side(Side.CLOSED);
							Maze[block_row][block_col].setNum_sides(Maze[block_row][block_col].getNum_sides() - 1);
						}
						else if (Maze[block_row][block_col + 1].getW_side() == Side.OPEN) {
							Maze[block_row][block_col].setE_side(Side.OPEN);
							Maze[block_row][block_col].setNum_sides(Maze[block_row][block_col].getNum_sides() - 1);
						}	
						
						// if number of sides > 0 randomise that number
						if (Maze[block_row][block_col].getNum_sides() > 0) {
							Random rand = new Random();
							int pathway = rand.nextInt(Maze[block_row][block_col].getNum_sides());
							
							// check each side of current block
							// if side is unassigned and random number is equal to zero, assign side to open and break
							// otherwise assign side to closed and decrement random number
							for (int sides = 4; sides > 0; sides--) {
								if (Maze[block_row][block_col].getN_side() == Side.UNASSIGNED && pathway == 0) {
									Maze[block_row][block_col].setN_side(Side.OPEN);
									break;
								}
								else {
									Maze[block_row][block_col].setN_side(Side.CLOSED);
									pathway--;
								}
								
								if (Maze[block_row][block_col].getE_side() == Side.UNASSIGNED && pathway == 0) {
									Maze[block_row][block_col].setE_side(Side.OPEN);
									break;
								}
								else {
									Maze[block_row][block_col].setN_side(Side.CLOSED);
									pathway--;
								}
								
								if (Maze[block_row][block_col].getS_side() == Side.UNASSIGNED && pathway == 0) {
									Maze[block_row][block_col].setS_side(Side.OPEN);
									break;
								}
								else {
									Maze[block_row][block_col].setN_side(Side.CLOSED);
									pathway--;
								}
								
								if (Maze[block_row][block_col].getW_side() == Side.UNASSIGNED && pathway == 0) {
									Maze[block_row][block_col].setW_side(Side.OPEN);
									break;
								}
								else {
									Maze[block_row][block_col].setN_side(Side.CLOSED);
									pathway--;
								}
							}

							// close all unassigned sides
							for (int sides = 4; sides > 0; sides--) {
								if (Maze[block_row][block_col].getN_side() == Side.UNASSIGNED) {
									Maze[block_row][block_col].setN_side(Side.CLOSED);
								}
								if (Maze[block_row][block_col].getE_side() == Side.UNASSIGNED) {
									Maze[block_row][block_col].setE_side(Side.CLOSED);
								}
								if (Maze[block_row][block_col].getS_side() == Side.UNASSIGNED) {
									Maze[block_row][block_col].setS_side(Side.CLOSED);
								}
								if (Maze[block_row][block_col].getW_side() == Side.UNASSIGNED) {
									Maze[block_row][block_col].setW_side(Side.CLOSED);
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
				if (dead_end) {
					break;
				}
			}
		} while (num_spaces > 0);
		
		/*
		 * Error check
		for (int block = 0; block < size; block++) {
			
			System.out.println(Maze[1][block].getN_side());
		}
		*/
	}
}
