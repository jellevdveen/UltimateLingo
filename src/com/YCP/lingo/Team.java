package com.YCP.lingo;

import com.YCP.lingo.Exception.TeamMakenException;


public class Team {
	// Ik definieer beide spelende teams als static variabelen, zodat ik makkelijk van actief team kan wisselen 
	// en kan zorgen dat er maximaal 2 teams gemaakt worden
	private static Team actieveTeam;
	private static Team team1;
	private static Team team2;
	private static boolean teamsGemaakt;
	
	private String naam;
	private Kaart teamKaart;
	private BallenBak teamBak;
	private int score;
	private int groeneBallen;
	
	// private constructor, hoeft maar 2 keer aangeroepen te worden, door functie startTeams
	private Team(String naam) {
		this.naam = naam;
	}
	
	
	// Static methods om teams te starten, van team te wisselen, het actieve team te returnen
	public static void startTeams(String naam1, String naam2) {
		if (!teamsGemaakt) {
			Team.team1 = new Team(naam1);
			Team.team2 = new Team(naam2);
			Team.actieveTeam = Team.team1;
			teamsGemaakt = true;
		} else {
			throw new TeamMakenException("Teams zijn al gemaakt, kan niet nog eens gedaan worden!");
		}
	}
	
	public static void wissel(){
		if(Team.actieveTeam == Team.team1) {
			Team.actieveTeam = Team.team2;
		} else {
			Team.actieveTeam = Team.team1;
		}
	}
	
	public static Team getActief(){
		return Team.actieveTeam;
	}
	
	
	// adders
	public int verhoogScore(int hoeveelheid) {
		this.score += hoeveelheid;
		return this.score;
	}
	
	public boolean verhoogGroen() {
		groeneBallen++;
		if (groeneBallen == 3) {
			groeneBallen = 0;
			return true;
		} else {
			return false;
		}
	}
	
	
	// makers voor Kaart en Ballenbak
	public void maakKaart(boolean even) {
		this.teamKaart = new Kaart(even);
	}
	
	public void beginKaart(boolean finale) {
		this.teamKaart.streepBeginWeg(finale);
	}
	
	public void maakBallenBak() {
		this.teamBak = new BallenBak(this.teamKaart, (3 - this.groeneBallen));
	}
	
	
	// Methode om een bal te laten trekken en terug te laten leggen
	public int trekBal() {
		int x = this.teamBak.trekBal();
		if (x > 0) {
			if (!this.teamKaart.streepWeg(x)) {
				return Kaart.NIET_WEGGESTREEPT;
			}
		}
		return x;
	}
	
	public void legBalTerug(int kleur) {
		this.teamBak.legBalTerug(kleur);
	}
	
	
	// Method om lingo te checken
	
	
	// getters
	public String getNaam() {
		return this.naam;
	}
	
	
}
