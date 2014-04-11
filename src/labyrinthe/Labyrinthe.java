package labyrinthe; 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Labyrinthe implements Serializable {

	protected int x;
	protected int y;
	protected Salle[][] tab_cases;
	protected String nom;
	protected String etat = "normal";
	ArrayList<Labyrinthe> labyrinthes = new ArrayList<Labyrinthe>();

	public Labyrinthe(Salle[][] tab) {
		super();
		tab_cases = tab;
		
	}
	/**
	 * Renvoie la taille du tableau contenant les cases
	 * 
	 * @return int
	 */
	public int getTaille() {
		return tab_cases.length;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return nom;
	}
	

	public String getEtat() {
		return etat;
	}
	
	public ArrayList<Labyrinthe> getLabyrinthe() {
		return new ArrayList<Labyrinthe>(this.labyrinthes);
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

	 public Salle createSalleFromLine(String ligne) {

		  String[] tab = ligne.split(" ");
		  x = Integer.parseInt(tab[0]);
		  y = Integer.parseInt(tab[1]);
		  etat = tab[2];
		  double period = Double.parseDouble(tab[3]);
		  int proba = Integer.parseInt(tab[4]);
		  double time = Double.parseDouble(tab[5]);
		  Salle salle = new Salle(x, y, etat, period, proba, time);	  

		  return salle;
	 }
	 
	 

	public void addToFile(Labyrinthe laby, File file) throws Exception {
		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
		buffer.write(laby.toString());
		buffer.close();
	}

	/**
	 * Permet d'Ã©crire dans l'arrayList avec un format de sortie spÃ©cifique.
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