package personnages;

import java.awt.Component;

import javax.swing.JLabel;

import labyrinthe.InterfaceEditeur;
import labyrinthe.Labyrinthe;
import labyrinthe.Salle;
/**
 * @author Loesch & Batifol 
 *
 */
public abstract class Personnage implements Runnable {
	protected int vie;
	protected int force;
	protected String nom;
	protected String race;
	protected int vitesseMouvement;
	String inclinaison;
	String arme;
	String armure;
	double attaque;
	double defense;
	Salle[][] tabSalles = InterfaceEditeur.getTabSalle();
	Labyrinthe laby = new Labyrinthe(tabSalles);
	String[] tabMouvements = { "haut", "bas", "gauche", "droite" };
	String[] sac;
	int tailleSac;
	int tempsMouvement;
	int protectionEau = 0;
	int protectionSombre = 0;
	boolean present; // Indique si le personnage est pr�sent dans le labyrinthe
						// ou non

	/**
	 * @param nom
	 * @param race
	 * @param force
	 * @param vitesseMouvement
	 */
	public Personnage(String nom, String race, int force, int vitesseMouvement,
			String inclinaison, String arme, String armure) {
		super();
		this.vie = 100;
		this.force = force;
		this.race = race;
		this.nom = nom;
		this.vitesseMouvement = vitesseMouvement;
		this.inclinaison = inclinaison;
		this.arme = arme;
		this.armure = armure;
		this.tempsMouvement = 11 - vitesseMouvement;
		tailleSac = 2 + (int) (Math.random() * ((4 - 2) + 1));
		sac = new String[tailleSac];
		for (int i = 0; i < tailleSac; i++) {
			sac[i] = "empty";
		}
		present = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	// ///////////////////////////////////////////////////// METHODES
	// ///////////////////////////////////////////////////////

	public abstract JLabel getLabel();

	public abstract void definirAttaque();

	public abstract void definirDefense();

	/**
	 * @param s
	 * @return
	 * 
	 *         V�rifie que l'objet en param�tre est pr�sent dans le sac
	 */
	public boolean verifierObjet(String s) {
		for (String objet : sac) {
			if (s.equalsIgnoreCase(objet)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public int emplacementLibreduSac() {
		int i = 0;
		for (String objet : sac) {
			if (objet.equalsIgnoreCase("empty")) {
				return i;
			}
			i++;
		}
		return 10;
	}

	public void retirerObjetSac(String s) {
		for (int i = 0; i < sac.length; i++) {
			if (s.equalsIgnoreCase(sac[i])) {
				sac[i] = "empty";
			}
		}
	}

	public boolean equals(Object o) {
		Personnage p = (Personnage) o;
		return ((this.nom.equals(p.getNom())));
	}

	/**
	 * A la suite d'un combat perdu, cette m�thode bloque le personnage dans la
	 * salle o� il a �t� vaincu
	 */
	public void combatPerdu() {
		if (verifierObjet("potion") == false) {
			try {
				Thread.sleep(1000 * tempsMouvement * 3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			retirerObjetSac("potion");
		}

	}

	public String toString() {
		return "Nom : " + nom + " race : " + race + " force : " + force
				+ " vitesse : " + vitesseMouvement + " inclinaison : "
				+ inclinaison + " arme : " + arme + " armure : " + armure
				+ " sac : " + tailleSac;
	}

	public abstract String toBase();

	public abstract Component setImage();

	// TODO : Faire un random sur les positions et faire avancer le personnage
	// en fonction.
	@Override
	public void run() {

	}

	// Permet de comparer deux personnages.
	public boolean contains(Personnage selected) {
		Personnage perso2 = new Personnage(nom, race, force, vitesseMouvement,
				inclinaison, arme, armure) {

			@Override
			public String toBase() {
				return null;
			}

			@Override
			public Component setImage() {
				return null;
			}

			@Override
			public void definirAttaque() {
				return;
			}

			@Override
			public void definirDefense() {

			}

			@Override
			public JLabel getLabel() {
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

	// ///////////////////////////////////////////////////// GETTERS ET SETTERS
	// ///////////////////////////////////////////////////////
	// Ces m�thodes �tant explicites, elles ne seront pas comment�es
	// individuellement

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

	public int getTempsMouvement() {
		return tempsMouvement;
	}

	public void setTempsMouvement(int i) {
		this.tempsMouvement = i;
	}

	public String getRace() {
		return race;
	}

	public String getInclinaison() {
		return inclinaison;
	}

	public String getArmure() {
		return armure;
	}

	public String getArme() {
		return arme;
	}

	public double getAttaque() {
		return attaque;
	}

	public double getDefense() {
		return defense;
	}

	public String[] getSac() {
		return sac;
	}

	public void setObjetdansSac(int i, String s) {
		sac[i] = s;
	}

	public int getTailleSac() {
		return tailleSac;
	}

	public boolean getPresent() {
		return present;
	}

	public int getProtectionEau() {
		return protectionEau;
	}

	public void setProtectionEau(int i) {
		protectionEau = i;
	}

	public void setProtectionSombre(int i) {
		protectionSombre = i;
	}

	public int getProtectionSombre() {
		return protectionSombre;
	}

	public void setPresent(boolean p) {
		present = p;
	}

}