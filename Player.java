package demo;

public class Player implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private int id;
	private int position = 1;
	private int totalCash = 800;
	private String currentArea = Board.squares[position].getAreaName();
	
	public Player(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTotalCash() {
		return totalCash;
	}
	
	public void setTotalCash(int totalCash) {
		this.totalCash = totalCash;
	}

	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getCurrentArea() {
		return currentArea;
	}

	public void setCurrentArea(int position) {
		this.currentArea = Board.squares[position-1].getAreaName();
	}
	 
	public void pay(int withdrawals) {
	  setTotalCash(getTotalCash() - withdrawals);
	}
	
	public void deposit(int deposits) {
		setTotalCash(getTotalCash() + deposits);
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", id=" + id + ", position=" + position + ", total cash=£" + totalCash + "]";
	}

}
