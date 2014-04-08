package personnages;

import labyrinthe.InterfaceEditeur;

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
//	InterfaceEditeur ie = new InterfaceEditeur();
	
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		Personnage p = (Personnage) o;
		return ((this.nom.equals(p.getNom())));
	}

	public String toString() {
		return "Nom : " + nom + " race : " +race + " force : " + force + " vitesse : " +vitesseMouvement;
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
	
	public String getRace() {
		return race;
	}
	
	public abstract String toBase();
	
	public abstract void setImage();
	
//	@Override
//	public void run() {
//		Salle[][] tab = ie.getTabSalle();
//		tab[0][0].add(new JLabel(new ImageIcon("./images/p-elf.png")));
//	}

	
	//Permet de comparer deux personnages.
	public boolean contains(Personnage selected) {
		Personnage perso2 = new Personnage(nom, race, force, vitesseMouvement) {
			
			@Override
			public String toBase() {return null;}
			@Override
			public void setImage() {}
		};
		if (perso2.nom.equals(selected.nom) && perso2.race.equals(selected.race) && perso2.force == selected.force && perso2.vitesseMouvement == selected.vitesseMouvement) {
			return true;
		}
		return false;
	}
}