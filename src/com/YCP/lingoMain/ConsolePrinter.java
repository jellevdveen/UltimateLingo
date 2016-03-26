// De enige class die op de console printen is deze
// slechts RaadWoorden en Main roepen deze class aan
// muv Team voor als het vraagteken wordt gepakt



package com.YCP.lingoMain;


public class ConsolePrinter {

	
	public static void print(String input, int wait) {
		System.out.println(input);
		ConsolePrinter.pause(wait);
	}
	
	public static void printSingle(String input, int wait) {
		System.out.print(input);
		ConsolePrinter.pause(wait);
	}
	
	public static void printRight(String input, int wait) {
		for (int i = 0; i < (50-input.length()); i++) {
			System.out.print(" ");
		}
		print(input, wait);
	}
	
	
	
	public static void pause(int wait) {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException IE) {
			System.out.println("Onbekende Error");
		}
	}
}
