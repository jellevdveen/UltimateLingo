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
		
		//Initialisation
		Main.woordenLijst = InputOutput.importWoordenLijst("Lingo.txt");
		Collections.shuffle(Main.woordenLijst);
		
		Team.startTeams("Team 1", "Team 2");
		finale = false;
		
		for (Team t : Team.getTeams()) {
			t.maakKaart();
			t.beginKaart(false);
			t.maakBallenBak();
		}
		
		
		//Actual game
		for (int ronde = 0; ronde < AANTAL_RONDEN; ronde++) {
			woord = new RaadWoorden(woordenLijst.remove(0));
			if (woord.speelRonde()) {
				Team.getActief().verhoogScore(25);
				Main.pakBallen(2);
			}
			if ((ronde == (AANTAL_RONDEN - 1)) && (Team.setHoogsteTeam())) {
				ronde--;
			}
		}
		
		Team.getActief().maakKaart();
		Team.getActief().maakBallenBak();
		Team.getActief().beginKaart(true);
		
		// finale
		for (int ronde = 0; ronde < 5; ronde++) {
			woord = new RaadWoorden(woordenLijst.remove(0));
			if (Main.pakBallen(woord.speelFinaleRonde())) {
				//je hebt lingo, spel is over
				break;
			} else {
				Team.getActief().verhoogScore(Team.getActief().getScore() * 2);
				//kies om door te gaan of te stoppen
			}
		}
	}
	
	public static int pakBal() {
		try {
		switch (Team.getActief().trekBal())	{
		case BallenBak.GROEN 	:	if (Team.getActief().verhoogGroen()){
										Team.getActief().verhoogScore(100);
										Team.getActief().legBalTerug(BallenBak.GROEN);
									}
									return BallenBak.GROEN;
		case BallenBak.GOUD		:	Team.getActief().legBalTerug(BallenBak.GOUD);
									return BallenBak.GOUD;
		case BallenBak.ROOD		:	Team.wissel();
									return BallenBak.ROOD;
		default					: 	if (Team.getActief().checkLingo()) {
										Team.getActief().verhoogScore(100);
										Team.getActief().maakKaart();
										Team.getActief().beginKaart(false);
										Team.getActief().maakBallenBak();
										if (!finale) {
											Team.wissel();
										}	
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
			int testBal = Main.pakBal();
			if (testBal == BallenBak.GROEN) {
				ballen--;
			} else if ((testBal == BallenBak.ROOD) ||
					   (testBal == BallenBak.GOUD)) 	{
				break;
			} else if (testBal == 1) {
				return true;
			}
		}
		return false;
	}
}
