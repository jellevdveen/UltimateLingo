package com.YCP.lingoMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import com.YCP.lingo.Team;
import com.YCP.lingo.Exception.HighscoreException;
import com.YCP.lingo.Exception.OnbekendeException;
import com.YCP.lingo.Exception.WoordenLijstException;


public class InputOutput {
	private static Scanner fileScanner;
	private static Scanner inputScanner = new Scanner(System.in);
	
	public static ArrayList<String> importWoordenLijst(String path) {
		// lengte van woordenlijst aanpassen aan aantal woorden in Lingo.txt
		ArrayList<String> returnList = new ArrayList<>(2500);
		try {
			fileScanner = new Scanner(new File(path));
			while (fileScanner.hasNext()) {
				returnList.add(fileScanner.next());
			}
			fileScanner.close();
		} catch (FileNotFoundException FNFE) {
			throw new WoordenLijstException("Kan woordenlijst niet vinden");
		}
		return returnList;
	}

	public static String userInput() {
		return inputScanner.next();
	}

	public static String[] highscore() {
		String returnString = "";
		try {
			fileScanner = new Scanner(new File("LingoScores.txt"));
			returnString = fileScanner.nextLine();
		} catch (FileNotFoundException FNFE) {
			throw new HighscoreException("Kan lijst met Highscores niet vinden");
		}
		return returnString.split("-");
	}

	public static void schrijfHighscore(String highscore) {
		try {
			PrintWriter writer = new PrintWriter("LingoScores.txt", "UTF-8");
			writer.println(Team.getActief().getNaam().replace('-', '_') + "-" + Team.getActief().getScore());
			writer.close();
		} catch (UnsupportedEncodingException UEE) {
			throw new OnbekendeException("Onbekende Exception");
		} catch (FileNotFoundException FNFE) {
			throw new HighscoreException("Kan lijst met Highscores niet vinden");
		}
		
	}
}
