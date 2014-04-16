package personnages;

import java.awt.Component;

import labyrinthe.InterfaceEditeur;
import labyrinthe.Labyrinthe;
import labyrinthe.Salle;

/**
 * @author stephen BATIFOL L2 MI
 */

public abstract class Personnage  {
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
	int tailleSac;
	int tempsMouvement = 11 - vitesseMouvement;
	Salle salle;

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
		tailleSac = 2 + (int)(Math.random() * ((4 - 2) + 1));
		salle = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	
/////////////////////////////////////////////////////// METHODES ///////////////////////////////////////////////////////
	
	public abstract void definirAttaque();
	
	public abstract void definirDefense();
	
	
	public boolean equals(Object o) {
		Personnage p = (Personnage) o;
		return ((this.nom.equals(p.getNom())));
	}
	
	/**
	 * A la suite d'un combat perdu, cette m�thode bloque le personnage dans la salle o� il a �t� vaincu
	 */
	public void combatPerdu() {
		try {
			Thread.sleep(1000 * tempsMouvement * 3);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
		};
		if (perso2.nom.equals(selected.nom)
				&& perso2.race.equals(selected.race)
				&& perso2.force == selected.force
				&& perso2.vitesseMouvement == selected.vitesseMouvement) {
			return true;
		}
		return false;
	}
	
/////////////////////////////////////////////////////// GETTERS ///////////////////////////////////////////////////////
	// Ces m�thodes �tant explicites, elles ne seront pas comment�es individuellement
	
	

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

	public int getTailleSac() {
		return tailleSac;
	}

	
}