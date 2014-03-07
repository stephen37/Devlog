package personnage;

public class Hero extends Personnage {

	private String arme = "";

	public Hero(int vie, int force, String nom, String arme) {
		super(vie, force, nom);
		this.arme = arme;
	}

	public String getArme() {
		return arme;
	}

	@Override
	public String toString() {
		return "Hero: " + super.toString() + " arme : " + arme;
	}

	@Override
	public String toBase() {
		return "Hero\t" + nom + "\t" + vie + "\t" + force + "\t" + arme;
	}
}
