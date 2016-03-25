package com.YCP.lingo;

import java.util.ArrayList;
import java.util.Collections;

import com.YCP.lingo.Exception.OntoevoegbareBalException;

public class BallenBak {
	public static final int GOUD = -1;
	public static final int ROOD = -2;
	public static final int GROEN = -3;
	public static final int VRAAGTEKEN = -4;
		
	private ArrayList<Integer> ballenInBak;
	
	// Constructor voor finale
	BallenBak(boolean even) {
		this.ballenInBak = new ArrayList<Integer>(36);
		for (int i = 1; i < 36; i++)
			if (even) {
				this.ballenInBak.add(2*i);
			}
		this.ballenInBak.add(GOUD);
		Collections.shuffle(ballenInBak);
	}
	// Constructor voor gewone ronde
	BallenBak(Kaart kaart, int aantalGroene) {
		this.ballenInBak.addAll(kaart.getGetallen());
		this.ballenInBak.add(VRAAGTEKEN);
		for (int i = 0; i < 3; i++) {
			this.ballenInBak.add(ROOD);
			if (i < aantalGroene) {
				this.ballenInBak.add(GROEN);
			}
		}
		Collections.shuffle(ballenInBak);
	}
	
	// Methode om 1 bal uit de bak te trekken
	int trekBal() {
		return ballenInBak.remove(0);
	}
	
	// Methode om losse ballen toe te voegen, bedoeld voor groene ballen en de gouden bal.
	void legBalTerug(int kleur) {
		if (kleur == GROEN) {
			for (int i = 0; i < 3; i++) {
				ballenInBak.add(GROEN);
			}
		} else if (kleur == GOUD) {
			ballenInBak.add(GOUD);
		} else {
			throw new OntoevoegbareBalException("Deze bal kun je niet toevoegen aan de bak!");
		}
		Collections.shuffle(ballenInBak);
	}
}