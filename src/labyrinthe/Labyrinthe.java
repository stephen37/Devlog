package labyrinthe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;

public class Labyrinthe implements Serializable {

	// protected int cases_x;
	// protected int cases_y;
	protected Salle[][] tab_cases;;
	protected String nom;

	public Labyrinthe(Salle[][] tab) {
		super();
		// this.nom = n;
		// this.cases_x = x;
		// this.cases_y = y;
		this.tab_cases = tab;
	}

	/**
	 * Renvoie la taille du tableau contenant les cases
	 * 
	 * @return int
	 */
	public int Taille() {
		return tab_cases.length;
	}

	// public int getX(){
	// return cases_x;
	// }
	//
	// public int getY(){
	// return cases_y;
	// }

	public String getName() {
		return nom;
	}

	public String toString() {
		String res = "";
		for (int i = 0; i < tab_cases.length; i++) {
			for (int j = 0; j < tab_cases[i].length; j++) {
				res += "" + tab_cases[i][j].x + " " + tab_cases[i][j].y + " "
						+ tab_cases[i][j].etat + " " + tab_cases[i][j].period
						+ " " + tab_cases[i][j].proba + " "
						+ tab_cases[i][j].time + "\n";
			}
		}
		return res;
	}

	public Labyrinthe createLabyrintheFromLine(String ligne) {
		String[] tab = ligne.split(" ");
		Salle[][] tab_cases = new Salle[6][tab.length];
		for (int i = 0; i < tab_cases.length; i++) {
			int x = Integer.parseInt(tab[0]);
			int y = Integer.parseInt(tab[1]);
			String etat = tab[2];
			double period = Double.parseDouble(tab[3]);
			int proba = Integer.parseInt(tab[4]);
			double time = Double.parseDouble(tab[5]);
			Salle c = new Salle(x, y, etat, period, proba, time);
			tab_cases[x][y] = c;
			System.out.println("Valeur de x " + x);
			System.out.println("valeur de y " + y);
			System.out.println("Valeur de etat " + etat);
			System.out.println("Valeur de period " + period);
			System.out.println("Valeur de proba " + proba);
			System.out.println("Valeur de time " + time);
		}
		
		Labyrinthe laby = new Labyrinthe(tab_cases);
		return laby;
	}
//Ancienne version 
	// public Labyrinthe createLabyrintheFromLine(String ligne) {
	// String[] tab_ligne = ligne.split("\n");
	// Salle[][] tab_cases = new Salle[6][tab_ligne.length];
	// for (int i = 0; i < tab_ligne.length; i++) {
	// System.out.println("Taille du tableau dans la boucle " +
	// tab_ligne.length);
	// System.out.println("Valeur de l'élément"+i+ "du tableau dans la boucle "
	// +tab_ligne[i]);
	// String[] tab = tab_ligne[i].split(" ");
	// int x = Integer.parseInt(tab[0]);
	// int y = Integer.parseInt(tab[1]);
	// String etat = tab[2];
	// double period = Double.parseDouble(tab[3]);
	// int proba = Integer.parseInt(tab[4]);
	// double time = Double.parseDouble(tab[5]);
	// Salle c = new Salle(x, y, etat, period, proba, time);
	// tab_cases[x][y] = c;
	// System.out.println("Valeur de x " + x);
	// System.out.println("valeur de y " + y);
	// }
	//
	// Labyrinthe laby = new Labyrinthe(tab_cases);
	// return laby;
	// }

	public void addToFile(Labyrinthe laby, File file) throws Exception {
		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
		buffer.write(laby.toString());
		buffer.close();
	}

	/**
	 * Permet d'écrire dans l'arrayList avec un format de sortie spécifique.
	 * 
	 * @return String
	 */
	public String toBase() {
		String res = "";
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 1; j++) {
				res += " " + tab_cases.length + "x" + tab_cases[i].length;
			}
		}
		return res;
	}

}