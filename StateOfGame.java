package demo;

public class StateOfGame implements java.io.Serializable {
	
	private static final long serialVersionUID = 5500318284308149898L;
	
	private Square[] boardSpace;
	private Player[] players;
	
	public Square[] getBoardSpace() {
		return boardSpace;
	}
	public void setBoardSpace(Square[] boardSpace) {
		this.boardSpace = boardSpace;
	}
	public Player[] getPlayers() {
		return players;
	}
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
}
