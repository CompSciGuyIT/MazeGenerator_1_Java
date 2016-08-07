package game;

public class Block {
	
	private int number;
	private int num_sides;
	private boolean vacant;
	private Side n_side;
	private Side e_side;
	private Side s_side;
	private Side w_side;
	private String block;
	
	public Block (int number) {
		this.number = number;
		this.num_sides = 4;
		this.vacant = true;
		this.n_side = Side.UNASSIGNED;
		this.e_side = Side.UNASSIGNED;
		this.s_side = Side.UNASSIGNED;
		this.w_side = Side.UNASSIGNED;
		this.block = "";
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNum_sides() {
		return num_sides;
	}

	public void setNum_sides(int num_sides) {
		this.num_sides = num_sides;
	}

	public boolean isVacant() {
		return vacant;
	}

	public void setVacant(boolean vacant) {
		this.vacant = vacant;
	}

	public Side getN_side() {
		return n_side;
	}

	public void setN_side(Side n_side) {
		this.n_side = n_side;
	}

	public Side getE_side() {
		return e_side;
	}

	public void setE_side(Side e_side) {
		this.e_side = e_side;
	}

	public Side getS_side() {
		return s_side;
	}

	public void setS_side(Side s_side) {
		this.s_side = s_side;
	}

	public Side getW_side() {
		return w_side;
	}

	public void setW_side(Side w_side) {
		this.w_side = w_side;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}
}
