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
	
	/**
	 * @param vie
	 * @param force
	 * @param nom
	 */
	public Personnage(int vie, int force, String nom) {
		super();
		this.vie = vie;
		this.force = force;
		this.nom = nom;
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
	

	public abstract void setImage();
	
	public abstract String toBase();

}