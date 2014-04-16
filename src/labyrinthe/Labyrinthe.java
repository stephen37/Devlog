package labyrinthe; 

import gestionnaire.gui.GestionnaireUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class Labyrinthe implements Serializable {

	protected int x;
	protected int y;
	protected Salle[][] tab_cases;
	protected String nom;
	protected String etat = "normal";
	protected String objet = "empty";
	ArrayList<Labyrinthe> labyrinthes = new ArrayList<Labyrinthe>();
	ArrayList<Salle> listSalle = new ArrayList<Salle>();
	File fileSelected;

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
		return nom + " " + (x+1) + "x" + (y+1);
	}

	 public Salle createSalleFromLine(String ligne) {

		  String[] tab = ligne.split(" ");
		  x = Integer.parseInt(tab[0]);
		  y = Integer.parseInt(tab[1]);
		  etat = tab[2];
		  double period = Double.parseDouble(tab[3]);
		  int proba = Integer.parseInt(tab[4]);
		  double time = Double.parseDouble(tab[5]);
		  objet = tab[6];
		  Salle salle = new Salle(x, y, etat, period, proba, time, objet);	  

		  return salle;
	 }
	 
	 

	public void addToFile(Labyrinthe laby, File file) throws Exception {
		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
		buffer.write(laby.toBase());
		buffer.close();
	}
	
	public void charger() {
		JFileChooser filechooser = new JFileChooser(".");
		if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				fileSelected = filechooser.getSelectedFile();
				BufferedReader br = new BufferedReader(new FileReader(
						fileSelected));
				String line;
				while ((line = br.readLine()) != null) {
					listSalle.add(createSalleFromLine(line));
				}

				nom = fileSelected.getName();
				int x = listSalle.get(listSalle.size() -1).GetX();
				int y = listSalle.get(listSalle.size() -1).GetY();
				Salle[][] tab = new Salle[x+1][y+1];
				int cpt = 0;
				for (int i = 0; i < x+1; i++) {
					for (int j = 0; j <  y+1; j++) {
						tab[i][j] = listSalle.get(cpt);
						cpt++;
						//System.out.print(tab[i][j].etat + " ");
					}
					System.out.println();
				}
				tab_cases = tab;
				br.close();
			} catch (Exception ex) {
				Logger.getLogger(GestionnaireUI.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * Permet d'Ã©crire dans l'arrayList avec un format de sortie spÃ©cifique.
	 * 
	 * @return String
	 */
	
	public String toBase() {
		String res = "";
		for (int i = 0; i < tab_cases.length; i++) {
			for (int j = 0; j < tab_cases[i].length; j++) {
				res += "" + tab_cases[i][j].x + " " + tab_cases[i][j].y + " "
						+ tab_cases[i][j].etat + " " + tab_cases[i][j].period
						+ " " + tab_cases[i][j].proba + " "
						+ tab_cases[i][j].time + " " + tab_cases[i][j].objet + "\n";
			}
		}
		
		return res;
	}
//	public String toBase() {
//		String res = "";
//		for (int i = 0; i < 1; i++) {
//			for (int j = 0; j < 1; j++) {
//				res += " " + tab_cases.length + "x" + tab_cases[i].length;
//			}
//		}
//		return res;
//	}

}