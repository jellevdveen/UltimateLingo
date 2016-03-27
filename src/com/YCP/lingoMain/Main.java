package com.YCP.lingoMain;

import java.util.ArrayList;
import java.util.Collections;

import com.YCP.lingo.BallenBak;
import com.YCP.lingo.Team;
import com.YCP.lingo.Exception.StaatNietOpDeKaart;
import com.YCP.lingoWoorden.RaadWoorden;

public class Main {
	private static final int AANTAL_RONDEN = 10;
	public static final int STANDARD_WAIT = 1000;
	
	private static ArrayList<String> woordenLijst;
	private static RaadWoorden woord;
	private static boolean finale;
	
	public static void main(String[] args) {
		
		Main.initialiseer();
		Main.gewoonSpel();
		Main.finale();
		
	}
	
	public static int pakBal() {
		try {
			int x = Team.getActief().trekBal(() -> { return vraagteken(); });
			switch (x) {
			case BallenBak.GROEN 	:	ConsolePrinter.print("Groene bal!", 0);
										if (Team.getActief().verhoogGroen()){
											ConsolePrinter.print("3e groene bal!, 100 bonuspunten!", 0);
											Team.getActief().verhoogScore(100);
											Team.getActief().legBalTerug(BallenBak.GROEN);
										}
										return BallenBak.GROEN;
			case BallenBak.GOUD		:	Team.getActief().legBalTerug(BallenBak.GOUD);
										ConsolePrinter.print("Gouden bal!\nJullie hoeven deze ronde geen ballen meer te pakken\n", Main.STANDARD_WAIT/2);
										return BallenBak.GOUD;
			case BallenBak.ROOD		:	ConsolePrinter.print("Rode bal!\nDe beurt gaat naar het andere team!\n", Main.STANDARD_WAIT/2);
										Team.wissel();
										return BallenBak.ROOD;
			default					: 	if (x != BallenBak.VRAAGTEKEN) {
											ConsolePrinter.print("\nHet is bal " + x + ".\nDeze wordt weggestreept!", 0);
											ConsolePrinter.printRight(Team.getActief().getKaart(), Main.STANDARD_WAIT);
										}
										if (Team.getActief().checkLingo() && (!Main.finale)) {
											ConsolePrinter.print("\nEn dat is Lingoooo!\nHonderd punten erbij voor " + Team.getActief().getNaam(), 0);
											Team.getActief().verhoogScore(100);
											ConsolePrinter.print("We maken voor " + Team.getActief().getNaam() + " een nieuwe kaart aan!\n", 0);
											Team.getActief().maakKaart();
											ConsolePrinter.print(Team.getActief().getKaart() + "\n", Main.STANDARD_WAIT);
											ConsolePrinter.print("En we strepen 8 getallen weg!\n", 0);
											Team.getActief().beginKaart(false);
											ConsolePrinter.print(Team.getActief().getKaart() + "\n", Main.STANDARD_WAIT);
											Team.getActief().maakBallenBak(false);
											Team.wissel();
											ConsolePrinter.print("De beurt gaat naar " + Team.getActief().getNaam(), Main.STANDARD_WAIT);
											return 1;
										} else if (Team.getActief().checkLingo()) {
											return 1;
										} else {
											return 0;
										}
			}
		} catch (StaatNietOpDeKaart SNODK) {
			ConsolePrinter.print(SNODK.getMessage(), 0);
			return 0;
		}
	}
	
	public static boolean pakBallen(int i) {
		for (int ballen = 0; ballen < i; ballen++) {
			ConsolePrinter.print(Team.getActief().getNaam() + ", jullie moeten nog " + (i - ballen) + 
					(((i - ballen) == 1) ? " bal " : " ballen ") +  "pakken!", Main.STANDARD_WAIT/2);
			if (!Main.finale) {
				ConsolePrinter.print("*PUBLIEK: \"GROEN! GROEN! GROEN!\"*", 0);
			}
			
			ConsolePrinter.print("Voer iets in om een bal te pakken!", 0);
			InputOutput.userInput();
			
			int testBal = Main.pakBal();
			if (testBal == BallenBak.GROEN) {
				ballen--;
			} else if (testBal == BallenBak.ROOD) {
				break;
			} else if (testBal == BallenBak.GOUD) {
				break;
			} else if (testBal == 1) {
				return true;
			}
		}
		return false;
	}

	//functie om in een lambda te gooien die vraagteken afvangt
	public static int vraagteken() {
		ConsolePrinter.print("Jullie hebben het vraagteken,\nwelk getal wil je wegstrepen?", 0);
		try {
			return Integer.valueOf(InputOutput.userInput());
		} catch (NumberFormatException NFE) {
			ConsolePrinter.print("Jullie zijn een stelletje prutsers,\ner is niks weggestreept.", 0);
			return BallenBak.VRAAGTEKEN;
		}
	}

	public static void finale() {
		ConsolePrinter.print("\nWe gaan de finale spelen met " + Team.getActief().getNaam(), Main.STANDARD_WAIT);
		ConsolePrinter.print("\nWe maken een kaart!\n", 0);
		Team.getActief().maakKaart();
		ConsolePrinter.print(Team.getActief().getKaart() + "\n", Main.STANDARD_WAIT);
		Team.getActief().maakBallenBak(true);
		ConsolePrinter.print("En we strepen ditmaal 16 getallen weg!\n", 0);
		Team.getActief().beginKaart(true);
		ConsolePrinter.print(Team.getActief().getKaart() + "\n", Main.STANDARD_WAIT);
		
		Main.finale = true;
		String[] s = InputOutput.highscore();
		
		
		for (int ronde = 0; ronde < 5; ronde++) {
			String goedeWoord = woordenLijst.remove(0);
			woord = new RaadWoorden(goedeWoord);
			int x = woord.speelFinaleRonde();
			if (x == 6) {
				ConsolePrinter.print("Het goede woord was " + goedeWoord.toUpperCase() + "\n", 0);
			}
			ConsolePrinter.print(Team.getActief().getKaart(), 0);
			if (Main.pakBallen(x)) {
				ConsolePrinter.print("Helaas, Lingo!\nHet spel is afgelopen.\nJullie mogen de volgende keer terugkomen!", 0);
				ConsolePrinter.print("De Highscore is " + s[1] + " punten! Gehaald door " + s[0] + "!", 0);
				break;
			} else {
				Team.getActief().verhoogScore(Team.getActief().getScore());
				if (ronde < 4) {
					ConsolePrinter.print("Jullie score is nu " + Team.getActief().getScore() + " punten!", 0);
					ConsolePrinter.print("Voer 'd' in om door te gaan voor " + (2*Team.getActief().getScore()) + " punten, of iets anders om te stoppen!", 0);
					if (InputOutput.userInput().equalsIgnoreCase("d")) {
						continue;
					}
				}
			}
			ConsolePrinter.print("Jullie hebben gewonnen, met " + Team.getActief().getScore() + " punten!", 0);
			if (Team.getActief().getScore() > Integer.valueOf(s[1])) {
				ConsolePrinter.print("Nieuwe Highscore!", Main.STANDARD_WAIT);
				InputOutput.schrijfHighscore();
				break;
			} else {
				ConsolePrinter.print("De Highscore is " + s[1] + " punten! Gehaald door " + s[0] + "!", 0);
			}
			
		}
	}
	
	public static void gewoonSpel() {
		for (int ronde = 0; ronde < AANTAL_RONDEN; ronde++) {
			String goedeWoord = woordenLijst.remove(0);
			woord = new RaadWoorden(goedeWoord);
			if (woord.speelRonde()) {
				ConsolePrinter.print("Jullie krijgen er 25 punten bij\nen mogen 2 ballen pakken.\n", Main.STANDARD_WAIT);
				ConsolePrinter.print(Team.getActief().getKaart(), 0);
				Team.getActief().verhoogScore(25);
				Main.pakBallen(2);
			} else {
				ConsolePrinter.print("Het goede woord was " + goedeWoord.toUpperCase(), Main.STANDARD_WAIT);
			}
			
			ConsolePrinter.print("De score is nu:\n" + Team.getScores(), Main.STANDARD_WAIT);
			
			if ((ronde == (AANTAL_RONDEN - 1)) && (Team.setHoogsteTeam())) {
				ConsolePrinter.print("Omdat het nog niet beslist is een extra ronde!", Main.STANDARD_WAIT);
				ronde--;
			}
		}
	}

	public static void initialiseer() {
		Main.woordenLijst = InputOutput.importWoordenLijst("Lingo.txt");
		Collections.shuffle(Main.woordenLijst);
		
		ConsolePrinter.print("Welkom bij Lingo!\nMet zoals gewoonlijk mister Lingo... François Boulangé!\n", Main.STANDARD_WAIT);
		
		ConsolePrinter.print("Team 1, voer jullie naam in!", Main.STANDARD_WAIT);
		String team1naam = InputOutput.userInput();
		ConsolePrinter.print("Team 2, voer jullie naam in!", Main.STANDARD_WAIT);
		String team2naam = InputOutput.userInput();
				
		Team.startTeams(team1naam, team2naam);
		Main.finale = false;
		
		for (Team t : Team.getTeams()) {
			ConsolePrinter.print("We maken voor " + t.getNaam() + " een kaart aan!\n", 0);
			t.maakKaart();
			ConsolePrinter.print(t.getKaart() + "\n", Main.STANDARD_WAIT);
			ConsolePrinter.print("En we strepen 8 getallen weg!\n", 0);
			t.beginKaart(false);
			ConsolePrinter.print(t.getKaart() + "\n", Main.STANDARD_WAIT);
			t.maakBallenBak(false);
		}
	}
}
