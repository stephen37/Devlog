package labyrinthe;

public class Labyrinthe {

	protected int cases_x;
	protected int cases_y;
	protected String[][] tab_cases = { {}, {} };
	protected String nom;

	public Labyrinthe(String n, int x, int y, String[][] tab) {
		super();
		this.nom = n;
		this.cases_x = x;
		this.cases_y = y;
		this.tab_cases = tab;
	}

	public int Taille() {
		return tab_cases.length;
	}

	public int getX() {
		return cases_x;
	}

	public int getY() {
		return cases_y;
	}

	public String getName() {
		return nom;
	}

	public String toBase() {
		String s = "";
		for (String[] etat_x : tab_cases) {
			for (String etat_y : etat_x)
				s += etat_y + " ";
		}
		return nom + "\t" + cases_x + "\t" + cases_y + "\t" + s;
	}
}
