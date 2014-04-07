package labyrinthe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ListeLabyrinthes implements Serializable {

	// private static ArrayList<Labyrinthe> labyrinthes;
	static ArrayList<Labyrinthe> labyrinthes = new ArrayList<Labyrinthe>();
	InterfaceEditeur ie;

	public ListeLabyrinthes() {
		labyrinthes = new ArrayList<Labyrinthe>();
	}

	public void addToFile(ArrayList<Labyrinthe> labyrinthes, File file)
			throws Exception {

		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
		for (Labyrinthe l : labyrinthes) {
			buffer.write(l.toBase() + "\n");
		}
		buffer.close();
	}

	public void chargerLabyrinthe(String file_name) throws Exception {
		ie = new InterfaceEditeur();
		FileReader file = new FileReader(file_name);
		BufferedReader bf = new BufferedReader(file);

		for (Salle[] tabCase : ie.getTabSalle()) {
			for (Salle cases : tabCase) {
				System.out.println("Case : " + cases.etat);
				ie.EtablirLabyrinthe(cases.x, cases.y);
			}
		}
//		bf.close();
	}

//	public Labyrinthe createLabyrintheFromLine(String ligne) {
//
//		String[] tab_ligne = ligne.split("\n");
//		Case[][] tab_cases = new Case[6][tab_ligne.length - 1];
//		for (int i = 0; i < tab_ligne.length; i++) {
//			String[] tab = ligne.split(" ");
//			int x = Integer.parseInt(tab[0]);
//			int y = Integer.parseInt(tab[1]);
//			String etat = tab[2];
//			int period = Integer.parseInt(tab[3]);
//			int proba = Integer.parseInt(tab[4]);
//			int time = Integer.parseInt(tab[5]);
//			Case c = new Case(x, y, etat, period, proba, time);
//			tab_cases[x][y] = c;
//		}
//		Labyrinthe laby = new Labyrinthe(tab_cases);
//		return laby;
//	}

	public void addToFile(Labyrinthe laby, File file) throws Exception {
		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
		buffer.write(laby.toString());
		buffer.close();
	}

	public static boolean ajouterLabyrinthe(Labyrinthe laby) {
		return labyrinthes.add(laby);
	}

	public ArrayList<Labyrinthe> getLabyrinthes() {
		return new ArrayList<Labyrinthe>(ListeLabyrinthes.labyrinthes);
	}
}