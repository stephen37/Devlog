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
	ArrayList<Labyrinthe> labyrinthes;

	public Labyrinthe(Salle[][] tab) {
		super();
		tab_cases = tab;
		// init();
	}

	
	// public void init() {
	// InterfaceEditeur.panel_labyrinthe.setLayout(new GridBagLayout());
	// InterfaceEditeur.panel_labyrinthe.setPreferredSize(new Dimension(300,
	// 300));
	// InterfaceEditeur.panel_labyrinthe.removeAll();
	// InterfaceEditeur.panel_labyrinthe.repaint();
	//
	// GridBagConstraints gbc = new GridBagConstraints();
	// gbc.gridheight = 1;
	// gbc.gridwidth = 1;
	// gbc.anchor = GridBagConstraints.CENTER;
	// labyrinthes = new ArrayList<Labyrinthe>();
	// for (int i = 0; i < tab_cases.length; i++) {
	// for (int j = 0; j < tab_cases[i].length; j++) {
	// gbc.gridx = i;
	// gbc.gridy = j;
	// // panel_case.setBorder(door);
	// InterfaceEditeur.panel_labyrinthe.add(tab_cases[i][j].panel_case, gbc);
	// }
	// }
	// InterfaceEditeur.content_pane.add(InterfaceEditeur.panel_labyrinthe,
	// BorderLayout.CENTER);
	// }

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
//Ancienne version
//	public Labyrinthe createLabyrintheFromLine(String ligne) {
//		String[] tab = ligne.split(" ");
//		Salle[][] tab_cases = new Salle[6][tab.length + 1];
//		x = Integer.parseInt(tab[0]);
//		y = Integer.parseInt(tab[1]);
//		etat = tab[2];
////		System.out.println("Etat " +etat);
//		double period = Double.parseDouble(tab[3]);
//		int proba = Integer.parseInt(tab[4]);
//		double time = Double.parseDouble(tab[5]);
//		Salle c = new Salle(x, y, etat, period, proba, time);
//		tab_cases[x][y] = c;
//		Labyrinthe laby = new Labyrinthe(tab_cases);
//
//		return laby;
//	}
	
	 public Salle createSalleFromLine(String ligne) {

		  String[] tab = ligne.split(" ");
		  x = Integer.parseInt(tab[0]);
		  y = Integer.parseInt(tab[1]);
		  etat = tab[2];
		  double period = Double.parseDouble(tab[3]);
		  int proba = Integer.parseInt(tab[4]);
		  double time = Double.parseDouble(tab[5]);
		  Salle c = new Salle(x, y, etat, period, proba, time);	  

		  return c;
		  //Labyrinthe laby = new Labyrinthe(tab_cases);

		  //return laby;
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