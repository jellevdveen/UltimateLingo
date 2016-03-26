package com.YCP.lingoWoorden;

import com.YCP.lingo.Team;
import com.YCP.lingoMain.ConsolePrinter;
import com.YCP.lingoMain.InputOutput;
import com.YCP.lingoMain.Main;

public class RaadWoorden {
	private static final int GOED 		= 1;
	private static final int VERKEERD	= 2;
	private static final int ONGELDIG 	= 3;
	
	private static final int AANTAL_KEER_RADEN = 5;
	
	private String woord;
	private StringBuilder bekendWoord;
	
	public RaadWoorden(String woord) {
		this.woord = woord;
		bekendWoord = new StringBuilder(String.valueOf(woord.toUpperCase().charAt(0)));
		for (int i = 1; i < this.woord.length(); i++) {
			bekendWoord.append(".");
		}
	}
	
	public boolean speelRonde() {
		for (int gok = 0; gok < RaadWoorden.AANTAL_KEER_RADEN; gok++) {
			switch (raadWoord()) {
			case RaadWoorden.GOED		:	ConsolePrinter.print("En dat is goed!", Main.STANDARD_WAIT/2);
											return true;
			case RaadWoorden.VERKEERD	:	continue;
			case RaadWoorden.ONGELDIG	:	if (gok < AANTAL_KEER_RADEN - 1) {
												ConsolePrinter.print("Het andere team is aan de beurt", 0);
												Team.wissel();
												if (bonusLetter()){
													ConsolePrinter.print("en krijgt een bonusletter!", 0);
												}
												ConsolePrinter.print("", 0);
											}
											continue;
			}
		}
		ConsolePrinter.print("Het andere team is aan de beurt", 0);
		Team.wissel();
		
		if (raadWoord() == GOED) {
			ConsolePrinter.print("En dat is goed!", Main.STANDARD_WAIT/2);
			return true;
		} else {
			return false;
		}
	}

	public int speelFinaleRonde() {
		int counter = 0;
		for (int gok = 0; gok < RaadWoorden.AANTAL_KEER_RADEN; gok++) {
			counter++;
			switch (raadWoord()) {
			case RaadWoorden.GOED		:	ConsolePrinter.print("Woord goed geraden!", Main.STANDARD_WAIT/2);
											return counter;
			case RaadWoorden.VERKEERD	:	
			case RaadWoorden.ONGELDIG	:	continue;
			}
		}
		return RaadWoorden.AANTAL_KEER_RADEN + 1;
	}
	
	private int raadWoord() {
		ConsolePrinter.print((Team.getActief().getNaam() + ", raad een woord van " + this.woord.length() + " letters!"), 0);
		ConsolePrinter.printRight(bekendWoord.toString(), 0);
		String geradenWoord = InputOutput.userInput();
		if (geradenWoord.equalsIgnoreCase(this.woord)) {
			return RaadWoorden.GOED;
		} else if (geradenWoord.length() != this.woord.length()) {
			ConsolePrinter.print("Geen " + this.woord.length() +  "-letterig woord!", 0);
			return ONGELDIG;
		}  else if (!testWoord(geradenWoord)) {
			ConsolePrinter.print("Woord bevat ongeldige karakters!", 0);
			return ONGELDIG;
		} else if (Character.toLowerCase(this.woord.charAt(0)) != Character.toLowerCase(geradenWoord.charAt(0))) {
			ConsolePrinter.print("Ongeldige beginletter!", 0);
			return ONGELDIG;
		} else {
			char[] testWord = (evalueerWoord(geradenWoord));
			for (int j = 0; j < this.woord.length(); j++) {
				ConsolePrinter.printSingle(String.valueOf(testWord[j]), (Main.STANDARD_WAIT/2));
				if (j == (this.woord.length() - 1)) {
					System.out.println("\n");
				}
			}
		}
		return VERKEERD;
	}
	
	private char[] evalueerWoord(String geradenWoord) {
		char[] iWoord = this.woord.toLowerCase().toCharArray();
		char[] gWoord = geradenWoord.toLowerCase().toCharArray();
		char[] returnWoord = new char[this.woord.length()];
		
		for (int i = 0; i < this.woord.length(); i++) {
			returnWoord[i] = '.';
			if (iWoord[i] == gWoord[i]) {
				this.bekendWoord.replace(i, i+1, String.valueOf(gWoord[i]).toUpperCase());
				returnWoord[i] = 'x' ;
				iWoord[i] = '-';
				gWoord[i] = '_';		
			}
		}
		
		for (int i = 0; i < this.woord.length(); i++) {
			for (int j = 0; j < this.woord.length(); j++) {
				if (iWoord[i] == gWoord[j]) {
					returnWoord[j] = 'o';
					gWoord[j] = '_';
					iWoord[i] = '-';
				}
			}
		}
		return returnWoord;
	}

	private boolean testWoord(String Woord) {
		char[] test = Woord.toLowerCase().toCharArray();
		for (char c : test) {
			if(!((c >= 'a') && (c <= 'z'))) {
				return false;
			}
		}
		return true;	
	}

	private boolean bonusLetter() {
		int aantalOnbekend = 0;
		int eersteOnbekend = 0;
		for (int i = 0; i < this.woord.length(); i++) {
			if(this.bekendWoord.charAt(i) == '.') {
				aantalOnbekend++;
				if (eersteOnbekend == 0) {
					eersteOnbekend = i + 1;
				}
			}
		}
				
		if(aantalOnbekend > 1) {
			this.bekendWoord.replace(eersteOnbekend - 1, eersteOnbekend, String.valueOf(this.woord.charAt(eersteOnbekend - 1)).toUpperCase());
			return true;
		}
		return false;
	}
}
