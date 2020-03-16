package demo;

import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class Monopoly {

	static Scanner input = new Scanner(System.in);
	private static StateOfGame lastStateOfGame, restoredStateOfGame;
	private static GameSaver gameSaver;
	
	public static void main(String[] args) {
		int face = 0;
		int position = 1;
		String roll;
		
		System.out.println("Welcome to Save Our Planet!");
		
		Player[] players = gameSetup();
		Square[] boardSpace = Board.squares;
		Dice die = new Dice();
		System.out.println("All players proceed to the Go square ...");
		

		//IMPLEMENT: the next turn logic
		System.out.print(players[0].getName() + ", current total cash: " + players[0].getTotalCash() + ", would you like to roll? y/n: ");
		roll = input.nextLine();
		for(int i = 0; i < players.length;) {
			if(roll.equalsIgnoreCase("y")) {
				face = throwDie(players[i], die);
				position = moveToDesignatedSquare(players[i], face);
				if(evaluateSquare(position, boardSpace, players[i], players)) {
					Board.displayPlayerAssets(players[i]);
				} else break;
			}
			else {
				break;
			}
			
			if(i == players.length - 1) {
				i = 0;
			} else i++;
			
			System.out.print(players[i].getName() + ", current total cash: " + players[i].getTotalCash() + ", would you like to roll? y/n: ");
			roll = input.nextLine();
			if(!roll.equalsIgnoreCase("y")) {
				break;
			}
		}
		
		//IMPLEMENT: end the game and display the winner
		isWinner(players);
		
		input.close();
		
		//serialization to save the state of the game
		lastStateOfGame = new StateOfGame();
		restoredStateOfGame = null;
		gameSaver = new GameSaver();
		
		try{
            restoredStateOfGame = gameSaver.getGameData("GameData");
            System.out.println("\nRestoring.");
        }
        catch(IOException e){
            System.out.println("\nError (1) reading file " + e);
        }
        catch(ClassNotFoundException e){
            System.out.println("\nError (2) reading file " + e);
        }

        if (restoredStateOfGame != null){
            System.out.println("\n\nRestored Monopoly board: " + restoredStateOfGame.getBoardSpace());
            System.out.println("Restored Players Information:\n" + restoredStateOfGame.getPlayers() + "\n\n");
        }

        lastStateOfGame.setBoardSpace(boardSpace);
        lastStateOfGame.setPlayers(players);

        gameSaver.setStateOfGame(lastStateOfGame);
        gameSaver.setFileName("GameData");

        try{
            gameSaver.saveGameData();
            System.out.println("\nData saved.");
        }
        catch(IOException e){
            System.out.println("\nError (3) saving file " + e);
        }

        try{
            restoredStateOfGame = gameSaver.getGameData("GameData");
            System.out.println("\nRestoring.");
        }
        catch(IOException e){
            System.out.println("\nError (1) reading file " + e);
        }
        catch(ClassNotFoundException e){
            System.out.println("\nError (2) reading file " + e);
        }

        if (restoredStateOfGame != null){
            System.out.println("\n\nRestored Monopoly board:");
            for(Square square : restoredStateOfGame.getBoardSpace()) System.out.println(square);
            System.out.println("\n\nRestored Players:");
            for(Player player : restoredStateOfGame.getPlayers()) System.out.println(player);
        }

	}
	
	public static Player[] gameSetup() {
		System.out.println("Please Enter the number of players ...");
		int	numberOfPlayers = Integer.parseInt(input.nextLine());
		
		while(numberOfPlayers > 4 || numberOfPlayers < 2) {
			System.out.println("Invalid number of players entered. The number of players should be between 2 and 4 inclusive.");
			System.out.println("Please Enter the number of players again ...");
			numberOfPlayers = Integer.parseInt(input.nextLine());
		}
		
		Player[] players = new Player[numberOfPlayers];
		
		ArrayList<String> names = new ArrayList<String>();
		
		for(int i = 0; i < players.length; i++) {
			System.out.print("Please enter the name of player " + (i+1) + ": ");
			String name = input.nextLine();
			
			//check for duplicate names
			name = isDuplicateName(names, name);
			
			players[i] = new Player(name, i + 5000);
		}
		
		for(Player player : players) {
			System.out.println(player.toString());
		}
				
		return players;
	}
	
	//check for duplicate names entered
	public static String isDuplicateName(ArrayList<String> names, String name) {
		
		boolean flag = false;
		
		if(!names.contains(name)) {
			names.add(name);
		} else {
			do {
				System.out.println("The username is already in use. Please re-enter the player name ...");
				name = input.nextLine();
				if(!names.contains(name)) {
					flag = false;
				} else flag = true;
			} while(flag);
		}
		
		return name;
	}
	
	public static int throwDie(Player player, Dice die){
				int face = die.throwDie();
				System.out.println(player.getName() + ", you've rolled a " + face);
				return face;
	}		
	
	public static int moveToDesignatedSquare(Player player, int face) {
		int temp = (face + player.getPosition());
		int boardLength = Board.squares.length;
		if(temp % boardLength == 0) {	
			player.setPosition(boardLength);
		} else {
			player.setPosition(temp % boardLength);
		}
		
		if(temp > ++boardLength) {
			System.out.println("You passed Go, collect £150!");
			player.deposit(150);
		}
		
		player.setCurrentArea(player.getPosition());
		System.out.println("You land on " + player.getCurrentArea());
		return player.getPosition();
	}
	
	public static boolean evaluateSquare(int position, Square[] boardSpace, Player player, Player[] players) {
		
		boolean isEvaluated = true;
		position = position -1;
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(Square sq : boardSpace) {
			if(sq.getOwnership() == player.getId()) list.add(sq.getIndex());
		}
		
		Square square = boardSpace[position];
		
		// check if the square is vacant, the square is vacant if the ownership is 0	
		if(square.getOwnership() == 0) {
			System.out.println(square.toString());
			
			// implement logic to check for sufficient points to pay
			System.out.println("The area is vacant.");
			
			if(player.getTotalCash() >= square.getAreaCost()) {
				
				System.out.println("Would you like to buy?");
				if(input.nextLine().equalsIgnoreCase("Y")) {
					
					square.setOwnership(player.getId());
					square.setTotalCost(square.getAreaCost());
					
					player.pay(square.getAreaCost());
					
					//IMPLEMENT: display the details of the buy
					System.out.println("You just bought the " + square.getAreaName() 
										+ " area of the field " + square.getFieldName() 
										+ " for £" + square.getAreaCost());
					
				} else {
					System.out.println("Player decided not to buy the area.");
				}
			} else System.out.println("Sorry, you do not have sufficient cash.");
	
		} 
		
		//check if the square is owned by the player
		else if(square.getOwnership() == player.getId()) {
			System.out.println("You landed on an area that you own.");
			
			//display the resources and development owned by the current player
			System.out.println("Displaying current resources and development ...");
			Board.displayPlayerAssets(player);
			
			//check if the player wants to develop an already owned area
			System.out.println("Would you like to develop an area? Enter Y or N");
			String answer = input.nextLine();
			if(answer.equalsIgnoreCase("Y")) {
				System.out.println("Which area would you like to develop? Enter the index of the area");
				int index = Integer.parseInt(input.nextLine());
				
				//check for if the area is actually owned by the player
				while(!list.contains(index)) {
					if(!(index >= 1 && index <= 12)) {
						System.out.println("Please enter an index between 1 and 12 ...");
						System.out.println("Which area would you like to develop? Enter the index of the area");
						index = Integer.parseInt(input.nextLine());
					} else {
						System.out.println("Sorry, you do not own the area, please enter the index of an area that you own ...");
						index = Integer.parseInt(input.nextLine());
					}
				}

				index = index - 1;
				//check if the board has been developed before
				boolean isDeveloped = Board.isNeverDeveloped(boardSpace[index], player);
				if(isDeveloped && boardSpace[index].getNumberOfDevelopments() < 3) {
					if(player.getTotalCash() >= boardSpace[index].getDevelopmentCost()) {
						int numberOfDevelopment = boardSpace[index].getNumberOfDevelopments() + 1;
						boardSpace[index].setNumberOfDevelopments(numberOfDevelopment);
						player.pay(boardSpace[index].getDevelopmentCost());
						boardSpace[index].setTotalCost(boardSpace[index].getTotalCost() + boardSpace[index].getDevelopmentCost());
					} else {
						System.out.println("Sorry, you do not have sufficient cash to develop.");
					}
				}
				
				//square has been developed three times or more and can have a major development
				else if(isDeveloped && boardSpace[index].getNumberOfDevelopments() >= 3) {
					if(player.getTotalCash() >= boardSpace[index].getMajorDevelopmentCost()) {
						System.out.println("The area is eligible for a major development. Would you like to carry out a major development? Enter Y or N");
						answer = input.nextLine();
						if(answer.equalsIgnoreCase("Y")) {
							boardSpace[index].setNumberOfDevelopments(boardSpace[index].getNumberOfDevelopments() + 1);
							player.pay(boardSpace[index].getMajorDevelopmentCost());
							boardSpace[index].setTotalCost(boardSpace[index].getTotalCost() + boardSpace[index].getMajorDevelopmentCost());
						} else {
							boardSpace[index].setNumberOfDevelopments(boardSpace[index].getNumberOfDevelopments() + 1);
							player.pay(boardSpace[index].getDevelopmentCost());
							boardSpace[index].setTotalCost(boardSpace[index].getTotalCost() + boardSpace[index].getDevelopmentCost());
						}
					}
				}
			}
		} else if(boardSpace[position].getAreaName().equals("Go")) {
			System.out.println(", collect £150!");
			player.deposit(150);
		} else if(boardSpace[position].getAreaName().equals("Empty")) {
			System.out.println("You land on Empty Square. No action is taken!");
		}
		
		//land on another player's area
		else {
			Player p = searchForPlayer(players, boardSpace, position);
			int rent = (int)(boardSpace[position].getTotalCost() * 0.2);
			System.out.print("You land on " + p.getName() + "'s area. ");
			System.out.println("Pay " + p.getName() + " £" + rent);
			if(player.getTotalCash() >= rent) {
				player.pay(rent);
				p.deposit(rent);
			} else {
				System.out.println("You do not have sufficient cash to pay ...");
				return false;
			}
			
		}
		return isEvaluated;
	}
	
	//search for the name of the player
	public static Player searchForPlayer(Player[] players, Square[] boardSpace, int position) {
		Player player = null;
		for(Player p : players) {
			if(p.getId() == boardSpace[position].getOwnership()) player = p;
		}
		
		return player;
	}
	
	public static void isWinner(Player[] players) {
		String winner = players[0].getName();
		for(int i = 1; i < players.length; i++) {
			if(players[i].getTotalCash() > players[i-1].getTotalCash()) {
				winner = players[i].getName();
			}
		}
		
		System.out.println("The winner is " + winner);
	}

}
