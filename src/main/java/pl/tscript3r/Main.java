package pl.tscript3r;

import java.util.ArrayList;
import java.util.List;

import pl.tscript3r.makao.Makao;
import pl.tscript3r.makao.utility.Printer;

public class Main {
	
	private static String getName() {
		return Printer.getValue("Enter players name: ", "Please enter your name!");
	}
	
	private static void setPlayerType(List<String> options) {
		options.clear();
		options.add("Human");
		options.add("AI");	
	}
	
	private static void setFinalMenu(List<String> options) {
		options.clear();
		options.add("Start game");
		options.add("Add player");
		options.add("Exit");
	}

	public static void main(String[] args) {
		
		Makao makao = new Makao();
		List<String> options = new ArrayList<String>();
		int result = 0;

		Printer.printMessage("   M A K A O \r\n\r\n");
		makao.addHumanPlayer(Printer.getValue("Enter your name: ", "Please enter your name!"));
		
		while(true) {
			setPlayerType(options);
			result = Printer.getMenuChoice("\r\nSelect additional player", options, "Select: ");
			if(result == 0)
				makao.addHumanPlayer(getName());
			else
				makao.addAIPlayer(getName());
			
			setFinalMenu(options);
			result = Printer.getMenuChoice("\r\nWhat you wanna do?", options, "Select: ");
			if(result != 1)
				break;
		}
		
		if(result == 0)
			makao.start(5);
	}

}
