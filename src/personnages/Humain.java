package personnages;
import java.awt.Image;

public class Humain extends Personnage {

	protected Image image;

	public Humain(String nom,String race,  int force, int vitesseMouvement) {
		super(nom, "Humain", force, vitesseMouvement);
	}

	/* (non-Javadoc)
	 * @see personnages.Personnage#toBase()
	 */
	@Override
	public String toBase() {
		return "Humain\t" + nom + "\t" + vie + "\t" + force
				+ "\t" + vitesseMouvement;
	}

	public int GetVItesseMouvement() {
		return vitesseMouvement;
	}

}
