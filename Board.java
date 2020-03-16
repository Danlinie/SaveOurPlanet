package demo;

import java.util.ArrayList;

public class Board {
	
	public static Square[] squares = {
			new Square(1, null, "Go", -1, 0, 0, 0),
			new Square (2, "Recycling", "Food Waste", 0, 100, 50, 100),
			new Square (3, "Recycling", "Packaging", 0, 150, 50, 100),
			new Square (4, "Public Transport", "Bus", 0, 150, 75, 150),
			new Square (5, "Public Transport", "Train", 0, 175, 75, 150),
			new Square (6, "Public Transport", "New Energy Vehicle", 0, 200, 75, 150),
			new Square (7, null, "Empty", -1, 0, 0, 0),
			new Square (8, "Renewable Energy", "Hydroelectricity", 0, 225, 125, 250),
			new Square (9, "Renewable Energy", "Solar", 0, 250, 125, 250),  		
			new Square (10, "Renewable Energy", "Wind", 0, 275, 125, 250), 
			new Square (11, "Agriculture", "Sustainable Farming", 0, 300, 150, 300),
			new Square (12, "Agriculture", "Planting Trees", 0, 350, 150, 300)
	};
	
	//IMPLEMENT: display areas owned by a particular player
	
	public static void displayPlayerAssets(Player player) {
		for(Square square : squares) {
			if(square.getOwnership() == player.getId())
				System.out.println(square);
				
		}
		System.out.println("Current available funds: " + player.getTotalCash());
		System.out.println();
	}
	
	//IMPLEMENT: check if an area can be developed
	//Logic: pass in the area that is wanted to be developed, check if the player owns the whole field, if yes, can develop, otherwise cannot
	
	public static boolean isNeverDeveloped(Square area, Player player) {
		
		boolean developed = false;
		ArrayList<Square> areas = new ArrayList<Square>();
		String areaFieldName = area.getFieldName();
		int id = player.getId();
		
		for(int i = 0; i < squares.length; i++) {
			if(squares[i].getOwnership() == id && squares[i].getFieldName().equals(areaFieldName))
				areas.add(squares[i]);
		}
		
		//The area has not been developed at all
		if(area.getNumberOfDevelopments() == 0) {
			if(((areaFieldName.equals("Agriculture") || areaFieldName.equals("Recycling")) && areas.size() == 2)
					|| ((areaFieldName.equals("Renewable Energy") || areaFieldName.equals("Public Transport")) && areas.size() == 3)) {
					developed = true;
			} else {
				developed = false;
				System.out.println("Sorry, you cannot develop the area because you have not owned the whole field");
			}
			
		} else developed = true;
		
		return developed;
	}

}
