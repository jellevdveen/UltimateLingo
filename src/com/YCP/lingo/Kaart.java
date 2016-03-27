package com.YCP.lingo;

import java.util.ArrayList;
import java.util.Collections;

public class Kaart {
	private int[][] getallenKaart;
	
	// maakt nieuwe kaart
	Kaart(boolean even) {
		getallenKaart = new int[5][5];
		
		// begin met toevoegen van 2 voor een even kaart en 1 voor een oneven kaart
		int toevoegGetal = (even ? 2 : 1);
		
		ArrayList<Integer> getallenOpKaart = new ArrayList<Integer>(25);
				
		while (getallenOpKaart.size() < 25) {
			getallenOpKaart.add(toevoegGetal);
			toevoegGetal += 2;
		}
		Collections.shuffle(getallenOpKaart);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				getallenKaart[i][j] = getallenOpKaart.remove(0);
			}
		}
	}
	
	// streept een aantal getallen weg om mee te beginnen
	Kaart streepBeginWeg (boolean finale) {
		if (finale) {
			getallenKaart[0][0] = getallenKaart[0][2] = getallenKaart[0][4] = getallenKaart[1][1] =
			getallenKaart[1][2] = getallenKaart[1][3] = getallenKaart[2][0] = getallenKaart[2][1] = 
			getallenKaart[2][3] = getallenKaart[2][4] = getallenKaart[3][1] = getallenKaart[3][2] =
			getallenKaart[3][3] = getallenKaart[4][0] = getallenKaart[4][2] = getallenKaart[4][4] = 0;
		} else {
			getallenKaart[0][4] = getallenKaart[1][0] = getallenKaart[1][2] = getallenKaart[2][3] = 
			getallenKaart[3][0] = getallenKaart[3][1] = getallenKaart[4][1] = getallenKaart[4][4] = 0;
		}
		return this;
	}
	
	// geeft alle zichtbare getallen op kaart terug, om een ballenbak mee te vullen
	ArrayList<Integer> getGetallen() {
		ArrayList<Integer> returnLijst = new ArrayList<Integer>(17);
		for(int[] row : getallenKaart) {
			for(int i: row) {
				returnLijst.add(i);
			}
		}
		return returnLijst;
	}

	// streept een getal weg, returnt true bij succes
	boolean streepWeg(int getal) {
		for (int[] row : this.getallenKaart) {
			for(int i = 0; i < row.length; i++) {
				if (row[i] == getal) {
					row[i] = 0;
					return true;
				}
			}
		}
		return false;
	}

	// checkt voor lingo
	boolean checkLingo() {
		for(int j = 0; j < 5; j++) {
			if (((getallenKaart[0][j] + getallenKaart[1][j] + getallenKaart[2][j] + getallenKaart[3][j] + getallenKaart[4][j]) == 0) ||
				((getallenKaart[j][0] + getallenKaart[j][1] + getallenKaart[j][2] + getallenKaart[j][3] + getallenKaart[j][4]) == 0) ||
				((getallenKaart[0][0] + getallenKaart[1][1] + getallenKaart[2][2] + getallenKaart[3][3] + getallenKaart[4][4]) == 0) ||
				((getallenKaart[0][4] + getallenKaart[1][3] + getallenKaart[2][2] + getallenKaart[3][1] + getallenKaart[4][0]) == 0)) {
				return true;
			}
		}
		return false;
	}
	
	// geeft visueel fijne weergave van Kaart
	@Override
	public String toString() {
		StringBuilder tempString = new StringBuilder("");
		for (int i = 0; i < 5; i++) {
			tempString.append("                                              ");
			for (int j = 0; j < 5; j++) {
				if (getallenKaart[i][j] == 0) {
					tempString.append("XX ");
				} else if (getallenKaart[i][j] < 10) {
					tempString.append("0" + getallenKaart[i][j] + " ");
				} else {
					tempString.append(getallenKaart[i][j] + " ");
				}
			}
			tempString.append("\n");
		}
		
		return tempString.toString();
		
	}

}
