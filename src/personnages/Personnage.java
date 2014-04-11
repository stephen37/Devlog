package personnages;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import labyrinthe.InterfaceEditeur;
import labyrinthe.Labyrinthe;
import labyrinthe.Salle;

/**
 * @author stephen BATIFOL L2 MI
 */

public abstract class Personnage implements Runnable {
	protected int vie;
	protected int force;
	protected String nom;
	protected String race;
	protected int vitesseMouvement;
	Salle[][] tabSalles = InterfaceEditeur.getTabSalle();
	Labyrinthe laby = new Labyrinthe(tabSalles);
	String[] tabMouvements = { "haut", "bas", "gauche", "droite" };

	/**
	 * @param nom
	 * @param race
	 * @param force
	 * @param vitesseMouvement
	 */
	public Personnage(String nom, String race, int force, int vitesseMouvement) {
		super();
		this.vie = 100;
		this.force = force;
		this.race = race;
		this.nom = nom;
		this.vitesseMouvement = vitesseMouvement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		Personnage p = (Personnage) o;
		return ((this.nom.equals(p.getNom())));
	}

	public String toString() {
		return "Nom : " + nom + " race : " + race + " force : " + force
				+ " vitesse : " + vitesseMouvement;
	}

	public int getVie() {
		return vie;
	}

	public int getForce() {
		return force;
	}

	public String getNom() {
		return nom;
	}

	public int getVitesse() {
		return vitesseMouvement;
	}

	public String getRace() {
		return race;
	}

	public abstract String toBase();

	public abstract Component setImage();

	// TODO : Faire un random sur les positions et faire avancer le personnage
	// en fonction.
	@Override
	public void run() {
		System.out.println("Lancement de run");
		int rand = (int) Math.random() * (5 - 1) + 1;
		if (InterfaceEditeur.tab[laby.getX()][laby.getY()].getEtat().equals(
				"exit")) {
			return;
		} else {
			switch (tabMouvements[rand]) {
			case "haut":
				InterfaceEditeur.tab[laby.getX()][laby.getY() + 1]
						.add(new JLabel(new ImageIcon("./images/p-elf.png")));
				break;
			case "droite":
				InterfaceEditeur.tab[laby.getX() + 1][laby.getY()]
						.add(new JLabel(new ImageIcon("./images/p-elf.png")));
				break;
			case "gauche":
				InterfaceEditeur.tab[laby.getX() - 1][laby.getY()]
						.add(new JLabel(new ImageIcon("./images/p-elf.png")));
				break;
			case "bas":
				InterfaceEditeur.tab[laby.getX()][laby.getY() - 1]
						.add(new JLabel(new ImageIcon("./images/p-elf.png")));
				break;
			default: 
				break;
			}

		}
	}

	// Permet de comparer deux personnages.
	public boolean contains(Personnage selected) {
		Personnage perso2 = new Personnage(nom, race, force, vitesseMouvement) {

			@Override
			public String toBase() {
				return null;
			}

			@Override
			public Component setImage() {
				return null;
			}
		};
		if (perso2.nom.equals(selected.nom)
				&& perso2.race.equals(selected.race)
				&& perso2.force == selected.force
				&& perso2.vitesseMouvement == selected.vitesseMouvement) {
			return true;
		}
		return false;
	}
}