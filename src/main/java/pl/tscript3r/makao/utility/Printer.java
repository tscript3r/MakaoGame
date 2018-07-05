package pl.tscript3r.makao.utility;

import java.util.List;
import java.util.Scanner;

public class Printer {
	
	private static Scanner in = new Scanner(System.in);
	
	public static void printOption(String message) {
		System.out.println(message);
	}
	
	public static void printMessage(String message) {
		System.out.println(message);
	}
	
	public static int getMenuChoice(String titleMessage, List<String> choices, String pickMessage) {
		int result = -1;
		
		System.out.println(titleMessage);
		for(int i = 0; i < choices.size(); i++)
			System.out.println(" " + (i + 1) + ") " + choices.get(i));
		while(true) {
			System.out.print(pickMessage);
			
			String input = in.nextLine();
			try {
				result = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Please pick one of the given options");
				continue;
			}
			
			System.out.println("");
			if(result >= 1 && result <= choices.size())
				return result - 1;
		}
		
	}
	
	public static String getValue(String message, String valueErrorMessage) {
		String result = "";
		while(true) {
			System.out.print(message);
			result = in.nextLine();
			if(!result.isEmpty())
				break;
			else
				System.out.println(valueErrorMessage);
		}
		return result;
	}
	
}
