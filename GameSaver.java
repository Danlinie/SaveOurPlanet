package demo;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameSaver {
	
	String fileName;
	StateOfGame stateOfGame;
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setStateOfGame(StateOfGame stateOfGame) {
		this.stateOfGame = stateOfGame;
	}
	
	public void saveGameData() throws IOException {
		FileOutputStream f_out = new FileOutputStream(fileName);
		ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
		obj_out.writeObject(stateOfGame);
		obj_out.close();
		f_out.close();
	}
	
	public StateOfGame getGameData(String fileName) throws IOException, ClassNotFoundException {
		StateOfGame stateOfGame = null;
		FileInputStream f_in = new FileInputStream(fileName);
        ObjectInputStream obj_in = new ObjectInputStream(f_in);
        try{
            Object obj = obj_in.readObject();
            stateOfGame = (StateOfGame)obj;
        }
        catch(EOFException e){
        }
        obj_in.close();
        f_in.close();
        
        return stateOfGame;
	}
}
