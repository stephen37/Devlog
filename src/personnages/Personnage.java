package personnages;
import java.awt.Image;

/**
 * @author stephen BATIFOL L2 MI
 *
 */

public abstract class Personnage {
	protected int vie;
	protected int force;
	protected String nom;
	protected String race;
	protected int vitesseMouvement;
	
	/**
	 * @param nom
	 * @param race
	 * @param force
	 * @param vitesseMouvement
	 */
	public Personnage(String nom,String race, int force, int vitesseMouvement) {
		super();
		this.vie = 100;
		this.force = force;
		this.race = race;
		this.nom = nom;
		this.vitesseMouvement = vitesseMouvement;
	}

	public boolean equals(Object o) {
		Personnage p = (Personnage) o;
		return ((this.nom.equals(p.getNom())));
	}

	public String toString() {
		return "Nom : " + nom + " vie : " + vie + " force : " + force;
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
	
	public int getVitesse () {
		return vitesseMouvement;
	}
	

	public abstract void setImage();
	
	public abstract String toBase();

}