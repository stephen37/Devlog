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

	static ArrayList<Labyrinthe> labyrinthes = new ArrayList<Labyrinthe>();

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
		InterfaceEditeur ie = new InterfaceEditeur();
		FileReader file = new FileReader(file_name);
		BufferedReader bf = new BufferedReader(file);

		for (Salle[] tabCase : ie.getTabSalle()) {
			for (Salle cases : tabCase) {
				System.out.println("Case : " + cases.etat);
				ie.EtablirLabyrinthe(cases.x, cases.y);
			}
		}
		bf.close();
	}


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